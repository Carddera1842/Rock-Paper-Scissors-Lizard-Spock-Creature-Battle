package org.example.model;

public class BattleCreature {

    private Creature creature;

    private int currentHp;
    private int currentAttack;
    private int currentDefense;

    private boolean poisoned;
    private int poisonDamage;

    public BattleCreature(Creature creature) {
        this.creature = creature;

        this.currentHp = creature.getBaseHp();
        this.currentAttack = creature.getAttack();
        this.currentDefense = creature.getDefense();

        this.poisoned = false;
        this.poisonDamage = 0;
    }

    public Creature getCreature() {
        return creature;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getCurrentAttack() {
        return currentAttack;
    }

    public int getCurrentDefense() {
        return currentDefense;
    }

    public boolean isPoisoned() {
        return poisoned;
    }

    public int getPoisonDamage() {
        return poisonDamage;
    }

    public boolean isFainted() {
        return currentHp <= 0;
    }

    public void takeDamage(int damage) {

        currentHp -= damage;

        if (currentHp < 0) {
            currentHp = 0;
        }
    }

    public void heal(int amount) {

        currentHp += amount;

        if (currentHp > creature.getBaseHp()) {
            currentHp = creature.getBaseHp();
        }
    }

    public void increaseAttack(int amount) {
        currentAttack += amount;
    }

    public void decreaseAttack(int amount) {

        currentAttack -= amount;

        if (currentAttack < 1) {
            currentAttack = 1;
        }
    }

    public void increaseDefense(int amount) {
        currentDefense += amount;
    }

    public void decreaseDefense(int amount) {

        currentDefense -= amount;

        if (currentDefense < 1) {
            currentDefense = 1;
        }
    }

    public void applyPoison(int damage) {
        poisoned = true;
        poisonDamage = damage;
    }

    public void applyPoisonDamage() {

        if (poisoned) {
            takeDamage(poisonDamage);
        }
    }

    public void curePoison() {
        poisoned = false;
        poisonDamage = 0;
    }
}