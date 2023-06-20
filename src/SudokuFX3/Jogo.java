/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package SudokuFX3;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Jogo extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Jogo.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sudoku");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static Object changeScene(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(Jogo.class.getResource(fxml));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            return loader.getController();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Stopping");
        MultiClient.stop();
        if (PlayFXMLController.readMessage != null) {
            System.out.println("Stopping");
            PlayFXMLController.readMessage.stop();
        }
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
