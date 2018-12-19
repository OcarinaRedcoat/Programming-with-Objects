package sth.core;
import sth.core.exception.BadEntryException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Person implements Comparable<Person> , Serializable {
    private String _name;
    private int _id;
    private int _phoneNumber;
    private List<Discipline> _blockedDisciplines;
    private List<Notification> _noteLst;

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201810051538L;

    protected Person(int id, int phoneNumber, String name) {
        _name = name;
        _id = id;
        _phoneNumber = phoneNumber;
        _blockedDisciplines = new ArrayList<>();
        _noteLst = new ArrayList<>();
    }

    protected String getName() {
        return _name;
    }

    protected int getId() {
        return _id;
    }

    protected int getPhoneNumber() {
        return _phoneNumber;
    }

    protected void changePhoneNumber(int i) {
        _phoneNumber = i;
    }

    abstract protected String getJob();

    abstract protected String getDescription();

    public final String toString(){
        return getJob() + "|" + getId() + "|" + getPhoneNumber() + "|" + getName() + getDescription();
    }

    abstract public boolean equals(Object o);

    public int compareTo(Person p) {
        return (this.getId() - p.getId());
    }

    void parseContext(String context, School school) throws BadEntryException {
        throw new BadEntryException("Should not have extra context: " + context);
    }

    protected void notifyAllPerson(Notification note, Discipline discipline){
        if (!_blockedDisciplines.contains(discipline)) {
            _noteLst.add(note);
        }
    }

    protected List<String> readNote(){
        List<String> nt = new ArrayList<>();
        for (Notification n: _noteLst){
            nt.add(n.notifyString());
        }
        _noteLst.clear();
        return nt;
    }

}
