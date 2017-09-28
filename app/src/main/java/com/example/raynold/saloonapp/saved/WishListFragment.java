package com.example.raynold.saloonapp.saved;


import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.transition.Fade;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.raynold.saloonapp.Activity.ProductDetailActivity;
import com.example.raynold.saloonapp.Model.Lumo;
import com.example.raynold.saloonapp.R;
import com.example.raynold.saloonapp.data.WishListModel;
import com.example.raynold.saloonapp.detail.WishListDetailActivity;
import com.example.raynold.saloonapp.viewmodel.SavedItemCollectionViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WishListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WishListFragment extends LifecycleFragment {


    private static final String EXTRA_ITEM_ID = "EXTRA_ITEM_ID";
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    SavedItemCollectionViewModel listItemCollectionViewModel;

    private List<WishListModel> listOfData;
    private LayoutInflater mLayoutInflater;
    private RecyclerView mRecyclerView;
    private SaveWishListAdapter mWishListAdapter;



    public WishListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WishListFragment.
     */
    public static WishListFragment newInstance() {

        return new WishListFragment();
    }

            /*------------------------------- Lifecycle -------------------------------*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((Lumo) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listItemCollectionViewModel =
                ViewModelProviders.of(this,viewModelFactory).get(SavedItemCollectionViewModel.class);
        listItemCollectionViewModel.getListItems().observe(this, new Observer<List<WishListModel>>() {
            @Override
            public void onChanged(@Nullable List<WishListModel> wishListModels) {

                if (WishListFragment.this.listOfData == null) {
                    setListData(wishListModels);
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_wish_list, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.wish_list_recyclerview);
        mLayoutInflater = getActivity().getLayoutInflater();





        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void startDetailActivity(String itemId, View viewRoot) {
        Activity container = getActivity();
        Intent i = new Intent(container, WishListDetailActivity.class);
        i.putExtra(EXTRA_ITEM_ID, itemId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            container.getWindow().setEnterTransition(new Fade(Fade.IN));
            container.getWindow().setEnterTransition(new Fade(Fade.OUT));

            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(container,
                            new Pair<View, String>(viewRoot.findViewById(R.id.wishlist_image),
                                    getString(R.string.transition_drawable)),
                            new Pair<View, String>(viewRoot.findViewById(R.id.wishlist_product_name),
                                    getString(R.string.transition_message)),
                            new Pair<View, String>(viewRoot.findViewById(R.id.wishlist_product_btn),
                                    getString(R.string.transition_price)),
                            new Pair<View, String>(viewRoot.findViewById(R.id.wishlist_price),
                                    getString(R.string.transition_button)));

            startActivity(i, options.toBundle());

        } else {
            startActivity(i);
        }
    }

    public void setListData(List<WishListModel> wishListModel) {
        listOfData = wishListModel;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mWishListAdapter = new SaveWishListAdapter();
        mRecyclerView.setAdapter(mWishListAdapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                mRecyclerView.getContext(),
                layoutManager.getOrientation()
        );

        mRecyclerView.addItemDecoration(itemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }


    /*-------------------- RecyclerView Boilerplate ----------------------*/

    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            //not used, as the first parameter above is 0
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                listItemCollectionViewModel.deleteListItem(
                        listOfData.get(position)
                );

                //ensure View is consistent with underlying data
                listOfData.remove(position);
                mWishListAdapter.notifyItemRemoved(position);


            }
        };
        return simpleItemTouchCallback;
    }

    class SaveWishListAdapter extends RecyclerView.Adapter<SaveWishListAdapter.SavedWishListViewHolder> {


        @Override
        public SavedWishListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = mLayoutInflater.inflate(R.layout.wishlist_list_item,parent,false);
            return new SavedWishListViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final SavedWishListViewHolder holder, int position) {

            final WishListModel wishListModel = listOfData.get(position);

            holder.wishlistName.setText(wishListModel.getName());
            holder.wishlistPrice.setText(wishListModel.getPrice());
            Picasso.with(getContext()).load(R.drawable.no_image_placeholder).into(holder.wishlistImage);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toasty.success(getContext(), "Clicked " + wishListModel.getName(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(holder.itemView.getContext(), WishListDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", wishListModel.getName());
                    bundle.putString("price", wishListModel.getPrice());
                    bundle.putString("image", wishListModel.getImage());
                    bundle.putString("detail",wishListModel.getDetail());
                    bundle.putInt("saved", wishListModel.getSaved());
                    bundle.putString("location", wishListModel.getLocation());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return listOfData.size();
        }

        class SavedWishListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView wishlistName;
            private TextView wishlistPrice;
            private Button wishlistBtn;
            private ImageView wishlistImage;


            public SavedWishListViewHolder(View itemView) {
                super(itemView);

                wishlistName = (TextView) itemView.findViewById(R.id.wishlist_product_name);
                wishlistPrice = (TextView) itemView.findViewById(R.id.wishlist_price);
                wishlistBtn = (Button) itemView.findViewById(R.id.wishlist_btn);
                wishlistImage = (ImageView) itemView.findViewById(R.id.wishlist_image);


            }

            @Override
            public void onClick(View v) {
                WishListModel listModel = listOfData.get(this.getAdapterPosition());

                startDetailActivity(listModel.getItemId(), v);
            }
        }
    }
}
