package com.shjeon.springpj.web.loggame.vo.unit;


import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Timer;
import java.util.TimerTask;

@ToString
@SuperBuilder
@Data
public abstract class GenericUnit {
    protected String name;
    protected String job;
    protected int level;
    protected int hp;
    protected int mp;
    protected int attack;
    protected double attackSpeed;
    protected int defend;
    @Builder.Default
    protected int money=0;

    protected int maxHp;
    protected int maxMp;


    public void heal() {
        if (this.hp < this.maxHp) {
            if (this.mp > 5){
                this.mp -= 5;
                if (this.hp + 20 < this.maxHp) {
                    this.hp = this.maxHp;
                } else {
                    this.hp += 20;
                }
            }
        }
    }

    public void steam() {
        if (this.mp > 10){
            Timer timer = new Timer();

            int orgAttack = this.attack;
            this.attack = (int) (this.attack + this.attack * 0.2);

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    attack = orgAttack;
                }
            };
            timer.schedule(task, 10000);
        }
    }

    abstract void levelUp();

    public GenericUnit(){

    }

    public GenericUnit(int level, int hp, int mp, int attack, double attackSpeed, int defend, int maxHp, int maxMp) {
        this.level = level;
        this.hp = hp;
        this.mp = mp;
        this.attack = attack;
        this.attackSpeed = attackSpeed;
        this.defend = defend;
        this.maxHp = maxHp;
        this.maxMp = maxMp;
    }
}
