package com.diogenes.wolfpack.skills;

import com.diogenes.wolfpack.effects.Bleed;
import com.diogenes.wolfpack.entities.Unit;

public class TuskSwing extends Skill {

    public TuskSwing() {
        super("Golpe de Presas", "Causa 80% do ATQ em dano e aplica Sangramento.", TargetingType.SINGLE_ENEMY);
    }

    @Override
    protected void execute(Unit user, Unit target) {
        target.takeDamage((int)(user.getAttack() * 0.8));
        target.addStatusEffect(new Bleed());
    }
}
