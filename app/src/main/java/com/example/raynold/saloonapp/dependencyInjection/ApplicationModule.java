package com.example.raynold.saloonapp.dependencyInjection;

import android.app.Application;

import com.example.raynold.saloonapp.Model.Lumo;
import dagger.Module;
import dagger.Provides;

/**
 * Created by RAYNOLD on 9/20/2017.
 */
@Module
public class ApplicationModule {

    private final Lumo application;
    public ApplicationModule(Lumo application) {
        this.application = application;
    }

    @Provides
    Lumo provideRoomDemoApplication(){
        return application;
    }

    @Provides
    Application provideApplication(){
        return application;
    }
}
