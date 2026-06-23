package com.diogenes.wolfpack.skills;

import com.diogenes.wolfpack.entities.Unit;

public class Nip extends Skill {

    public Nip() {
        super("Mordiscar", "Ataque místico que ignora defesa e causa 100% do ATQ.", TargetingType.SINGLE_ENEMY);
    }

    @Override
    protected void execute(Unit user, Unit target) {
        target.applyTrueDamage(user.getAttack());
    }
}
