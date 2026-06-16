package com.diogenes.wolfpack.effects;

import com.diogenes.wolfpack.entities.Unit;

public class AttackUp extends StatusEffect {

    private static final int ATTACK_BONUS = 2;

    public AttackUp(int duration) {
        super("Ataque Aumentado", duration);
    }

    @Override
    public void onApply(Unit target) {
        target.modifyAttack(ATTACK_BONUS);
    }

    @Override
    public void onTurnStart(Unit target) {
    }

    @Override
    public void onRemove(Unit target) {
        target.modifyAttack(-ATTACK_BONUS);
    }
}
