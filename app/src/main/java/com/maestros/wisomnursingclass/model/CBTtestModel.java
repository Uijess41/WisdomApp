package com.maestros.wisomnursingclass.model;

import java.util.List;

public class CBTtestModel {


    private String id;
    private String marks;
    private String Question;
    private String answer;
    private List<CBTOptions> cbtOptionsList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<CBTOptions> getCbtOptionsList() {
        return cbtOptionsList;
    }

    public void setCbtOptionsList(List<CBTOptions> cbtOptionsList) {
        this.cbtOptionsList = cbtOptionsList;
    }

    public static class CBTOptions {

        private String id;
        private String question_id;
        private String options;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuestion_id() {
            return question_id;
        }

        public void setQuestion_id(String question_id) {
            this.question_id = question_id;
        }

        public String getOptions() {
            return options;
        }

        public void setOptions(String options) {
            this.options = options;
        }
    }
}
