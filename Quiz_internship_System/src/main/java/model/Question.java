package model;

import java.util.*;

public class Question {

    private int question_id;
    private String question_text;
    private String type;
    private int marks;

    private List<Option> options;

    public int getQuestion_id() { return question_id; }
    public void setQuestion_id(int question_id) { this.question_id = question_id; }

    public String getQuestion_text() { return question_text; }
    public void setQuestion_text(String question_text) { this.question_text = question_text; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getMarks() { return marks; }
    public void setMarks(int marks) { this.marks = marks; }

    public List<Option> getOptions() { return options; }
    public void setOptions(List<Option> options) { this.options = options; }
}