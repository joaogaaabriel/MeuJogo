package com.seunome.meujogo.manager;

import com.badlogic.gdx.Gdx;
import com.seunome.meujogo.entity.Asteroid;

import java.util.ArrayList;
import java.util.List;

public class AsteroidManager {

    private static final float SPAWN_INTERVAL = 1f;

    private final List<Asteroid> asteroids = new ArrayList<>();
    private float spawnTimer = 0f;

    public void update(float delta) {
        spawnTimer += delta;
        if (spawnTimer >= SPAWN_INTERVAL) {
            spawnTimer = 0f;
            float x = (float) (Math.random() * Gdx.graphics.getWidth());
            float y = Gdx.graphics.getHeight();
            asteroids.add(new Asteroid(x, y));
        }

        for (Asteroid a : asteroids) {
            a.update(delta);
        }

        asteroids.removeIf(a -> a.y < -50f);
    }

    public void reset() {
        asteroids.clear();
        spawnTimer = 0f;
    }

    public List<Asteroid> getAsteroids() {
        return asteroids;
    }
}

