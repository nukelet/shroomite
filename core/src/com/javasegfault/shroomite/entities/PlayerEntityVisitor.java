package com.javasegfault.shroomite.entities;

public class PlayerEntityVisitor implements IEntityVisitor {
    public void visitPlayer(PlayerAgent player) {

    }

    public void visitLevelExit(LevelExit level) {
        System.out.println("yeet");
    }
}
