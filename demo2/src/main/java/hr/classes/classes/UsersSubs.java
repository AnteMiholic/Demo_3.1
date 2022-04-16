package hr.classes.classes;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UsersSubs {
    int Uid;
    String name;
    String surname;
    ObjectProperty<LocalDate> subStart;
    ObjectProperty<LocalDate> subEnd;
    String tip;
    int frozen;

    public UsersSubs(int uid, String name, String surname, LocalDate subStart, LocalDate subEnd, String tip, int frozen
                     ) {
        this.Uid = uid;
        this.name = name;
        this.surname = surname;
        this.subStart = new SimpleObjectProperty<LocalDate>(subStart);
        this.subEnd = new SimpleObjectProperty<LocalDate>(subEnd);
        this.tip = tip;
        this.frozen = frozen;

    }

    public UsersSubs(int uid) {
        Uid = uid;
    }

    public UsersSubs() {

    }
    public void setter(int uid, String name, String surname, LocalDate subStart, LocalDate subEnd, String tip, int frozen
                       ){
        this.Uid = uid;
        this.name = name;
        this.surname = surname;
        this.subStart = new SimpleObjectProperty<LocalDate>(subStart);
        this.subEnd = new SimpleObjectProperty<LocalDate>(subEnd);
        this.tip = tip;
        this.frozen = frozen;

    }

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getSubStart() {
        if (subStart == null){
            return LocalDate.of(0, 1, 1);
        }
        return subStart.get();
    }

    public void setSubStart(LocalDate subStart) {
        this.subStart.set(subStart);
    }

    public LocalDate getSubEnd() {
        if (subEnd == null){
            return LocalDate.of(0, 1, 1);
        }
        return subEnd.get();
    }
    public ObjectProperty<LocalDate> subEndProperty() {
        return subEnd;
    }
    public ObjectProperty<LocalDate> subStartProperty() {
        return subStart;
    }
    @Override
    public String toString() {
        return "UsersSubs{" +
                "Uid=" + Uid +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", subStart=" + subStart +
                ", subEnd=" + subEnd +
                ", tip='" + tip + '\'' +
                ", frozen=" + frozen ;
    }

    public void setSubEnd(LocalDate subEnd) {
        this.subEnd.set(subEnd);
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getFrozen() {
        return frozen;
    }

    public void setFrozen(int frozen) {
        this.frozen = frozen;
    }

    public boolean isFrozen() {
        return getFrozen() == 1;
    }

    public boolean isActive() {
        if (LocalDate.now().isAfter(getSubStart().minusDays(1)) && LocalDate.now().isBefore(getSubEnd().plusDays(1)) && !isFrozen()) {

            return true;
        } else
        return false;
    }
   public String formatDate (LocalDate date){
       return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
   }
}
