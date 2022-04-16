package hr.controllers.controllers;

import Database.SubscriptionQueryClass;
import Database.UserQuery;
import hr.classes.classes.Subscriptions;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.MainApp;

import java.io.IOException;
import java.time.LocalDate;

public class SubscriptionWindowAddNewController {
    @FXML
    private DatePicker datumPocetkaClanarine;
    @FXML
    private DatePicker datumKrajaClanarine;
    @FXML
    private ComboBox tipClanarineComBox;
    @FXML
    private Button addToDatabaseButton;

    @FXML
    private Button buttonBack;
    @FXML


    public  void initialize(int data) {

        datumPocetkaClanarine.setValue(LocalDate.now());
        datumKrajaClanarine.setValue(LocalDate.now().plusDays(30));
        tipClanarineComBox.setItems(FXCollections.observableArrayList(
                new String("Grupna"),
                new String("Individualna")));
        tipClanarineComBox.setValue("Grupna");

        addToDatabaseButton.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                event.consume();
                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                int max = UserQuery.getMaxUserID();
                String tipClanarine=tipClanarineComBox.getValue().toString();
                LocalDate d1 =  datumPocetkaClanarine.getValue();
                LocalDate d2 =  datumKrajaClanarine.getValue();
                SubscriptionQueryClass.insertSubscription(tipClanarine, d1,
                        d2, data);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Spremanje uspješno!");
                alert.setHeaderText("Podaci o članarini su spremljeni!");
                alert.setContentText("Članarina od: " + d1.toString() + " " + d2.toString() + " saved to the database!");
                alert.showAndWait();
                showUserWindow(data);
            }});
        buttonBack.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                event.consume();
                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();

                showUserWindow(data);
            }
        });

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
