package com.diogenes.wolfpack.skills;

import com.diogenes.wolfpack.entities.Unit;

public class BossMaul extends Skill {

    public BossMaul() {
        super("Dilascerada Forte", "Ataque devastador que causa 140% do ATQ em dano.", TargetingType.SINGLE_ENEMY);
    }

    @Override
    protected void execute(Unit user, Unit target) {
        target.takeDamage((int)(user.getAttack() * 1.4));
    }
}
