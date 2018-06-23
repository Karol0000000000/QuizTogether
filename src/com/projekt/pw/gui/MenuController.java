package com.projekt.pw.gui;

import com.projekt.pw.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class MenuController {

    private BorderPane borderPane;
    private FXMLLoader loader;
    private Main main;

    @FXML
    private Pane paneMenu;

    @FXML
    private TextField textFieldNazwaGr1;

    @FXML
    private TextField textFieldNazwaGr2;

    @FXML
    public void initialize(){

        loader = new FXMLLoader(this.getClass().getResource("/game.fxml"));
        try {
            borderPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GameController graController = loader.getController();
        graController.setMenuController(this);
        graController = loader.getController();
        textFieldNazwaGr1.textProperty().bindBidirectional(graController.getNameProperty1());
        textFieldNazwaGr2.textProperty().bindBidirectional(graController.getNameProperty2());
    }

    @FXML
    public void onActionNext(){
        setContentScreen(borderPane);
    }

    @FXML
    public void onActionAboutProgram(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O programie");
        alert.setHeaderText("Quiz Together");
        alert.setContentText("Dwoch graczy gra w tym samym czasie, pracujac na wspolny rezultat koncowy. " +
                "Punkty za pytanie oblicza sie w nastepujacy sposob: 15 - czas, jaki gracz potrzebowal na pozytywną " +
                "odpowiedz na dane pytanie. Każdy z graczy odpowiada na 10 pytań");

        alert.showAndWait();
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Main getMain() {
        return main;
    }

    public void setContentScreen(Node node) {
        paneMenu.getChildren().clear();
        paneMenu.getChildren().add(node);
    }


}
