package com.maestros.wisomnursingclass.model.test;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Test_dto {
    @SerializedName("data")
    @Expose
    private List<Question_dto> data = null;
    @SerializedName("Questions_count")
    @Expose
    private Integer questionsCount;

    public List<Question_dto> getData() {
        return data;
    }

    public void setData(List<Question_dto> data) {
        this.data = data;
    }

    public Integer getQuestionsCount() {
        return questionsCount;
    }

    public void setQuestionsCount(Integer questionsCount) {
        this.questionsCount = questionsCount;
    }
}
