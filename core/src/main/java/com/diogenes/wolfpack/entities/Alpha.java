package com.diogenes.wolfpack.entities;

import com.diogenes.wolfpack.skills.Bite;

public class Alpha extends Wolf{

    public Alpha() {
        super("Alpha", 45, 8, 7, 4);

        // Base skills to Alpha Wolf
        addSkill(new Bite()); // Adding Bite Skill
    }

    @Override
    void onLevelUp() {
        this.maxHp += 3;
        // TODO: will get a new skill automatically in level 3, probably
    }
}
