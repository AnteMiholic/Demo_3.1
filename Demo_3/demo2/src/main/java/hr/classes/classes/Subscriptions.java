package hr.classes.classes;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Subscriptions {
    int subID;
    String tip;
    ObjectProperty<LocalDate> startingDate;
    ObjectProperty<LocalDate> endDate;
    int frozen;
    int uID;
    ObjectProperty<LocalDate> created;
    int counter;

    /* Constructor */
    /*---------------------------------------------------*/
    public Subscriptions(int subID,String tip, LocalDate startingDate, LocalDate endDate, int frozen, int uID, LocalDate created) {
        this.subID = subID;
        this.tip = tip;
        this.startingDate = new SimpleObjectProperty<LocalDate>(startingDate);
        this.endDate = new SimpleObjectProperty<LocalDate>(endDate);
        this.frozen = frozen;
        this.uID = uID;
        this.created = new SimpleObjectProperty<LocalDate>(created);
    }

    public Subscriptions(int subID,String tip, LocalDate startingDate, LocalDate endDate, int frozen, int uID,
                         LocalDate created, int counter) {
        this.subID = subID;
        this.tip = tip;
        this.startingDate = new SimpleObjectProperty<LocalDate>(startingDate);
        this.endDate = new SimpleObjectProperty<LocalDate>(endDate);
        this.frozen = frozen;
        this.uID = uID;
        this.created = new SimpleObjectProperty<LocalDate>(created);
        this.counter = counter;
    }

    public int getSubID() {
        return subID;
    }

    public int getuID() {
        return uID;
    }

    public LocalDate getCreated() {
        return created.get();
    }

    public String getTip() {
        return tip;
    }

    public Subscriptions(int subID, String tip, LocalDate startingDate, LocalDate endDate, int frozen, LocalDate created) {
        this.subID = subID;
        this.tip = tip;
        this.startingDate = new SimpleObjectProperty<LocalDate>(startingDate);
        this.endDate = new SimpleObjectProperty<LocalDate>(endDate);
        this.frozen = frozen;

        this.created = new SimpleObjectProperty<LocalDate>(created);
    }

    @Override
    public String toString() {
        return "Subscriptions{" +
                "subID=" + subID +
                ", tip='" + tip + '\'' +
                ", startingDate=" + startingDate +
                ", endDate=" + endDate +
                ", frozen=" + frozen +
                ", uID=" + uID +
                ", created=" + created +
                '}';
    }

    /*  Getters and Setters*/
    /*---------------------------------------------------*/
    public LocalDate getStartingDate() {
        return startingDate.get();
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate.set(startingDate);
    }

    public LocalDate getEndDate() {
        return endDate.get();
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate.set(endDate);
    }

    public int getFrozen() {
        return frozen;
    }

    public boolean isFrozen() {
        if(getFrozen()==1){
            return true;
        }
        else
            return false;
    }

    public int getCounter() {
        return counter;
    }

    public void setFrozen(int frozen) {
        this.frozen = frozen;
    }

    public boolean isActive() {
        if (LocalDate.now().isAfter(getStartingDate().minusDays(1)) && LocalDate.now().isBefore(getEndDate().plusDays(1)) && !isFrozen()) {
            return true;
        } else
            return false;
    }
    public ObjectProperty<LocalDate> subEndProperty() {
        return endDate;
    }
    public ObjectProperty<LocalDate> subStartProperty() {
        return startingDate;
    }
    public ObjectProperty<LocalDate> subCreateProperty() {
        return created;
    }

    public String formatDate (LocalDate date){
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    }
}
