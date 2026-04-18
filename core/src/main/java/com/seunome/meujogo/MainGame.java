package com.seunome.meujogo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.seunome.meujogo.screen.MenuScreen;

public class MainGame extends Game {

    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new MenuScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();

        if (batch != null) {
            batch.dispose();
            batch = null;
        }
    }
}