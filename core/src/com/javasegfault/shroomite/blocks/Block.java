package com.javasegfault.shroomite.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.javasegfault.shroomite.blocks.BlockType;
import com.javasegfault.shroomite.World;
import com.javasegfault.shroomite.util.Position;

public abstract class Block implements BlockInterface {
    protected World world;
	protected Position position;
    protected BlockVisitorInterface visitor;

    public boolean isUpdated;

    // whether the block interacts with other blocks
    public boolean inert = false;
    // whether the block can move
    public boolean movable = false;
    public boolean flammable = false;
    // whether the block is part of a rigid body (in case we want
    // to do stuff with Box2D later)
    public boolean rigid = false;
    public boolean solid = true;
    public boolean liquid = false;
	
	public Block(Position position, World world) {
		this.position = position;
        this.world = world;
    }

    public Position getPosition() {
        return this.position;
    }

    private void setPosition(Position pos) {
        this.position = pos;
    }
	
	public abstract BlockType getType();
	
	public abstract Texture getTexture();

    // TODO: this should probably be delegated to the graphics component
    // maybe the block should only store a reference to a TextureName object
	// public void render() {
	// 	game.drawBlockRegion(getTexture(), position.getX(), position.getY());
	// }

    public void render() {

    }

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
}
