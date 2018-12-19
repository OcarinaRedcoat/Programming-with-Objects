package sth.core;

import java.io.Serializable;

public class Notification implements Serializable, InterfaceObserver {

    private static final long serialVersionUID = 201810051538L;
    private String _message;

    protected Notification(String message){
        _message = message;
    }

    public String notifyString(){
        return _message;
    }

}
