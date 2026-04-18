package com.seunome.meujogo.manager;

import com.seunome.meujogo.entity.Asteroid;
import com.seunome.meujogo.entity.Player;

import java.util.List;

public class CollisionManager {

    private static final float PLAYER_WIDTH  = 32f;
    private static final float PLAYER_HEIGHT = 32f;

    public boolean collidedWithAsteroid(Player player, List<Asteroid> asteroids) {
        for (Asteroid a : asteroids) {
            if (overlaps(player.x, player.y, PLAYER_WIDTH, PLAYER_HEIGHT,
                         a.x,      a.y,      a.width,      a.height)) {
                return true;
            }
        }
        return false;
    }

    public boolean reachedGoal(Player player, float goalX, float goalY, float goalSize) {
        return overlaps(player.x, player.y, PLAYER_WIDTH, PLAYER_HEIGHT,
                        goalX,    goalY,    goalSize,     goalSize);
    }

    private boolean overlaps(float ax, float ay, float aw, float ah,
                              float bx, float by, float bw, float bh) {
        return ax      < bx + bw
            && ax + aw > bx
            && ay      < by + bh
            && ay + ah > by;
    }
}