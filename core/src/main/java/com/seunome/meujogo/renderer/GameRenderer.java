package com.seunome.meujogo.renderer;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.seunome.meujogo.entity.Asteroid;
import com.seunome.meujogo.entity.Player;

import java.util.List;

public class GameRenderer {

    private static final float GOAL_ROTATION = 45f;

    private final OrthographicCamera camera;
    private final SpriteBatch batch;

    private Texture playerTexture;
    private Texture asteroidTexture;
    private Texture goalTexture;

    public GameRenderer(int screenWidth, int screenHeight) {
        camera = new OrthographicCamera(screenWidth, screenHeight);
        batch = new SpriteBatch();

        playerTexture = new Texture("nave.png");
        asteroidTexture = new Texture("asteroide.png");
        goalTexture = new Texture("marte.png");

        camera.position.set(screenWidth / 2f, screenHeight / 2f, 0f);
        camera.update();
    }

    public void draw(Player player, List<Asteroid> asteroids,
                     boolean goalActive, float goalX, float goalY, float goalSize) {

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        if (goalActive) {
            drawGoal(goalX, goalY, goalSize);
        }

        drawAsteroids(asteroids);
        drawPlayer(player);

        batch.end();
    }

    private void drawPlayer(Player player) {
        batch.draw(
                playerTexture,
                player.x,
                player.y,
                player.width,
                player.height
        );
    }

    private void drawAsteroids(List<Asteroid> asteroids) {
        for (Asteroid asteroid : asteroids) {
            batch.draw(
                    asteroidTexture,
                    asteroid.x, asteroid.y,
                    asteroid.width / 2f, asteroid.height / 2f,
                    asteroid.width, asteroid.height,
                    1f, 1f,
                    asteroid.rotation,
                    0, 0,
                    asteroidTexture.getWidth(), asteroidTexture.getHeight(),
                    false, false
            );
        }
    }

    private void drawGoal(float goalX, float goalY, float goalSize) {
        batch.draw(
                goalTexture,
                goalX, goalY,
                goalSize / 2f, goalSize / 2f,
                goalSize, goalSize,
                1f, 1f,
                GOAL_ROTATION,
                0, 0,
                goalTexture.getWidth(), goalTexture.getHeight(),
                false, false
        );
    }

    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.position.set(width / 2f, height / 2f, 0f);
        camera.update();
    }

    public void dispose() {
        batch.dispose();

        if (playerTexture != null) {
            playerTexture.dispose();
            playerTexture = null;
        }

        if (asteroidTexture != null) {
            asteroidTexture.dispose();
            asteroidTexture = null;
        }

        if (goalTexture != null) {
            goalTexture.dispose();
            goalTexture = null;
        }
    }
}