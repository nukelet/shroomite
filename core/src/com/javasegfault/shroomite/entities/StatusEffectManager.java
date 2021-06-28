package com.javasegfault.shroomite.entities;

import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.TimeUtils;

public class StatusEffectManager {
    private PlayerAgent player;

    private ObjectSet<StatusEffect> statusEffects;

    public StatusEffectManager(PlayerAgent player) {
        this.player = player;
        statusEffects = new ObjectSet<StatusEffect>();
    }

    public void addStatusEffect(StatusEffect effect) {
        if (statusEffects.add(effect)) {
            System.out.println("Adding status effect: " + effect);
            long currentTick = TimeUtils.millis();
            effect.setStartTick(currentTick);
            effect.setLastTick(currentTick);
        }
    }

    public void removeStatusEffect(StatusEffect effect) {
        if (statusEffects.remove(effect)) {
            System.out.println("Removing status effect: " + effect);
        }
    }

    public ObjectSet<StatusEffect> getStatusEffects() {
        return statusEffects;
    }

    public void applyStatusEffect(StatusEffect effect) {
        long currentTick = TimeUtils.millis();
        long offset = currentTick - effect.getLastTick();
        // System.out.println("offset: " + offset);
        // System.out.printf("current: %d, last: %d, start: %d\n",
        //         currentTick, effect.getLastTick(), effect.getStartTick());
        if (currentTick > effect.getStartTick() + effect.getDuration()) {
            statusEffects.remove(effect);
        } else if (currentTick - effect.getLastTick() > effect.getTickDuration()) {
            player.addHp(-effect.getDamage());
            effect.setLastTick(currentTick);
        } else {
            return;
        }
    }

    public void updateStatusEffects() {
        for (StatusEffect effect : statusEffects) {
            applyStatusEffect(effect);
        }
    }
}
