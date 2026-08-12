package org.example.model;

public class Move {

    private int id;
    private String name;
    private String type;
    private int damage;
    private int accuracy;
    private String description;
    private String moveCategory;
    private int effectValue;

    public Move(
            int id,
            String name,
            String type,
            int damage,
            int accuracy,
            String description,
            String moveCategory,
            int effectValue
    ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.damage = damage;
        this.accuracy = accuracy;
        this.description = description;
        this.moveCategory = moveCategory;
        this.effectValue = effectValue;
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

    public int getDamage() {
        return damage;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public String getDescription() {
        return description;
    }

    public String getMoveCategory() {
        return moveCategory;
    }

    public int getEffectValue() {
        return effectValue;
    }
}