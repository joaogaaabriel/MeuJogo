package com.seunome.meujogo.renderer;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.seunome.meujogo.starts.MenuStars;

public class MenuBackgroundRenderer {

    private final ShapeRenderer shape;
    private final MenuStars stars;

    public MenuBackgroundRenderer(MenuStars stars) {
        this.stars = stars;
        this.shape = new ShapeRenderer();
    }

    public void draw(float width, float height, float pulse) {
        shape.begin(ShapeRenderer.ShapeType.Filled);

        drawStars();
        drawPath(width, height);

        shape.end();
    }

    private void drawStars() {
        for (int i = 0; i < stars.getStarCount(); i++) {
            float size = stars.getStarSize(i);
            float brightness = 0.5f + size * 0.15f;

            shape.setColor(brightness, brightness, brightness + 0.1f, 1f);
            shape.rect(stars.getStarX(i), stars.getStarY(i), size, size);
        }
    }

    private void drawPath(float width, float height) {
        shape.setColor(0.3f, 0.6f, 1f, 0.18f);

        for (int i = 0; i < 14; i++) {
            float t = (float) i / 14f;
            float x = 75f + t * (width - 150f);
            float y = 65f + t * (height - 130f);
            shape.rect(x, y, 8f, 2f);
        }
    }

    public void dispose() {
        shape.dispose();
    }
}