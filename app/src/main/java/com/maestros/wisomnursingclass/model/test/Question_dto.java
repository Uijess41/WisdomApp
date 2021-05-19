package com.maestros.wisomnursingclass.model.test;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.maestros.wisomnursingclass.model.Option;

import java.util.List;

public class Question_dto {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("Question")
    @Expose
    private String question;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("options")
    @Expose
    private List<Option> options = null;

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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

}
