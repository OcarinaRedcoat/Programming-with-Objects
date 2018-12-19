package sth.core;

import sth.core.exception.*;

import java.io.Serializable;
import java.util.*;

public class Discipline implements Serializable, InterfaceObservable {

    private static final long serialVersionUID = 201810051538L;
    private String _name;
    private int _maxCapacity;
    private Course _course;
    private List<Teacher> _teacherLst;
    private List<Student> _studentLst;
    private List<Project> _projectLst;

    protected Discipline(String name, Course cour) {
        _name = name;
        _course = cour;
        _maxCapacity = Integer.MAX_VALUE;
        _teacherLst = new ArrayList<>();
        _studentLst = new ArrayList<>();
        _projectLst = new ArrayList<>();
    }

    protected String getName() {
        return _name;
    }

    protected void addTeacher(Teacher t) {
        _teacherLst.add(t);
    }

    protected void enrollStudent(Student s) {
        if (_studentLst.size() < _maxCapacity) {
            _studentLst.add(s);
        }
        s.enrrol(this);
    }

    protected boolean createProject(int id ,String name) throws NoSuchDisciplineIdException, DuplicateProjectIdException {
        for (Project p: _projectLst){
            if(p.getName().equals(name)){
                throw new DuplicateProjectIdException(name);
            }
        }
        for (Teacher t: _teacherLst){
            if (t.getId() == id){
                if (!t.teacherContainsDiscipline(this._name)){
                    throw new NoSuchDisciplineIdException(this._name);
                }
                else {
                    Project p = new Project(this, name);
                    _projectLst.add(p);
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean close(int id, String name) throws NoSuchProjectIdException, NoSuchDisciplineIdException, OpeningSurveyIdException{
        for (Teacher t: _teacherLst){
            if (t.getId() == id){
                if (!t.teacherContainsDiscipline(this._name)){
                    throw new NoSuchDisciplineIdException(this._name);
                }
            }
        }
        for (Project p: _projectLst){
            if (p.getName().equals(name)){
                p.closed();
                return true;
            }
        }
        throw new NoSuchProjectIdException(name);
    }

    protected List<String> showAllStudents(){
        List<String> lst = new ArrayList<>();
        Collections.sort(_studentLst, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getId() - o2.getId();
            }
        });
        for (Student s: this._studentLst) {
            lst.add(s.toString());
        }
        return lst;
    }

    protected Course getCourse(){
        return _course;
    }


    protected String showSubmissions(String project) throws NoSuchProjectIdException{
        for (Project p: _projectLst){
            if (p.getName().equals(project)){
                 String lst = p.toString();
                return lst;
            }
        }
        throw new NoSuchProjectIdException(project);
    }

    protected void submitProjectStudent(Student std, String project, String mess) throws NoSuchProjectIdException{
        for (Project p: _projectLst){
            if (p.getName().equals(project)){
                p.submit(std, mess);
                return;
            }
        }
        throw new NoSuchProjectIdException(project);
    }

    protected void createSurvey(String project) throws NoSuchProjectIdException, DuplicateSurveyIdException {
        for (Project p: _projectLst){
            if (p.getName().equals(project) && !p.getStatus()){
                p.createSurvey();
                return;
            }
        }
        throw new NoSuchProjectIdException(project);
    }

    protected void cancelSurvey(String project) throws NoSuchProjectIdException, NoSurveyIdException, SurveyFinishedIdException, NonEmptySurveyIdException{
        for (Project p: _projectLst){
            if (p.getName().equals(project)){
                p.cancelSurvey();
                return;
            }
        }
        throw new NoSuchProjectIdException(project);
    }

    protected void openSurvey(String project) throws NoSuchProjectIdException, NoSurveyIdException, OpeningSurveyIdException{
        for (Project p: _projectLst){
            if (p.getName().equals(project)){
                p.openSurvey();
                return;
            }
        }
        throw new NoSuchProjectIdException(project);
    }

    protected void closeSurvey(String project) throws NoSuchProjectIdException, NoSurveyIdException, ClosingSurveyIdException{
        for (Project p: _projectLst){
            if (p.getName().equals(project)){
                p.closeSurvey();
                return;
            }
        }
        throw new NoSuchProjectIdException(project);
    }

    protected void finaliseSurvey(String project) throws NoSuchProjectIdException, NoSurveyIdException, FinishingSurveyIdException{
        for (Project p: _projectLst){
            if (p.getName().equals(project)){
                p.finaliseSurvey();
                return;
            }
        }
        throw new NoSuchProjectIdException(project);
    }

    protected void doSurvey(Student std, String project, int hours, String comment) throws NoSurveyIdException, NoSuchProjectIdException{
        for (Project p: _projectLst){
            if (p.getName().equals(project)){
                p.fillSurvey(std, hours, comment);
                return;
            }
        }
        throw new NoSuchProjectIdException(project);
    }

    protected String showSurveyResultsStudent(String project) throws NoSuchProjectIdException, NoSurveyIdException{
        for (Project p: _projectLst){
            if (p.getName().equals(project)){
                return p.showSurveyResultsStudent();
            }
        }
        throw new NoSuchProjectIdException(project);
    }

    protected String showSurveyResultsTeacher(String project) throws NoSuchProjectIdException, NoSurveyIdException{
        for (Project p: _projectLst){
            if (p.getName().equals(project)){
                return p.showSurveyResultsTeacher();
            }
        }
        throw new NoSuchProjectIdException(project);
    }

    protected List<String> showSurveysDiscipline(){
        List<String> lst = new ArrayList<>();
        _projectLst.sort(new Comparator<Project>() {
            @Override
            public int compare(Project o1, Project o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        for (Project p: _projectLst){
            lst.add(p.showSurveysDiscipline());
        }
        return lst;
    }

    public void notifyAll(Notification note){
        for (Teacher t: _teacherLst){
            t.notifyAllPerson(note, this);
        }
    }

    protected boolean hasStudent(Student student){
        for (Student std: _studentLst){
            if (std.equals(student)){
                return true;
            }
        }
        return false;
    }
}
