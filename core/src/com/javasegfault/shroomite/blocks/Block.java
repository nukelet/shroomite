package com.javasegfault.shroomite.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.javasegfault.shroomite.BlockType;
import com.javasegfault.shroomite.World;
import com.javasegfault.shroomite.util.Position;

public abstract class Block {
    protected Position position;
    protected World world;
    public boolean isUpdated;

    // whether the block interacts with other blocks
    public boolean inert = false;
    // whether the block can move
    public boolean movable = false;
    public boolean flammable = false;
    // whether the block is part of a rigid body (in case we want
    // to do stuff with Box2D later)
    public boolean rigid = false;

    public Block(Position position, World world) {
        this.position = position;
        this.world = world;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public abstract BlockType getType();

    public abstract Texture getTexture();

    public void destroySelf() {
        world.removeBlock(this);
    }

    public abstract void interact(Block block);

    public void interactBlock(Block block) {
        if (block == null) {
            return;
        } else {
            interact(block);
        }
    };

    public void move(Position pos) {
        world.removeBlock(this);
        setPosition(pos);
        world.addBlock(this);
    }

    // gotta implement stuff like move(), interact(Block block), destroy(), ...
    // these would be used by the physics engine
}
