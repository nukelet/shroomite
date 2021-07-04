package com.javasegfault.shroomite.entities;

public interface IEntityVisitor {
    public void visitPlayer(PlayerAgent player);
    public void visitLevelExit(LevelExit exit);
    public void visitLever(Lever lever);
}
