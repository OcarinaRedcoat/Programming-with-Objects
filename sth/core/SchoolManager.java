package sth.core;

import sth.core.exception.*;

import java.io.*;
import java.util.List;

//FIXME import other classes if needed

/**
 * The façade class.
 */
public class SchoolManager implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201810051538L;
  private String _file;
  private School _school = new School();
  private Person _person;

  /**
   * @param datafile
   * @throws ImportFileException
   * @throws //InvalidCourseSelectionException
   */
  public void importFile(String datafile) throws ImportFileException {
    try{
      _school.importFile(datafile);
    } catch (IOException | BadEntryException e){
      throw new ImportFileException(e);
    }
  }

  public void newSaveAs(String filename) throws FileNotFoundException, IOException {

    if (filename == null){
      throw new FileNotFoundException();
    }
    _file = filename;

    try{
      ObjectOutputStream save = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_file)));
      save.writeObject(_school);
      save.close();
    } catch (FileNotFoundException e ) {
      throw new FileNotFoundException(_file);
    } catch (IOException e) {
      e.printStackTrace(); }
  }


  public String getFileName(){
    return _file;
  }

  public void setFileName(String nome){
    _file = nome;
  }

  /*
  public void open(String filename) throws NoSuchPersonIdException, IOException, ClassNotFoundException {

    try{
      ObjectInputStream load = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
      _school = (School) load.readObject();
      load.close();
      Person p = _school.getPerson(_person.getId());
      if (p == null){
        throw new NoSuchPersonIdException(_person.getId());
      }
      _person = p;
      _file = filename;
    }
    catch (FileNotFoundException e) {
      throw new FileNotFoundException(filename);
    }
  }*/

  public void open(String fileName) throws IOException, ClassNotFoundException, NoSuchPersonIdException {
    ObjectInputStream inob = new ObjectInputStream(new FileInputStream(fileName));
    School tempSchool = (School)inob.readObject();
    inob.close();
    Person p = tempSchool.getPerson(_person.getId());
    if (p == null){
      throw new NoSuchPersonIdException(_person.getId());
    }

    _person = p;
    _school = tempSchool;

  }



  /**
   * Do the login of the user with the given identifier.
   * 
   * @param id identifier of the user to login
   * @throws NoSuchPersonIdException if there is no uers with the given identifier
   */
  public void login(int id) throws NoSuchPersonIdException {
    _person = _school.getPerson(id);
    if (_person == null){
      throw new NoSuchPersonIdException(id);
    }
  }

  /**
   * @return true when the currently logged in person is an administrative
   */
  public boolean isLoggedUserAdministrative() {
    return _school.checkEmployee(_person);
  }

  /**
   * @return true when the currently logged in person is a professor
   */
  public boolean isLoggedUserProfessor() {
    return _school.checkTeacher(_person);
  }

  /**
   * @return true when the currently logged in person is a student
   */
  public boolean isLoggedUserStudent() {
    return _school.checkStudent(_person);
  }

  /**
   * @return true when the currently logged in person is a representative
   */
  public boolean isLoggedUserRepresentative() {
    return _school.checkRepresentative(_person);
  }




  public String showPerson() throws NoSuchPersonIdException{
    return _school.showPerson(getPersonIdLogged());
  }

  public List<String> showAllPeople(){
    return _school.showPeople();
  }

  public String changePhoneNumber(int newNum)throws NoSuchPersonIdException {
    return _school.changeTelephoneNumber(getPersonIdLogged(), newNum);
  }

  public String searchPerson(String partOfName){
    return _school.searchPerson(partOfName);
  }






  public void createProject(String discipline, String name) throws NoSuchDisciplineIdException, DuplicateProjectIdException {
    _school.createProjectDiscipline(getPersonIdLogged(), discipline, name);
  }

  public void closeProject(String discipline, String name) throws NoSuchProjectIdException, NoSuchDisciplineIdException, OpeningSurveyIdException{
    _school.closeProjectIfPossible(getPersonIdLogged() ,discipline, name);
  }

  public List<String> showStudentsDiscipline(String discipline) throws NoSuchDisciplineIdException{
    return _school.showStudentsD((Teacher) _person, discipline);
  }

  public String showAllSubmissionProject(String discipline, String project) throws NoSuchDisciplineIdException, NoSuchProjectIdException{
    return _school.showSubmissionProject(getPersonIdLogged(), discipline, project);
  }

  public String showSurveyResultsTeacher(String discipline, String project) throws NoSurveyIdException, NoSuchProjectIdException, NoSuchDisciplineIdException{
    return _school.showSurveyResultsTeacher(getPersonIdLogged(), discipline, project);
  }





  public void submitProjectByStudent(String discipline, String project, String mess) throws NoSuchDisciplineIdException, NoSuchProjectIdException{
    _school.submit((Student)_person, discipline, project, mess);
  }

  public void doSurvey(String discipline, String project, int hours, String comment) throws NoSuchDisciplineIdException, NoSurveyIdException, NoSuchProjectIdException{
    _school.doSurvey((Student)_person, discipline, project, hours, comment);
  }

  public String showSurveyResultsStudent(String discipline, String project) throws NoSurveyIdException, NoSuchProjectIdException, NoSuchDisciplineIdException{
    return _school.showSurveyResultsStudent(getPersonIdLogged(), discipline,project);
  }



  public void createSurvey(String discipline, String project) throws NoSuchDisciplineIdException, NoSuchProjectIdException, DuplicateSurveyIdException{
    _school.createSurvey(getPersonIdLogged(), discipline, project);
  }

  public void cancelSurvey(String discipline, String project) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoSurveyIdException, SurveyFinishedIdException, NonEmptySurveyIdException{
    _school.cancelSurvey(getPersonIdLogged(), discipline, project);
  }

  public void openSurvey(String discipline, String project) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoSurveyIdException, OpeningSurveyIdException{
    _school.openSurvey(getPersonIdLogged(), discipline, project);
  }

  public void closeSurvey(String discipline, String project) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoSurveyIdException, ClosingSurveyIdException{
    _school.closeSurvey(getPersonIdLogged(), discipline, project);
  }

  public void finaliseSurvey(String discipline, String project) throws NoSuchProjectIdException, NoSuchDisciplineIdException, NoSurveyIdException, FinishingSurveyIdException{
    _school.finaliseSurvey(getPersonIdLogged(), discipline, project);
  }

  public List<String> showSurveysDiscipline(String discipline) {
    return _school.showSurveysDiscipline(getPersonIdLogged(), discipline);
  }


  public List<String> readAllNotification(){
    return _person.readNote();
  }




  public int getPersonIdLogged(){
    return _person.getId();
  }
}
