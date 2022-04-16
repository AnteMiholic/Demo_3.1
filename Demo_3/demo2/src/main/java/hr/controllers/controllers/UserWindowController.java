package hr.controllers.controllers;

import Database.SubscriptionQueryClass;
import Database.UserQuery;
import hr.classes.classes.Subscriptions;
import hr.classes.classes.Users;
import hr.classes.classes.UsersSubs;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.MainApp;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


public class UserWindowController {
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private ComboBox genderComboBox;
    @FXML
    private TextField emailTextField;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private TableView<Subscriptions> subscriptionsTableView;
    @FXML
    private TableColumn<Subscriptions, LocalDate> usersStartColumn;
    @FXML
    private TableColumn<Subscriptions, LocalDate> usersEndColumn;
    @FXML
    private TableColumn<Subscriptions, String> usersPauseColumn;
    @FXML
    private TableColumn<Subscriptions, String> usersTypeColumn;
    @FXML
    private TableColumn<Subscriptions, String> usersStatusColumn;
    @FXML
    private TableColumn<Subscriptions, LocalDate> usersDateofCreation;
    @FXML
    private TableColumn<Subscriptions, String> usersFrozenDaysColumn;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonRemove;
    @FXML
    private Button buttonBack;

    @FXML
    private Button updateUserInDatabase;
    List<Subscriptions> userSubsList = new ArrayList<>();
    public int data1=0;


    @FXML
    public void initialize(int userID) {
        data1=userID;
        //grayouts save button if no changes made
        updateUserInDatabase.setDisable(true);

        userSubsList= SubscriptionQueryClass.getSubsForUser(userID);
        //FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/mainScreen.fxml"));
        Users currentUser = UserQuery.getUserFromID(data1);
        nameTextField.setText(currentUser.getName());
        surnameTextField.setText(currentUser.getSurname());
        phoneNumberTextField.setText(currentUser.getPhoneNumber());
        genderComboBox.setItems(FXCollections.observableArrayList(
                new String("Žensko"),
                new String("Muško"),
                new String("Ostalo")));
        switch (currentUser.getGender()) {
            case "Z" -> genderComboBox.setValue("Žensko");
            case "M" -> genderComboBox.setValue("Muško");
            case "O" -> genderComboBox.setValue("Ostalo");
        };
        emailTextField.setText(currentUser.getEmail());
        birthDatePicker.setValue(currentUser.getDateOfBirth());

        //Listens to change
        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateUserInDatabase.setDisable(false);
        });
        surnameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateUserInDatabase.setDisable(false);
        });
        phoneNumberTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateUserInDatabase.setDisable(false);
        });
        genderComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateUserInDatabase.setDisable(false);
        });
        emailTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateUserInDatabase.setDisable(false);
        });
        birthDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateUserInDatabase.setDisable(false);
        });

        onShowALlButtonClick();

        buttonAdd.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                    event.consume();
                    final Node source = (Node) event.getSource();
                    final Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                showSubscriptionWindowNew(userID);

            }});

        updateUserInDatabase.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                event.consume();
                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                String name = nameTextField.getText();
                String surname = surnameTextField.getText();
                LocalDate birthDate = birthDatePicker.getValue();
                String gender = "";
                if (genderComboBox.getValue().toString().equals("Žensko")){
                    gender = "Z";
                } else
                    gender = genderComboBox.getValue().toString();
                String email = emailTextField.getText();
                String phone = phoneNumberTextField.getText();
                UserQuery.changeUser(userID,name,surname,birthDate,gender,email,phone);
                try {
                    showUserTable();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        buttonRemove.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Brisanje korisnika");
                alert.setHeaderText("Potvrda izbora");
                alert.setContentText("Jeste li sigurni?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    UserQuery.deleteUser(userID);
                    final Node source = (Node) event.getSource();
                    final Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                    try {
                        showUserTable();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    alert.close();
                }
                event.consume();
            }

        });

        buttonBack.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                event.consume();
                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                try {
                    showUserTable();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        subscriptionsTableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                TablePosition pos = subscriptionsTableView.getSelectionModel().getSelectedCells().get(0);
                //Item here is the table view type:
                Subscriptions subscription = subscriptionsTableView.getItems().get(pos.getRow());

                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                showSubscriptionWindow(subscription,userID);
            }
        });


    }

    private Node buttonAddCursorChange() {

        return null;
    }

    public void showSubscriptionWindow(Subscriptions data, int useriD){

        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/subscriptionWindow.fxml"));
        Scene scene = null;
        try {

            scene = new Scene(fxmlLoader.load(), 1000, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SubscriptionWindowController subscriptionWindowController = fxmlLoader.getController();
        subscriptionWindowController.initialize(data,useriD);
        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Uređivanje članarine");
        popupWindow.setUserData(data);
        popupWindow.setScene(scene);
        popupWindow.show();


    }


    public void showSubscriptionWindowNew(int data){

        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/subscriptionWindowAddNew.fxml"));
        Scene scene = null;
        try {

            scene = new Scene(fxmlLoader.load(), 1000, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SubscriptionWindowAddNewController subscriptionWindowController = fxmlLoader.getController();
        subscriptionWindowController.initialize(data);
        Stage popupWindow = new Stage();
        popupWindow.setTitle("Uređivanje članarine");
        popupWindow.setUserData(data);
        popupWindow.setScene(scene);

        popupWindow.show();


    }
    @FXML
    protected void onShowALlButtonClick(){
        usersStartColumn.setCellValueFactory(cellData -> cellData.getValue().subStartProperty());
        usersEndColumn.setCellValueFactory(cellData -> cellData.getValue().subEndProperty());
        usersTypeColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getTip());
        });
        usersFrozenDaysColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(String.valueOf(cellData.getValue().getCounter()));
        });
        usersPauseColumn.setCellValueFactory(cellData -> {
            int frozen = cellData.getValue().getFrozen();
            String s = Integer.toString(frozen);
            return new SimpleStringProperty(s);
        });
        usersDateofCreation.setCellValueFactory(cellData -> cellData.getValue().subCreateProperty());
        usersStatusColumn.setCellValueFactory(cellData -> {

            if (cellData.getValue().isActive()){
                return new SimpleStringProperty("Aktivna");
            }
            else{
                return new SimpleStringProperty("Neaktivna");
            }
        });

        //Set to observable array
        ObservableList<Subscriptions> usersObservableList = FXCollections.observableList(userSubsList);


        //Date formating
        DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        // Custom rendering of the table cell.
        usersStartColumn.setCellFactory(column -> {
            return new TableCell<Subscriptions, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Format date.
                        setText(myDateFormatter.format(item));
                    }
                }
            };
        });
        // Custom rendering of the table cell.
        usersEndColumn.setCellFactory(column -> {
            return new TableCell<Subscriptions, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Format date.
                        setText(myDateFormatter.format(item));
                    }
                }
            };
        });
        usersDateofCreation.setCellFactory(column -> {
            return new TableCell<Subscriptions, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Format date.
                        setText(myDateFormatter.format(item));
                    }
                }
            };
        });



        subscriptionsTableView.setItems(usersObservableList);

        subscriptionsTableView.setRowFactory(tv -> new TableRow<Subscriptions>() {
            @Override
            protected void updateItem(Subscriptions item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null)
                    setStyle("");
               else if (item.isActive())
                    setStyle("-fx-background-color: #90EE90;");
                else if (!item.isActive())
                    setStyle("-fx-background-color: #FF7F7F;");
                else
                    setStyle("");
            }
        });
    }




    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
    public void showUserTable() throws IOException {

        Scene scene = new Scene(loadFXML("mainScreen"));
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        MainApp.getStage().setTitle("Tablica Korisnika");
        MainApp.getStage().setX(bounds.getMinX());
        MainApp.getStage().setY(bounds.getMinY());
        MainApp.getStage().setWidth(bounds.getWidth());
        MainApp.getStage().setHeight(bounds.getHeight());
        MainApp.getStage().setScene(scene);
        MainApp.getStage().show();

    }

}
