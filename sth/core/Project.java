package sth.core;

import sth.core.exception.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Comparator;

public class Project implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201810051538L;

    private String _name;
    private String _description;
    private boolean _closed;
    private Survey _survey;
    private List<Submission> _subLst;
    private Discipline _discipline;

    protected Project(Discipline discipline, String name) {
        _name = name;
        _description = "Forza Ferrrari";
        _closed = false;
        _survey = null;
        _subLst = new ArrayList<>();
        _discipline = discipline;
    }

    protected String getName() {
        return _name;
    }

    protected String getDisciplineName(){
        return _discipline.getName();
    }

    protected void closed() throws OpeningSurveyIdException{
        _closed = true;
        if (_survey != null){
            _survey.open();
        }
    }

    protected boolean getStatus() {
        return _closed;
    }

    protected void submit(Student st, String mess) throws NoSuchProjectIdException {
        if (!_closed) {

            Iterator<Submission> iter = _subLst.iterator();
            while (iter.hasNext()){
                if(iter.next().getStudentId() == st.getId()){
                    iter.remove();
                }
            }
            Submission sub = new Submission(st, mess);
            _subLst.add(sub);
        } else {
            throw new NoSuchProjectIdException(_name);
        }
    }

    protected int numberSubmissions(){
        return _subLst.size();
    }

    protected Survey getSurvey() {
        return _survey;
    }

    public String toString() {
        String str;
        str = (_discipline.getName() + " - " + _name);
        _subLst.sort(new Comparator<Submission>() {
            @Override
            public int compare(Submission o1, Submission o2) {
                return o1.getStudentId() - o2.getStudentId();
            }
        });
        for (Submission s : _subLst) {
            str += s.toString();
        }
        return str;
    }

    protected boolean createSurvey() throws DuplicateSurveyIdException {
        if (_survey == null) {
            _survey = new Survey(this);
            return true;
        } else {
            throw new DuplicateSurveyIdException(this._name);
        }
    }

    protected void cancelSurvey() throws NoSurveyIdException, SurveyFinishedIdException, NonEmptySurveyIdException {
        if (_survey == null) {
            throw new NoSurveyIdException(this._name);
        } else {
            _survey.cancel();
        }
    }

    protected void openSurvey() throws NoSurveyIdException, OpeningSurveyIdException {
        if (_survey == null) {
            throw new NoSurveyIdException(this._name);
        } else {
            _survey.open();
        }
    }

    protected void closeSurvey() throws NoSurveyIdException, ClosingSurveyIdException {
        if (_survey == null) {
            throw new NoSurveyIdException(this._name);
        } else {
            _survey.close();
        }
    }

    protected void finaliseSurvey() throws NoSurveyIdException, FinishingSurveyIdException {
        if (_survey == null) {
            throw new NoSurveyIdException(this._name);
        } else {
            _survey.finalized();
        }
    }

    protected void fillSurvey(Student std, int hours, String mess) throws NoSurveyIdException, NoSuchProjectIdException{
        if (_survey == null){
            throw new NoSurveyIdException(this._name);
        }
        for (Submission s: _subLst){
            if (s.getStudentId() == std.getId()){
                if (!s.checkSurvey()) {
                    _survey.addAnswer(hours, mess);
                    s.didSurvey();
                }
                return;
            }
        }
        throw new NoSuchProjectIdException(_name);
    }

    protected String showSurveyResultsStudent() throws NoSurveyIdException{
        if (_survey == null){
            throw new NoSurveyIdException(this._name);
        } else {
            return _survey.getResultsStudent();
        }
    }

    protected String showSurveyResultsTeacher() throws NoSurveyIdException {
        if (_survey == null) {
            throw new NoSurveyIdException(this._name);
        } else {
            return _survey.getResultTeacher();
        }
    }

    protected String showSurveysDiscipline() {
        if (_survey == null){
            return "";
        } else {
            return _survey.getDisciplineResultRepresentative();
        }
    }

    protected void notifyDiscipline(Notification note){
        _discipline.notifyAll(note);
        _discipline.getCourse().notifyRep(note, _discipline);
    }

    protected void surveyWhipeOut(){
        _survey = null;
    }
}
