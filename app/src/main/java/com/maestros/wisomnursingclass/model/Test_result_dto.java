package com.maestros.wisomnursingclass.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Test_result_dto {

    @SerializedName("Your_Marks")
    @Expose
    private String yourMarks;
    @SerializedName("Right_answer")
    @Expose
    private Integer rightAnswer;
    @SerializedName("Wrong_answer")
    @Expose
    private Integer wrongAnswer;

    public String getYourMarks() {
        return yourMarks;
    }

    public void setYourMarks(String yourMarks) {
        this.yourMarks = yourMarks;
    }

    public Integer getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(Integer rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public Integer getWrongAnswer() {
        return wrongAnswer;
    }

    public void setWrongAnswer(Integer wrongAnswer) {
        this.wrongAnswer = wrongAnswer;
    }

}
