package com.diogenes.wolfpack.entities;

public class Scout extends Wolf{
    public Scout() {
        super("Scout", 25, 7, 3, 8);

        // Base skills to Scout Wolf
    }

    @Override
    void onLevelUp() {
        this.maxHp++;
        this.speed++;
        // TODO: will get a new skill automatically in level 3, probably
    }
}
