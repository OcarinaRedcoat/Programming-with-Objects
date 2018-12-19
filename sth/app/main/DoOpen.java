package sth.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;
import sth.core.Parser;
import sth.core.exception.NoSuchPersonIdException;
import sth.app.exception.NoSuchPersonException;
//FIXME import other classes if needed

/**
 * 4.1.1. Open existing document.
 */
public class DoOpen extends Command<SchoolManager> {

    //FIXME add input fields if needed
    private Input<String> _file;
    /**
     * @param receiver
     */
    public DoOpen(SchoolManager receiver) {
        super(Label.OPEN, receiver);
        _file = _form.addStringInput(Message.openFile());
    }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void execute() throws NoSuchPersonException {
        try {
            _form.parse();
            _receiver.setFileName(_file.value());
            _receiver.open(_file.value());
            List<String> lst = _receiver.readAllNotification();
            for (String s: lst){
                _display.addLine(s);
            }
            _display.display();
        }catch (NoSuchPersonIdException e){
            throw new NoSuchPersonException(e.getId());
        } catch (FileNotFoundException fnfe) {
            _display.popup(Message.fileNotFound());
        } catch (ClassNotFoundException | IOException e) {
            // shouldn't happen in a controlled test setup
            e.printStackTrace();
        }
    }

}
