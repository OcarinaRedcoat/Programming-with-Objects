package sth.app.teaching;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.NoSuchDisciplineException;
import sth.core.SchoolManager;
import sth.core.exception.NoSuchDisciplineIdException;

import java.util.List;

//FIXME import other classes if needed

/**
 * 4.4.4. Show course students.
 */
public class DoShowDisciplineStudents extends Command<SchoolManager> {

  private Input<String> _discipline;

  /**
   * @param receiver
   */

  public DoShowDisciplineStudents(SchoolManager receiver) {
    super(Label.SHOW_COURSE_STUDENTS, receiver);
    _discipline = _form.addStringInput(Message.requestDisciplineName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws NoSuchDisciplineException {
    try {
      _form.parse();

      List<String> list = _receiver.showStudentsDiscipline(_discipline.value());
      for (String st : list) {
        _display.addLine(st);
      }
      _display.display();

    } catch (NoSuchDisciplineIdException e) {
      throw new NoSuchDisciplineException(_discipline.value());
    }
  }
}
