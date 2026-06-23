package com.diogenes.wolfpack.skills;

import com.diogenes.wolfpack.effects.AttackUp;
import com.diogenes.wolfpack.entities.Unit;

public class QuickPounce extends Skill {

    private static final int SELF_BUFF_DURATION = 2;

    public QuickPounce() {
        super("Salto Rápido", "Causa 80% do ATQ em dano e aumenta o próprio ATQ por 2 turnos.", TargetingType.SINGLE_ENEMY);
    }

    @Override
    protected void execute(Unit user, Unit target) {
        target.takeDamage((int)(user.getAttack() * 0.8));
        user.addStatusEffect(new AttackUp(SELF_BUFF_DURATION));
    }
}
