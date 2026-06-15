package com.diogenes.wolfpack.entities;

import com.diogenes.wolfpack.effects.StatusEffect;
import com.diogenes.wolfpack.skills.Skill;

import java.util.ArrayList;
import java.util.List;

public abstract class Unit {

    protected String name;

    protected int maxHp;
    private int hp;
    protected int attack;
    protected int defense;
    protected int speed;

    protected List<StatusEffect> statusEffects;
    protected List<Skill> skills;

    public Unit(String name, int maxHp, int attack, int defense, int speed){
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.statusEffects = new ArrayList<>();
        this.skills = new ArrayList<>();
    }

    public boolean isAlive(){
        return hp > 0;
    }

    public int takeDamage(int damage){
        int realDamage = Math.max(1, damage - defense);
        hp = Math.max(0, hp - realDamage);
        return realDamage;
    }

    public int heal(int healAmount){
        int newHp = healAmount + hp;
        if(newHp > maxHp){
            newHp = maxHp;
        }
        healAmount = newHp - hp;
        hp = newHp;
        return healAmount;
    }

    public void addStatusEffect(StatusEffect effect){
        this.statusEffects.add(effect);
        effect.onApply(this);
    }

    public void removeStatusEffect(StatusEffect effect){
        effect.onRemove(this);
        this.statusEffects.remove(effect);
    }

    public List<StatusEffect> getStatusEffects() {
        return this.statusEffects;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAttack() {
        return this.attack;
    }

    public int getSpeed(){
        return this.speed;
    }

    public int getDefense(){
        return this.defense;
    }

    public List<Skill> getSkills(){
        return this.skills;
    }

    public void addSkill(Skill skill){
        this.skills.add(skill);
    }

    public void modifyAttack(int amount) { this.attack += amount; }

    public void modifyDefense(int amount) { this.defense += amount; }

    public void modifySpeed(int amount) { this.speed += amount; }
}
