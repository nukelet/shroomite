package com.javasegfault.shroomite.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.javasegfault.shroomite.IWorld;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.TextureName;
import com.javasegfault.shroomite.World;
import com.javasegfault.shroomite.blocks.Block;
import com.javasegfault.shroomite.blocks.IBlockVisitor;

public abstract class Entity implements IEntity {
    // position measured from bottom left corner
    protected Vector2 position;

    protected IWorld world;
    protected IBlockVisitor blockVisitor;
    protected IEntityVisitor entityVisitor;
    // separate the hitbox rectangle from the texture rectangle
    protected Rectangle hitbox;
    protected Rectangle textureRect;

    // TODO: hard-coded
    public Entity(IWorld world, Vector2 position) {
        this.world = world;
        this.position = position;

        float HITBOX_WIDTH = 30;
        float HITBOX_HEIGHT = 30;
        float TEXTURE_WIDTH = 30;
        float TEXTURE_HEIGHT = 40;

        this.hitbox = new Rectangle(position.x, position.y,
                HITBOX_WIDTH, HITBOX_HEIGHT);
        this.textureRect = new Rectangle(hitbox.x, hitbox.y, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        adjustTextureRect();
    }

    protected void adjustTextureRect() {
        textureRect.setCenter(hitbox.getCenter(new Vector2()));
        textureRect.setY(hitbox.y);
    }

    public float getWidth() {
        return hitbox.width;
    }

    public float getHeight() {
        return hitbox.height;
    }

    public float getTextureWidth() {
        return textureRect.width;
    }

    public float getTextureHeight() {
        return textureRect.height;
    }

    public Rectangle getHitbox() {
        return new Rectangle(hitbox);
    }

    public void setHitboxSize(float width, float height) {
        hitbox.setSize(width, height);
    }

    public void setTextureSize(float width, float height) {
        textureRect.setSize(width, height);
    }

    public Rectangle getTextureRect() {
        return new Rectangle(textureRect);
    }

    public boolean overlaps(Entity entity) {
        return hitbox.overlaps(entity.getHitbox());
    }


    public void interact(Block block) {
        block.accept(blockVisitor);
    }

    public void interact(Entity entity) {
        entity.accept(entityVisitor);
    }

    public abstract void accept(IEntityVisitor visitor);

    // TODO: change this to setPosition
    public void move(Vector2 vec) {
        setPosition(vec.x, vec.y);
    }

    // TODO: this should return a copy, something in the
    // Physics component is accessing this directly
    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        setPosition(position.x, position.y);
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
        hitbox.setPosition(position);
        adjustTextureRect();
    }

    public abstract Texture getTexture();
}
