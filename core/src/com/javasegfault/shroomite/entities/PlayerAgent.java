package com.javasegfault.shroomite.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.TimeUtils;
import com.javasegfault.shroomite.IWorld;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.TextureName;
import com.javasegfault.shroomite.World;
import com.javasegfault.shroomite.blocks.Block;
import com.javasegfault.shroomite.blocks.IBlockVisitor;

public class PlayerAgent extends Entity {
    public Vector2 speed;

    private StatusEffectManager statusEffectManager;

    public boolean interacting;
    public long lastInteractionTick;

    public boolean breakingBlock;
    public long lastBreakingBlockTick;

    private int hp;
    private int stamina;

    public PlayerAgent(IWorld world, Vector2 position) {
        super(world, position);
        this.speed = new Vector2(0, 0);
        this.blockVisitor = new PlayerBlockVisitor(this);
        this.entityVisitor = new PlayerEntityVisitor(this);
        this.statusEffectManager = new StatusEffectManager(this);

        hp = 100;
        stamina = 100;

        interacting = false;
        lastInteractionTick = 0;

        breakingBlock = false;
        lastBreakingBlockTick = 0;
    }

    public void enableBreakingBlock() {
        breakingBlock = true;
        lastBreakingBlockTick = TimeUtils.millis();
    }

    public void breakBlock(Block block) {
        if (TimeUtils.timeSinceNanos(lastBreakingBlockTick) > 100) {
            block.destroySelf();
        }
    }

    public void enableInteractions() {
        interacting = true;
        lastInteractionTick = TimeUtils.millis();
    }

    public void accept(IEntityVisitor visitor) {
        visitor.visitPlayer(this);
    }

    public void setSpeed(Vector2 speed) {
        this.speed = speed;
    }

    public void setSpeed(float x, float y) {
        setSpeed(new Vector2(x, y));
    }

    public Vector2 getSpeed() {
        return this.speed;
    }

    public Texture getTexture() {
        return Shroomite.textures.get(TextureName.ADVENTURER_IDLE);
    }

    public void addStatusEffect(StatusEffect effect) {
        statusEffectManager.addStatusEffect(effect);
    }

    public void removeStatusEffects(StatusEffect effect) {
        statusEffectManager.removeStatusEffect(effect);
    }

    public void updateStatusEffects() {
        statusEffectManager.updateStatusEffects();
    }

    public ObjectSet<StatusEffect> getStatusEffects() {
        return statusEffectManager.getStatusEffects();
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getStamina() {
        return stamina;
    }

    public void addHp(int deltaHp) {
        hp += deltaHp;
    }

    public void addStamina(int deltaStamina) { 
        stamina += deltaStamina;
    }
}
