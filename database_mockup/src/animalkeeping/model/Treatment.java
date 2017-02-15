package animalkeeping.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Treatment extends Entity {
    private Date start;
    private Date end;
    private Subject subject;
    private Person person;
    private TreatmentType type;
    private Set<TreatmentNote> notes = new HashSet<>(0);
    public Treatment() {}

    public Treatment(Date start, Subject subject, Person person, TreatmentType type) {
        this.start = start;
        this.subject = subject;
        this.person = person;
        this.type = type;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public TreatmentType getType() {
        return type;
    }

    public void setType(TreatmentType type) {
        this.type = type;
    }

    public Set<TreatmentNote> getNotes() {
        return notes;
    }

    public void setNotes(Set<TreatmentNote> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Treatment{" +
                "id=" + getId() +
                ", type=" + getType() +
                ", start=" + getStart() +
                ", end=" + ((getEnd() != null) ? getEnd() : "") +
                ", subject=" + getSubject() +
                ", person=" + getPerson() +
                '}';
    }
}
