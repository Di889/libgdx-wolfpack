package com.diogenes.wolfpack.skills;

import com.diogenes.wolfpack.effects.AttackDown;
import com.diogenes.wolfpack.entities.Unit;

import java.util.List;

public class FerociousRoar extends Skill {

    private static final int DURATION = 2;
    private static final int ATTACK_PENALTY = 2;

    public FerociousRoar() {
        super("Rugido Feroz", "Reduz fortemente o ATQ de toda a matilha por 2 turnos.", TargetingType.ALL_ENEMIES);
    }

    @Override
    protected void execute(Unit user, Unit target) {
        target.addStatusEffect(new AttackDown(DURATION, ATTACK_PENALTY));
    }

    @Override
    protected void execute(Unit user, List<? extends Unit> targets) {
        for (Unit target : targets) {
            target.addStatusEffect(new AttackDown(DURATION, ATTACK_PENALTY));
        }
    }
}
