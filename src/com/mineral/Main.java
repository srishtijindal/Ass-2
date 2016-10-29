package com.mineral;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("view/dashboard.fxml"));
    primaryStage.initStyle(StageStyle.TRANSPARENT);
    primaryStage.setTitle("Mineral cards");

    Scene scene = new Scene(root, 231, 373);
    scene
        .getStylesheets()
        .add(this.getClass().getResource("resources/style/fx.css").toExternalForm());
    primaryStage.setScene(scene);

    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();
    primaryStage.setX(bounds.getMinX());
    primaryStage.setY(bounds.getMinY());
    primaryStage.setWidth(bounds.getWidth());
    primaryStage.setHeight(bounds.getHeight());

    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
