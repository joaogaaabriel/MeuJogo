package com.seunome.meujogo.renderer;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.seunome.meujogo.entity.Asteroid;
import com.seunome.meujogo.entity.Player;

import java.util.List;

public class GameRenderer {

    private static final float PLAYER_WIDTH  = 64f;
    private static final float PLAYER_HEIGHT = 64f;
    private static final float GOAL_ROTATION = 45f;

    private final OrthographicCamera camera;
    private final SpriteBatch        batch;
    private final Texture            playerTexture;
    private final Texture            asteroidTexture;
    private final Texture            goalTexture;

    public GameRenderer(int screenWidth, int screenHeight) {
        camera          = new OrthographicCamera(screenWidth, screenHeight);
        batch           = new SpriteBatch();
        playerTexture   = new Texture("nave.png");
        asteroidTexture = new Texture("asteroide.png");
        goalTexture     = new Texture("marte.png");

        camera.position.set(screenWidth / 2f, screenHeight / 2f, 0);
        camera.update();
    }

    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
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

    private void drawAsteroids(List<Asteroid> asteroids) {
        for (Asteroid a : asteroids) {
            batch.draw(
                asteroidTexture,
                a.x, a.y,
                a.width  / 2f, a.height / 2f,
                a.width, a.height,
                1f, 1f,
                a.rotation,
                0, 0,
                asteroidTexture.getWidth(), asteroidTexture.getHeight(),
                false, false
            );
        }
    }

    private void drawPlayer(Player player) {
        batch.draw(playerTexture, player.x, player.y, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    public void dispose() {
        batch.dispose();
        playerTexture.dispose();
        asteroidTexture.dispose();
        goalTexture.dispose();
    }
}