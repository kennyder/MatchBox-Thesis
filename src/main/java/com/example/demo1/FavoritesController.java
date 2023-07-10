package com.example.demo1;

import com.licenta.dao.EchipaFavorita;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class FavoritesController implements Initializable {

    @FXML
    private AnchorPane scenePane;
    @FXML
    Button backToMain;
    @FXML
    Button exit;
    Stage stage;
    @FXML
    private ListView echipe;
    private ObservableList<String> echipeFavoriteOL = FXCollections.observableArrayList();

    public void Exit(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You're about to exit");
        alert.setContentText("Are you sure you want to exit?");

        if(alert.showAndWait().get() == ButtonType.OK){
            stage = (Stage) scenePane.getScene().getWindow();
            System.out.println("Exiting...");
            stage.close();
        }
    }

    public void handleBackToMainBtn() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Stage window = (Stage) backToMain.getScene().getWindow();
        window.setScene(new Scene(root, 602, 638));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ShareData sd = ShareData.getInstance();
        Map<Long, EchipaFavorita> map = sd.getListaEchipeFavorite();

        for (Long id:map.keySet()){
            echipeFavoriteOL.add(map.get(id).toString());
        }
        echipe.setItems(echipeFavoriteOL);
    }
}
