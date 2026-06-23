package com.diogenes.wolfpack.skills;

import com.diogenes.wolfpack.effects.Marked;
import com.diogenes.wolfpack.entities.Unit;

public class QuickSnap extends Skill {

    public QuickSnap() {
        super("Bote Rápido", "Causa 110% do ATQ em dano. Se o alvo estiver Marcado, ignora a defesa.", TargetingType.SINGLE_ENEMY);
    }

    @Override
    protected void execute(Unit user, Unit target) {
        int baseDamage = (int)(user.getAttack() * 1.1);

        if (target.hasStatusEffect(Marked.class)) {
            target.applyTrueDamage(baseDamage);
        } else {
            target.takeDamage(baseDamage);
        }
    }
}
