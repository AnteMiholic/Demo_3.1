package hr.classes.classes;

import java.time.LocalDate;

public class CheckIns {

    LocalDate CheckInDate;

    /* Constructor */
    /*---------------------------------------------------*/
    public CheckIns(LocalDate checkInDate) {
        CheckInDate = checkInDate;
    }


    /*  Getters and Setters*/
    /*---------------------------------------------------*/
    public LocalDate getCheckInDate() {
        return CheckInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        CheckInDate = checkInDate;
    }


}
