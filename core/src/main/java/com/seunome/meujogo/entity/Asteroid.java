package com.seunome.meujogo.entity;

public class Asteroid {

    public float x;
    public float y;
    public float speed = 200;

    public float width = 32;
    public float height = 32;

    public Asteroid(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update(float delta) {
        y -= speed * delta;
    }
}
