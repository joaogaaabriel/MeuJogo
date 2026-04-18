package com.seunome.meujogo.renderer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.seunome.meujogo.entity.Asteroid;
import com.seunome.meujogo.entity.Player;

import java.util.List;

public class GameRenderer {

    private final SpriteBatch   batch;
    private final ShapeRenderer shape;
    private final Texture       playerTexture;

    public GameRenderer() {
        batch         = new SpriteBatch();
        shape         = new ShapeRenderer();
        playerTexture = new Texture("libgdx.png");
    }

    public void draw(Player player, List<Asteroid> asteroids,
                     float goalX, float goalY, float goalSize) {
        drawShapes(asteroids, goalX, goalY, goalSize);
        drawPlayer(player);
    }

    private void drawShapes(List<Asteroid> asteroids,
                             float goalX, float goalY, float goalSize) {
        shape.begin(ShapeRenderer.ShapeType.Filled);

        shape.setColor(0, 1, 0, 1);
        shape.rect(goalX, goalY, goalSize, goalSize);

        shape.setColor(1, 0, 0, 1);
        for (Asteroid a : asteroids) {
            shape.rect(a.x, a.y, a.width, a.height);
        }

        shape.end();
    }

    private void drawPlayer(Player player) {
        batch.begin();
        batch.draw(playerTexture, player.x, player.y);
        batch.end();
    }

    public void dispose() {
        batch.dispose();
        shape.dispose();
        playerTexture.dispose();
    }
}
