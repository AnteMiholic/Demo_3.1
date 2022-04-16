package hr.controllers.controllers;

import Database.SubscriptionQueryClass;
import hr.classes.classes.Subscriptions;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.MainApp;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

public class SubscriptionWindowController {

    @FXML
    private DatePicker datumPocetkaClanarine;
    @FXML
    private DatePicker datumKrajaClanarine;
    @FXML
    private ComboBox tipClanarineComBox;
    @FXML
    private Button addToDatabaseButton;
    @FXML
    private Button deleteSubButton;
    @FXML
    private Button buttonBack;
    @FXML
    private Button buttonFreeze;
    @FXML
    private Button buttonUnfreeze;
    @FXML
    private DatePicker frozenDatePicker;
    @FXML
    private Button spremiZamrzni;
    @FXML
    private Button spremiOdmrzni;


    @FXML
    public  void initialize(Subscriptions data,int uid) {
        //grays out buttons
        frozenDatePicker.setValue(LocalDate.now());
        if(data.isFrozen()){
            buttonFreeze.setDisable(true);
        } else
            buttonUnfreeze.setDisable(true);
        addToDatabaseButton.setDisable(true);
        spremiZamrzni.setDisable(true);
        spremiZamrzni.setVisible(false);
        spremiOdmrzni.setDisable(true);
        spremiOdmrzni.setVisible(false);
        frozenDatePicker.setDisable(true);
        frozenDatePicker.setVisible(false);

        datumPocetkaClanarine.setValue(data.getStartingDate());
        datumKrajaClanarine.setValue(data.getEndDate());
        tipClanarineComBox.setItems(FXCollections.observableArrayList(
                new String("Grupna"),
                new String("Individualna")));
        if (data.getTip().equals("Grupna")){
            tipClanarineComBox.getSelectionModel().selectFirst();
        }
        else if(data.getTip().equals("Individualna"))
            tipClanarineComBox.getSelectionModel().selectLast();

        addToDatabaseButton.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                event.consume();
                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                String tipClanarine=tipClanarineComBox.getValue().toString();
                LocalDate d1 =  datumPocetkaClanarine.getValue();
                LocalDate d2 =  datumKrajaClanarine.getValue();
                SubscriptionQueryClass.changeSubscription(tipClanarine,d1,d2, data.getFrozen(),data.getSubID());
                showUserWindow(uid);
            }});

        //Freeze i Unfreeze
        buttonFreeze.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                event.consume();

                // Disables/enables buttons
                addToDatabaseButton.setDisable(true);
                spremiZamrzni.setVisible(true);
                spremiZamrzni.setDisable(false);
                frozenDatePicker.setVisible(true);
                frozenDatePicker.setDisable(false);
            }});
        buttonUnfreeze.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                event.consume();

                // Disables/enables buttons
                addToDatabaseButton.setDisable(true);
                spremiOdmrzni.setVisible(true);
                spremiOdmrzni.setDisable(false);
                frozenDatePicker.setVisible(true);
                frozenDatePicker.setDisable(false);
            }});

        //SAVE za freeze i unfreeze
        spremiZamrzni.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                event.consume();

                SubscriptionQueryClass.freezeSubscription(data.getSubID(), frozenDatePicker.getValue());

                //zatvara scenu
                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                showUserWindow(uid);

            }});
        spremiOdmrzni.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                event.consume();

                LocalDate subEnd =  datumKrajaClanarine.getValue();
                SubscriptionQueryClass.unfreezeSubscription(data.getSubID(), subEnd, frozenDatePicker.getValue(),
                        data.getCreated());

                //zatvara scenu
                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                showUserWindow(uid);

            }});

        //Listens to change
        datumPocetkaClanarine.valueProperty().addListener((observable, oldValue, newValue) -> {

            addToDatabaseButton.setDisable(false);
        });
        datumKrajaClanarine.valueProperty().addListener((observable, oldValue, newValue) -> {

            addToDatabaseButton.setDisable(false);
        });
        tipClanarineComBox.valueProperty().addListener((observable, oldValue, newValue) -> {

            addToDatabaseButton.setDisable(false);
        });

        buttonBack.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                event.consume();
                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();

                showUserWindow(uid);
            }
        });

        deleteSubButton.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Brisanje članarina");
                alert.setHeaderText("Potvrda izbora");
                alert.setContentText("Jeste li sigurni?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    SubscriptionQueryClass.deleteSubscription(data.getSubID());
                    final Node source = (Node) event.getSource();
                    final Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                    showUserWindow(uid);
                } else {
                    alert.close();
                }
                event.consume();
                }});
    }

    public void showUserWindow(int data){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/userWindow.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1000, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserWindowController userWindowController = fxmlLoader.getController();
        userWindowController.initialize(data);
        Stage popupWindow = new Stage();
        popupWindow.setTitle("Uređivanje korisnika");
        popupWindow.setUserData(data);
        popupWindow.setScene(scene);
        popupWindow.show();
    }
}
