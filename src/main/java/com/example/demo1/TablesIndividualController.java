package com.example.demo1;

import com.licenta.dao.*;
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
import javafx.scene.control.cell.PropertyValueFactory;
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
import java.util.*;

public class TablesIndividualController implements Initializable {

    @FXML
    Button backToMain;
    @FXML
    Button backToResults;
    @FXML
    Button fixtures;
    @FXML
    private ImageView logo1;
    @FXML
    Label logoLabel;
    @FXML
    Button exit;
    @FXML
    private AnchorPane scenePane;
    @FXML
    Label Name;
    @FXML
    private ListView getInfo;
    @FXML
    private TableView<IndividualStandings> standings;

    @FXML
    private TableColumn<IndividualStandings, Integer> defeats;

    @FXML
    private TableColumn<IndividualStandings, Integer> draws;

    @FXML
    private TableColumn<IndividualStandings, Integer> ga;

    @FXML
    private TableColumn<IndividualStandings, Integer> gf;

    @FXML
    private TableColumn<IndividualStandings, String> name;

    @FXML
    private TableColumn<IndividualStandings, Integer> played;

    @FXML
    private TableColumn<IndividualStandings, Integer> points;

    @FXML
    private TableColumn<IndividualStandings, Integer> spot;

    @FXML
    private TableColumn<IndividualStandings, Integer> wins;

    private ObservableList<String> items = FXCollections.observableArrayList();
    Stage stage;

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

    public void handleFixturesButton() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FixturesIndividual.fxml"));
        Stage window = (Stage) fixtures.getScene().getWindow();
        window.setScene(new Scene(root, 602, 638));
    }

    public void handleBackToResults() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("IndividualStats.fxml"));
        Stage window = (Stage) backToResults.getScene().getWindow();
        window.setScene(new Scene(root, 602, 638));
    }



    String body = "{\"get\":\"teams\",\"parameters\":{\"name\":\"Chelsea\"},\"errors\":[],\"results\":1,\"paging\":{\"current\":1,\"total\":1},\"response\":[{\"team\":{\"id\":49,\"name\":\"Chelsea\",\"code\":\"CHE\",\"country\":\"England\",\"founded\":1905,\"national\":false,\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/teams\\/49.png\"},\"venue\":{\"id\":519,\"name\":\"Stamford Bridge\",\"address\":\"Fulham Road\",\"city\":\"London\",\"capacity\":41841,\"surface\":\"grass\",\"image\":\"https:\\/\\/media-2.api-sports.io\\/football\\/venues\\/519.png\"}}]}\n";

    List<IndividualStandings> parseInfo() {
        OkHttpClient client2 = new OkHttpClient();
        ShareData sd = ShareData.getInstance();
        String bodyLeagueID = null;
        if (sd.getUseAPI()) {
            Request request = new Request.Builder()
                    .url("https://api-football-v1.p.rapidapi.com/v3/leagues?team="+sd.getTeamId()+"&type=League")
                    .get()
                    .addHeader("X-RapidAPI-Key", "25f378b6damshc17fe739842f292p139f8cjsnb5482d97aa7f")
                    .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                    .build();
            try {
                Response responseLeague = client2.newCall(request).execute();
                bodyLeagueID = responseLeague.body().string();
//                System.out.println(bodyLeagueID);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

        }else bodyLeagueID="{\"get\":\"leagues\",\"parameters\":{\"team\":\"49\",\"type\":\"League\"},\"errors\":[],\"results\":2,\"paging\":{\"current\":1,\"total\":1},\"response\":[{\"league\":{\"id\":39,\"name\":\"Premier League\",\"type\":\"League\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/leagues\\/39.png\"},\"country\":{\"name\":\"England\",\"code\":\"GB\",\"flag\":\"https:\\/\\/media-3.api-sports.io\\/flags\\/gb.svg\"},\"seasons\":[{\"year\":2010,\"start\":\"2010-08-14\",\"end\":\"2011-05-17\",\"current\":false,\"coverage\":{\"fixtures\":{\"events\":true,\"lineups\":true,\"statistics_fixtures\":false,\"statistics_players\":false},\"standings\":true,\"players\":true,\"top_scorers\":true,\"top_assists\":true,\"top_cards\":true,\"injuries\":false,\"predictions\":true,\"odds\":false}},{\"year\":2011,\"start\":\"2011-08-13\",\"end\":\"2012-05-13\",\"current\":false,\"coverage\":{\"fixtures\":{\"events\":true,\"lineups\":true,\"statistics_fixtures\":false,\"statistics_players\":false},\"standings\":true,\"players\":true,\"top_scorers\":true,\"top_assists\":true,\"top_cards\":true,\"injuries\":false,\"predictions\":true,\"odds\":false}},{\"year\":2012,\"start\":\"2012-08-18\",\"end\":\"2013-05-19\",\"current\":false,\"coverage\":{\"fixtures\":{\"events\":true,\"lineups\":true,\"statistics_fixtures\":false,\"statistics_players\":false},\"standings\":true,\"players\":true,\"top_scorers\":true,\"top_assists\":true,\"top_cards\":true,\"injuries\":false,\"predictions\":true,\"odds\":false}},{\"year\":2013,\"start\":\"2013-08-17\",\"end\":\"2014-05-11\",\"current\":false,\"coverage\":{\"fixtures\":{\"events\":true,\"lineups\":true,\"statistics_fixtures\":false,\"statistics_players\":false},\"standings\":true,\"players\":true,\"top_scorers\":true,\"top_assists\":true,\"top_cards\":true,\"injuries\":false,\"predictions\":true,\"odds\":false}},{\"year\":2014,\"start\":\"2014-08-16\",\"end\":\"2015-05-24\",\"current\":false,\"coverage\":{\"fixtures\":{\"events\":true,\"lineups\":true,\"statistics_fixtures\":true,\"statistics_players\":true},\"standings\":true,\"players\":true,\"top_scorers\":true,\"top_assists\":true,\"top_cards\":true,\"injuries\":false,\"predictions\":true,\"odds\":false}},{\"year\":2015,\"start\":\"2015-08-08\",\"end\":\"2016-05-17\",\"current\":false,\"coverage\":{\"fixtures\":{\"events\":true,\"lineups\":true,\"statistics_fixtures\":true,\"statistics_players\":true},\"standings\":true,\"players\":true,\"top_scorers\":true,\"top_assists\":true,\"top_cards\":true,\"injuries\":false,\"predictions\":true,\"odds\":false}},{\"year\":2016,\"start\":\"2016-08-13\",\"end\":\"2017-05-21\",\"current\":false,\"coverage\":{\"fixtures\":{\"events\":true,\"lineups\":true,\"statistics_fixtures\":true,\"statistics_players\":true},\"standings\":true,\"players\":true,\"top_scorers\":true,\"top_assists\":true,\"top_cards\":true,\"injuries\":false,\"predictions\":true,\"odds\":false}},{\"year\":2017,\"start\":\"2017-08-11\",\"end\":\"2018-05-13\",\"current\":false,\"coverage\":{\"fixtures\":{\"events\":true,\"lineups\":true,\"statistics_fixtures\":true,\"statistics_players\":true},\"standings\":true,\"players\":true,\"top_scorers\":true,\"top_assists\":true,\"top_cards\":true,\"injuries\":false,\"predictions\":true,\"odds\":false}},{\"year\":2018,\"start\":\"2018-08-10\",\"end\":\"2019-05-12\",\"current\":false,\"coverage\":{\"fixtures\":{\"events\":true,\"lineups\":true,\"statistics_fixtures\":true,\"statistics_players\":true},\"standings\":true,\"players\":true,\"top_scorers\":true,\"top_assists\":true,\"top_cards\":true,\"injuries\":false,\"predictions\":true,\"odds\":false}},{\"year\":2019,\"start\":\"2019-08-09\",\"end\":\"2020-07-26\",\"current\":false,\"coverage\":{\"fixtures\":{\"events\":true,\"lineups\":true,\"statistics_fixtures\":true,\"statistics_players\":true},\"standings\":true,\"players\":true,\"top_scorers\":true,\"top_assists\":true,\"top_cards\":true,\"injuries\":false,\"predictions\":true,\"odds\":false}},{\"year\":2020,\"start\":\"2020-09-12\",\"end\":\"2021-05-23\",\"current\":false,\"coverage\":{\"fixtures\":{\"events\":true,\"lineups\":true,\"statistics_fixtures\":true,\"statistics_players\":true},\"standings\":true,\"players\":true,\"top_scorers\":true,\"top_assists\":true,\"top_cards\":true,\"injuries\":true,\"predictions\":true,\"odds\":false}},{\"year\":2021,\"start\":\"2021-08-13\",\"end\":\"2022-05-22\",\"current\":false,\"coverage\":{\"fixtures\":{\"events\":true,\"lineups\":true,\"statistics_fixtures\":true,\"statistics_players\":true},\"standings\":true,\"players\":true,\"top_scorers\":true,\"top_assists\":true,\"top_cards\":true,\"injuries\":true,\"predictions\":true,\"odds\":false}},{\"year\":2022,\"start\":\"2022-08-05\",\"end\":\"2023-05-28\",\"current\":false,\"coverage\":{\"fixtures\":{\"events\":true,\"lineups\":true,\"statistics_fixtures\":true,\"statistics_players\":true},\"standings\":true,\"players\":true,\"top_scorers\":true,\"top_assists\":true,\"top_cards\":true,\"injuries\":true,\"predictions\":true,\"odds\":false}},{\"year\":2023,\"start\":\"2023-08-11\",\"end\":\"2024-05-19\",\"current\":true,\"coverage\":{\"fixtures\":{\"events\":false,\"lineups\":false,\"statistics_fixtures\":false,\"statistics_players\":false},\"standings\":true,\"players\":true,\"top_scorers\":true,\"top_assists\":true,\"top_cards\":true,\"injuries\":false,\"predictions\":true,\"odds\":false}}]},{\"league\":{\"id\":15,\"name\":\"FIFA Club World Cup\",\"type\":\"League\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/leagues\\/15.png\"},\"country\":{\"name\":\"World\",\"code\":null,\"flag\":null},\"seasons\":[{\"year\":2021,\"start\":\"2022-02-03\",\"end\":\"2022-02-12\",\"current\":false,\"coverage\":{\"fixtures\":{\"events\":true,\"lineups\":true,\"statistics_fixtures\":true,\"statistics_players\":true},\"standings\":false,\"players\":false,\"top_scorers\":false,\"top_assists\":false,\"top_cards\":false,\"injuries\":false,\"predictions\":true,\"odds\":false}}]}]}\n";

        try {
            JSONParser parser = new JSONParser();
            JSONObject data = null;
            data = (JSONObject) parser.parse(bodyLeagueID);
            JSONArray response = (JSONArray) data.get("response");
            JSONObject league = (JSONObject) ((JSONObject)response.get(0)).get("league");
            sd.setLeagueID((Long) league.get("id"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        List<IndividualStandings> rez = new LinkedList<>();
        String body2 = null;
        if(sd.getUseAPI()){
            Request request2 = new Request.Builder()
                    .url("https://api-football-v1.p.rapidapi.com/v3/standings?season=2022&league="+sd.getLeagueID())
                    .get()
                    .addHeader("X-RapidAPI-Key", "25f378b6damshc17fe739842f292p139f8cjsnb5482d97aa7f")
                    .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                    .build();

                   try {
            Response response2 = client2.newCall(request2).execute();
            body2 = response2.body().string();
//            System.out.println(body2);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        }else  body2 = "{\"get\":\"standings\",\"parameters\":{\"league\":\"39\",\"season\":\"2020\"},\"errors\":[],\"results\":1,\"paging\":{\"current\":1,\"total\":1},\"response\":[{\"league\":{\"id\":39,\"name\":\"Premier League\",\"country\":\"England\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/leagues\\/39.png\",\"flag\":\"https:\\/\\/media-2.api-sports.io\\/flags\\/gb.svg\",\"season\":2020,\"standings\":[[{\"rank\":1,\"team\":{\"id\":50,\"name\":\"Manchester City\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/teams\\/50.png\"},\"points\":86,\"goalsDiff\":51,\"group\":\"Premier League\",\"form\":\"WLWLW\",\"status\":\"same\",\"description\":\"Promotion - Champions League (Group Stage)\",\"all\":{\"played\":38,\"win\":27,\"draw\":5,\"lose\":6,\"goals\":{\"for\":83,\"against\":32}},\"home\":{\"played\":19,\"win\":13,\"draw\":2,\"lose\":4,\"goals\":{\"for\":43,\"against\":17}},\"away\":{\"played\":19,\"win\":14,\"draw\":3,\"lose\":2,\"goals\":{\"for\":40,\"against\":15}},\"update\":\"2021-05-23T00:00:00+00:00\"},{\"rank\":2,\"team\":{\"id\":33,\"name\":\"Manchester United\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/teams\\/33.png\"},\"points\":74,\"goalsDiff\":29,\"group\":\"Premier League\",\"form\":\"WDLLW\",\"status\":\"same\",\"description\":\"Promotion - Champions League (Group Stage)\",\"all\":{\"played\":38,\"win\":21,\"draw\":11,\"lose\":6,\"goals\":{\"for\":73,\"against\":44}},\"home\":{\"played\":19,\"win\":9,\"draw\":4,\"lose\":6,\"goals\":{\"for\":38,\"against\":28}},\"away\":{\"played\":19,\"win\":12,\"draw\":7,\"lose\":0,\"goals\":{\"for\":35,\"against\":16}},\"update\":\"2021-05-23T00:00:00+00:00\"},{\"rank\":3,\"team\":{\"id\":40,\"name\":\"Liverpool\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/teams\\/40.png\"},\"points\":69,\"goalsDiff\":26,\"group\":\"Premier League\",\"form\":\"WWWWW\",\"status\":\"same\",\"description\":\"Promotion - Champions League (Group Stage)\",\"all\":{\"played\":38,\"win\":20,\"draw\":9,\"lose\":9,\"goals\":{\"for\":68,\"against\":42}},\"home\":{\"played\":19,\"win\":10,\"draw\":3,\"lose\":6,\"goals\":{\"for\":29,\"against\":20}},\"away\":{\"played\":19,\"win\":10,\"draw\":6,\"lose\":3,\"goals\":{\"for\":39,\"against\":22}},\"update\":\"2021-05-23T00:00:00+00:00\"},{\"rank\":4,\"team\":{\"id\":49,\"name\":\"Chelsea\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/teams\\/49.png\"},\"points\":67,\"goalsDiff\":22,\"group\":\"Premier League\",\"form\":\"LWLWW\",\"status\":\"same\",\"description\":\"Promotion - Champions League (Group Stage)\",\"all\":{\"played\":38,\"win\":19,\"draw\":10,\"lose\":9,\"goals\":{\"for\":58,\"against\":36}},\"home\":{\"played\":19,\"win\":9,\"draw\":6,\"lose\":4,\"goals\":{\"for\":31,\"against\":18}},\"away\":{\"played\":19,\"win\":10,\"draw\":4,\"lose\":5,\"goals\":{\"for\":27,\"against\":18}},\"update\":\"2021-05-23T00:00:00+00:00\"},{\"rank\":5,\"team\":{\"id\":46,\"name\":\"Leicester\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/teams\\/46.png\"},\"points\":66,\"goalsDiff\":18,\"group\":\"Premier League\",\"form\":\"LLWLD\",\"status\":\"same\",\"description\":\"Promotion - Europa League (Group Stage)\",\"all\":{\"played\":38,\"win\":20,\"draw\":6,\"lose\":12,\"goals\":{\"for\":68,\"against\":50}},\"home\":{\"played\":19,\"win\":9,\"draw\":1,\"lose\":9,\"goals\":{\"for\":34,\"against\":30}},\"away\":{\"played\":19,\"win\":11,\"draw\":5,\"lose\":3,\"goals\":{\"for\":34,\"against\":20}},\"update\":\"2021-05-23T00:00:00+00:00\"},{\"rank\":6,\"team\":{\"id\":48,\"name\":\"West Ham\",\"logo\":\"https:\\/\\/media-1.api-sports.io\\/football\\/teams\\/48.png\"},\"points\":65,\"goalsDiff\":15,\"group\":\"Premier League\",\"form\":\"WWDLW\",\"status\":\"same\",\"description\":\"Promotion - Europa League (Group Stage)\",\"all\":{\"played\":38,\"win\":19,\"draw\":8,\"lose\":11,\"goals\":{\"for\":62,\"against\":47}},\"home\":{\"played\":19,\"win\":10,\"draw\":4,\"lose\":5,\"goals\":{\"for\":32,\"against\":22}},\"away\":{\"played\":19,\"win\":9,\"draw\":4,\"lose\":6,\"goals\":{\"for\":30,\"against\":25}},\"update\":\"2021-05-23T00:00:00+00:00\"},{\"rank\":7,\"team\":{\"id\":47,\"name\":\"Tottenham\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/teams\\/47.png\"},\"points\":62,\"goalsDiff\":23,\"group\":\"Premier League\",\"form\":\"WLWLW\",\"status\":\"same\",\"description\":\"Promotion - Europa Conference League (Qualification)\",\"all\":{\"played\":38,\"win\":18,\"draw\":8,\"lose\":12,\"goals\":{\"for\":68,\"against\":45}},\"home\":{\"played\":19,\"win\":10,\"draw\":3,\"lose\":6,\"goals\":{\"for\":35,\"against\":20}},\"away\":{\"played\":19,\"win\":8,\"draw\":5,\"lose\":6,\"goals\":{\"for\":33,\"against\":25}},\"update\":\"2021-05-23T00:00:00+00:00\"},{\"rank\":8,\"team\":{\"id\":42,\"name\":\"Arsenal\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/42.png\"},\"points\":61,\"goalsDiff\":16,\"group\":\"Premier League\",\"form\":\"WWWWW\",\"status\":\"same\",\"description\":null,\"all\":{\"played\":38,\"win\":18,\"draw\":7,\"lose\":13,\"goals\":{\"for\":55,\"against\":39}},\"home\":{\"played\":19,\"win\":8,\"draw\":4,\"lose\":7,\"goals\":{\"for\":24,\"against\":21}},\"away\":{\"played\":19,\"win\":10,\"draw\":3,\"lose\":6,\"goals\":{\"for\":31,\"against\":18}},\"update\":\"2021-05-23T00:00:00+00:00\"},{\"rank\":9,\"team\":{\"id\":63,\"name\":\"Leeds\",\"logo\":\"https:\\/\\/media-1.api-sports.io\\/football\\/teams\\/63.png\"},\"points\":59,\"goalsDiff\":8,\"group\":\"Premier League\",\"form\":\"WWWWL\",\"status\":\"same\",\"description\":null,\"all\":{\"played\":38,\"win\":18,\"draw\":5,\"lose\":15,\"goals\":{\"for\":62,\"against\":54}},\"home\":{\"played\":19,\"win\":8,\"draw\":5,\"lose\":6,\"goals\":{\"for\":28,\"against\":21}},\"away\":{\"played\":19,\"win\":10,\"draw\":0,\"lose\":9,\"goals\":{\"for\":34,\"against\":33}},\"update\":\"2021-05-23T00:00:00+00:00\"},{\"rank\":10,\"team\":{\"id\":45,\"name\":\"Everton\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/45.png\"},\"points\":59,\"goalsDiff\":-1,\"group\":\"Premier League\",\"form\":\"LWLDW\",\"status\":\"same\",\"description\":null,\"all\":{\"played\":38,\"win\":17,\"draw\":8,\"lose\":13,\"goals\":{\"for\":47,\"against\":48}},\"home\":{\"played\":19,\"win\":6,\"draw\":4,\"lose\":9,\"goals\":{\"for\":24,\"against\":28}},\"away\":{\"played\":19,\"win\":11,\"draw\":4,\"lose\":4,\"goals\":{\"for\":23,\"against\":20}},\"update\":\"2021-05-23T00:00:00+00:00\"},{\"rank\":11,\"team\":{\"id\":66,\"name\":\"Aston Villa\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/teams\\/66.png\"},\"points\":55,\"goalsDiff\":9,\"group\":\"Premier League\",\"form\":\"WWLDL\",\"status\":\"same\",\"description\":null,\"all\":{\"played\":38,\"win\":16,\"draw\":7,\"lose\":15,\"goals\":{\"for\":55,\"against\":46}},\"home\":{\"played\":19,\"win\":7,\"draw\":4,\"lose\":8,\"goals\":{\"for\":29,\"against\":27}},\"away\":{\"played\":19,\"win\":9,\"draw\":3,\"lose\":7,\"goals\":{\"for\":26,\"against\":19}},\"update\":\"2021-05-23T00:00:00+00:00\"},{\"rank\":12,\"team\":{\"id\":34,\"name\":\"Newcastle\",\"logo\":\"https:\\/\\/media-1.api-sports.io\\/football\\/teams\\/34.png\"},\"points\":45,\"goalsDiff\":-16,\"group\":\"Premier League\",\"form\":\"WWLWL\",\"status\":\"same\",\"description\":null,\"all\":{\"played\":38,\"win\":12,\"draw\":9,\"lose\":17,\"goals\":{\"for\":46,\"against\":62}},\"home\":{\"played\":19,\"win\":6,\"draw\":5,\"lose\":8,\"goals\":{\"for\":26,\"against\":33}},\"away\":{\"played\":19,\"win\":6,\"draw\":4,\"lose\":9,\"goals\":{\"for\":20,\"against\":29}},\"update\":\"2021-05-23T00:00:00+00:00\"},{\"rank\":13,\"team\":{\"id\":39,\"name\":\"Wolves\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/39.png\"},\"points\":45,\"goalsDiff\":-16,\"group\":\"Premier League\",\"form\":\"LLLWD\",\"status\":\"same\",\"description\":null,\"all\":{\"played\":38,\"win\":12,\"draw\":9,\"lose\":17,\"goals\":{\"for\":36,\"against\":52}},\"home\":{\"played\":19,\"win\":7,\"draw\":4,\"lose\":8,\"goals\":{\"for\":21,\"against\":25}},\"away\":{\"played\":19,\"win\":5,\"draw\":5,\"lose\":9,\"goals\":{\"for\":15,\"against\":27}},\"update\":\"2021-05-23T00:00:00+00:00\"},{\"rank\":14,\"team\":{\"id\":52,\"name\":\"Crystal Palace\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/52.png\"},\"points\":44,\"goalsDiff\":-25,\"group\":\"Premier League\",\"form\":\"LLWLW\",\"status\":\"same\",\"description\":null,\"all\":{\"played\":38,\"win\":12,\"draw\":8,\"lose\":18,\"goals\":{\"for\":41,\"against\":66}},\"home\":{\"played\":19,\"win\":6,\"draw\":5,\"lose\":8,\"goals\":{\"for\":20,\"against\":32}},\"away\":{\"played\":19,\"win\":6,\"draw\":3,\"lose\":10,\"goals\":{\"for\":21,\"against\":34}},\"update\":\"2021-05-23T00:00:00+00:00\"},{\"rank\":15,\"team\":{\"id\":41,\"name\":\"Southampton\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/teams\\/41.png\"},\"points\":43,\"goalsDiff\":-21,\"group\":\"Premier League\",\"form\":\"LLWWL\",\"status\":\"same\",\"description\":null,\"all\":{\"played\":38,\"win\":12,\"draw\":7,\"lose\":19,\"goals\":{\"for\":47,\"against\":68}},\"home\":{\"played\":19,\"win\":8,\"draw\":3,\"lose\":8,\"goals\":{\"for\":28,\"against\":25}},\"away\":{\"played\":19,\"win\":4,\"draw\":4,\"lose\":11,\"goals\":{\"for\":19,\"against\":43}},\"update\":\"2021-05-23T00:00:00+00:00\"},{\"rank\":16,\"team\":{\"id\":51,\"name\":\"Brighton\",\"logo\":\"https:\\/\\/media-1.api-sports.io\\/football\\/teams\\/51.png\"},\"points\":41,\"goalsDiff\":-6,\"group\":\"Premier League\",\"form\":\"LWDLW\",\"status\":\"same\",\"description\":null,\"all\":{\"played\":38,\"win\":9,\"draw\":14,\"lose\":15,\"goals\":{\"for\":40,\"against\":46}},\"home\":{\"played\":19,\"win\":4,\"draw\":9,\"lose\":6,\"goals\":{\"for\":22,\"against\":22}},\"away\":{\"played\":19,\"win\":5,\"draw\":5,\"lose\":9,\"goals\":{\"for\":18,\"against\":24}},\"update\":\"2021-05-23T00:00:00+00:00\"},{\"rank\":17,\"team\":{\"id\":44,\"name\":\"Burnley\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/44.png\"},\"points\":39,\"goalsDiff\":-22,\"group\":\"Premier League\",\"form\":\"LLLWL\",\"status\":\"same\",\"description\":null,\"all\":{\"played\":38,\"win\":10,\"draw\":9,\"lose\":19,\"goals\":{\"for\":33,\"against\":55}},\"home\":{\"played\":19,\"win\":4,\"draw\":6,\"lose\":9,\"goals\":{\"for\":14,\"against\":27}},\"away\":{\"played\":19,\"win\":6,\"draw\":3,\"lose\":10,\"goals\":{\"for\":19,\"against\":28}},\"update\":\"2021-05-23T00:00:00+00:00\"},{\"rank\":18,\"team\":{\"id\":36,\"name\":\"Fulham\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/36.png\"},\"points\":28,\"goalsDiff\":-26,\"group\":\"Premier League\",\"form\":\"LDLLL\",\"status\":\"same\",\"description\":\"Relegation - Championship\",\"all\":{\"played\":38,\"win\":5,\"draw\":13,\"lose\":20,\"goals\":{\"for\":27,\"against\":53}},\"home\":{\"played\":19,\"win\":2,\"draw\":4,\"lose\":13,\"goals\":{\"for\":9,\"against\":28}},\"away\":{\"played\":19,\"win\":3,\"draw\":9,\"lose\":7,\"goals\":{\"for\":18,\"against\":25}},\"update\":\"2021-05-23T00:00:00+00:00\"},{\"rank\":19,\"team\":{\"id\":60,\"name\":\"West Brom\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/60.png\"},\"points\":26,\"goalsDiff\":-41,\"group\":\"Premier League\",\"form\":\"LLLLD\",\"status\":\"same\",\"description\":\"Relegation - Championship\",\"all\":{\"played\":38,\"win\":5,\"draw\":11,\"lose\":22,\"goals\":{\"for\":35,\"against\":76}},\"home\":{\"played\":19,\"win\":3,\"draw\":6,\"lose\":10,\"goals\":{\"for\":15,\"against\":39}},\"away\":{\"played\":19,\"win\":2,\"draw\":5,\"lose\":12,\"goals\":{\"for\":20,\"against\":37}},\"update\":\"2021-05-23T00:00:00+00:00\"},{\"rank\":20,\"team\":{\"id\":62,\"name\":\"Sheffield Utd\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/62.png\"},\"points\":23,\"goalsDiff\":-43,\"group\":\"Premier League\",\"form\":\"WLWLL\",\"status\":\"same\",\"description\":\"Relegation - Championship\",\"all\":{\"played\":38,\"win\":7,\"draw\":2,\"lose\":29,\"goals\":{\"for\":20,\"against\":63}},\"home\":{\"played\":19,\"win\":5,\"draw\":1,\"lose\":13,\"goals\":{\"for\":12,\"against\":27}},\"away\":{\"played\":19,\"win\":2,\"draw\":1,\"lose\":16,\"goals\":{\"for\":8,\"against\":36}},\"update\":\"2021-05-23T00:00:00+00:00\"}]]}}]}\n";

        try {
            JSONParser parse = new JSONParser();
            JSONObject dataObject = null;
            dataObject = (JSONObject) parse.parse(body2);
            JSONArray ligile = (JSONArray) dataObject.get("response");

            IndividualStandings joc = new IndividualStandings();
            JSONObject _liga =(JSONObject) ligile.get(0);
            JSONObject liga = (JSONObject) _liga.get("league");
            JSONArray _standings = (JSONArray) liga.get("standings");
            JSONArray _standings_items = (JSONArray) _standings.get(0);

            for(int j=0; j<_standings_items.size(); j++){
                IndividualStandings s = new IndividualStandings();
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
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return rez;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ShareData sd = ShareData.getInstance();
        String teamName = sd.getTeamname();
        System.out.println("teamName " + teamName);
        Name.setText(teamName + " " + Name.getText());

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api-football-v1.p.rapidapi.com/v3/teams?name=" + teamName)
                .get()
                .addHeader("X-RapidAPI-Key", "25f378b6damshc17fe739842f292p139f8cjsnb5482d97aa7f")
                .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                .build();
        String body = null;

        if (sd.getUseAPI()) {

            try {
                Response response = client.newCall(request).execute();
                body = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }else
            body = "{\"get\":\"teams\",\"parameters\":{\"name\":\"Chelsea\"},\"errors\":[],\"results\":1,\"paging\":{\"current\":1,\"total\":1},\"response\":[{\"team\":{\"id\":49,\"name\":\"Chelsea\",\"code\":\"CHE\",\"country\":\"England\",\"founded\":1905,\"national\":false,\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/teams\\/49.png\"},\"venue\":{\"id\":519,\"name\":\"Stamford Bridge\",\"address\":\"Fulham Road\",\"city\":\"London\",\"capacity\":41841,\"surface\":\"grass\",\"image\":\"https:\\/\\/media-2.api-sports.io\\/football\\/venues\\/519.png\"}}]}\n";


        try {
            JSONParser parse = new JSONParser();
            JSONObject dataObject = null;
            dataObject = (JSONObject) parse.parse(body);
            JSONArray obj = (JSONArray) dataObject.get("response");
            JSONObject echipa =(JSONObject) ((JSONObject) obj.get(0)).get("team");
            String logo = (String) echipa.get("logo");
            Image img = new Image(logo);
            logo1 = new ImageView(img);
            logoLabel.setGraphic(logo1);
            JSONObject venue = (JSONObject) ((JSONObject) obj.get(0)).get("venue");

            List<String> info = new ArrayList<>();
            items.add("Team Name: " + (String) echipa.get("name"));
            items.add("Country: " + (String) echipa.get("country"));
            items.add("Founded in: "+(Long) echipa.get("founded"));
            items.add("Stadium Name: " +(String) venue.get("name"));
            items.add("City Name: " +(String) venue.get("city"));
            getInfo.setItems(items);


        }catch (ParseException e) {
            throw new RuntimeException(e);
        }

        defeats.setCellValueFactory(new PropertyValueFactory<IndividualStandings, Integer>("defeats"));
        wins.setCellValueFactory(new PropertyValueFactory<IndividualStandings, Integer>("wins"));
        played.setCellValueFactory(new PropertyValueFactory<IndividualStandings, Integer>("played"));
        draws.setCellValueFactory(new PropertyValueFactory<IndividualStandings, Integer>("draws"));
        spot.setCellValueFactory(new PropertyValueFactory<IndividualStandings, Integer>("spot"));
        points.setCellValueFactory(new PropertyValueFactory<IndividualStandings, Integer>("points"));
        gf.setCellValueFactory(new PropertyValueFactory<IndividualStandings, Integer>("gf"));
        ga.setCellValueFactory(new PropertyValueFactory<IndividualStandings, Integer>("ga"));
        name.setCellValueFactory(new PropertyValueFactory<IndividualStandings, String>("name"));
        standings.getItems().setAll(parseInfo());

    }



}
