package sth.core;

public class Employee extends Person {
    protected Employee(int id, int phoneNumber, String name) {
        super(id, phoneNumber, name);
    }

    protected String getJob(){
        return "FUNCIONÁRIO";
    }

    protected String getDescription(){
        return "";
    }

    public boolean equals(Object o) {
        if (o instanceof Teacher) {
            Teacher p = (Teacher) o;
            return getId() == p.getId();
        }
        return false;
    }
}