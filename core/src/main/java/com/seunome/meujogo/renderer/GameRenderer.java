package com.seunome.meujogo.renderer;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.seunome.meujogo.entity.Asteroid;
import com.seunome.meujogo.entity.Player;

import java.util.List;

public class GameRenderer {

    private static final float PLAYER_WIDTH  = 32f;
    private static final float PLAYER_HEIGHT = 32f;

    private final OrthographicCamera camera;
    private final SpriteBatch        batch;
    private final ShapeRenderer      shape;
    private final Texture            playerTexture;

    public GameRenderer(int screenWidth, int screenHeight) {
        camera        = new OrthographicCamera(screenWidth, screenHeight);
        batch         = new SpriteBatch();
        shape         = new ShapeRenderer();
        playerTexture = new Texture("libgdx.png");

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

        shape.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);

        drawShapes(asteroids, goalActive, goalX, goalY, goalSize);
        drawPlayer(player);
    }

    private void drawShapes(List<Asteroid> asteroids,
                             boolean goalActive, float goalX, float goalY, float goalSize) {
        shape.begin(ShapeRenderer.ShapeType.Filled);

        if (goalActive) {
            shape.setColor(0, 1, 0, 1);
            shape.rect(goalX, goalY, goalSize, goalSize);
        }

        shape.setColor(1, 0, 0, 1);
        for (Asteroid a : asteroids) {
            shape.rect(a.x, a.y, a.width, a.height);
        }

        shape.end();
    }

    private void drawPlayer(Player player) {
        batch.begin();
        batch.draw(playerTexture, player.x, player.y, PLAYER_WIDTH, PLAYER_HEIGHT);
        batch.end();
    }

    public void dispose() {
        batch.dispose();
        shape.dispose();
        playerTexture.dispose();
    }
}