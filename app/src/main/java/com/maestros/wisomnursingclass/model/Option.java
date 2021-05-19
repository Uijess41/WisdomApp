package com.maestros.wisomnursingclass.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option {

@SerializedName("id")
@Expose
private String id;
@SerializedName("question_id")
@Expose
private String questionId;
@SerializedName("options")
@Expose
private String options;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getQuestionId() {
return questionId;
}

public void setQuestionId(String questionId) {
this.questionId = questionId;
}

public String getOptions() {
return options;
}

public void setOptions(String options) {
this.options = options;
}

}