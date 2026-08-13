package org.example.model;

public class Player {

    private int id;
    private String username;

    public Player(int id, String username) {
        this.id = id;
        this.username = username;
    }

    // Used when creating a brand-new player
    // before MySQL assigns an ID
    public Player(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return username;
    }
}