package com.diogenes.wolfpack.entities;

public abstract class Wolf extends Unit {

    private boolean trainedAttack;
    private boolean trainedDefense;
    private boolean trainedHealth;

    public Wolf(String name, int maxHp, int attack, int defense, int speed) {
        super(name, maxHp, attack, defense, speed);
        this.trainedAttack = false;
        this.trainedDefense = false;
        this.trainedHealth = false;
    }

    public boolean trainAttack() {
        if (trainedAttack) return false;
        this.attack++;
        trainedAttack = true;
        return true;
    }

    public boolean trainDefense() {
        if (trainedDefense) return false;
        this.defense++;
        trainedDefense = true;
        return true;
    }

    public boolean trainMaxHp() {
        if (trainedDefense) return false;
        this.maxHp += 5;
        this.heal(5);
        trainedHealth = true;
        return true;
    }

    public boolean hasTrainedAttack() {
        return trainedAttack;
    }

    public boolean hasTrainedDefense() {
        return trainedDefense;
    }

    public boolean hasTrainedHealth() {
        return trainedHealth;
    }
}
