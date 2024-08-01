package Gui.View;

import Gui.Controller.StartController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    public Scene scene;
    public static Stage mainStage;
    public static StartController controller;

    public void start(Stage primaryStage) throws IOException {
        primaryStage.setResizable(false);
        App.mainStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartPage.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        scene = new Scene(root);
        primaryStage.setTitle("Sprinter");
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Static/sprinterLogo2.jpeg"))); // sprinterLogo1.jpg
        primaryStage.getIcons().add(icon);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();

        if (root instanceof AnchorPane) {
            AnchorPane anchorPane = (AnchorPane) root;
            ImageView imageView = new ImageView();
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Static/orange.jpg")));
            imageView.setOnMouseClicked(event -> controller.handleImageClick("Made by \n Mazen Ahmed - Haya Shalaby \n Team Digital Demand & Solution \n Summer Internship \n1/8/2024"));
            imageView.setImage(image);
            imageView.setFitWidth(70); // Set the width of the image
            imageView.setFitHeight(70); // Set the height of the image
            AnchorPane.setTopAnchor(imageView, 20.0); // 10 px from top
            AnchorPane.setRightAnchor(imageView, 20.0); // 10 px from right
            anchorPane.getChildren().add(imageView);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
