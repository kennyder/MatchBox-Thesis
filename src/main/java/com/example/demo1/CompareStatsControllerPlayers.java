package com.example.demo1;

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
import java.util.List;
import java.util.ResourceBundle;

public class CompareStatsControllerPlayers implements Initializable {

    @FXML
    Button backToMain;
    @FXML
    private ListView player1;
    @FXML
    private ListView player2;
    @FXML
    TextField textSearch1;
    @FXML
    TextField textSearch2;
    @FXML
    Button search1;
    @FXML
    Button search2;
    @FXML
    Button exit;
    @FXML
    private AnchorPane scenePane;
    @FXML
            Button backToTeams;
    @FXML
            Button favorites;
    @FXML
    ImageView logo1;
    @FXML
    ImageView logo2;
    @FXML
    Label logoLabel;
    @FXML
    Label logo2Label;
    Stage stage;
    private ObservableList<String> items = FXCollections.observableArrayList();
    private ObservableList<String> items2 = FXCollections.observableArrayList();

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

    public void handleBackToTeams() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("CompareStats.fxml"));
        Stage window = (Stage) backToTeams.getScene().getWindow();
        window.setScene(new Scene(root, 602, 638));
    }

    public void searchPlayer1() throws Exception {
        String playername1 = textSearch1.getText();
        player1.setItems(getPlayerInfo(playername1));
    }

    public void searchPlayer2() throws Exception {
        String playername2 = textSearch2.getText();
        player2.setItems(getPlayerInfo(playername2));
    }

    public void chooseFavorite() throws Exception {

    }

    private  ObservableList<String> getPlayerInfo(String playerName) {
        ShareData sd = ShareData.getInstance();
        String body= null;
        String playerID = null;
        ObservableList<String> items= FXCollections.observableArrayList();
        if(sd.getUseAPI()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api-football-v1.p.rapidapi.com/v3/players?league=61&search=" + playerName)
                    .get()
                    .addHeader("X-RapidAPI-Key", "25f378b6damshc17fe739842f292p139f8cjsnb5482d97aa7f")
                    .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                body = response.body().string();
                System.out.println("body: " + body);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } else body = "{\"get\":\"players\",\"parameters\":{\"id\":\"276\",\"season\":\"2020\"},\"errors\":[],\"results\":1,\"paging\":{\"current\":1,\"total\":1},\"response\":[{\"player\":{\"id\":276,\"name\":\"Neymar\",\"firstname\":\"Neymar\",\"lastname\":\"da Silva Santos J\\u00fanior\",\"age\":31,\"birth\":{\"date\":\"1992-02-05\",\"place\":\"Mogi das Cruzes\",\"country\":\"Brazil\"},\"nationality\":\"Brazil\",\"height\":\"175 cm\",\"weight\":\"68 kg\",\"injured\":false,\"photo\":\"https:\\/\\/media-1.api-sports.io\\/football\\/players\\/276.png\"},\"statistics\":[{\"team\":{\"id\":85,\"name\":\"Paris Saint Germain\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/85.png\"},\"league\":{\"id\":61,\"name\":\"Ligue 1\",\"country\":\"France\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/leagues\\/61.png\",\"flag\":\"https:\\/\\/media-2.api-sports.io\\/flags\\/fr.svg\",\"season\":2020},\"games\":{\"appearences\":18,\"lineups\":15,\"minutes\":1416,\"number\":null,\"position\":\"Attacker\",\"rating\":\"7.455555\",\"captain\":false},\"substitutes\":{\"in\":3,\"out\":2,\"bench\":3},\"shots\":{\"total\":53,\"on\":21},\"goals\":{\"total\":9,\"conceded\":0,\"assists\":5,\"saves\":null},\"passes\":{\"total\":916,\"key\":61,\"accuracy\":40},\"tackles\":{\"total\":14,\"blocks\":null,\"interceptions\":9},\"duels\":{\"total\":362,\"won\":173},\"dribbles\":{\"attempts\":155,\"success\":87,\"past\":null},\"fouls\":{\"drawn\":67,\"committed\":33},\"cards\":{\"yellow\":6,\"yellowred\":1,\"red\":1},\"penalty\":{\"won\":null,\"commited\":null,\"scored\":5,\"missed\":1,\"saved\":null}},{\"team\":{\"id\":85,\"name\":\"Paris Saint Germain\",\"logo\":\"https:\\/\\/media-1.api-sports.io\\/football\\/teams\\/85.png\"},\"league\":{\"id\":2,\"name\":\"UEFA Champions League\",\"country\":\"World\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/leagues\\/2.png\",\"flag\":null,\"season\":2020},\"games\":{\"appearences\":9,\"lineups\":9,\"minutes\":746,\"number\":null,\"position\":\"Attacker\",\"rating\":\"7.355555\",\"captain\":false},\"substitutes\":{\"in\":0,\"out\":3,\"bench\":0},\"shots\":{\"total\":23,\"on\":17},\"goals\":{\"total\":6,\"conceded\":0,\"assists\":2,\"saves\":null},\"passes\":{\"total\":365,\"key\":20,\"accuracy\":33},\"tackles\":{\"total\":5,\"blocks\":1,\"interceptions\":3},\"duels\":{\"total\":182,\"won\":86},\"dribbles\":{\"attempts\":63,\"success\":33,\"past\":null},\"fouls\":{\"drawn\":44,\"committed\":11},\"cards\":{\"yellow\":3,\"yellowred\":0,\"red\":0},\"penalty\":{\"won\":null,\"commited\":null,\"scored\":1,\"missed\":0,\"saved\":null}}]}]}\n";
        try {
            JSONParser parse = new JSONParser();
            JSONObject dataObjects = (JSONObject) parse.parse(body);
            JSONArray response = (JSONArray) dataObjects.get("response");
            JSONObject player = (JSONObject) ((JSONObject) response.get(0)).get("player");
            playerID = "" + (Long) player.get("id");

        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        if(sd.getUseAPI()) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://api-football-v1.p.rapidapi.com/v3/players?id="+playerID+"&season=2022")
                    .get()
                    .addHeader("X-RapidAPI-Key", "25f378b6damshc17fe739842f292p139f8cjsnb5482d97aa7f")
                    .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                    .build();
            try{
                Response response = client.newCall(request).execute();
                body = response.body().string();
                System.out.println("body: " + body);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }else {
            body = "{\"get\":\"players\",\"parameters\":{\"id\":\"276\",\"season\":\"2020\"},\"errors\":[],\"results\":1,\"paging\":{\"current\":1,\"total\":1},\"response\":[{\"player\":{\"id\":276,\"name\":\"Neymar\",\"firstname\":\"Neymar\",\"lastname\":\"da Silva Santos J\\u00fanior\",\"age\":31,\"birth\":{\"date\":\"1992-02-05\",\"place\":\"Mogi das Cruzes\",\"country\":\"Brazil\"},\"nationality\":\"Brazil\",\"height\":\"175 cm\",\"weight\":\"68 kg\",\"injured\":false,\"photo\":\"https:\\/\\/media-1.api-sports.io\\/football\\/players\\/276.png\"},\"statistics\":[{\"team\":{\"id\":85,\"name\":\"Paris Saint Germain\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/85.png\"},\"league\":{\"id\":61,\"name\":\"Ligue 1\",\"country\":\"France\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/leagues\\/61.png\",\"flag\":\"https:\\/\\/media-2.api-sports.io\\/flags\\/fr.svg\",\"season\":2020},\"games\":{\"appearences\":18,\"lineups\":15,\"minutes\":1416,\"number\":null,\"position\":\"Attacker\",\"rating\":\"7.455555\",\"captain\":false},\"substitutes\":{\"in\":3,\"out\":2,\"bench\":3},\"shots\":{\"total\":53,\"on\":21},\"goals\":{\"total\":9,\"conceded\":0,\"assists\":5,\"saves\":null},\"passes\":{\"total\":916,\"key\":61,\"accuracy\":40},\"tackles\":{\"total\":14,\"blocks\":null,\"interceptions\":9},\"duels\":{\"total\":362,\"won\":173},\"dribbles\":{\"attempts\":155,\"success\":87,\"past\":null},\"fouls\":{\"drawn\":67,\"committed\":33},\"cards\":{\"yellow\":6,\"yellowred\":1,\"red\":1},\"penalty\":{\"won\":null,\"commited\":null,\"scored\":5,\"missed\":1,\"saved\":null}},{\"team\":{\"id\":85,\"name\":\"Paris Saint Germain\",\"logo\":\"https:\\/\\/media-1.api-sports.io\\/football\\/teams\\/85.png\"},\"league\":{\"id\":2,\"name\":\"UEFA Champions League\",\"country\":\"World\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/leagues\\/2.png\",\"flag\":null,\"season\":2020},\"games\":{\"appearences\":9,\"lineups\":9,\"minutes\":746,\"number\":null,\"position\":\"Attacker\",\"rating\":\"7.355555\",\"captain\":false},\"substitutes\":{\"in\":0,\"out\":3,\"bench\":0},\"shots\":{\"total\":23,\"on\":17},\"goals\":{\"total\":6,\"conceded\":0,\"assists\":2,\"saves\":null},\"passes\":{\"total\":365,\"key\":20,\"accuracy\":33},\"tackles\":{\"total\":5,\"blocks\":1,\"interceptions\":3},\"duels\":{\"total\":182,\"won\":86},\"dribbles\":{\"attempts\":63,\"success\":33,\"past\":null},\"fouls\":{\"drawn\":44,\"committed\":11},\"cards\":{\"yellow\":3,\"yellowred\":0,\"red\":0},\"penalty\":{\"won\":null,\"commited\":null,\"scored\":1,\"missed\":0,\"saved\":null}}]}]}\n";
    }
        try {
            JSONParser parse = new JSONParser();
            JSONObject dataObject = null;
            dataObject = (JSONObject) parse.parse(body);
            JSONArray response = (JSONArray) dataObject.get("response");
            JSONObject player = (JSONObject) ((JSONObject) response.get(0)).get("player");
            String logo = (String) player.get("photo");
            Image img = new Image(logo);
            logo1 = new ImageView(img);
            logoLabel.setGraphic(logo1);
            JSONArray jucator = (JSONArray) ((JSONObject)response.get(0)).get("statistics");
            JSONObject games = (JSONObject) ((JSONObject) jucator.get(0)).get("games");
            JSONObject shots = (JSONObject) ((JSONObject) jucator.get(0)).get("shots");
            JSONObject goalsAssists = (JSONObject) ((JSONObject) jucator.get(0)).get("goals");
            JSONObject passes = (JSONObject) ((JSONObject) jucator.get(0)).get("passes");
            JSONObject tackles = (JSONObject) ((JSONObject) jucator.get(0)).get("tackles");
            JSONObject duels = (JSONObject) ((JSONObject) jucator.get(0)).get("duels");
            JSONObject dribbles = (JSONObject) ((JSONObject) jucator.get(0)).get("dribbles");
            JSONObject fouls = (JSONObject) ((JSONObject) jucator.get(0)).get("fouls");


            List<String> player1Stats = new ArrayList<>();
            items.add("Position: " + (String) games.get("position"));
            items.add("Appearances: " + (Long) games.get("appearences"));
            items.add("Shots: " + (Long) shots.get("total"));
            items.add("Shots on target: " + (Long) shots.get("on"));
            items.add("Goals scored: " + (Long) goalsAssists.get("total"));
            items.add("Assists: " + (Long) goalsAssists.get("assists"));
            items.add("Passes completed: " + (Long) passes.get("total"));
            items.add("Key passes: " + (Long) passes.get("key"));
            items.add("Tackles: " + (Long) tackles.get("total"));
            items.add("Interceptions: " + (Long) tackles.get("interceptions"));
            items.add("Duels: " + (Long) duels.get("total"));
            items.add("Duels won: " + (Long) duels.get("won"));
            items.add("Dribbles: " + (Long) dribbles.get("attempts"));
            items.add("Successful dribbles: " + (Long) dribbles.get("success"));
            items.add("Fouls won: " + (Long) fouls.get("drawn"));
            items.add("Fouls committed: " + (Long) fouls.get("committed"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return items;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        player1.setItems(items);
        player2.setItems(items2);

    }
}
