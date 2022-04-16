package hr.controllers.controllers;


import Database.SubscriptionQueryClass;
import hr.classes.classes.Subscriptions;
import hr.classes.classes.Users;
import hr.classes.classes.UsersSubs;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.MainApp;
import javafx.scene.paint.Color;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainScreenController {

    @FXML
    private TableColumn<UsersSubs, String> usersUidColumn;
    @FXML
    private TableColumn<UsersSubs, String> usersNameColumn;
    @FXML
    private TableColumn<UsersSubs, String> usersSurnameColumn;
    @FXML
    private TableColumn<UsersSubs, LocalDate> usersStartColumn;
    @FXML
    private TableColumn<UsersSubs, LocalDate> usersEndColumn;
    @FXML
    private TableColumn<UsersSubs, String> usersStatusColumn;
    @FXML
    private TableColumn<UsersSubs, String> usersTypeColumn;
    @FXML
    private TableView<UsersSubs> usersTable;
    @FXML
    private TextField searchInput;
    @FXML
    private DatePicker datePickerField;
    @FXML
    private ComboBox comboBoxSearch;
    @FXML
    private ComboBox comboBoxStatus;

    public void setComboBoxSearch()
    {

        comboBoxSearch.setItems(FXCollections.observableArrayList(
                new String("Ime"),
                new String("Prezime"),
                new String("Datum početka"),
                new String("Datum kraja"),
                new String("Tip članarine")));
    }

    public void setComboBoxStatus()
    {
        comboBoxStatus.setItems(FXCollections.observableArrayList(
                new String("Grupna"),
                new String("Individualna")));
    }


    private static List<UsersSubs> SubscriptionList = new ArrayList<>();


    @FXML
    public void initialize() {
        datePickerField.setValue(LocalDate.now());
        SubscriptionList.clear();
        SubscriptionList= SubscriptionQueryClass.getUsersSubs();

        onShowALlButtonClick();
        setComboBoxSearch();
        setComboBoxStatus();
        comboBoxSearch.getSelectionModel().selectFirst();
        comboBoxStatus.getSelectionModel().selectFirst();

        usersTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                TablePosition pos = usersTable.getSelectionModel().getSelectedCells().get(0);
                //Item here is the table view type:
                int user = usersTable.getItems().get(pos.getRow()).getUid();
                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                showUserWindow(user);
            }
        });

        // CONDITIONAL FORMATTING
//        usersTable.setRowFactory(tv -> new TableRow<>() {
//            @Override
//            protected void updateItem(UsersSubs userSub, boolean empty) {
//                super.updateItem(userSub, empty);
//                if (userSub.isActive())
//                    setStyle("-fx-background-color: #baffba;");
//                else if (!userSub.isActive())
//                    setStyle("-fx-background-color: #ffd7d1;");
//                else if (userSub.isFrozen())
//                    setStyle("-fx-background-color: #ahd0d1;");
//            }
//        });

        comboBoxSearch.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {

                if (t1.equals("Ime") || t1.equals("Prezime") ){
                    setToText();
                }
                else if (t1.equals("Datum početka") || t1.equals("Datum kraja")){
                    setToDate();
                }
                else{
                    setToComboBox();
                }

            }

        });

    }

    @FXML
    protected  void onShowALlButtonClick(){
        usersUidColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(String.valueOf(cellData.getValue().getUid()));
        });
        usersNameColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getName());
        });
        usersSurnameColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getSurname());
        });
        usersStartColumn.setCellValueFactory(cellData -> cellData.getValue().subStartProperty());
        usersEndColumn.setCellValueFactory(cellData -> cellData.getValue().subEndProperty());
        usersStatusColumn.setCellValueFactory(cellData -> {

            if (cellData.getValue().isActive() && cellData.getValue().getFrozen()!=1){
                return new SimpleStringProperty("Aktivna");
            }
            else if (!cellData.getValue().isActive() && cellData.getValue().getFrozen()!=1){
                return new SimpleStringProperty("Neaktivna");
            }
            else {
                return new SimpleStringProperty("Pauzirana");
            }
        });

        usersTypeColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getTip());
        });

        ObservableList<UsersSubs> usersObservableList = FXCollections.observableList(SubscriptionList);

        DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        // Custom rendering of the table cell.
        usersStartColumn.setCellFactory(column -> {
            return new TableCell<UsersSubs, LocalDate>() {
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
        usersEndColumn.setCellFactory(column -> {
            return new TableCell<UsersSubs, LocalDate>() {
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



        usersTable.setItems(usersObservableList);


        usersTable.setRowFactory(tv -> new TableRow<UsersSubs>() {
            @Override
            protected void updateItem(UsersSubs item, boolean empty) {
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

    @FXML
    protected void onAktivniButtonClick(){
        List<UsersSubs> filteredList = new ArrayList<>(SubscriptionList);

        filteredList = filteredList.stream()
                .filter(UsersSubs::isActive)
                .collect(Collectors.toList());
        usersTable.setItems(FXCollections.observableList(filteredList));
    }

    @FXML
    protected void onNeaktivniButtonClick(){
        List<UsersSubs> filteredList = new ArrayList<>(SubscriptionList);

        filteredList = filteredList.stream()
                .filter(Predicate.not(UsersSubs::isActive))
                .collect(Collectors.toList());
        usersTable.setItems(FXCollections.observableList(filteredList));
    }

    @FXML
    protected void onPauziraniButtonClick(){
        List<UsersSubs> filteredList = new ArrayList<>(SubscriptionList);

        filteredList = filteredList.stream()
                .filter(UsersSubs::isFrozen)
                .collect(Collectors.toList());
        usersTable.setItems(FXCollections.observableList(filteredList));
    }

    @FXML
    protected void onPredIstekomButtonClick(){
        List<UsersSubs> filteredList = new ArrayList<>(SubscriptionList);
        filteredList = filteredList.stream()
                .filter(usersSub -> usersSub.getSubEnd().isAfter(LocalDate.now()))
                .filter(usersSub -> usersSub.getSubEnd().isBefore(LocalDate.now().plusDays(10)))
                .collect(Collectors.toList());
        usersTable.setItems(FXCollections.observableList(filteredList));
    }


    @FXML
    protected void onSearchButtonClick() {

        List<UsersSubs> filteredList = new ArrayList<>(SubscriptionList);
        String searchInputText = searchInput.getText();
        //Pretraživanje po imenu
        if (comboBoxSearch.getValue().toString().equals("Ime")) {
            String searchInputName = searchInput.getText();
            if (searchInputName.isEmpty() == false) {
                filteredList = filteredList.stream()
                        .filter(user -> user.getName().toLowerCase().contains(searchInputName.toLowerCase()))
                        .collect(Collectors.toList());
            }
        }
        //Pretraživanje po prezimenu
        else if (comboBoxSearch.getValue().toString().equals("Prezime") ) {

            String searchInputSurname = searchInput.getText();
            if (searchInputSurname.isEmpty() == false) {
                filteredList = filteredList.stream()
                        .filter(user -> user.getSurname().toLowerCase().contains(searchInputSurname.toLowerCase()))
                        .collect(Collectors.toList());
            }
        }

        //Pretraživanje po datumuPočetka
        else if (comboBoxSearch.getValue().toString().equals( "Datum početka")) {

            filteredList = filteredList.stream()
                    .filter(usersSub -> usersSub.getSubStart().isEqual(datePickerField.getValue()))
                    .collect(Collectors.toList());

        }

        //Pretraživanje po datumuKraja
        else if (comboBoxSearch.getValue().toString() .equals("Datum kraja") ) {
            LocalDate date = datePickerField.getValue();
            filteredList = filteredList.stream()
                    .filter(usersSub -> usersSub.getSubEnd().isEqual(datePickerField.getValue()))
                    .collect(Collectors.toList());

        }

        //Pretraživanje po Tipu članarine
        else if (comboBoxSearch.getValue().toString().equals("Tip članarine") ) {
            String searchInputSubscription = comboBoxStatus.getValue().toString();
            if (comboBoxStatus.getValue().toString() .equals("Grupna") ) {
                filteredList = filteredList.stream()
                        .filter(user -> user.getTip().toLowerCase().contains(searchInputSubscription.toLowerCase()))
                        .collect(Collectors.toList());
            } else if (comboBoxStatus.getValue().toString().equals("Individualna") ) {
                filteredList = filteredList.stream()
                        .filter(user -> user.getTip().toLowerCase().contains(searchInputSubscription.toLowerCase()))
                        .collect(Collectors.toList());
            }

        } else {
            //TO DO ERROR prompt
        }
        usersTable.setItems(FXCollections.observableList(filteredList));
    }

    //Metode za swapannje search bara
    @FXML
    protected void setToDate(){
        comboBoxStatus.setVisible(false);
        comboBoxStatus.setDisable(true);
        searchInput.setVisible(false);
        searchInput.setDisable(true);
        datePickerField.setVisible(true);
        datePickerField.setDisable(false);
    }
    @FXML
    protected void setToText(){
        datePickerField.setVisible(false);
        datePickerField.setDisable(true);
        comboBoxStatus.setVisible(false);
        comboBoxStatus.setDisable(true);
        searchInput.setVisible(true);
        searchInput.setDisable(false);
    }
    @FXML
    protected void setToComboBox(){
        datePickerField.setVisible(false);
        datePickerField.setDisable(true);
        searchInput.setVisible(false);
        searchInput.setDisable(true);
        comboBoxStatus.setVisible(true);
        comboBoxStatus.setDisable(false);

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
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Uređivanje korisnika");
        popupWindow.setUserData(data);
        popupWindow.setScene(scene);
        popupWindow.show();
    }
    public void gigaChad(){
        String string =
                """
                        ⣿⣿⣿⣿⣿⣿⣿⣿⡿⠿⠛⠛⠛⠋⠉⠈⠉⠉⠉⠉⠛⠻⢿⣿⣿⣿⣿⣿⣿⣿
                        ⣿⣿⣿⣿⣿⡿⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠛⢿⣿⣿⣿⣿
                        ⣿⣿⣿⣿⡏⣀⠀⠀⠀⠀⠀⠀⠀⣀⣤⣤⣤⣄⡀⠀⠀⠀⠀⠀⠀⠀⠙⢿⣿⣿
                        ⣿⣿⣿⢏⣴⣿⣷⠀⠀⠀⠀⠀⢾⣿⣿⣿⣿⣿⣿⡆⠀⠀⠀⠀⠀⠀⠀⠈⣿⣿
                        ⣿⣿⣟⣾⣿⡟⠁⠀⠀⠀⠀⠀⢀⣾⣿⣿⣿⣿⣿⣷⢢⠀⠀⠀⠀⠀⠀⠀⢸⣿
                        ⣿⣿⣿⣿⣟⠀⡴⠄⠀⠀⠀⠀⠀⠀⠙⠻⣿⣿⣿⣿⣷⣄⠀⠀⠀⠀⠀⠀⠀⣿
                        ⣿⣿⣿⠟⠻⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠶⢴⣿⣿⣿⣿⣿⣧⠀⠀⠀⠀⠀⠀⣿
                        ⣿⣁⡀⠀⠀⢰⢠⣦⠀⠀⠀⠀⠀⠀⠀⠀⢀⣼⣿⣿⣿⣿⣿⡄⠀⣴⣶⣿⡄⣿
                        ⣿⡋⠀⠀⠀⠎⢸⣿⡆⠀⠀⠀⠀⠀⠀⣴⣿⣿⣿⣿⣿⣿⣿⠗⢘⣿⣟⠛⠿⣼
                        ⣿⣿⠋⢀⡌⢰⣿⡿⢿⡀⠀⠀⠀⠀⠀⠙⠿⣿⣿⣿⣿⣿⡇⠀⢸⣿⣿⣧⢀⣼
                        ⣿⣿⣷⢻⠄⠘⠛⠋⠛⠃⠀⠀⠀⠀⠀⢿⣧⠈⠉⠙⠛⠋⠀⠀⠀⣿⣿⣿⣿⣿
                        ⣿⣿⣧⠀⠈⢸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠟⠀⠀⠀⠀⢀⢃⠀⠀⢸⣿⣿⣿⣿
                        ⣿⣿⡿⠀⠴⢗⣠⣤⣴⡶⠶⠖⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⡸⠀⣿⣿⣿⣿
                        ⣿⣿⣿⡀⢠⣾⣿⠏⠀⠠⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠛⠉⠀⣿⣿⣿⣿
                        ⣿⣿⣿⣧⠈⢹⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⣿⣿⣿⣿
                        ⣿⣿⣿⣿⡄⠈⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣴⣾⣿⣿⣿⣿⣿
                        ⣿⣿⣿⣿⣧⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿
                        ⣿⣿⣿⣿⣷⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣴⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
                        ⣿⣿⣿⣿⣿⣦⣄⣀⣀⣀⣀⠀⠀⠀⠀⠘⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
                        ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⡄⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
                        ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⠀⠀⠀⠙⣿⣿⡟⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿
                        ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠇⠀⠁⠀⠀⠹⣿⠃⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿
                        ⣿⣿⣿⣿⣿⣿⣿⣿⡿⠛⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⢐⣿⣿⣿⣿⣿⣿⣿⣿⣿
                        ⣿⣿⣿⣿⠿⠛⠉⠉⠁⠀⢻⣿⡇⠀⠀⠀⠀⠀⠀⢀⠈⣿⣿⡿⠉⠛⠛⠛⠉⠉
                        ⣿⡿⠋⠁⠀⠀⢀⣀⣠⡴⣸⣿⣇⡄⠀⠀⠀⠀⢀⡿⠄⠙⠛⠀⣀⣠⣤⣤⠄""";
    }
}