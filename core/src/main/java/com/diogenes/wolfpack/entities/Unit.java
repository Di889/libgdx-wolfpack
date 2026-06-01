package com.diogenes.wolfpack.entities;

import com.diogenes.wolfpack.effects.StatusEffect;

import java.util.List;

public abstract class Unit {

    protected String name;
    protected int level;
    protected int xp; // TODO: sera refatorado pra apenas os playables charactears

    protected int maxHp;
    private int hp;
    protected int attack;
    protected int defense;
    protected int speed;

    protected List<StatusEffect> statusEffects; // TODO: inicializar como arraylist no construtor

    public Unit(String name, int maxHp, int attack, int defense, int speed){
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.xp = 0;
        this.level = 1;
    }

    public boolean isAlive(){
        return hp > 0;
    }

    public int takeDamage(int damage){
        int realDamage = damage - defense;
        if(realDamage < 0) {
            realDamage = 0;
        }
        int newHp = hp - realDamage;
        if(newHp < 0){
            newHp = 0;
        }
        hp = newHp;
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


}
