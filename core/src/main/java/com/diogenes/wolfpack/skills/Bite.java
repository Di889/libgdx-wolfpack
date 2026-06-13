package com.diogenes.wolfpack.skills;

import com.diogenes.wolfpack.entities.Unit;

public class Bite extends Skill {

    public Bite() {
        super("Bite", "Basic Attack", 0, false);
    }

    @Override
    public void execute(Unit user, Unit target) {
        target.takeDamage(user.getAttack());

    }
}
