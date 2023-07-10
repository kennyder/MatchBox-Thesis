package com.example.demo1;

import com.licenta.dao.Joc;
import com.licenta.dao.Standings;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import java.net.URL;
import java.util.ResourceBundle;

public class TablesViewController implements Initializable {
    @FXML
    Button search;
    @FXML
    Button fixtures;
    @FXML
    Button results;

    @FXML
    Button backToMain;
    @FXML
    Button exit;
    @FXML
    private AnchorPane scenePane;
    Stage stage;

    @FXML
    private TableView<Standings> standings;

    @FXML
    private TableColumn<Standings, Integer> defeats;

    @FXML
    private TableColumn<Standings, Integer> draws;

    @FXML
    private TableColumn<Standings, Integer> ga;

    @FXML
    private TableColumn<Standings, Integer> gf;

    @FXML
    private TableColumn<Standings, String> name;

    @FXML
    private TableColumn<Standings, Integer> played;

    @FXML
    private TableColumn<Standings, Integer> points;

    @FXML
    private TableColumn<Standings, Integer> spot;

    @FXML
    private TableColumn<Standings, Integer> wins;

    public TablesViewController() throws IOException {
    }


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

    public void handleSearchBtn() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("IndividualStats.fxml"));
        Stage window = (Stage) search.getScene().getWindow();
        window.setScene(new Scene(root, 602, 638));

    }

    public void handleFixturesBtn() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("TablesViewFixtures.fxml"));
        Stage window = (Stage) fixtures.getScene().getWindow();
        window.setScene(new Scene(root, 602, 638));
    }

    public void handleResultsButton() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ResultsTableView.fxml"));
        Stage window = (Stage) results.getScene().getWindow();
        window.setScene(new Scene(root, 602, 638));
    }


//        String body = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        defeats.setCellValueFactory(new PropertyValueFactory<Standings, Integer>("defeats"));
        wins.setCellValueFactory(new PropertyValueFactory<Standings, Integer>("wins"));
        played.setCellValueFactory(new PropertyValueFactory<Standings, Integer>("played"));
        draws.setCellValueFactory(new PropertyValueFactory<Standings, Integer>("draws"));
        spot.setCellValueFactory(new PropertyValueFactory<Standings, Integer>("spot"));
        points.setCellValueFactory(new PropertyValueFactory<Standings, Integer>("points"));
        gf.setCellValueFactory(new PropertyValueFactory<Standings, Integer>("gf"));
        ga.setCellValueFactory(new PropertyValueFactory<Standings, Integer>("ga"));
        name.setCellValueFactory(new PropertyValueFactory<Standings, String>("name"));
        standings.getItems().setAll(parseInfo());
    }
    List<Standings> parseInfo(){
        List<Standings> rez = new LinkedList<>();
        ShareData sd = ShareData.getInstance();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api-football-v1.p.rapidapi.com/v3/standings?season=2022&league=39")
                .get()
                .addHeader("X-RapidAPI-Key", "25f378b6damshc17fe739842f292p139f8cjsnb5482d97aa7f")
                .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                .build();

//    try {
//            Response response = client.newCall(request).execute();
//            String body = response.body().string();
//           System.out.println("body: "+body);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }

        String body = "{\"get\":\"standings\",\"parameters\":{\"league\":\"39\",\"season\":\"2022\"},\"errors\":[],\"results\":1,\"paging\":{\"current\":1,\"total\":1},\"response\":[{\"league\":{\"id\":39,\"name\":\"Premier League\",\"country\":\"England\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/leagues\\/39.png\",\"flag\":\"https:\\/\\/media-2.api-sports.io\\/flags\\/gb.svg\",\"season\":2022,\"standings\":[[{\"rank\":1,\"team\":{\"id\":50,\"name\":\"Manchester City\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/teams\\/50.png\"},\"points\":89,\"goalsDiff\":61,\"group\":\"Premier League\",\"form\":\"LDWWW\",\"status\":\"same\",\"description\":\"Promotion - Champions League (Group Stage: )\",\"all\":{\"played\":38,\"win\":28,\"draw\":5,\"lose\":5,\"goals\":{\"for\":94,\"against\":33}},\"home\":{\"played\":19,\"win\":17,\"draw\":1,\"lose\":1,\"goals\":{\"for\":60,\"against\":17}},\"away\":{\"played\":19,\"win\":11,\"draw\":4,\"lose\":4,\"goals\":{\"for\":34,\"against\":16}},\"update\":\"2023-05-28T00:00:00+00:00\"},{\"rank\":2,\"team\":{\"id\":42,\"name\":\"Arsenal\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/teams\\/42.png\"},\"points\":84,\"goalsDiff\":45,\"group\":\"Premier League\",\"form\":\"WLLWW\",\"status\":\"same\",\"description\":\"Promotion - Champions League (Group Stage: )\",\"all\":{\"played\":38,\"win\":26,\"draw\":6,\"lose\":6,\"goals\":{\"for\":88,\"against\":43}},\"home\":{\"played\":19,\"win\":14,\"draw\":3,\"lose\":2,\"goals\":{\"for\":53,\"against\":25}},\"away\":{\"played\":19,\"win\":12,\"draw\":3,\"lose\":4,\"goals\":{\"for\":35,\"against\":18}},\"update\":\"2023-05-28T00:00:00+00:00\"},{\"rank\":3,\"team\":{\"id\":33,\"name\":\"Manchester United\",\"logo\":\"https:\\/\\/media-1.api-sports.io\\/football\\/teams\\/33.png\"},\"points\":75,\"goalsDiff\":15,\"group\":\"Premier League\",\"form\":\"WWWWL\",\"status\":\"same\",\"description\":\"Promotion - Champions League (Group Stage: )\",\"all\":{\"played\":38,\"win\":23,\"draw\":6,\"lose\":9,\"goals\":{\"for\":58,\"against\":43}},\"home\":{\"played\":19,\"win\":15,\"draw\":3,\"lose\":1,\"goals\":{\"for\":36,\"against\":10}},\"away\":{\"played\":19,\"win\":8,\"draw\":3,\"lose\":8,\"goals\":{\"for\":22,\"against\":33}},\"update\":\"2023-05-28T00:00:00+00:00\"},{\"rank\":4,\"team\":{\"id\":34,\"name\":\"Newcastle\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/teams\\/34.png\"},\"points\":71,\"goalsDiff\":35,\"group\":\"Premier League\",\"form\":\"DDWDL\",\"status\":\"same\",\"description\":\"Promotion - Champions League (Group Stage: )\",\"all\":{\"played\":38,\"win\":19,\"draw\":14,\"lose\":5,\"goals\":{\"for\":68,\"against\":33}},\"home\":{\"played\":19,\"win\":11,\"draw\":6,\"lose\":2,\"goals\":{\"for\":36,\"against\":14}},\"away\":{\"played\":19,\"win\":8,\"draw\":8,\"lose\":3,\"goals\":{\"for\":32,\"against\":19}},\"update\":\"2023-05-28T00:00:00+00:00\"},{\"rank\":5,\"team\":{\"id\":40,\"name\":\"Liverpool\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/40.png\"},\"points\":67,\"goalsDiff\":28,\"group\":\"Premier League\",\"form\":\"DDWWW\",\"status\":\"same\",\"description\":\"Promotion - Europa League (Group Stage: )\",\"all\":{\"played\":38,\"win\":19,\"draw\":10,\"lose\":9,\"goals\":{\"for\":75,\"against\":47}},\"home\":{\"played\":19,\"win\":13,\"draw\":5,\"lose\":1,\"goals\":{\"for\":46,\"against\":17}},\"away\":{\"played\":19,\"win\":6,\"draw\":5,\"lose\":8,\"goals\":{\"for\":29,\"against\":30}},\"update\":\"2023-05-28T00:00:00+00:00\"},{\"rank\":6,\"team\":{\"id\":51,\"name\":\"Brighton\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/teams\\/51.png\"},\"points\":62,\"goalsDiff\":19,\"group\":\"Premier League\",\"form\":\"LDWLW\",\"status\":\"same\",\"description\":\"Promotion - Europa League (Group Stage: )\",\"all\":{\"played\":38,\"win\":18,\"draw\":8,\"lose\":12,\"goals\":{\"for\":72,\"against\":53}},\"home\":{\"played\":19,\"win\":10,\"draw\":4,\"lose\":5,\"goals\":{\"for\":37,\"against\":21}},\"away\":{\"played\":19,\"win\":8,\"draw\":4,\"lose\":7,\"goals\":{\"for\":35,\"against\":32}},\"update\":\"2023-05-28T00:00:00+00:00\"},{\"rank\":7,\"team\":{\"id\":66,\"name\":\"Aston Villa\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/66.png\"},\"points\":61,\"goalsDiff\":5,\"group\":\"Premier League\",\"form\":\"WDWLL\",\"status\":\"same\",\"description\":\"Promotion - Europa Conference League (Qualification: )\",\"all\":{\"played\":38,\"win\":18,\"draw\":7,\"lose\":13,\"goals\":{\"for\":51,\"against\":46}},\"home\":{\"played\":19,\"win\":12,\"draw\":2,\"lose\":5,\"goals\":{\"for\":33,\"against\":21}},\"away\":{\"played\":19,\"win\":6,\"draw\":5,\"lose\":8,\"goals\":{\"for\":18,\"against\":25}},\"update\":\"2023-05-28T00:00:00+00:00\"},{\"rank\":8,\"team\":{\"id\":47,\"name\":\"Tottenham\",\"logo\":\"https:\\/\\/media-1.api-sports.io\\/football\\/teams\\/47.png\"},\"points\":60,\"goalsDiff\":7,\"group\":\"Premier League\",\"form\":\"WLLWL\",\"status\":\"same\",\"description\":null,\"all\":{\"played\":38,\"win\":18,\"draw\":6,\"lose\":14,\"goals\":{\"for\":70,\"against\":63}},\"home\":{\"played\":19,\"win\":12,\"draw\":1,\"lose\":6,\"goals\":{\"for\":37,\"against\":25}},\"away\":{\"played\":19,\"win\":6,\"draw\":5,\"lose\":8,\"goals\":{\"for\":33,\"against\":38}},\"update\":\"2023-05-28T00:00:00+00:00\"},{\"rank\":9,\"team\":{\"id\":55,\"name\":\"Brentford\",\"logo\":\"https:\\/\\/media-1.api-sports.io\\/football\\/teams\\/55.png\"},\"points\":59,\"goalsDiff\":12,\"group\":\"Premier League\",\"form\":\"WWWLW\",\"status\":\"same\",\"description\":null,\"all\":{\"played\":38,\"win\":15,\"draw\":14,\"lose\":9,\"goals\":{\"for\":58,\"against\":46}},\"home\":{\"played\":19,\"win\":10,\"draw\":7,\"lose\":2,\"goals\":{\"for\":35,\"against\":18}},\"away\":{\"played\":19,\"win\":5,\"draw\":7,\"lose\":7,\"goals\":{\"for\":23,\"against\":28}},\"update\":\"2023-05-28T00:00:00+00:00\"},{\"rank\":10,\"team\":{\"id\":36,\"name\":\"Fulham\",\"logo\":\"https:\\/\\/media-1.api-sports.io\\/football\\/teams\\/36.png\"},\"points\":52,\"goalsDiff\":2,\"group\":\"Premier League\",\"form\":\"LDWWL\",\"status\":\"same\",\"description\":null,\"all\":{\"played\":38,\"win\":15,\"draw\":7,\"lose\":16,\"goals\":{\"for\":55,\"against\":53}},\"home\":{\"played\":19,\"win\":8,\"draw\":5,\"lose\":6,\"goals\":{\"for\":31,\"against\":29}},\"away\":{\"played\":19,\"win\":7,\"draw\":2,\"lose\":10,\"goals\":{\"for\":24,\"against\":24}},\"update\":\"2023-05-28T00:00:00+00:00\"},{\"rank\":11,\"team\":{\"id\":52,\"name\":\"Crystal Palace\",\"logo\":\"https:\\/\\/media-1.api-sports.io\\/football\\/teams\\/52.png\"},\"points\":45,\"goalsDiff\":-9,\"group\":\"Premier League\",\"form\":\"DDWLW\",\"status\":\"same\",\"description\":null,\"all\":{\"played\":38,\"win\":11,\"draw\":12,\"lose\":15,\"goals\":{\"for\":40,\"against\":49}},\"home\":{\"played\":19,\"win\":7,\"draw\":7,\"lose\":5,\"goals\":{\"for\":21,\"against\":23}},\"away\":{\"played\":19,\"win\":4,\"draw\":5,\"lose\":10,\"goals\":{\"for\":19,\"against\":26}},\"update\":\"2023-05-28T00:00:00+00:00\"},{\"rank\":12,\"team\":{\"id\":49,\"name\":\"Chelsea\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/49.png\"},\"points\":44,\"goalsDiff\":-9,\"group\":\"Premier League\",\"form\":\"DLLDW\",\"status\":\"same\",\"description\":null,\"all\":{\"played\":38,\"win\":11,\"draw\":11,\"lose\":16,\"goals\":{\"for\":38,\"against\":47}},\"home\":{\"played\":19,\"win\":6,\"draw\":7,\"lose\":6,\"goals\":{\"for\":20,\"against\":19}},\"away\":{\"played\":19,\"win\":5,\"draw\":4,\"lose\":10,\"goals\":{\"for\":18,\"against\":28}},\"update\":\"2023-05-28T00:00:00+00:00\"},{\"rank\":13,\"team\":{\"id\":39,\"name\":\"Wolves\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/39.png\"},\"points\":41,\"goalsDiff\":-27,\"group\":\"Premier League\",\"form\":\"LDLWL\",\"status\":\"same\",\"description\":null,\"all\":{\"played\":38,\"win\":11,\"draw\":8,\"lose\":19,\"goals\":{\"for\":31,\"against\":58}},\"home\":{\"played\":19,\"win\":9,\"draw\":3,\"lose\":7,\"goals\":{\"for\":19,\"against\":20}},\"away\":{\"played\":19,\"win\":2,\"draw\":5,\"lose\":12,\"goals\":{\"for\":12,\"against\":38}},\"update\":\"2023-05-28T00:00:00+00:00\"},{\"rank\":14,\"team\":{\"id\":48,\"name\":\"West Ham\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/48.png\"},\"points\":40,\"goalsDiff\":-13,\"group\":\"Premier League\",\"form\":\"LWLWL\",\"status\":\"same\",\"description\":null,\"all\":{\"played\":38,\"win\":11,\"draw\":7,\"lose\":20,\"goals\":{\"for\":42,\"against\":55}},\"home\":{\"played\":19,\"win\":8,\"draw\":4,\"lose\":7,\"goals\":{\"for\":26,\"against\":24}},\"away\":{\"played\":19,\"win\":3,\"draw\":3,\"lose\":13,\"goals\":{\"for\":16,\"against\":31}},\"update\":\"2023-05-28T00:00:00+00:00\"},{\"rank\":15,\"team\":{\"id\":35,\"name\":\"Bournemouth\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/teams\\/35.png\"},\"points\":39,\"goalsDiff\":-34,\"group\":\"Premier League\",\"form\":\"LLLLW\",\"status\":\"same\",\"description\":null,\"all\":{\"played\":38,\"win\":11,\"draw\":6,\"lose\":21,\"goals\":{\"for\":37,\"against\":71}},\"home\":{\"played\":19,\"win\":6,\"draw\":4,\"lose\":9,\"goals\":{\"for\":20,\"against\":28}},\"away\":{\"played\":19,\"win\":5,\"draw\":2,\"lose\":12,\"goals\":{\"for\":17,\"against\":43}},\"update\":\"2023-05-28T00:00:00+00:00\"},{\"rank\":16,\"team\":{\"id\":65,\"name\":\"Nottingham Forest\",\"logo\":\"https:\\/\\/media-1.api-sports.io\\/football\\/teams\\/65.png\"},\"points\":38,\"goalsDiff\":-30,\"group\":\"Premier League\",\"form\":\"DWDWL\",\"status\":\"same\",\"description\":null,\"all\":{\"played\":38,\"win\":9,\"draw\":11,\"lose\":18,\"goals\":{\"for\":38,\"against\":68}},\"home\":{\"played\":19,\"win\":8,\"draw\":6,\"lose\":5,\"goals\":{\"for\":27,\"against\":24}},\"away\":{\"played\":19,\"win\":1,\"draw\":5,\"lose\":13,\"goals\":{\"for\":11,\"against\":44}},\"update\":\"2023-05-28T00:00:00+00:00\"},{\"rank\":17,\"team\":{\"id\":45,\"name\":\"Everton\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/45.png\"},\"points\":36,\"goalsDiff\":-23,\"group\":\"Premier League\",\"form\":\"WDLWD\",\"status\":\"same\",\"description\":null,\"all\":{\"played\":38,\"win\":8,\"draw\":12,\"lose\":18,\"goals\":{\"for\":34,\"against\":57}},\"home\":{\"played\":19,\"win\":6,\"draw\":3,\"lose\":10,\"goals\":{\"for\":16,\"against\":27}},\"away\":{\"played\":19,\"win\":2,\"draw\":9,\"lose\":8,\"goals\":{\"for\":18,\"against\":30}},\"update\":\"2023-05-28T00:00:00+00:00\"},{\"rank\":18,\"team\":{\"id\":46,\"name\":\"Leicester\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/46.png\"},\"points\":34,\"goalsDiff\":-17,\"group\":\"Premier League\",\"form\":\"WDLLD\",\"status\":\"same\",\"description\":\"Relegation - Championship\",\"all\":{\"played\":38,\"win\":9,\"draw\":7,\"lose\":22,\"goals\":{\"for\":51,\"against\":68}},\"home\":{\"played\":19,\"win\":5,\"draw\":4,\"lose\":10,\"goals\":{\"for\":23,\"against\":27}},\"away\":{\"played\":19,\"win\":4,\"draw\":3,\"lose\":12,\"goals\":{\"for\":28,\"against\":41}},\"update\":\"2023-05-28T00:00:00+00:00\"},{\"rank\":19,\"team\":{\"id\":63,\"name\":\"Leeds\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/teams\\/63.png\"},\"points\":31,\"goalsDiff\":-30,\"group\":\"Premier League\",\"form\":\"LLDLL\",\"status\":\"same\",\"description\":\"Relegation - Championship\",\"all\":{\"played\":38,\"win\":7,\"draw\":10,\"lose\":21,\"goals\":{\"for\":48,\"against\":78}},\"home\":{\"played\":19,\"win\":5,\"draw\":7,\"lose\":7,\"goals\":{\"for\":26,\"against\":37}},\"away\":{\"played\":19,\"win\":2,\"draw\":3,\"lose\":14,\"goals\":{\"for\":22,\"against\":41}},\"update\":\"2023-05-28T00:00:00+00:00\"},{\"rank\":20,\"team\":{\"id\":41,\"name\":\"Southampton\",\"logo\":\"https:\\/\\/media-1.api-sports.io\\/football\\/teams\\/41.png\"},\"points\":25,\"goalsDiff\":-37,\"group\":\"Premier League\",\"form\":\"DLLLL\",\"status\":\"same\",\"description\":\"Relegation - Championship\",\"all\":{\"played\":38,\"win\":6,\"draw\":7,\"lose\":25,\"goals\":{\"for\":36,\"against\":73}},\"home\":{\"played\":19,\"win\":2,\"draw\":5,\"lose\":12,\"goals\":{\"for\":19,\"against\":37}},\"away\":{\"played\":19,\"win\":4,\"draw\":2,\"lose\":13,\"goals\":{\"for\":17,\"against\":36}},\"update\":\"2023-05-28T00:00:00+00:00\"}]]}}]}\n";
        try {
            JSONParser parse = new JSONParser();
            JSONObject dataObject = null;
            dataObject = (JSONObject) parse.parse(body);
//            System.out.println("json");
            //Get the first JSON object in the JSON array
            JSONArray ligile = (JSONArray) dataObject.get("response");


                Standings joc = new Standings();
                JSONObject _liga =(JSONObject) ligile.get(0);
                JSONObject liga = (JSONObject) _liga.get("league");
                JSONArray _standings = (JSONArray) liga.get("standings");
                JSONArray _standings_items = (JSONArray) _standings.get(0);

                for(int j=0; j<_standings_items.size(); j++){
                    Standings s = new Standings( );
                    JSONObject _standing = (JSONObject) _standings_items.get(j);
                    s.setPoints( (Long) _standing.get("points"));
                    s.setSpot( (Long) _standing.get("rank"));
                    JSONObject allMatches = (JSONObject) _standing.get("all");
                    JSONObject goals = (JSONObject) allMatches.get("goals");
                    s.setGf((Long) goals.get("for"));
                    s.setGa((Long) goals.get("against"));
                    s.setWins((Long) allMatches.get("win"));
                    s.setPlayed((Long) allMatches.get("played"));
                    s.setDefeats((Long) allMatches.get("lose"));
                    s.setDraws((Long) allMatches.get("draw"));
                    JSONObject teams = (JSONObject) _standing.get("team");
                    s.setName((String) teams.get("name"));
                    rez.add(s);
                }

          //  }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return rez;
    }
}
