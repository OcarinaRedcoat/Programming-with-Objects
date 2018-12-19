package sth.app.representative;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.*;
import sth.core.SchoolManager;

import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSurveyIdException;
import sth.core.exception.OpeningSurveyIdException;

/**
 * 4.6.3. Open survey.
 */
public class DoOpenSurvey extends sth.app.common.ProjectCommand {

  /**
   * @param receiver
   */

  public DoOpenSurvey(SchoolManager receiver) {
    super(Label.OPEN_SURVEY, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void myExecute() throws NoSurveyException, NoSuchDisciplineException, NoSuchProjectException, OpeningSurveyException {
    try{
      _receiver.openSurvey(_discipline.value(), _project.value());
    } catch (NoSurveyIdException e){
      throw new NoSurveyException(_discipline.value(), _project.value());
    } catch (NoSuchDisciplineIdException e){
      throw new NoSuchDisciplineException(_discipline.value());
    } catch (NoSuchProjectIdException e){
      throw new NoSuchProjectException(_discipline.value(), _project.value());
    } catch (OpeningSurveyIdException e){
      throw new OpeningSurveyException(_discipline.value(), _project.value());
    }
  }

}
