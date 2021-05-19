package com.maestros.wisomnursingclass.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Course_content_dto {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("content_name")
    @Expose
    private String contentName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }


}
