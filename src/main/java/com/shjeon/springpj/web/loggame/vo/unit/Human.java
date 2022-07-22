package com.shjeon.springpj.web.loggame.vo.unit;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Timer;
import java.util.TimerTask;

@Getter
@Setter
@SuperBuilder
@ToString
public class Human extends GenericUnit{

    public Human(){
        super(1,150, 90,30,1.5,20,150,90);
    }

    @Override
    void levelUp() {
        this.level += 1;
        this.attack += 10;
        this.defend += 2;
        this.maxHp += 50;
        this.maxMp += 10;
        this.hp = maxHp;
        this.mp = maxMp;
    }

    public void upGuard(){
        if (this.mp > 10) {
            Timer timer = new Timer();
            int orgDefend = this.defend;
            this.defend = (int) (orgDefend + orgDefend*0.2);
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    System.out.println(defend);
                    defend = orgDefend;
                }
            };
            timer.schedule(task, 10000);
        }
    }
}
