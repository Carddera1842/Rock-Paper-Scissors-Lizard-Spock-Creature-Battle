package org.example.model;

public class Creature {

    private int id;
    private String name;
    private String type;
    private int baseHp;
    private int attack;
    private int defense;
    private int speed;
    private String description;

    public Creature(
            int id,
            String name,
            String type,
            int baseHp,
            int attack,
            int defense,
            int speed,
            String description
    ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.baseHp = baseHp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getBaseHp() {
        return baseHp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public String getDescription() {
        return description;
    }
}