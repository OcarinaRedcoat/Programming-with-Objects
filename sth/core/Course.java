package sth.core;

import sth.core.exception.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {
    private final String _name;
    private List<Student> _studentLst;
    private List<Discipline> _disciplineLst;
    private List<Student> _representativeLst;

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201810051538L;

    protected Course(String name) {
        _name = name;
        _studentLst = new ArrayList<>();
        _disciplineLst = new ArrayList<>();
        _representativeLst = new ArrayList<>();
    }

    protected String getName() {
        return _name;
    }

    protected void addStudent(Student s) {
        _studentLst.add(s);
    }

    protected void addDiscipline(Discipline d) {
        _disciplineLst.add(d);
    }

    Discipline parseDiscipline(String discipline) {
        for (Discipline d : _disciplineLst) {
            if (d.getName().equals(discipline)) {
                return d;
            }
        }
        Discipline d = new Discipline(discipline, this);
        addDiscipline(d);
        return d;
    }

    protected boolean createNewProject(int id, String discipline, String name) throws NoSuchDisciplineIdException, DuplicateProjectIdException {
        for (Discipline d : _disciplineLst) {
            if (d.getName().equals(discipline)) {
                if (d.createProject(id, name)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean closeProj(int id, String discipline, String name) throws NoSuchProjectIdException, NoSuchDisciplineIdException, OpeningSurveyIdException {
        for (Discipline d : _disciplineLst) {
            if (d.getName().equals(discipline)) {
                if (d.close(id, name)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected List<String> showSurveysDiscipline(String discipline){
        List<String> lst = new ArrayList<>();
        for (Discipline d: _disciplineLst){
            if (d.getName().equals(discipline)){
                lst = d.showSurveysDiscipline();
            }
        }
        return lst;
    }

    protected void createSurvey(String discipline, String project) throws NoSuchDisciplineIdException, NoSuchProjectIdException, DuplicateSurveyIdException {
        for (Discipline d: _disciplineLst){
            if (d.getName().equals(discipline)){
                d.createSurvey(project);
                return;
            }
        }
        throw new NoSuchDisciplineIdException(discipline);
    }

    protected void cancelSurvey(String discipline, String project) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoSurveyIdException, SurveyFinishedIdException, NonEmptySurveyIdException{
        for (Discipline d: _disciplineLst){
            if (d.getName().equals(discipline)){
                d.cancelSurvey(project);
                return;
            }
        }
        throw new NoSuchDisciplineIdException(discipline);
    }

    protected void openSurvey(String discipline, String project) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoSurveyIdException, OpeningSurveyIdException{
        for (Discipline d: _disciplineLst){
            if (d.getName().equals(discipline)){
                d.openSurvey(project);
                return;
            }
        }
        throw new NoSuchDisciplineIdException(discipline);
    }

    protected void closeSurvey(String discipline, String project) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoSurveyIdException, ClosingSurveyIdException{
        for (Discipline d: _disciplineLst){
            if (d.getName().equals(discipline)){
                d.closeSurvey(project);
                return;
            }
        }
        throw new NoSuchDisciplineIdException(discipline);
    }

    protected void finaliseSurvey(String discipline, String project) throws NoSuchProjectIdException, NoSuchDisciplineIdException, NoSurveyIdException, FinishingSurveyIdException{
        for (Discipline d: _disciplineLst){
            if (d.getName().equals(discipline)){
                d.finaliseSurvey(project);
                return;
            }
        }
        throw new NoSuchDisciplineIdException(discipline);
    }

    protected void notifyRep(Notification note, Discipline discipline){
        for (Student st: _studentLst){
            if (discipline.hasStudent(st) && !st.getRepresentative()){
                st.notifyAllPerson(note, discipline);
            }
            if (st.getRepresentative()) {
                st.notifyAllPerson(note, discipline);
            }
        }
    }
}