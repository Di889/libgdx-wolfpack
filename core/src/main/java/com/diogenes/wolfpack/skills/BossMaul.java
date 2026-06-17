package com.diogenes.wolfpack.skills;

import com.diogenes.wolfpack.entities.Unit;

public class BossMaul extends Skill {

    public BossMaul() {
        super("Dilascerada Forte", "Ataque devastador que causa 200% do ATQ em dano.", TargetingType.SINGLE_ENEMY);
    }

    @Override
    protected void execute(Unit user, Unit target) {
        target.takeDamage(user.getAttack() * 2);
    }
}
