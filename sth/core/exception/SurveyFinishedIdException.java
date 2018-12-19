package sth.core.exception;

public class SurveyFinishedIdException extends Exception{

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201809021324L;

    /** Person id. */
    private String _id;

    /**
     * @param id
     */
    public SurveyFinishedIdException(String id) {
        _id = id;
    }

    /** @return id */
    public String getId() {
        return _id;
    }

}