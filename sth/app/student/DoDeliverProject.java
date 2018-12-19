package sth.app.student;

import pt.tecnico.po.ui.Input;
import sth.app.exception.NoSuchDisciplineException;
import sth.app.exception.NoSuchProjectException;
import sth.core.SchoolManager;

import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSuchDisciplineIdException;

/**
 * 4.5.1. Deliver project.
 */
public class DoDeliverProject extends sth.app.common.ProjectCommand {

  /**
   * @param receiver
   */

  private Input<String> _message;

  public DoDeliverProject(SchoolManager receiver) {
    super(Label.DELIVER_PROJECT, receiver);
    _message = _form.addStringInput(Message.requestDeliveryMessage());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void myExecute() throws NoSuchDisciplineException, NoSuchProjectException {
    try {
      _receiver.submitProjectByStudent(_discipline.value(), _project.value(), _message.value());
    }catch (NoSuchDisciplineIdException e){
      throw new NoSuchDisciplineException(_discipline.value());
    }catch (NoSuchProjectIdException e){
      throw new NoSuchProjectException(_discipline.value(), _project.value());
    }
  }

}