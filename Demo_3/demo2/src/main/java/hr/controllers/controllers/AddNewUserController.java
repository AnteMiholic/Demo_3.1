package hr.controllers.controllers;

import Database.SubscriptionQueryClass;
import Database.UserQuery;
import Database.SubscriptionQueryClass;
import Database.UserQuery;
import hr.classes.classes.Users;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class AddNewUserController{

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private ComboBox genderComboBox;
    @FXML
    private DatePicker datumPocetkaClanarine;
    @FXML
    private DatePicker datumKrajaClanarine;
    @FXML
    private ComboBox tipClanarineComBox;

    @FXML
    public void initialize() {
        genderComboBox.setItems(FXCollections.observableArrayList(
                new String("Žensko"),
                new String("Muško"),
                new String("Ostalo")));
        tipClanarineComBox.setItems(FXCollections.observableArrayList(
                new String("Grupna"),
                new String("Individualna")));
        tipClanarineComBox.setValue("Grupna");
        datumPocetkaClanarine.setValue(LocalDate.now());
        datumKrajaClanarine.setValue(LocalDate.now().plusDays(30));
    }
    public void addToDatabaseButtonClick(){

       String errorMessages = "";
        if(nameTextField.getText().isEmpty()){
            errorMessages = errorMessages +"Ime nebi trebalo biti prazno!\n";
        }
        if(surnameTextField.getText().isEmpty()){
            errorMessages = errorMessages +"Prezime nebi trebalo biti prazno!\n";
        }
        if(genderComboBox.getValue() == null){
            errorMessages = errorMessages +"Spol nebi trebao biti prazan!\n";
        }

        if(errorMessages.isEmpty()) {
            String name = nameTextField.getText();
            String surname = surnameTextField.getText();
            LocalDate birthDate = birthDatePicker.getValue();
            String gender;
            if (genderComboBox.getValue().toString().equals("Žensko")) {
                gender = "Z";
            } else
                gender = genderComboBox.getValue().toString();
            String email = emailTextField.getText();
            String phone = phoneNumberTextField.getText();
            if(birthDatePicker.getValue()==null){
                birthDate=LocalDate.now();
            }
            if(phoneNumberTextField.getText()==null){
                phone="0";
            }
            if(emailTextField.getText()==null){
                email="0";
            }
            UserQuery.insertUser(name, surname, birthDate, gender, email, phone);

            //ArrayList<Users> usersArrayList = new ArrayList<>(UserQuery.getUsers());

            int max = UserQuery.getMaxUserID();

            String tipClanarine = tipClanarineComBox.getValue().toString();
            LocalDate d1 = datumPocetkaClanarine.getValue();
            LocalDate d2 = datumKrajaClanarine.getValue();
            SubscriptionQueryClass.insertSubscription(tipClanarine, d1,
                    d2, max);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Spremanje uspješno!");
            alert.setHeaderText("Podaci o korisniku su spremljeni!");
            alert.setContentText("Korisnik " + name + " " + surname + " saved to the database!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Spremanje nije uspjelo!");
            alert.setHeaderText("Korisnik nije stvoren!");
            alert.setContentText(errorMessages);
            alert.showAndWait();
        }
    }






}
