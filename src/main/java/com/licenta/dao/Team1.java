package com.licenta.dao;

public class Team1 {

    long cleanSheets;
    String form;
    long goalsFor;
    long goalsAgainst;
    long wins;

    public long getCleanSheets() {
        return cleanSheets;
    }

    public Team1() {
        this.cleanSheets = cleanSheets;
        this.form = form;
        this.goalsFor = goalsFor;
        this.goalsAgainst = goalsAgainst;
        this.wins = wins;
        this.draws = draws;
        this.defeats = defeats;
        this.biggestWinHome = biggestWinHome;
        this.biggestWinAway = biggestWinAway;
        this.failedToScore = failedToScore;
    }

    public void setCleanSheets(long cleanSheets) {
        this.cleanSheets = cleanSheets;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public long getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(long goalsFor) {
        this.goalsFor = goalsFor;
    }

    public long getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(long goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public long getWins() {
        return wins;
    }

    public void setWins(long wins) {
        this.wins = wins;
    }

    public long getDraws() {
        return draws;
    }

    public void setDraws(long draws) {
        this.draws = draws;
    }

    public long getDefeats() {
        return defeats;
    }

    public void setDefeats(long defeats) {
        this.defeats = defeats;
    }

    public long getBiggestWinHome() {
        return biggestWinHome;
    }

    public void setBiggestWinHome(long biggestWinHome) {
        this.biggestWinHome = biggestWinHome;
    }

    public long getBiggestWinAway() {
        return biggestWinAway;
    }

    public void setBiggestWinAway(long biggestWinAway) {
        this.biggestWinAway = biggestWinAway;
    }

    public long getFailedToScore() {
        return failedToScore;
    }

    public void setFailedToScore(long failedToScore) {
        this.failedToScore = failedToScore;
    }

    long draws;
    long defeats;
    long biggestWinHome;
    long biggestWinAway;
    long failedToScore;

    @Override
    public String toString() {
        return "Team1{" +
                "cleanSheets=" + cleanSheets +
                ", form='" + form + '\'' +
                ", goalsFor=" + goalsFor +
                ", goalsAgainst=" + goalsAgainst +
                ", wins=" + wins +
                ", draws=" + draws +
                ", defeats=" + defeats +
                ", biggestWinHome=" + biggestWinHome +
                ", biggestWinAway=" + biggestWinAway +
                ", failedToScore=" + failedToScore +
                '}';
    }
}
