package com.churpi.minicerdo.dto;

/**
 * Created by gdlfravi on 7/25/16.
 */
public class GameLevel {
    private String name;
    private String description;
    private int version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
