package com.diogenes.wolfpack.effects;

import com.diogenes.wolfpack.entities.Unit;

public class Bleed extends StatusEffect {

    private static final int DAMAGE_PER_TURN = 2;

    public Bleed() {
        super("Sangramento", 2);
    }

    @Override
    public void onApply(Unit target) {
    }

    @Override
    public void onTurnStart(Unit target) {
        target.applyTrueDamage(DAMAGE_PER_TURN);
    }

    @Override
    public void onRemove(Unit target) {
    }
}
