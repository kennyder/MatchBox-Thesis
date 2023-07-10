package com.example.demo1;

import com.licenta.dao.Joc;
import com.licenta.dao.Team1;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class CompareStatsControllerTeams implements Initializable {

    @FXML
    Button backToMain;
    @FXML
    ImageView logo1;
    @FXML
    ImageView logo2;
    @FXML
    Label logo1Label;
    @FXML
    Label logo2Label;
    @FXML
    TextField textSearch1;
    @FXML
    TextField textSearch2;
    @FXML
    Button search1;
    @FXML
    Button search2;
    @FXML
    private ListView team1;
    @FXML
    private ListView team2;
    @FXML
    Button backToPlayers;
    @FXML
    Button exit;
    @FXML
    private AnchorPane scenePane;
    Stage stage;
    private ObservableList<String> items = FXCollections.observableArrayList();
    private ObservableList<String> items2 = FXCollections.observableArrayList();

    @FXML
    Label Name;

    public void handleBackToMainBtn() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Stage window = (Stage) backToMain.getScene().getWindow();
        window.setScene(new Scene(root, 602, 638));
    }

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

    public void handleBackToPlayers() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("CompareStatsPlayers.fxml"));
        Stage window = (Stage) backToPlayers.getScene().getWindow();
        window.setScene(new Scene(root, 602, 638));
    }

    public void searchTeam1() throws Exception {
        String teamname1 = textSearch1.getText();
        team1.setItems(getTeamInfo(teamname1));
    }

    public void searchTeam2() throws Exception {
        String teamname2 = textSearch2.getText();
        team2.setItems(getTeamInfo(teamname2));
    }


    private  ObservableList<String> getTeamInfo(String teamName){
        ShareData sd = ShareData.getInstance();
        String body=null;
        String teamID = null;
        ObservableList<String> items= FXCollections.observableArrayList();
        if(sd.getUseAPI()){
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://api-football-v1.p.rapidapi.com/v3/teams?name=" + teamName)
                    .get()
                    .addHeader("X-RapidAPI-Key", "25f378b6damshc17fe739842f292p139f8cjsnb5482d97aa7f")
                    .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                    .build();

            try{
                Response response = client.newCall(request).execute();
                body = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }else
            body = "{\"get\":\"teams\",\"parameters\":{\"name\":\"Chelsea\"},\"errors\":[],\"results\":1,\"paging\":{\"current\":1,\"total\":1},\"response\":[{\"team\":{\"id\":49,\"name\":\"Chelsea\",\"code\":\"CHE\",\"country\":\"England\",\"founded\":1905,\"national\":false,\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/teams\\/49.png\"},\"venue\":{\"id\":519,\"name\":\"Stamford Bridge\",\"address\":\"Fulham Road\",\"city\":\"London\",\"capacity\":41841,\"surface\":\"grass\",\"image\":\"https:\\/\\/media-1.api-sports.io\\/football\\/venues\\/519.png\"}}]}\n";
     try {
         JSONParser parse = new JSONParser();
        JSONObject dataObjects = (JSONObject) parse.parse(body);
        JSONArray echipa = (JSONArray) dataObjects.get("response");
        JSONObject team = (JSONObject) ((JSONObject)echipa.get(0)).get("team");
        String logo = (String) team.get("logo");
        Image img = new Image(logo);
        logo1 = new ImageView(img);
        logo1Label.setGraphic(logo1);
        teamID =""+ (Long) team.get("id");

     } catch (ParseException e) {
         e.printStackTrace();
         throw new RuntimeException();
     }


        if(sd.getUseAPI()) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api-football-v1.p.rapidapi.com/v3/teams/statistics?league=39&season=2022&team=" + teamID)
                    .get()
                    .addHeader("X-RapidAPI-Key", "25f378b6damshc17fe739842f292p139f8cjsnb5482d97aa7f")
                    .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                    .build();
            try{
                Response response = client.newCall(request).execute();
                body = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }else {
            body = "{\"get\":\"teams\\/statistics\",\"parameters\":{\"league\":\"39\",\"season\":\"2020\",\"team\":\"33\"},\"errors\":[],\"results\":11,\"paging\":{\"current\":1,\"total\":1},\"response\":{\"league\":{\"id\":39,\"name\":\"Premier League\",\"country\":\"England\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/leagues\\/39.png\",\"flag\":\"https:\\/\\/media-3.api-sports.io\\/flags\\/gb.svg\",\"season\":2020},\"team\":{\"id\":33,\"name\":\"Manchester United\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/33.png\"},\"form\":\"LWLWDLWWWWDWWDWWWDWLDWDDWDDWWWWWDWLLDW\",\"fixtures\":{\"played\":{\"home\":19,\"away\":19,\"total\":38},\"wins\":{\"home\":9,\"away\":12,\"total\":21},\"draws\":{\"home\":4,\"away\":7,\"total\":11},\"loses\":{\"home\":6,\"away\":0,\"total\":6}},\"goals\":{\"for\":{\"total\":{\"home\":38,\"away\":35,\"total\":73},\"average\":{\"home\":\"2.0\",\"away\":\"1.8\",\"total\":\"1.9\"},\"minute\":{\"0-15\":{\"total\":9,\"percentage\":\"12.50%\"},\"16-30\":{\"total\":10,\"percentage\":\"13.89%\"},\"31-45\":{\"total\":7,\"percentage\":\"9.72%\"},\"46-60\":{\"total\":11,\"percentage\":\"15.28%\"},\"61-75\":{\"total\":15,\"percentage\":\"20.83%\"},\"76-90\":{\"total\":12,\"percentage\":\"16.67%\"},\"91-105\":{\"total\":8,\"percentage\":\"11.11%\"},\"106-120\":{\"total\":null,\"percentage\":null}}},\"against\":{\"total\":{\"home\":28,\"away\":16,\"total\":44},\"average\":{\"home\":\"1.5\",\"away\":\"0.8\",\"total\":\"1.2\"},\"minute\":{\"0-15\":{\"total\":8,\"percentage\":\"17.78%\"},\"16-30\":{\"total\":5,\"percentage\":\"11.11%\"},\"31-45\":{\"total\":12,\"percentage\":\"26.67%\"},\"46-60\":{\"total\":8,\"percentage\":\"17.78%\"},\"61-75\":{\"total\":5,\"percentage\":\"11.11%\"},\"76-90\":{\"total\":5,\"percentage\":\"11.11%\"},\"91-105\":{\"total\":2,\"percentage\":\"4.44%\"},\"106-120\":{\"total\":null,\"percentage\":null}}}},\"biggest\":{\"streak\":{\"wins\":5,\"draws\":2,\"loses\":2},\"wins\":{\"home\":\"9-0\",\"away\":\"1-4\"},\"loses\":{\"home\":\"1-6\",\"away\":null},\"goals\":{\"for\":{\"home\":9,\"away\":4},\"against\":{\"home\":6,\"away\":2}}},\"clean_sheet\":{\"home\":6,\"away\":7,\"total\":13},\"failed_to_score\":{\"home\":3,\"away\":5,\"total\":8},\"penalty\":{\"scored\":{\"total\":10,\"percentage\":\"100.00%\"},\"missed\":{\"total\":0,\"percentage\":\"0%\"},\"total\":10},\"lineups\":[{\"formation\":\"4-2-3-1\",\"played\":36},{\"formation\":\"4-3-1-2\",\"played\":2}],\"cards\":{\"yellow\":{\"0-15\":{\"total\":1,\"percentage\":\"1.56%\"},\"16-30\":{\"total\":5,\"percentage\":\"7.81%\"},\"31-45\":{\"total\":11,\"percentage\":\"17.19%\"},\"46-60\":{\"total\":17,\"percentage\":\"26.56%\"},\"61-75\":{\"total\":10,\"percentage\":\"15.63%\"},\"76-90\":{\"total\":20,\"percentage\":\"31.25%\"},\"91-105\":{\"total\":null,\"percentage\":null},\"106-120\":{\"total\":null,\"percentage\":null}},\"red\":{\"0-15\":{\"total\":null,\"percentage\":null},\"16-30\":{\"total\":1,\"percentage\":\"100.00%\"},\"31-45\":{\"total\":null,\"percentage\":null},\"46-60\":{\"total\":null,\"percentage\":null},\"61-75\":{\"total\":null,\"percentage\":null},\"76-90\":{\"total\":null,\"percentage\":null},\"91-105\":{\"total\":null,\"percentage\":null},\"106-120\":{\"total\":null,\"percentage\":null}}}}}\n";
        }


        try {
            JSONParser parse = new JSONParser();
            JSONObject dataObject = null;
            dataObject = (JSONObject) parse.parse(body);
            JSONObject statistici = (JSONObject) dataObject.get("response");

            JSONObject fixtures = (JSONObject) statistici.get("fixtures");
            JSONObject played = (JSONObject) fixtures.get("played");
            JSONObject wins = (JSONObject) fixtures.get("wins");
            JSONObject draws = (JSONObject) fixtures.get("draws");
            JSONObject defeats = (JSONObject) fixtures.get("loses");
            JSONObject failedToScore = (JSONObject) statistici.get("failed_to_score");
            JSONObject cleanSheets = (JSONObject) statistici.get("clean_sheet");
            JSONObject biggest = (JSONObject) statistici.get("biggest");
            JSONObject biggestWins = (JSONObject) biggest.get("wins");

            List<String> team1Stats = new ArrayList<>();
            items.add("Form: " + (String) statistici.get("form"));
            items.add("Games Played: " + (Long) played.get("total"));
            items.add("Wins: " + (Long) wins.get("total"));
            items.add("Draws: " + (Long) draws.get("total"));
            items.add("Defeats: " + (Long) defeats.get("total"));
            items.add("Failed to score in: " +  (Long) failedToScore.get("total") + " matches.");
            if(cleanSheets!=null) {
                items.add("Clean sheets: " + (Long) cleanSheets.get("total"));
            }else items.add("Clean sheets: - " );
            items.add("Biggest home win: " + (String) biggestWins.get("home"));
            items.add("Biggest away win: " + (String) biggestWins.get("away"));


        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return items;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        team1.setItems(items);


        team2.setItems(items2);


    }
}
