package hr.controllers.controllers;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import main.MainApp;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class MenuBarController {
    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
    public void showUserTable() throws IOException {

        Scene scene = new Scene(loadFXML("mainScreen"));


//        byte[] emojiByteCode = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x92, (byte)0xAA};
//        String emoji = new String(emojiByteCode, StandardCharsets.UTF_8);
//        Image image = new Image(String.valueOf(MenuBarController.class.getResource("/flexed-biceps_1f4aa.png")));
//        scene.setCursor(new ImageCursor(image, image.getHeight(), image.getHeight()));
//        scene.getCursor();

        MainApp.getStage().setTitle("Tablica Korisnika ");
        SetFullScreen(scene);


    }
    public void showAddUser() throws IOException {

        Scene scene = new Scene(loadFXML("addNewUser"));
        Screen screen = Screen.getPrimary();
        MainApp.getStage().setTitle("Dodaj novog korisnika");
        SetFullScreen(scene);

    }

    private void SetFullScreen(Scene scene) {
        MainApp.getStage().setX(bounds.getMinX());
        MainApp.getStage().setY(bounds.getMinY());
        MainApp.getStage().setWidth(bounds.getWidth());
        MainApp.getStage().setHeight(bounds.getHeight());
        MainApp.getStage().setScene(scene);
        MainApp.getStage().close();
        MainApp.getStage().show();
    }
}
