package hr.classes.classes;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Users {
    int id;
    String name;
    String surname;
    String phoneNumber;
    String gender;
    String email;
    LocalDate dateOfRegistration;
    LocalDate dateOfBirth;
    List<Subscriptions> sub = new ArrayList<Subscriptions>();

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", dateOfRegistration=" + dateOfRegistration +
                ", dateOfBirth=" + dateOfBirth +
                ", sub=" + sub +
                '}';
    }

    /* Constructor */
    /*---------------------------------------------------*/
    public Users(int id,String name, String surname, String phoneNumber, String gender, String email,
                 LocalDate dateOfRegistration, LocalDate dateOfBirth) {
        this.id=id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.email = email;
        this.dateOfRegistration = dateOfRegistration;
        this.dateOfBirth = dateOfBirth;
    }

    public Users(int id, String name, String surname, String phoneNumber, String gender, String email,
                 LocalDate dateOfRegistration, LocalDate dateOfBirth, List<Subscriptions> sub) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.email = email;
        this.dateOfRegistration = dateOfRegistration;
        this.dateOfBirth = dateOfBirth;
        this.sub = sub;
    }

    /*  Getters and Setters*/
    /*---------------------------------------------------*/
    public int getID() {
        return id;
    }

    public void setID(String name) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public List<Subscriptions> getSub() {return sub;}

    public void setSub(List<Subscriptions> sub) {this.sub = sub;}

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(LocalDate dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }



}
