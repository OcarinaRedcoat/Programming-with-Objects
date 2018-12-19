package sth.app.representative;

import sth.app.exception.ClosingSurveyException;
import sth.app.exception.NoSuchDisciplineException;
import sth.app.exception.NoSuchProjectException;
import sth.app.exception.NoSurveyException;
import sth.core.SchoolManager;

import sth.core.exception.ClosingSurveyIdException;
import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSurveyIdException;

/**
 * 4.5.4. Close survey.
 */
public class DoCloseSurvey extends sth.app.common.ProjectCommand {
  /**
   * @param receiver
   */


  public DoCloseSurvey(SchoolManager receiver) {
    super(Label.CLOSE_SURVEY, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void myExecute() throws  NoSurveyException, NoSuchDisciplineException, NoSuchProjectException, ClosingSurveyException{
    try{
      _receiver.closeSurvey(_discipline.value(), _project.value());
    } catch (NoSurveyIdException e){
      throw new NoSurveyException(_discipline.value(), _project.value());
    } catch (NoSuchDisciplineIdException e){
      throw new NoSuchDisciplineException(_discipline.value());
    } catch (NoSuchProjectIdException e){
      throw new NoSuchProjectException(_discipline.value(), _project.value());
    } catch (ClosingSurveyIdException e){
      throw new ClosingSurveyException(_discipline.value(), _project.value());
    }
  }

}
