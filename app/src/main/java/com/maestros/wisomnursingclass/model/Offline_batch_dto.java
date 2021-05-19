package com.maestros.wisomnursingclass.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offline_batch_dto {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("text_name")
    @Expose
    private String textName;
    @SerializedName("class_name")
    @Expose
    private String className;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("Path")
    @Expose
    private String path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTextName() {
        return textName;
    }

    public void setTextName(String textName) {
        this.textName = textName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}
