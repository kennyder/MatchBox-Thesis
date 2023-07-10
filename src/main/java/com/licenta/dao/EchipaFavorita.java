package com.licenta.dao;

import java.io.Serializable;

public class EchipaFavorita implements Serializable {
    private Long teamId;
    private String teamName;
    private String result;

    public EchipaFavorita(){

    }
    public EchipaFavorita(Long teamId, String teamName, String result) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.result = result;
    }

    @Override
    public String toString() {
        return  teamName + " " + result;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
