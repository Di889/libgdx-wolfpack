package com.diogenes.wolfpack.battle;

import com.diogenes.wolfpack.effects.StatusEffect;
import com.diogenes.wolfpack.entities.Enemy;
import com.diogenes.wolfpack.entities.Unit;
import com.diogenes.wolfpack.entities.Wolf;
import com.diogenes.wolfpack.skills.TargetingType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BattleManager {

    private List<Wolf> wolves;
    private List<Enemy> enemies;
    private List<Unit> turnOrder;
    private int currentTurnIndex;

    public BattleManager(List<Wolf> wolves, List<Enemy> enemies){
        this.wolves = wolves;
        this.enemies = enemies;
        this.currentTurnIndex = 0;
        buildTurnOrder();
    }

    public void buildTurnOrder(){
        turnOrder = new ArrayList<>();
        turnOrder.addAll(enemies);
        turnOrder.addAll(wolves);
        turnOrder.sort(Comparator.comparingInt(Unit::getSpeed).reversed());
    }

    public Unit getCurrentUnit(){
        return turnOrder.get(currentTurnIndex);
    }

    public void nextTurn(){
        do{
            currentTurnIndex++;
            if(currentTurnIndex >= turnOrder.size()){
                currentTurnIndex = 0;
            }
        }while(!turnOrder.get(currentTurnIndex).isAlive());
    }

    public boolean playerWon(){
        for(Enemy e : enemies){
            if(e.isAlive())
                return false;
        }

        return true;

    }

    public boolean playerLost(){
        for(Wolf w : wolves){
            if(w.isAlive())
                return false;
        }

        return true;

    }

    public void executeAction(Unit user, BattleAction action){
        TargetingType targetingType = action.skill.getTargetingType();

        switch (targetingType) {
            case SINGLE_ENEMY:
            case SINGLE_ALLY:
                action.skill.use(user, action.target);
                break;
            case ALL_ENEMIES:
                action.skill.use(user, (user instanceof Wolf) ? enemies : wolves);
                break;
            case ALL_ALLIES:
                action.skill.use(user, (user instanceof Wolf) ? wolves : enemies);
                break;
            case SELF:
                action.skill.use(user, user);
                break;
        }
    }

    // manages the status effects of a given unit
    // should be called at the start(before action handling) of the unit's turn
    public void processTurnStart(Unit unit){
        List<StatusEffect> effects = new ArrayList<>(unit.getStatusEffects());

        for(StatusEffect effect : effects){
            effect.onTurnStart(unit);
            effect.tick();
            if(effect.isExpired()){
                unit.removeStatusEffect(effect);
            }
        }
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Wolf> getWolves() {
        return wolves;
    }

    public List<Unit> getTurnOrder() {
        return turnOrder;
    }
}
