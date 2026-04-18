package com.seunome.meujogo;

import com.badlogic.gdx.Game;
import com.seunome.meujogo.screen.GameScreen;

public class MainGame extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen());
    }
}