package com.projekt.pw.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import java.util.Optional;

public class SummaryController {

    private GameController gameController;

    @FXML
    private Label labelGratulacje;

    @FXML
    private Label labelNastepnymLepiej;

    @FXML
    private Label labelNazwaGr1;

    @FXML
    private Label labelNazwaGr2;

    @FXML
    private Label labelRezultat;

    @FXML
    void onActionOneMore(ActionEvent event) {
        try {
            gameController.getMenuController().getMain().start(gameController.getMenuController().getMain().getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onActionExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Wyjście");
        alert.setHeaderText("Quiz Together");
        alert.setContentText("Czy na pewno wyjść?");
        Optional<ButtonType> buttonType = alert.showAndWait();

        if(buttonType.get() == ButtonType.OK){
            Platform.exit();
        }
    }

    public void setGoodScore(){
        labelGratulacje.setVisible(true);
        labelNastepnymLepiej.setVisible(false);
    }

    public void setBadScore(){
        labelGratulacje.setVisible(false);
        labelNastepnymLepiej.setVisible(true);
    }

    public void setLabelNamePlayer1(String NazwaGr1) {
        this.labelNazwaGr1.setText(NazwaGr1);
    }

    public void setLabelNamePlayer2(String NazwaGr2) {
        this.labelNazwaGr2.setText(NazwaGr2);
    }

    public void setLabelScore(String Rezultat) {
        this.labelRezultat.setText(Rezultat);
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

}
