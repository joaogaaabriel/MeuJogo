package com.seunome.meujogo.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.seunome.meujogo.entity.Asteroid;
import com.seunome.meujogo.entity.Player;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private static final float PLAYER_WIDTH   = 32f;
    private static final float PLAYER_HEIGHT  = 32f;
    private static final float GOAL_X         = 500f;
    private static final float GOAL_Y         = 300f;
    private static final float GOAL_SIZE      = 50f;
    private static final float SPAWN_INTERVAL = 1f;
    private static final float RESET_DELAY    = 2f;

    private SpriteBatch    batch;
    private ShapeRenderer  shape;
    private Texture        playerTexture;

    private Player              player;
    private ArrayList<Asteroid> asteroids;

    private float   spawnTimer;
    private float   resetTimer;
    private boolean gameOver;
    private boolean venceu;

    @Override
    public void show() {
        batch         = new SpriteBatch();
        shape         = new ShapeRenderer();
        playerTexture = new Texture("libgdx.png");
        initGame();
    }

    private void initGame() {
        player    = new Player();
        asteroids = new ArrayList<>();

        player.x = Gdx.graphics.getWidth()  / 2f;
        player.y = 50f;

        spawnTimer = 0f;
        resetTimer = 0f;
        gameOver   = false;
        venceu     = false;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (gameOver || venceu) {
            resetTimer += delta;
            if (resetTimer >= RESET_DELAY) {
                initGame();
            }
            return;
        }

        updateGame(delta);
        drawGame();
    }

    private void updateGame(float delta) {
        player.update(delta);

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

        for (Asteroid a : asteroids) {
            if (overlaps(player.x, player.y, PLAYER_WIDTH, PLAYER_HEIGHT,
                         a.x,      a.y,      a.width,      a.height)) {
                gameOver   = true;
                resetTimer = 0f;
                System.out.println("GAME OVER!");
                return;
            }
        }

        if (overlaps(player.x, player.y, PLAYER_WIDTH, PLAYER_HEIGHT,
                     GOAL_X,   GOAL_Y,   GOAL_SIZE,    GOAL_SIZE)) {
            venceu     = true;
            resetTimer = 0f;
            System.out.println("VOCE VENCEU!");
        }
    }

    private void drawGame() {
        shape.begin(ShapeRenderer.ShapeType.Filled);

        shape.setColor(0, 1, 0, 1);
        shape.rect(GOAL_X, GOAL_Y, GOAL_SIZE, GOAL_SIZE);

        shape.setColor(1, 0, 0, 1);
        for (Asteroid a : asteroids) {
            shape.rect(a.x, a.y, a.width, a.height);
        }

        shape.end();

        batch.begin();
        batch.draw(playerTexture, player.x, player.y);
        batch.end();
    }

    private boolean overlaps(float ax, float ay, float aw, float ah,
                              float bx, float by, float bw, float bh) {
        return ax      < bx + bw
            && ax + aw > bx
            && ay      < by + bh
            && ay + ah > by;
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        shape.dispose();
        playerTexture.dispose();
    }
}