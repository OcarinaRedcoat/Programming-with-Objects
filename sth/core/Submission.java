package sth.core;

import java.io.Serializable;

public class Submission implements Serializable {

    private static final long serialVersionUID = 201810051538L;
    private String _message;
    private Student _std;
    private boolean _submitedSurvey;

    protected Submission(Student s, String mess) {
        _message = mess;
        _std = s;
        _submitedSurvey = false;
    }

    protected int getStudentId(){
        return _std.getId();
    }

    protected String getSubmissionMess(){
        return _message;
    }

    public String toString(){
        return "\n* " + getStudentId() + " - " + getSubmissionMess();
    }

    protected void didSurvey(){
        _submitedSurvey = true;
    }

    protected boolean checkSurvey(){
        return _submitedSurvey;
    }
}