package com.javasegfault.shroomite.entities;

import com.badlogic.gdx.utils.TimeUtils;

public class PlayerEntityVisitor implements IEntityVisitor {
    private PlayerAgent player;

    PlayerEntityVisitor(PlayerAgent player) {
        this.player = player;
    }

    public void visitPlayer(PlayerAgent player) {

    }

    public void visitLevelExit(LevelExit level) {
        System.out.println("yeet");
    }

    public void visitLever(Lever lever) {
        if (player.interacting) {
            lever.toggleLock();
        }
    }
}
