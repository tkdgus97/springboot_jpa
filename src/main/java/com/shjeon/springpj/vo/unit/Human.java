package com.shjeon.springpj.vo.unit;

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
        super(150, 100,50,1.3,20,150,100);
    }

    public void upGuard(){
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
