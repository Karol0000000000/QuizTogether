package com.projekt.pw.gui;

import com.projekt.pw.mechanics.ClassToSelectFromDB;
import com.projekt.pw.mechanics.Players;
import com.projekt.pw.mechanics.CommonData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GameController {


    private Thread threadPlayer1, threadPlayer2;
    private Boolean[] whoseTurn;
    private Map<Integer,String> answersPLayer1;
    private Map<Integer,String> answersPLayer2;
    private int numberQuestion1, numberQuestion2;
    private StringProperty nameProperty1;
    private StringProperty nameProperty2;
    private ClassToSelectFromDB classToSelectFromDB;
    private Thread threadSelectDB;
    private CommonData commonData;
    private MenuController menuController;
    private BorderPane borderPane;
    private SummaryController summaryController;
    private double tempPoints;
    private Players player1;
    private Players player2;
    private Object monitorDB;
    private Object monitorP1, monitorP2;

    @FXML
    private VBox vBoxGr1;

    @FXML
    private VBox vBoxGr2;

    @FXML
    private Label labelSumaPunktow;

    @FXML
    private Label labelCzasGr1;

    @FXML
    private Label labelCzasGr2;

    @FXML
    private Button buttonStart;

    @FXML
    private Label labelZlaOdpGr2;

    @FXML
    private Label labelDobraOdpGr2;

    @FXML
    private Label labelZlaOdpGr1;

    @FXML
    private Label labelDobraOdpGr1;

    @FXML
    private Label labelAGr1;

    @FXML
    private Label labelBGr1;

    @FXML
    private Label labelCGr1;

    @FXML
    private Label labelDGr1;

    @FXML
    private Label labelAGr2;

    @FXML
    private Label labelBGr2;

    @FXML
    private Label labelCGr2;

    @FXML
    private Label labelDGr2;

    @FXML
    private Label nazwaGr1;

    @FXML
    private Label nazwaGr2;

    @FXML
    private Label labelIlePytanGracz1;

    @FXML
    private Label labelPytanieGracz1;

    @FXML
    private Label labelIlePytanGracz2;

    @FXML
    private Label labelPytanieGracz2;

    @FXML
    public void initialize() {
        monitorDB = new Object();
        monitorP1 = new Object();
        monitorP2 = new Object();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/summary.fxml"));
        try {
            borderPane = loader.load();
            summaryController = loader.getController();
            summaryController.setGameController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        classToSelectFromDB = new ClassToSelectFromDB(monitorDB);
        whoseTurn = new Boolean[]{true, true};
        numberQuestion1 = 0;
        numberQuestion2 = 0;
        nameProperty1 = new SimpleStringProperty("Gracz 1");
        nameProperty2 = new SimpleStringProperty("Gracz 2");
        nazwaGr1.textProperty().bind(nameProperty1);
        nazwaGr2.textProperty().bind(nameProperty2);
        answersPLayer1 = new HashMap<Integer, String>();
        answersPLayer2 = new HashMap<Integer, String>();
        commonData = new CommonData();
        threadSelectDB = new Thread(classToSelectFromDB);
        classToSelectFromDB.setThreadFinish(false);
        player1 = new Players(this, whoseTurn, 0, answersPLayer1, classToSelectFromDB, commonData, monitorDB, monitorP1);
        player2 = new Players(this, whoseTurn, 1, answersPLayer2, classToSelectFromDB, commonData, monitorDB, monitorP2);
        player1.setThreadFinish(false);
        player2.setThreadFinish(false);
        threadPlayer1 = new Thread(player1);
        threadPlayer2 = new Thread(player2);
        setVisibleGoodAnswer1(false);
        setVisibleGoodAnswer2(false);
        setVisibleBadAnswer1(false);
        setVisibleBadAnswer2(false);
        setVisibleTime1(false);
        setVisibleTime2(false);
    }

    @FXML
    void onActionButtonAGr2(ActionEvent event) {
        while(!whoseTurn[1]) {
            numberQuestion2++;
            answersPLayer2.put(numberQuestion2,"A");
            whoseTurn[1] = true;
            synchronized(monitorP2){
                monitorP2.notify();
            }
        }
    }

    @FXML
    void onActionButtonBGr2(ActionEvent event) {
        while(!whoseTurn[1]) {
            numberQuestion2++;
            answersPLayer2.put(numberQuestion2,"B");
            whoseTurn[1] = true;
            synchronized(monitorP2){
                monitorP2.notify();
            }
        }
    }

    @FXML
    void onActionButtonCGr2(ActionEvent event) {
        while(!whoseTurn[1]) {
            numberQuestion2++;
            answersPLayer2.put(numberQuestion2,"C");
            whoseTurn[1] = true;
            synchronized(monitorP2){
                monitorP2.notify();
            }
        }
    }

    @FXML
    void onActionButtonDGr2(ActionEvent event) {
        while(!whoseTurn[1]) {
            numberQuestion2++;
            answersPLayer2.put(numberQuestion2,"D");
            whoseTurn[1] = true;
            synchronized(monitorP2){
                monitorP2.notify();
            }
        }
    }

    @FXML
    void onActionKeyGr1(KeyEvent event) {
        if(!whoseTurn[0]){
            if (event.getCode() == KeyCode.DIGIT1) {
                numberQuestion1++;
                answersPLayer1.put(numberQuestion1,"A");
                whoseTurn[0] = true;
                synchronized(monitorP1){
                    monitorP1.notify();
                }
            } else if (event.getCode() == KeyCode.DIGIT2) {
                numberQuestion1++;
                answersPLayer1.put(numberQuestion1,"B");
                whoseTurn[0] = true;
                synchronized(monitorP1){
                    monitorP1.notify();
                }
            } else if (event.getCode() == KeyCode.DIGIT3) {
                numberQuestion1++;
                answersPLayer1.put(numberQuestion1,"C");
                whoseTurn[0] = true;
                synchronized(monitorP1){
                    monitorP1.notify();
                }
            } else if (event.getCode() == KeyCode.DIGIT4) {
                numberQuestion1++;
                answersPLayer1.put(numberQuestion1,"D");
                whoseTurn[0] = true;
                synchronized(monitorP1){
                    monitorP1.notify();
                }
            }
        }
    }

    @FXML
    void onMouseClickedFinishGame(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Wyjście");
        alert.setHeaderText("Quiz Together");
        alert.setContentText("Czy na pewno zakończyć?");
        Optional<ButtonType> buttonType = alert.showAndWait();

        if(buttonType.get() == ButtonType.OK) {
            displaySummary();
        }
    }

    public void displaySummary() {
            stopThreads();
            menuController.getMain().onClose(menuController.getMain().getStage());
            menuController.setContentScreen(borderPane);
            summaryController.setLabelNamePlayer1(nazwaGr1.getText());
            summaryController.setLabelNamePlayer2(nazwaGr2.getText());
            if(tempPoints > 40.0){
                summaryController.setGoodScore();}
            else {
                summaryController.setBadScore();}
            summaryController.setLabelScore("Rezultat: " + String.format("%.2f", tempPoints));
    }

    @FXML
    void onActionStart(ActionEvent event) {

        buttonStart.setDisable(true);
        threadSelectDB.start();
        threadPlayer1.start();
        threadPlayer2.start();
        menuController.getMain().getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                stopThreads();
            }
        });
    }

    public void stopThreads(){
        if(threadPlayer1.isAlive()){
            player1.setThreadFinish(true);
        }
        if(threadPlayer2.isAlive()){
            player2.setThreadFinish(true);
        }
        if(threadSelectDB.isAlive()){
            classToSelectFromDB.setThreadFinish(true);
        }
        synchronized(monitorDB){
            monitorDB.notify();
        }
        synchronized (monitorP1){
            monitorP1.notify();
        }
        synchronized (monitorP2){
            monitorP2.notify();
        }
    }

    public void setLabelAPlayer1(String labelAGr1) {
        this.labelAGr1.setText(labelAGr1);
    }

    public void setLabelBPlayer1(String labelBGr1) {
        this.labelBGr1.setText(labelBGr1);
    }

    public void setLabelCPlayer1(String labelCGr1) {
        this.labelCGr1.setText(labelCGr1);
    }

    public void setLabelDPlayer1(String labelDGr1) {
        this.labelDGr1.setText(labelDGr1);
    }

    public void setLabelAPlayer2(String labelAGr1) {
        this.labelAGr2.setText(labelAGr1);
    }

    public void setLabelBPlayer2(String labelBGr1) {
        this.labelBGr2.setText(labelBGr1);
    }

    public void setLabelCPlayer2(String labelCGr1) {
        this.labelCGr2.setText(labelCGr1);
    }

    public void setLabelDPlayer2(String labelDGr1) {
        this.labelDGr2.setText(labelDGr1);
    }

    public void setLabelHowMuchQuestionGracz1(String labelIlePytanGracz1) {
        this.labelIlePytanGracz1.setText(labelIlePytanGracz1);
    }

    public void setLabelQuestionGracz1(String labelPytanieGracz1) {
        this.labelPytanieGracz1.setText(labelPytanieGracz1);
    }

    public void setLabelHowMuchQuestionGracz2(String labelIlePytanGracz2) {
        this.labelIlePytanGracz2.setText(labelIlePytanGracz2);
    }

    public void setLabelQuestionGracz2(String labelPytanieGracz2) {
        this.labelPytanieGracz2.setText(labelPytanieGracz2);
    }

    public StringProperty getNameProperty1() {
        return nameProperty1;
    }

    public StringProperty getNameProperty2() {
        return nameProperty2;
    }

    public MenuController getMenuController() {
        return menuController;
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    public void setDisableVBox1(){
        vBoxGr1.setDisable(true);
    }

    public void setDisableVBox2(){
        vBoxGr2.setDisable(true);
    }

    public void setTotalPoints(double punkty){
        labelSumaPunktow.setText(String.format("Wspólnie zdobyte punkty: %.2f",punkty));
        tempPoints = punkty;
    }

    public void setTime1(double time){
        labelCzasGr1.setText(String.valueOf(time)+"s");
    }

    public void setTime2(double time){
        labelCzasGr2.setText(String.valueOf(time)+"s");
    }

    public void setVisibleTime1(boolean b){
        labelCzasGr1.setVisible(b);
    }

    public void setVisibleTime2(boolean b){
        labelCzasGr2.setVisible(b);
    }

    public void setVisibleGoodAnswer1(boolean b){
        labelDobraOdpGr1.setVisible(b);
    }

    public void setVisibleGoodAnswer2(boolean b){
        labelDobraOdpGr2.setVisible(b);
    }

    public void setVisibleBadAnswer1(boolean b){
        labelZlaOdpGr1.setVisible(b);
    }

    public void setVisibleBadAnswer2(boolean b){
        labelZlaOdpGr2.setVisible(b);
    }

    public Label getLabelAGr1() {
        return labelAGr1;
    }

    public Label getLabelBGr1() {
        return labelBGr1;
    }

    public Label getLabelCGr1() {
        return labelCGr1;
    }

    public Label getLabelDGr1() {
        return labelDGr1;
    }

    public Label getLabelAGr2() {
        return labelAGr2;
    }

    public Label getLabelBGr2() {
        return labelBGr2;
    }

    public Label getLabelCGr2() {
        return labelCGr2;
    }

    public Label getLabelDGr2() {
        return labelDGr2;
    }
}
