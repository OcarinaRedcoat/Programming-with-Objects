package sth.app.person;


import sth.core.SchoolManager;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Display;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;
import sth.core.exception.NoSuchPersonIdException;
import sth.app.exception.NoSuchPersonException;
//FIXME import other classes if needed

/**
 * 4.2.2. Change phone number.
 */
public class DoChangePhoneNumber extends Command<SchoolManager> {

  private Input<Integer> _newPhoneNumber;


  /**
   * @param receiver
   */
  public DoChangePhoneNumber(SchoolManager receiver) {
    super(Label.CHANGE_PHONE_NUMBER, receiver);
    _newPhoneNumber = _form.addIntegerInput(Message.requestPhoneNumber());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws NoSuchPersonException{
    try {
      _form.parse();
      String s = _receiver.changePhoneNumber(_newPhoneNumber.value());
      _display.addLine(s);
      _display.display();
    } catch (NoSuchPersonIdException e){
      throw new NoSuchPersonException(e.getId());
    }

  }

}
