package com.seunome.meujogo.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.seunome.meujogo.entity.Player;
import com.seunome.meujogo.manager.AsteroidManager;
import com.seunome.meujogo.manager.CollisionManager;
import com.seunome.meujogo.renderer.GameRenderer;

public class GameScreen implements Screen {

    private static final float GOAL_X      = 500f;
    private static final float GOAL_Y      = 300f;
    private static final float GOAL_SIZE   = 50f;
    private static final float RESET_DELAY = 2f;

    private Player            player;
    private AsteroidManager   asteroidManager;
    private CollisionManager  collisionManager;
    private GameRenderer      renderer;

    private float   resetTimer;
    private boolean gameOver;
    private boolean venceu;

    @Override
    public void show() {
        asteroidManager  = new AsteroidManager();
        collisionManager = new CollisionManager();
        renderer         = new GameRenderer();
        initGame();
    }

    private void initGame() {
        player = new Player();
        player.x = Gdx.graphics.getWidth() / 2f;
        player.y = 50f;

        asteroidManager.reset();

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

        player.update(delta);
        asteroidManager.update(delta);

        if (collisionManager.collidedWithAsteroid(player, asteroidManager.getAsteroids())) {
            gameOver = true;
            System.out.println("GAME OVER!");
        } else if (collisionManager.reachedGoal(player, GOAL_X, GOAL_Y, GOAL_SIZE)) {
            venceu = true;
            System.out.println("VOCÊ VENCEU!");
        }

        renderer.draw(player, asteroidManager.getAsteroids(), GOAL_X, GOAL_Y, GOAL_SIZE);
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
