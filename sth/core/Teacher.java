package sth.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import sth.core.exception.BadEntryException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSurveyIdException;

public class Teacher extends Person {

    private List<Discipline> _disciplineLst;
    protected Teacher(int id, int phoneNumber, String name) {
        super(id, phoneNumber, name);
        _disciplineLst = new ArrayList<>();
    }

    protected String getJob(){
        return "DOCENTE";
    }

    protected String getDescription(){
        String str = "";
        Collections.sort(_disciplineLst, new Comparator<Discipline>() {
            public int compare(Discipline d1, Discipline d2) {
                return d1.getName().compareTo(d2.getName());
            }
        });
        Collections.sort(_disciplineLst, new Comparator<Discipline>() {
            public int compare(Discipline d1, Discipline d2) {
                return d1.getCourse().getName().compareTo(d2.getCourse().getName());
            }
        });
        for (Discipline d: _disciplineLst){
            str += "\n* " + d.getCourse().getName() + " - " + d.getName();
        }
        return str;
    }


    public boolean equals(Object o) {
        if (o instanceof Teacher) {
            Teacher p = (Teacher) o;
            return getId() == p.getId();
        }
        return false;
    }

    protected List<String> showDisciplineStudents(String discipline) throws NoSuchDisciplineIdException{
        boolean doesItExist = false;
        List<String> lst = new ArrayList<>();
        for (Discipline d: _disciplineLst) {
            if (d.getName().equals(discipline)) {
                lst = (d.showAllStudents());
                doesItExist = true;
            }
        }
        if (doesItExist == false){
            throw new NoSuchDisciplineIdException(discipline);
        }
        return lst;
    }

    @Override
    void parseContext(String lineContext, School school) throws BadEntryException {
        String components[] =  lineContext.split("\\|");

        if (components.length != 2)
            throw new BadEntryException("Invalid line context " + lineContext);

        Course course = school.parseCourse(components[0]);
        Discipline discipline = course.parseDiscipline(components[1]);

        discipline.addTeacher(this);
        _disciplineLst.add(discipline);
    }

    protected boolean teacherContainsDiscipline(String name) {
        for (Discipline d : _disciplineLst) {
            if (d.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    protected String showSurveyResultsTeacher(String discipline, String project) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoSurveyIdException {
        for (Discipline d : _disciplineLst) {
            if (d.getName().equals(discipline)) {
                return d.showSurveyResultsTeacher(project);
            }
        }
        throw new NoSuchDisciplineIdException(discipline);
    }

    protected String showSubmission(String discipline, String project) throws NoSuchDisciplineIdException, NoSuchProjectIdException{
        for (Discipline d: _disciplineLst){
            if (d.getName().equals(discipline)){
                return d.showSubmissions(project);
            }
        }
        throw new NoSuchDisciplineIdException(discipline);
    }
}

