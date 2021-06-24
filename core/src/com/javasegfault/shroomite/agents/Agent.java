package com.javasegfault.shroomite.agents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.TextureName;
import com.javasegfault.shroomite.World;
import com.javasegfault.shroomite.blocks.Block;
import com.javasegfault.shroomite.blocks.BlockVisitorInterface;

public abstract class Agent implements AgentInterface {
    // position measured from bottom left corner
    Vector2 position;
    World world;
    BlockVisitorInterface blockVisitor;
    // separate the hitbox rectangle from the texture rectangle
    Rectangle hitbox;
    private final float HITBOX_WIDTH;
    private final float HITBOX_HEIGHT;
    private final float TEXTURE_WIDTH;
    private final float TEXTURE_HEIGHT;
    
    public float getWidth() {
        return this.HITBOX_WIDTH;
    }

    public float getHeight() {
        return this.HITBOX_HEIGHT;
    }

    public float getTextureWidth() {
        return this.TEXTURE_WIDTH;
    }

    public float getTextureHeight() {
        return this.TEXTURE_HEIGHT;
    }

    // TODO: hard-coded
    public Agent(World world, Vector2 position) {
        this.world = world;
        this.position = position;
        this.HITBOX_WIDTH = 70;
        this.HITBOX_HEIGHT = 60;
        this.TEXTURE_WIDTH = 70;
        this.TEXTURE_HEIGHT = 60;

    }

    public void interact(Block block) {
        block.accept(blockVisitor);
    }

    // TODO: change this to setPosition
    public void move(Vector2 vec) {
        this.position = vec;
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setPosition(float x, float y) {
        setPosition(new Vector2(x, y));
    }

    public Texture getTexture() {
        return Shroomite.textures.get(TextureName.ADVENTURER_IDLE);
    }
}