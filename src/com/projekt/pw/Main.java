package com.projekt.pw;

import com.projekt.pw.gui.MenuController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

public class Main extends Application{

    public static void main(String[] args){
        launch(args);
    }

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/menu.fxml"));
        Pane pane = loader.load();
        MenuController menu = loader.getController();
        menu.setMain(this);
        Scene scene = new Scene(pane,1000,600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Aplikacja");
        primaryStage.setResizable(false);
        primaryStage.show();
        onClose(primaryStage);
        this.stage = primaryStage;
    }

    public void onClose(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Wyjście");
                alert.setHeaderText("Quiz Together");
                alert.setContentText("Czy na pewno wyjść?");
                Optional<ButtonType> buttonType = alert.showAndWait();

                if(buttonType.get() == ButtonType.OK){
                    Platform.exit();
                }else{
                    event.consume();
                }
            }
        });
    }

    public Stage getStage() {
        return stage;
    }
}
