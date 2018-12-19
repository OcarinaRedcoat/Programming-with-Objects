package sth.core;


import java.io.Serializable;

public class Answer implements Serializable {
    private static final long serialVersionUID = 201810051538L;
    private int _numberHours;
    private String _customMessage;

    protected Answer(int hours, String message){
        _numberHours = hours;
        _customMessage = message;
    }

    protected int getHours(){
        return _numberHours;
    }

    protected String getCustomMessage(){
        return _customMessage;
    }
}
