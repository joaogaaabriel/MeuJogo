package com.seunome.meujogo.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.seunome.meujogo.entity.Player;
import com.seunome.meujogo.manager.AsteroidManager;
import com.seunome.meujogo.manager.CollisionManager;
import com.seunome.meujogo.renderer.GameRenderer;

public class GameScreen implements Screen {

    private static final float RESET_DELAY    = 3f;
    private static final float GOAL_SPAWN_TIME = 20f;
    private static final float GOAL_SIZE       = 50f;
    private static final float GOAL_SPEED      = 150f;

    private Player           player;
    private AsteroidManager  asteroidManager;
    private CollisionManager collisionManager;
    private GameRenderer     renderer;

    private float   resetTimer;
    private boolean gameOver;
    private boolean venceu;

    private float   goalX;
    private float   goalY;
    private float   goalTimer;
    private boolean goalActive;

    @Override
    public void show() {
        asteroidManager  = new AsteroidManager();
        collisionManager = new CollisionManager();
        renderer = new GameRenderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        initGame();
    }

    private void initGame() {
        player   = new Player();
        player.x = Gdx.graphics.getWidth() / 2f;
        player.y = 50f;

        asteroidManager.reset();

        resetTimer = 0f;
        gameOver   = false;
        venceu     = false;

        goalActive = false;
        goalTimer  = 0f;
        goalX      = 0f;
        goalY      = 0f;
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
            renderer.draw(player, asteroidManager.getAsteroids(), goalActive, goalX, goalY, GOAL_SIZE);
            return;
        }

        update(delta);
        renderer.draw(player, asteroidManager.getAsteroids(), goalActive, goalX, goalY, GOAL_SIZE);
    }

    private void update(float delta) {
        player.update(delta);
        asteroidManager.update(delta);

        goalTimer += delta;

        if (!goalActive && goalTimer >= GOAL_SPAWN_TIME) {
            spawnGoal();
        }

        if (goalActive) {
            goalY -= GOAL_SPEED * delta;

            if (goalY < -GOAL_SIZE) {
                goalActive = false;
                gameOver   = true;
                System.out.println("PERDEU! NAO PEGOU O OBJETIVO");
                return;
            }

            if (collisionManager.reachedGoal(player, goalX, goalY, GOAL_SIZE)) {
                goalActive = false;
                venceu     = true;
                System.out.println("VOCE VENCEU!");
                return;
            }
        }

        if (collisionManager.collidedWithAsteroid(player, asteroidManager.getAsteroids())) {
            gameOver = true;
            System.out.println("GAME OVER!");
        }
    }

    private void spawnGoal() {
        goalX      = (float) (Math.random() * (Gdx.graphics.getWidth() - GOAL_SIZE));
        goalY      = Gdx.graphics.getHeight();
        goalActive = true;
        goalTimer  = 0f;
    }

    @Override
        public void resize(int width, int height) {
        renderer.resize(width, height);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        renderer.dispose();
    }
}