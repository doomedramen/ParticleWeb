package com.wookoouk.particleweb;

import java.util.Random;

/**
 * Created by martinpage on 17/01/15.
 */
public class Point {

    float x, y, dx, dy, speed;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;

        Random random = new Random();

        dx = (random.nextFloat() - random.nextFloat());
        dy = (random.nextFloat() - random.nextFloat());
        speed = (random.nextFloat() - random.nextFloat() + 1)*3;
    }

    public void translate() {
        x += (dx * speed);
        y += (dy * speed);
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }
}
