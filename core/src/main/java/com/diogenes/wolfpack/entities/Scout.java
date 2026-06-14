package com.diogenes.wolfpack.entities;

import com.diogenes.wolfpack.skills.Bite;

public class Scout extends Wolf{
    public Scout() {
        super("Rastreador", 25, 7, 3, 8);

        // Base skills to Scout Wolf
        addSkill(new Bite());
    }

    @Override
    void onLevelUp() {
        this.maxHp++;
        this.speed++;
        // TODO: will get a new skill automatically in level 3, probably
    }
}
