package sth.app.main;

import pt.tecnico.po.ui.DialogException;
import sth.core.School;
import sth.core.SchoolManager;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * §3.1.1. Save to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<SchoolManager> {

  private Input<String> _filename;

  private String _otherFile;

  /**
   * @param receiver
   */
  public DoSave(SchoolManager receiver) {
    super(Label.SAVE, receiver);
    _filename = _form.addStringInput(Message.newSaveAs());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute(){
    if (_receiver.getFileName() != null) {
      _otherFile = _receiver.getFileName();
    }
    else {
      _form.parse();
    }
    try {
      if (_receiver.getFileName() != null) {
        _receiver.newSaveAs(_otherFile);
      }
      else {
        _receiver.newSaveAs(_filename.value());
      }
    /*} catch (FileNotFoundException fnfe){*/
    } catch (IOException e) {
      // shouldn't happen in a controlled test setup
      e.printStackTrace();
    }
  }
}