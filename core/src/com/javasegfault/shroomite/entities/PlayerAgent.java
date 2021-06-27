package com.javasegfault.shroomite.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.TextureName;
import com.javasegfault.shroomite.World;
import com.javasegfault.shroomite.blocks.IBlockVisitor;

public class PlayerAgent extends Entity {
    public Vector2 speed;

    private IBlockVisitor blockVisitor;

    private StatusEffectManager statusEffectManager;

    private int hp;
    private int stamina;

    public PlayerAgent(World world, Vector2 position) {
        super(world, position);
        this.speed = new Vector2(0, 0);
        this.blockVisitor = new PlayerBlockVisitor(this);
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

    public void updateStatusEffects() {
        statusEffectManager.updateStatusEffects();
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
