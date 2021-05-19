package com.maestros.wisomnursingclass.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Purchase_dto {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("strtotime")
    @Expose
    private String strtotime;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("Course_name")
    @Expose
    private String courseName;
    @SerializedName("Subject_Name")
    @Expose
    private String subjectName;
    @SerializedName("Class_name")
    @Expose
    private String className;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("curent_date")
    @Expose
    private String curentDate;
    @SerializedName("Path")
    @Expose
    private String Path;

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStrtotime() {
        return strtotime;
    }

    public void setStrtotime(String strtotime) {
        this.strtotime = strtotime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCurentDate() {
        return curentDate;
    }

    public void setCurentDate(String curentDate) {
        this.curentDate = curentDate;
    }

}
