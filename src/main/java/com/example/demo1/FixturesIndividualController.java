package com.example.demo1;

import com.licenta.dao.EchipaFavorita;
import com.licenta.dao.FixturesIndividual;
import com.licenta.dao.IndividualResults;
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

public class FixturesIndividualController implements Initializable {

    @FXML
    Button backToMain;
    @FXML
    private ImageView logo1;
    @FXML
    Label logoLabel;
    @FXML
    Button favorites;
    @FXML
    Button backToResults;
    @FXML
    private ListView fixturesList;
    @FXML
    Button exit;
    @FXML
    private AnchorPane scenePane;
    @FXML
    Label Name;
    @FXML
    private ListView getInfo;
    @FXML
    Button backToTables;

    private ObservableList<String> items = FXCollections.observableArrayList();
    private ObservableList<String> fixturesListView = FXCollections.observableArrayList();
    Stage stage;
    private EchipaFavorita tmpEchipaFavorita = new EchipaFavorita();

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

    public void handleBackToResults() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("IndividualStats.fxml"));
        Stage window = (Stage) backToResults.getScene().getWindow();
        window.setScene(new Scene(root, 602, 638));
    }

    public void handleTablesButton() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("TablesIndividual.fxml"));
        Stage window = (Stage) backToTables.getScene().getWindow();
        window.setScene(new Scene(root, 602, 638));
    }

    public void chooseFavorite() throws Exception {
        ShareData sd = ShareData.getInstance();
        sd.addEchipaFavorita(tmpEchipaFavorita);
    }

    String body = "{\"get\":\"teams\",\"parameters\":{\"name\":\"Chelsea\"},\"errors\":[],\"results\":1,\"paging\":{\"current\":1,\"total\":1},\"response\":[{\"team\":{\"id\":49,\"name\":\"Chelsea\",\"code\":\"CHE\",\"country\":\"England\",\"founded\":1905,\"national\":false,\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/teams\\/49.png\"},\"venue\":{\"id\":519,\"name\":\"Stamford Bridge\",\"address\":\"Fulham Road\",\"city\":\"London\",\"capacity\":41841,\"surface\":\"grass\",\"image\":\"https:\\/\\/media-2.api-sports.io\\/football\\/venues\\/519.png\"}}]}\n";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ShareData sd = ShareData.getInstance();
        String teamName = sd.getTeamname();
//        System.out.println("teamName " + teamName);
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
//                System.out.println("body: " + body);
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
            Long id = (Long) echipa.get("id");
            sd.setTeamId (id);
            tmpEchipaFavorita.setTeamId(id);

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
        String body2 = null;
        if(sd.getUseAPI()) {


            OkHttpClient client2 = new OkHttpClient();

            Request request2 = new Request.Builder()
                    .url("https://api-football-v1.p.rapidapi.com/v2/fixtures/team/" + sd.getTeamId() + "/next/10?timezone=Europe%2FLondon")
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
        }else
         body2 = "{\"api\":{\"results\":10,\"fixtures\":[{\"fixture_id\":1030298,\"league_id\":4991,\"league\":{\"name\":\"Friendlies Clubs\",\"country\":\"World\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/leagues\\/667.png\",\"flag\":null},\"event_date\":\"2023-07-12T16:00:00+01:00\",\"event_timestamp\":1689174000,\"firstHalfStart\":null,\"secondHalfStart\":null,\"round\":\"Club Friendlies 1\",\"status\":\"Not Started\",\"statusShort\":\"NS\",\"elapsed\":0,\"venue\":\"Ullevaal Stadion\",\"referee\":null,\"homeTeam\":{\"team_id\":33,\"team_name\":\"Manchester United\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/33.png\"},\"awayTeam\":{\"team_id\":63,\"team_name\":\"Leeds\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/63.png\"},\"goalsHomeTeam\":null,\"goalsAwayTeam\":null,\"score\":{\"halftime\":null,\"fulltime\":null,\"extratime\":null,\"penalty\":null}},{\"fixture_id\":1030300,\"league_id\":4991,\"league\":{\"name\":\"Friendlies Clubs\",\"country\":\"World\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/leagues\\/667.png\",\"flag\":null},\"event_date\":\"2023-07-19T14:00:00+01:00\",\"event_timestamp\":1689771600,\"firstHalfStart\":null,\"secondHalfStart\":null,\"round\":\"Club Friendlies 1\",\"status\":\"Not Started\",\"statusShort\":\"NS\",\"elapsed\":0,\"venue\":\"Murrayfield Stadium\",\"referee\":null,\"homeTeam\":{\"team_id\":33,\"team_name\":\"Manchester United\",\"logo\":\"https:\\/\\/media-1.api-sports.io\\/football\\/teams\\/33.png\"},\"awayTeam\":{\"team_id\":80,\"team_name\":\"Lyon\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/teams\\/80.png\"},\"goalsHomeTeam\":null,\"goalsAwayTeam\":null,\"score\":{\"halftime\":null,\"fulltime\":null,\"extratime\":null,\"penalty\":null}},{\"fixture_id\":1030302,\"league_id\":4991,\"league\":{\"name\":\"Friendlies Clubs\",\"country\":\"World\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/leagues\\/667.png\",\"flag\":null},\"event_date\":\"2023-07-22T22:00:00+01:00\",\"event_timestamp\":1690059600,\"firstHalfStart\":null,\"secondHalfStart\":null,\"round\":\"Club Friendlies 1\",\"status\":\"Not Started\",\"statusShort\":\"NS\",\"elapsed\":0,\"venue\":\"MetLife Stadium\",\"referee\":null,\"homeTeam\":{\"team_id\":42,\"team_name\":\"Arsenal\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/teams\\/42.png\"},\"awayTeam\":{\"team_id\":33,\"team_name\":\"Manchester United\",\"logo\":\"https:\\/\\/media-1.api-sports.io\\/football\\/teams\\/33.png\"},\"goalsHomeTeam\":null,\"goalsAwayTeam\":null,\"score\":{\"halftime\":null,\"fulltime\":null,\"extratime\":null,\"penalty\":null}},{\"fixture_id\":1030307,\"league_id\":4991,\"league\":{\"name\":\"Friendlies Clubs\",\"country\":\"World\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/leagues\\/667.png\",\"flag\":null},\"event_date\":\"2023-07-26T03:30:00+01:00\",\"event_timestamp\":1690338600,\"firstHalfStart\":null,\"secondHalfStart\":null,\"round\":\"Club Friendlies 1\",\"status\":\"Not Started\",\"statusShort\":\"NS\",\"elapsed\":0,\"venue\":\"Snapdragon Stadium\",\"referee\":null,\"homeTeam\":{\"team_id\":33,\"team_name\":\"Manchester United\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/33.png\"},\"awayTeam\":{\"team_id\":1837,\"team_name\":\"Wrexham\",\"logo\":\"https:\\/\\/media-1.api-sports.io\\/football\\/teams\\/1837.png\"},\"goalsHomeTeam\":null,\"goalsAwayTeam\":null,\"score\":{\"halftime\":null,\"fulltime\":null,\"extratime\":null,\"penalty\":null}},{\"fixture_id\":1030312,\"league_id\":4991,\"league\":{\"name\":\"Friendlies Clubs\",\"country\":\"World\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/leagues\\/667.png\",\"flag\":null},\"event_date\":\"2023-07-27T03:30:00+01:00\",\"event_timestamp\":1690425000,\"firstHalfStart\":null,\"secondHalfStart\":null,\"round\":\"Club Friendlies 1\",\"status\":\"Not Started\",\"statusShort\":\"NS\",\"elapsed\":0,\"venue\":\"NRG Stadium\",\"referee\":null,\"homeTeam\":{\"team_id\":541,\"team_name\":\"Real Madrid\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/541.png\"},\"awayTeam\":{\"team_id\":33,\"team_name\":\"Manchester United\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/33.png\"},\"goalsHomeTeam\":null,\"goalsAwayTeam\":null,\"score\":{\"halftime\":null,\"fulltime\":null,\"extratime\":null,\"penalty\":null}},{\"fixture_id\":1030319,\"league_id\":4991,\"league\":{\"name\":\"Friendlies Clubs\",\"country\":\"World\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/leagues\\/667.png\",\"flag\":null},\"event_date\":\"2023-07-31T02:00:00+01:00\",\"event_timestamp\":1690765200,\"firstHalfStart\":null,\"secondHalfStart\":null,\"round\":\"Club Friendlies 1\",\"status\":\"Not Started\",\"statusShort\":\"NS\",\"elapsed\":0,\"venue\":\"Allegiant Stadium\",\"referee\":null,\"homeTeam\":{\"team_id\":33,\"team_name\":\"Manchester United\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/33.png\"},\"awayTeam\":{\"team_id\":165,\"team_name\":\"Borussia Dortmund\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/165.png\"},\"goalsHomeTeam\":null,\"goalsAwayTeam\":null,\"score\":{\"halftime\":null,\"fulltime\":null,\"extratime\":null,\"penalty\":null}},{\"fixture_id\":1035046,\"league_id\":5267,\"league\":{\"name\":\"Premier League\",\"country\":\"England\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/leagues\\/39.png\",\"flag\":\"https:\\/\\/media-1.api-sports.io\\/flags\\/gb.svg\"},\"event_date\":\"2023-08-14T20:00:00+01:00\",\"event_timestamp\":1692039600,\"firstHalfStart\":null,\"secondHalfStart\":null,\"round\":\"Regular Season - 1\",\"status\":\"Not Started\",\"statusShort\":\"NS\",\"elapsed\":0,\"venue\":\"Old Trafford\",\"referee\":null,\"homeTeam\":{\"team_id\":33,\"team_name\":\"Manchester United\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/teams\\/33.png\"},\"awayTeam\":{\"team_id\":39,\"team_name\":\"Wolves\",\"logo\":\"https:\\/\\/media-1.api-sports.io\\/football\\/teams\\/39.png\"},\"goalsHomeTeam\":null,\"goalsAwayTeam\":null,\"score\":{\"halftime\":null,\"fulltime\":null,\"extratime\":null,\"penalty\":null}},{\"fixture_id\":1035054,\"league_id\":5267,\"league\":{\"name\":\"Premier League\",\"country\":\"England\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/leagues\\/39.png\",\"flag\":\"https:\\/\\/media-1.api-sports.io\\/flags\\/gb.svg\"},\"event_date\":\"2023-08-19T15:00:00+01:00\",\"event_timestamp\":1692453600,\"firstHalfStart\":null,\"secondHalfStart\":null,\"round\":\"Regular Season - 2\",\"status\":\"Not Started\",\"statusShort\":\"NS\",\"elapsed\":0,\"venue\":\"Tottenham Hotspur Stadium\",\"referee\":null,\"homeTeam\":{\"team_id\":47,\"team_name\":\"Tottenham\",\"logo\":\"https:\\/\\/media-1.api-sports.io\\/football\\/teams\\/47.png\"},\"awayTeam\":{\"team_id\":33,\"team_name\":\"Manchester United\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/teams\\/33.png\"},\"goalsHomeTeam\":null,\"goalsAwayTeam\":null,\"score\":{\"halftime\":null,\"fulltime\":null,\"extratime\":null,\"penalty\":null}},{\"fixture_id\":1035064,\"league_id\":5267,\"league\":{\"name\":\"Premier League\",\"country\":\"England\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/leagues\\/39.png\",\"flag\":\"https:\\/\\/media-3.api-sports.io\\/flags\\/gb.svg\"},\"event_date\":\"2023-08-26T15:00:00+01:00\",\"event_timestamp\":1693058400,\"firstHalfStart\":null,\"secondHalfStart\":null,\"round\":\"Regular Season - 3\",\"status\":\"Not Started\",\"statusShort\":\"NS\",\"elapsed\":0,\"venue\":\"Old Trafford\",\"referee\":null,\"homeTeam\":{\"team_id\":33,\"team_name\":\"Manchester United\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/33.png\"},\"awayTeam\":{\"team_id\":65,\"team_name\":\"Nottingham Forest\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/65.png\"},\"goalsHomeTeam\":null,\"goalsAwayTeam\":null,\"score\":{\"halftime\":null,\"fulltime\":null,\"extratime\":null,\"penalty\":null}},{\"fixture_id\":1035067,\"league_id\":5267,\"league\":{\"name\":\"Premier League\",\"country\":\"England\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/leagues\\/39.png\",\"flag\":\"https:\\/\\/media-3.api-sports.io\\/flags\\/gb.svg\"},\"event_date\":\"2023-09-02T15:00:00+01:00\",\"event_timestamp\":1693663200,\"firstHalfStart\":null,\"secondHalfStart\":null,\"round\":\"Regular Season - 4\",\"status\":\"Not Started\",\"statusShort\":\"NS\",\"elapsed\":0,\"venue\":\"Emirates Stadium\",\"referee\":null,\"homeTeam\":{\"team_id\":42,\"team_name\":\"Arsenal\",\"logo\":\"https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/42.png\"},\"awayTeam\":{\"team_id\":33,\"team_name\":\"Manchester United\",\"logo\":\"https:\\/\\/media-2.api-sports.io\\/football\\/teams\\/33.png\"},\"goalsHomeTeam\":null,\"goalsAwayTeam\":null,\"score\":{\"halftime\":null,\"fulltime\":null,\"extratime\":null,\"penalty\":null}}]}}\n";

        List<FixturesIndividual> listMeciuri = new ArrayList<>();

        try {
            JSONParser parse = new JSONParser();
            JSONObject dataObject = null;
            dataObject = (JSONObject) parse.parse(body2);
            JSONObject api = (JSONObject) dataObject.get("api");
            JSONArray fixtures = (JSONArray) api.get("fixtures");

            for(int i = 0; i < fixtures.size(); i++) {
                FixturesIndividual games = new FixturesIndividual();
                JSONObject meci = (JSONObject) fixtures.get(i);
                JSONObject detalii = (JSONObject) meci.get("league");
                games.setLeague((String) detalii.get("name"));
                games.setDate((String) meci.get("event_date"));
                JSONObject homeTeam = (JSONObject) meci.get("homeTeam");
                games.setHomeTeam((String) homeTeam.get("team_name"));
                JSONObject awayTeam = (JSONObject) meci.get("awayTeam");
                games.setAwayTeam((String) awayTeam.get("team_name"));
                listMeciuri.add(games);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        List<FixturesIndividual> meciuri = listMeciuri;
        fixturesList.setItems(fixturesListView);
        for (FixturesIndividual meci:meciuri){
            fixturesListView.add(String.valueOf(meci));
        }

    }
}
