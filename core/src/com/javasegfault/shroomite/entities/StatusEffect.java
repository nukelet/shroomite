package com.javasegfault.shroomite.entities;

public enum StatusEffect {
    FIRE(500, 8000, 4);

    private final long duration;
    private final long tickDuration;
    private final int damage;
    private long startTick;
    private long lastTick;

    // all in milliseconds
    StatusEffect(long tickDuration, long duration, int damage) {
        this.startTick = 0;
        this.lastTick = 0;
        this.tickDuration = tickDuration;
        this.duration = duration;
        this.damage = damage;
    }

    public long getDuration() {
        return duration;
    }

    public long getTickDuration() {
        return tickDuration;
    }

    public long getLastTick() {
        return lastTick;
    }

    public void setLastTick(long lastTick) {
        this.lastTick = lastTick;
    }

    public long getStartTick() {
        return startTick;
    }

    public void setStartTick(long startTick) {
        this.startTick = startTick;
    }

    public int getDamage() {
        return damage;
    }

    public long getRemainingTime() {
        return duration - (lastTick - startTick);
    }
}
