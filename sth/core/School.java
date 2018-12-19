package sth.core;
//FIXME import other classes if needed

import sth.core.exception.*;

import java.io.IOException;
import java.util.*;

/**
 * School implementation.
 */
public class School implements java.io.Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201810051538L;

    private final String _name;

    private List<Course> _courseLst;
    private Map<Integer, Person> _personsMap;
    // FIXME define object fields (attributes and, possibly, associations)

    protected School() {
        _name = "ISEL";
        _courseLst = new ArrayList<>();
        _personsMap = new TreeMap<>();
    }

    /**
     * @param filename
     * @throws BadEntryException
     * @throws IOException
     */
    void importFile(String filename) throws IOException, BadEntryException {
        Parser parser = new Parser(this);
        parser.parseFile(filename);
    }

    /**
     * @return _name
     */
    protected String getName() {
        return _name;
    }

    /**
     * @param p
     */
    protected void addPerson(Person p) {
        _personsMap.put(p.getId(), p);
    }

    /**
     * @param id
     * @return e
     */
    protected Person getPerson(int id) {
        for (Map.Entry<Integer, Person> e: _personsMap.entrySet()){
            if (e.getKey() == id){
                return e.getValue();
            }
        }
        return null;
    }

    /**
     * @param id
     * @return p
     */
    protected String showPerson(int id) throws NoSuchPersonIdException {
        Person p = getPerson(id);
        if (p == null) {
            throw new NoSuchPersonIdException(id);
        }

        return p.toString();
    }

    /**
     * @return allPeople
     */
    protected List<String> showPeople() {
        List<String> allPeople = new ArrayList<>();

        for (Map.Entry<Integer, Person> e: _personsMap.entrySet()){
            allPeople.add(e.getValue().toString());
        }

        return allPeople;
    }

    /**
     * @param course
     * @return c
     */
    Course parseCourse(String course) {
        for (Course c : _courseLst) {
            if (c.getName().equals(course)) {
                return c;
            }
        }

        Course c = new Course(course);
        _courseLst.add(c);
        return c;

    }

    /**
     * @param id
     * @param newNum
     * @throws NoSuchProjectIdException
     * @return p
     */
    protected String changeTelephoneNumber(int id, int newNum) throws NoSuchPersonIdException {
        Person p = getPerson(id);
        if (p == null) {
            throw new NoSuchPersonIdException(id);
        }
        p.changePhoneNumber(newNum);
        return p.toString();
    }

    /**
     * @param discipline
     * @param name
     */
    protected void createProjectDiscipline(int id, String discipline, String name) throws DuplicateProjectIdException, NoSuchDisciplineIdException {
        for (Course c : _courseLst) {
            if (c.createNewProject(id, discipline, name)) {
                return;
            }
        }
        throw new NoSuchDisciplineIdException(discipline);
    }

    /**
     * @param discipline
     * @param name
     */
    protected void closeProjectIfPossible(int id, String discipline, String name)
            throws NoSuchProjectIdException, NoSuchDisciplineIdException, OpeningSurveyIdException {
        for (Course c : _courseLst) {
            if (c.closeProj(id, discipline, name)) {
                return;
            }
        }
        throw new NoSuchDisciplineIdException(discipline);
    }

    /**
     * @param t
     * @param discipline
     * @return t
     * @return null
     */
    protected List<String> showStudentsD(Teacher t, String discipline) throws NoSuchDisciplineIdException {
        for (Map.Entry<Integer, Person> e: _personsMap.entrySet()){
            if (e.getValue() instanceof  Teacher && e.getValue().equals(t)){
                return t.showDisciplineStudents(discipline);
            }
        }

        return null;
    }

    /**
     * @param partOfName
     * @return peopleNames
     * @return null
     */
    protected String searchPerson(String partOfName) {
        String peopleNames = "";
        List<Person> auxLst = new ArrayList<>();

        for (Map.Entry<Integer, Person> e: _personsMap.entrySet()){
            if (e.getValue().getName().toLowerCase().contains(partOfName.toLowerCase())){
                auxLst.add(e.getValue());
            }
        }

        auxLst.sort(new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.getName().compareTo(p2.getName());
            }
        });
        for (Person p : auxLst) {
            peopleNames += p.toString();
            peopleNames += "\n";
        }

        if (peopleNames != null) {
            return peopleNames;
        } else {
            return null;
        }
    }

    /**
     * @param e
     * @return true
     * @return false
     */
    protected boolean checkEmployee(Person e) {
        for (Map.Entry<Integer, Person> p: _personsMap.entrySet()){
            if (p.getValue() instanceof  Employee && p.getValue().equals(e)){
                return true;
            }
        }

        return false;
    }

    /**
     * @param e
     * @return true
     * @return false
     */
    protected boolean checkTeacher(Person e) {
        for (Map.Entry<Integer, Person> p: _personsMap.entrySet()){
            if (p.getValue() instanceof  Teacher && p.getValue().equals(e)){
                return true;
            }
        }
        return false;
    }

    /**
     * @param e
     * @return true
     * @return false
     */
    protected boolean checkStudent(Person e) {
        for (Map.Entry<Integer, Person> p: _personsMap.entrySet()){
            if (p.getValue() instanceof  Student && p.getValue().equals(e)){
                return true;
            }
        }
        return false;
    }

    /**
     * @param e
     * @return true
     * @return false
     */
    protected boolean checkRepresentative(Person e) {
        for (Map.Entry<Integer, Person> p: _personsMap.entrySet()){
            if (p.getValue() instanceof  Student && p.getValue().equals(e)){
                if (((Student) p.getValue()).getRepresentative()){
                 return true;
                }
            }
        }
        return false;
    }

    protected String showSubmissionProject(int id, String discipline, String project)
            throws NoSuchProjectIdException, NoSuchDisciplineIdException {

        for (Map.Entry<Integer, Person> p: _personsMap.entrySet()){
            if (p.getValue() instanceof  Teacher && p.getKey() == id){
                return ((Teacher) p.getValue()).showSubmission(discipline, project);
            }
        }
        return null;
    }

    protected void submit(Student std, String discipline, String project, String mess)
            throws NoSuchProjectIdException, NoSuchDisciplineIdException {

        for (Map.Entry<Integer, Person> p: _personsMap.entrySet()){
            if (p.getValue() instanceof  Student && p.getValue().equals(std)){
                    ((Student) p.getValue()).submitProject(discipline, project, mess);
                    return;
                }
            }
        }

    protected void createSurvey(int id, String discipline, String project)
            throws NoSuchDisciplineIdException, NoSuchProjectIdException, DuplicateSurveyIdException {

        for (Map.Entry<Integer, Person> p: _personsMap.entrySet()){
            if (p.getValue() instanceof  Student && p.getKey() == id){
                ((Student) p.getValue()).createSurvey(discipline, project);
            }
        }
    }

    protected void cancelSurvey(int id, String discipline, String project) throws NoSuchDisciplineIdException,
            NoSuchProjectIdException, NoSurveyIdException, SurveyFinishedIdException, NonEmptySurveyIdException {

        for (Map.Entry<Integer, Person> p: _personsMap.entrySet()){
            if (p.getValue() instanceof  Student && p.getKey() == id){
                ((Student) p.getValue()).cancelSurvey(discipline, project);
            }
        }
    }

    protected void openSurvey(int id, String discipline, String project) throws NoSuchDisciplineIdException,
            NoSuchProjectIdException, NoSurveyIdException, OpeningSurveyIdException {

        for (Map.Entry<Integer, Person> p: _personsMap.entrySet()){
            if (p.getValue() instanceof  Student && p.getKey() == id){
                ((Student) p.getValue()).openSurvey(discipline, project);
            }
        }
    }

    protected void closeSurvey(int id, String discipline, String project) throws NoSuchDisciplineIdException,
            NoSuchProjectIdException, NoSurveyIdException, ClosingSurveyIdException {

        for (Map.Entry<Integer, Person> p: _personsMap.entrySet()){
            if (p.getValue() instanceof  Student && p.getKey() == id){
                ((Student) p.getValue()).closeSurvey(discipline, project);
            }
        }
    }

    protected void finaliseSurvey(int id, String discipline, String project) throws NoSuchProjectIdException,
            NoSuchDisciplineIdException, NoSurveyIdException, FinishingSurveyIdException {

        for (Map.Entry<Integer, Person> p: _personsMap.entrySet()){
            if (p.getValue() instanceof  Student && p.getKey() == id){
                ((Student) p.getValue()).finaliseSurvey(discipline, project);
            }
        }
    }

    protected void doSurvey(Student std, String discipline, String project, int hours, String comment) throws NoSuchDisciplineIdException, NoSurveyIdException, NoSuchProjectIdException{

        for (Map.Entry<Integer, Person> p: _personsMap.entrySet()){
            if (p.getValue() instanceof  Student && p.getValue().equals(std)){
                ((Student) p.getValue()).doSurvey(std, discipline, project, hours, comment);
            }
        }
    }

    protected String showSurveyResultsStudent(int id, String discipline, String project) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoSurveyIdException{

        for (Map.Entry<Integer, Person> p: _personsMap.entrySet()){
            if (p.getValue() instanceof  Student && p.getKey() == id){
                return ((Student) p.getValue()).showSurveyResultsStudent(discipline, project);
            }
        }
        return null;
    }

    protected String showSurveyResultsTeacher(int id, String discipline, String project) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoSurveyIdException{
        for (Map.Entry<Integer, Person> p: _personsMap.entrySet()){
            if (p.getValue() instanceof  Teacher && p.getKey() == id){
                return ((Teacher) p.getValue()).showSurveyResultsTeacher(discipline, project);
            }
        }
        return null;
    }

    protected List<String> showSurveysDiscipline(int id, String discipline)  {
        List<String> lst = new ArrayList<>();

        for (Map.Entry<Integer, Person> p: _personsMap.entrySet()){
            if (p.getValue() instanceof  Student && p.getKey() == id){
                lst = ((Student) p.getValue()).showSurveysDiscipline(discipline);
            }
        }
        return lst;
    }

}
