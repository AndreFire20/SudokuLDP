/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package SudokuFX3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class PlayFXMLController implements Initializable {

    @FXML
    ImageView back;

    @FXML
    GridPane gridPane;

    @FXML
    Pane pane;

    @FXML
    Label name, time;

    String selected = "";

    Button selectedCell;

    final int ServerPort = 1234;

    private Node loadFXML(String name) {
        stopTimer();
        try {
            FXMLLoader loader = new FXMLLoader(Jogo.class.getResource(name));
            if (name.toLowerCase().contains("win")) {
                WinFXMLController controller = loader.getController();
                controller.setTime(time.getText());
            }
            MultiClient.stop();
            return loader.load();
        } catch (IOException e) {
            return null;
        }
    }

    private void select(String text) {
        if (selectedCell != null) {
            selectedCell.setText(text);
            selectedCell.setDisable(true);
            selected = "";
            if (filled(cells)) {
                if (checkSolution(cells)) {
                    pane.getChildren().add(loadFXML("WinFXML.fxml"));
                    try {
                        MultiClient.dos.writeUTF("finish#" + System.currentTimeMillis());
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                } else {
                    pane.getChildren().add(loadFXML("LostFXML.fxml"));
                }
                pane.setVisible(true);
            }
        }
    }

    @FXML
    private void btnOne() {
        selected = "1";
        select(selected);
    }

    @FXML
    private void btnTwo() {
        selected = "2";
        select(selected);
    }

    @FXML
    private void btnThree() {
        selected = "3";
        select(selected);
    }

    @FXML
    private void btnFour() {
        selected = "4";
        select(selected);
    }

    @FXML
    private void btnFive() {
        selected = "5";
        select(selected);
    }

    @FXML
    private void btnSix() {
        selected = "6";
        select(selected);
    }

    @FXML
    private void btnSeven() {
        selected = "7";
        select(selected);
    }

    @FXML
    private void btnEight() {
        selected = "8";
        select(selected);
    }

    @FXML
    private void btnNine() {
        selected = "9";
        select(selected);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    private Jogador jogador;
    private SudokuGrid grid;
    private Button[][] cells;
    private Timeline timeline;
    private int seconds = 0;
    private String PLAYER1_IMAGE = "game_area_player1.png";
    private String PLAYER2_IMAGE = "game_area_player2.png";
    public static Thread readMessage;

    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        try {
            MultiClient.main(null);
            readMessage = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (MultiClient.s.isConnected()) {
                            String msg = MultiClient.dis.readUTF();
                            if (msg.equals("start")) {
                                Platform.runLater(() -> {
                                    startTimer();
                                    updateGrid(cells);
                                });
                            }
                            if (msg.equals("stop")) {
                                Platform.runLater(() -> {
                                    stopTimer();
                                    loadFXML("LostFXML.fxml");
                                });
                            }
                        }
                    } catch (IOException e) {
                    }
                }
            });

            readMessage.start();

            grid = new SudokuGrid();
            jogador = new Jogador();

            timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                seconds++;
                updateTimerLabel();
            }));
            timeline.setCycleCount(Animation.INDEFINITE);

            gridPane.setPadding(new Insets(10, 10, 10, 10));

            cells = new Button[9][9];
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    Button cell = new Button();
                    cell.setPrefWidth(30);
                    cell.setPrefHeight(30);
                    cell.setAlignment(Pos.CENTER);
                    cell.setDisable(true);

                    cell.focusedProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                            if (t1) {
                                selectedCell = cell;
                            }
                        }
                    });
                    cell.setOnAction((ActionEvent t) -> {
                        selectedCell = cell;
//                    if (!selected.isEmpty()) {
//                        cell.setText(selected);
//                        cell.setDisable(true);
//                        selected = "";
//                        if (filled(cells)) {
//                            if (checkSolution(cells)) {
//                                showMessage("Solução correta!");
//                            } else {
//                                showMessage("Solução incorreta!");
//                            }
//                        }
//                    }
                    });

                    gridPane.add(cell, col, row);
                    cells[row][col] = cell;
                }
            }

            grid.generateInitialGrid();
        } catch (Exception e) {

        }
    }

    private void updateGrid(Button[][] cells) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int value = grid.getValue(row, col);
                if (value != 0) {
                    cells[row][col].setText(Integer.toString(value));
                    cells[row][col].setDisable(true);
                } else {
                    cells[row][col].setText("");
                    cells[row][col].setDisable(false);
                }
            }
        }
    }

    private boolean checkSolution(Button[][] cells) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String value = cells[row][col].getText();
                if (value.isEmpty()) {
                    return false;
                }
                if (!grid.isValueValid(row, col, Integer.parseInt(value))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private boolean filled(Button[][] cells) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String value = cells[row][col].getText();
                if (value.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void startTimer() {
        timeline.play();
    }

    private void stopTimer() {
        timeline.stop();
    }

    private void resetTimer() {
        seconds = 0;
        updateTimerLabel();
    }

    private void updateTimerLabel() {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int remainingSeconds = seconds % 60;

        String time = String.format("%02d:%02d", minutes, remainingSeconds);
        this.time.setText(time);
    }
}
