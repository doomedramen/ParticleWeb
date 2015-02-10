package com.wookoouk.particleweb;

import java.util.Random;

public class Point {

    int min = 1;
    int max = 2;

    float x, y, dx, dy;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;

        Random random = new Random();

        int dxI = min + (int) (Math.random() * max);
        int dyI = min + (int) (Math.random() * max);

        if (random.nextBoolean()) {
            dxI = -dxI;
        }
        if (random.nextBoolean()) {
            dyI = -dyI;
        }

        dx = dxI;
        dy = dyI;

    }

    public void translate() {
        x += dx;
        y += dy;
    }

//    public void setPosition(float x, float y) {
//        this.x = x;
//        this.y = y;
//    }
}
