package com.example.raynold.saloonapp.model;

/**
 * Created by RAYNOLD on 12/14/2017.
 */

public class Regimen {

    private String hairName;
    private String detail;
    private String style;
    private String moisturize;
    private String cleanse;
    private String condition;
    private int imageRes;

    public Regimen(String hairName, String detail, String style, String moisturize, String cleanse, String condition, int imageRes) {
        this.hairName = hairName;
        this.detail = detail;
        this.style = style;
        this.moisturize = moisturize;
        this.cleanse = cleanse;
        this.condition = condition;
        this.imageRes = imageRes;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public String getHairName() {
        return hairName;
    }

    public void setHairName(String hairName) {
        this.hairName = hairName;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getMoisturize() {
        return moisturize;
    }

    public void setMoisturize(String moisturize) {
        this.moisturize = moisturize;
    }

    public String getCleanse() {
        return cleanse;
    }

    public void setCleanse(String cleanse) {
        this.cleanse = cleanse;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
