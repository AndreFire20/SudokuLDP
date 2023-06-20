/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package SudokuFX3;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class NameFXMLController implements Initializable {

    @FXML
    TextField nameField;

    @FXML
    private void play() {
        if (nameField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Name can not be empty", ButtonType.OK);
            return;
        }
        PlayFXMLController controller = (PlayFXMLController) Jogo.changeScene("PlayFXML.fxml");
        controller.setName(nameField.getText());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
