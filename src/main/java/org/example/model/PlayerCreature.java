package org.example.model;

public class PlayerCreature {

    private int id;
    private int playerId;
    private Creature creature;

    private int level;
    private int experience;
    private int wins;
    private int losses;

    public PlayerCreature(
            int id,
            int playerId,
            Creature creature,
            int level,
            int experience,
            int wins,
            int losses
    ) {
        this.id = id;
        this.playerId = playerId;
        this.creature = creature;
        this.level = level;
        this.experience = experience;
        this.wins = wins;
        this.losses = losses;
    }

    public int getId() {
        return id;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Creature getCreature() {
        return creature;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public void addWin() {
        wins++;
    }

    public void addLoss() {
        losses++;
    }

    public void addExperience(int amount) {
        experience += amount;
    }

    @Override
    public String toString() {
        return creature.getName()
                + " | Level: " + level
                + " | XP: " + experience
                + " | W: " + wins
                + " | L: " + losses;
    }

    public boolean checkForLevelUp() {

        int requiredXp = level * 100;

        if (experience >= requiredXp) {

            experience -= requiredXp;
            level++;

            return true;
        }

        return false;
    }

    public int checkForLevelUps() {

        int levelsGained = 0;

        while (experience >= level * 100) {

            experience -= level * 100;
            level++;

            levelsGained++;
        }

        return levelsGained;
    }
}