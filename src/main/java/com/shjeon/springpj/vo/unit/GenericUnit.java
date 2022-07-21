package com.shjeon.springpj.vo.unit;


import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Timer;
import java.util.TimerTask;

@ToString
@SuperBuilder
@Data
public class GenericUnit {
    protected int hp;
    protected int mp;
    protected int attack;
    protected double attackSpeed;
    protected int defend;
    protected int money=0;

    protected int maxHp;
    protected int maxMp;


    public void heal() {
        if (this.hp < this.maxHp) {
            if (this.hp + 20 < this.maxHp) {
                this.hp = this.maxHp;
            } else {
                this.hp += 20;
            }
        }
    }

    public void steam() {
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

    public GenericUnit(){

    }

    public GenericUnit(int hp, int mp, int attack, double attackSpeed, int defend, int maxHp, int maxMp) {
        this.hp = hp;
        this.mp = mp;
        this.attack = attack;
        this.attackSpeed = attackSpeed;
        this.defend = defend;
        this.maxHp = maxHp;
        this.maxMp = maxMp;
    }
}
