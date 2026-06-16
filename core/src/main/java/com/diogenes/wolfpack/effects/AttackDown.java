package com.diogenes.wolfpack.effects;

import com.diogenes.wolfpack.entities.Unit;

public class AttackDown extends StatusEffect {

    private final int attackPenalty;

    public AttackDown(int duration, int attackPenalty) {
        super("Ataque Reduzido", duration);
        this.attackPenalty = attackPenalty;
    }

    @Override
    public void onApply(Unit target) {
        target.modifyAttack(-attackPenalty);
    }

    @Override
    public void onTurnStart(Unit target) {
    }

    @Override
    public void onRemove(Unit target) {
        target.modifyAttack(attackPenalty);
    }
}
