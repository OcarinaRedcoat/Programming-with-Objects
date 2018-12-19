package sth.core;

//TODO see Person.java
//FIXME id static counter wtv

import sth.core.exception.*;

import java.util.Collections;
import java.util.Comparator;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private Course _course;
    private List<Discipline> _disciplineLst;
    private boolean _isRepresentative;

    protected Student(int id, int phoneNumber, String name, boolean isRep) {
        super(id, phoneNumber, name);
        _isRepresentative = isRep;
        _disciplineLst = new ArrayList<>();
    }

    protected void setRepresentative() {
        if (!_isRepresentative) {
            _isRepresentative = true;
        } else {
            _isRepresentative = false;
        }
    }

    protected boolean getRepresentative() {
        return _isRepresentative;
    }


    protected String getJob(){
        if (_isRepresentative){
            return "DELEGADO";
        }else{
            return "ALUNO";
        }
    }

    protected String getDescription(){
        String str = "";
        Collections.sort(_disciplineLst, new Comparator<Discipline>() {
            public int compare(Discipline d1, Discipline d2) {
                return d1.getName().compareTo(d2.getName());
            }
        });

        for (Discipline d: _disciplineLst){
            str += "\n* " + d.getCourse().getName() + " - " + d.getName();
        }
        //str += "\n";
        return str;
    }

    public boolean equals(Object o) {
        if (o instanceof Student) {
            Student p = (Student) o;
            return getId() == p.getId();
        }
        return false;
    }

    @Override
    void parseContext(String lineContext, School school) throws BadEntryException {
        String components[] =  lineContext.split("\\|");

        if (components.length != 2)
            throw new BadEntryException("Invalid line context " + lineContext);

        if (_course == null) {
            _course = school.parseCourse(components[0]);
            _course.addStudent(this);
        }

        Discipline dis = _course.parseDiscipline(components[1]);

        dis.enrollStudent(this);
    }

    protected void enrrol(Discipline d) {
        if (_disciplineLst.size() < 7) {
            _disciplineLst.add(d);
        }
    }

    protected void submitProject(String discipline, String project, String mess) throws NoSuchDisciplineIdException, NoSuchProjectIdException {
        for (Discipline d: _disciplineLst){
            if (d.getName().equals(discipline)){
                d.submitProjectStudent(this, project, mess);
                return;
            }
        }
        throw new NoSuchDisciplineIdException(discipline);
    }

    protected void createSurvey(String discipline, String project) throws NoSuchDisciplineIdException, NoSuchProjectIdException, DuplicateSurveyIdException {
        _course.createSurvey(discipline, project);

    }

    protected void cancelSurvey(String discipline, String project) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoSurveyIdException, SurveyFinishedIdException, NonEmptySurveyIdException{
        _course.cancelSurvey(discipline, project);
    }

    protected void openSurvey(String discipline, String project) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoSurveyIdException, OpeningSurveyIdException{
        _course.openSurvey(discipline, project);
    }

    protected void closeSurvey(String discipline, String project) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoSurveyIdException, ClosingSurveyIdException{
        _course.closeSurvey(discipline, project);
    }

    protected void finaliseSurvey(String discipline, String project) throws NoSuchProjectIdException, NoSuchDisciplineIdException, NoSurveyIdException, FinishingSurveyIdException{
        _course.finaliseSurvey(discipline, project);
    }


    protected void doSurvey(Student std, String discipline, String project, int hours, String comment) throws NoSuchProjectIdException, NoSuchDisciplineIdException, NoSurveyIdException{
        for (Discipline d: _disciplineLst){
            if (d.getName().equals(discipline)){
                d.doSurvey(std, project, hours, comment);
                return;
            }
        }
        throw new NoSuchDisciplineIdException(discipline);
    }

    protected String showSurveyResultsStudent(String discipline, String project) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoSurveyIdException{
        for (Discipline d : _disciplineLst) {
            if (d.getName().equals(discipline)) {
                return d.showSurveyResultsStudent(project);
            }
        }
        throw new NoSuchDisciplineIdException(discipline);
    }

    protected List<String> showSurveysDiscipline(String discipline) {
        List<String> lst = _course.showSurveysDiscipline(discipline);
        return lst;
    }
    
}
