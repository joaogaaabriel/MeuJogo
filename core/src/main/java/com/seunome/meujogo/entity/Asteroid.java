package com.seunome.meujogo.entity;

public class Asteroid {

    public float x;
    public float y;
    public float speed = 200;

    public float width = 64;
    public float height = 64;

    public float rotation = 0;

    public Asteroid(float x, float y) {
        this.x = x;
        this.y = y;
        this.rotation = (float) Math.random() * 360;
    }

    public void update(float delta) {
        y -= speed * delta;
        rotation += 90 * delta;
    }
}
