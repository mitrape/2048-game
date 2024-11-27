package org.example.game2048;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 820, 687);

        HelloController game = fxmlLoader.getController();

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    game.moveUp();
                    break;
                case S:
                    game.moveDown();
                    break;
                case A:
                    game.moveLeft();
                    break;
                case D:
                    game.moveRight();
                    break;
            }
        });

        stage.setTitle("2048");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
