package com.diogenes.wolfpack.entities;

import com.diogenes.wolfpack.battle.BattleAction;
import com.diogenes.wolfpack.effects.Bleed;
import com.diogenes.wolfpack.skills.Bash;
import com.diogenes.wolfpack.skills.Skill;
import com.diogenes.wolfpack.skills.TuskSwing;

import java.util.ArrayList;
import java.util.List;

public class Boar extends Enemy {

    public Boar() {
        super("Javali", 30, 6, 4, 4);
        this.foodReward = 2;

        addSkill(new Bash());
        addSkill(new TuskSwing());
    }

    @Override
    public BattleAction chooseAction(List<? extends Unit> targets) {
        List<Unit> aliveTargets = new ArrayList<>();
        for (Unit u : targets) {
            if (u.isAlive()) aliveTargets.add(u);
        }
        if (aliveTargets.isEmpty()) return null;

        Unit highestHpTarget = aliveTargets.get(0);
        for (Unit u : aliveTargets) {
            if (u.getHp() > highestHpTarget.getHp()) {
                highestHpTarget = u;
            }
        }

        Skill skillToUse = highestHpTarget.hasStatusEffect(Bleed.class) ? getSkills().get(0) : getSkills().get(1);

        return new BattleAction(skillToUse, highestHpTarget);
    }
}
