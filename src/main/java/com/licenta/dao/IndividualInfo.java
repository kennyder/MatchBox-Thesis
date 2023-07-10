package com.licenta.dao;

public class IndividualInfo {

    String name;
    String country;
    long founded;
    String stadiumName;
    String stadiumCity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getFounded() {
        return founded;
    }

    public void setFounded(long founded) {
        this.founded = founded;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public String getStadiumCity() {
        return stadiumCity;
    }

    public void setStadiumCity(String stadiumCity) {
        this.stadiumCity = stadiumCity;
    }

    public IndividualInfo() {
        this.name = name;
        this.country = country;
        this.founded = founded;
        this.stadiumName = stadiumName;
        this.stadiumCity = stadiumCity;
    }

    @Override
    public String toString() {
        return "IndividualInfo{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", founded=" + founded +
                ", stadiumName='" + stadiumName + '\'' +
                ", stadiumCity='" + stadiumCity + '\'' +
                '}';
    }
}
