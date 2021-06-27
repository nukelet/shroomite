package com.javasegfault.shroomite.entities;

import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.TimeUtils;

public class StatusEffectManager {
    private PlayerAgent player;

    ObjectSet<StatusEffect> statusEffects;

    public StatusEffectManager(PlayerAgent player) {
        this.player = player;
    }

    public void addStatusEffect(StatusEffect effect) {
        if (statusEffects.add(effect)) {
            long currentTick = TimeUtils.millis();
            effect.setStartTick(currentTick);
            effect.setLastTick(currentTick);
        }
    }

    public void applyStatusEffect(StatusEffect effect) {
        long currentTick = TimeUtils.millis();
        if (currentTick > effect.getStartTick() + effect.getDuration()) {
            statusEffects.remove(effect);
        } else if (currentTick - effect.getLastTick() > effect.getTickDuration()) {
            player.addHp(-effect.getDamage());
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
