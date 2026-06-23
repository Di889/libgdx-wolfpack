package com.diogenes.wolfpack.entities;

import com.diogenes.wolfpack.battle.BattleAction;
import com.diogenes.wolfpack.effects.AttackUp;
import com.diogenes.wolfpack.skills.FoxNip;
import com.diogenes.wolfpack.skills.QuickPounce;
import com.diogenes.wolfpack.skills.Skill;

import java.util.ArrayList;
import java.util.List;

public class Fox extends Enemy {

    public Fox() {
        super("Raposa", 18, 5, 2, 9);
        this.foodReward = 1;

        addSkill(new FoxNip());
        addSkill(new QuickPounce());
    }

    @Override
    public BattleAction chooseAction(List<? extends Unit> targets) {
        Unit lowestHpTarget = targets.get(0);
        for (Unit u : targets) {
            if (u.getHp() < lowestHpTarget.getHp()) {
                lowestHpTarget = u;
            }
        }

        Skill skillToUse = this.hasStatusEffect(AttackUp.class) ? getSkills().get(0) : getSkills().get(1);

        return new BattleAction(skillToUse, lowestHpTarget);
    }
}
