package sth.app.representative;

import sth.app.exception.FinishingSurveyException;
import sth.app.exception.NoSuchDisciplineException;
import sth.app.exception.NoSuchProjectException;
import sth.app.exception.NoSurveyException;
import sth.core.SchoolManager;

//FIXME import other classes if needed

import sth.core.exception.FinishingSurveyIdException;
import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSurveyIdException;

/**
 * 4.6.5. Finish survey.
 */
public class DoFinishSurvey extends sth.app.common.ProjectCommand {

  /**
   * @param receiver
   */

  public DoFinishSurvey(SchoolManager receiver) {
    super(Label.FINISH_SURVEY, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void myExecute() throws NoSurveyException, NoSuchProjectException, NoSuchDisciplineException, FinishingSurveyException{
    try {
      _receiver.finaliseSurvey(_discipline.value(), _project.value());
    } catch (NoSurveyIdException e){
      throw new NoSurveyException(_discipline.value(), _project.value());
    } catch (NoSuchProjectIdException e){
      throw new NoSuchProjectException(_discipline.value(), _project.value());
    } catch (NoSuchDisciplineIdException e){
      throw new NoSuchDisciplineException(_discipline.value());
    } catch (FinishingSurveyIdException e){
      throw new FinishingSurveyException(_discipline.value(), _project.value());
    }
  }
}
