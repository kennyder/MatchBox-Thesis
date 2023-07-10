package com.example.demo1;

import com.licenta.dao.EchipaFavorita;
import com.licenta.dao.IndividualInfo;
import com.licenta.dao.ResourceManager;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class ShareData {


    private boolean useAPI = true; //true

    public ImageView logo;

    private java.util.Map<Long, EchipaFavorita> echipeFavorite = new HashMap<>();
    public java.util.Map<Long, EchipaFavorita> getListaEchipeFavorite(){
            try {
                echipeFavorite = (java.util.Map<Long, EchipaFavorita>) ResourceManager.load("echipeFavorite.obj");

        }catch(FileNotFoundException e){
            echipeFavorite = new HashMap<>();
        }catch(Exception e){
                e.printStackTrace();
            }
        return echipeFavorite;
    }

    public void addEchipaFavorita(EchipaFavorita e) throws Exception {
        echipeFavorite.put(e.getTeamId(), e);
        ResourceManager.save((Serializable) echipeFavorite, "echipeFavorite.obj");
    }

    public ImageView getLogo() {
        return logo;
    }

    public void setLogo(ImageView logo) {
        this.logo = logo;
    }

    Long playerID;

    public Long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(Long playerID) {
        this.playerID = playerID;
    }

    Long leagueID;

    public Long getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(Long leagueID) {
        this.leagueID = leagueID;
    }

    private static ShareData instance= null;
    IndividualInfo teamInfo = new IndividualInfo();

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public static void setInstance(ShareData instance) {
        ShareData.instance = instance;
    }

    public Long getTeamId() {
        return teamId;
    }

    private Long teamId = null;

    public boolean getUseAPI() {
        return useAPI;
    }

    public IndividualInfo getTeamInfo() {
        return teamInfo;
    }

    public void setTeamInfo(IndividualInfo teamInfo) {
        this.teamInfo = teamInfo;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    private String teamname;

    private ShareData(){

    }
    public static ShareData getInstance(){
        if (instance ==null){
            instance = new ShareData();
        }
        return instance;
    }
}
