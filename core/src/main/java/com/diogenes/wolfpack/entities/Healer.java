package com.diogenes.wolfpack.entities;

public class Healer extends Wolf{
    public Healer() {
        super("Healer", 30, 4, 4, 6);

        // Base skills to Healer Wolf
    }

    @Override
    void onLevelUp() {
        this.maxHp += 2;
        if(level % 2 == 0){
            this.speed++;
        }
        // TODO: will get a new skill automatically in level 3, probably
    }
}
