package sth.app.representative;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.*;
import sth.core.School;
import sth.core.SchoolManager;

import sth.core.exception.*;

/**
 * 4.5.2. Cancel survey.
 */
public class DoCancelSurvey extends sth.app.common.ProjectCommand {

  /**
   * @param receiver
   */

  public DoCancelSurvey(SchoolManager receiver) {
    super(Label.CANCEL_SURVEY, receiver);
  }

  /** @see sth.app.common.ProjectCommand#myExecute() */
  @Override
  public final void myExecute() throws NoSurveyException, NoSuchProjectException, SurveyFinishedException, NonEmptySurveyException, NoSuchDisciplineException{
    try {
      _receiver.cancelSurvey(_discipline.value(), _project.value());
    } catch (NoSurveyIdException e){
      throw new NoSurveyException(_discipline.value(), _project.value());
    } catch (NoSuchProjectIdException e){
      throw new NoSuchProjectException(_discipline.value(), _project.value());
    } catch (SurveyFinishedIdException e){
      throw new SurveyFinishedException(_discipline.value(), _project.value());
    } catch (NonEmptySurveyIdException e){
      throw new NonEmptySurveyException(_discipline.value(), _project.value());
    } catch (NoSuchDisciplineIdException e){
      throw new NoSuchDisciplineException(_discipline.value());
    }
  }

}
