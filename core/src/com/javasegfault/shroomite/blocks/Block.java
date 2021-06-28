package com.javasegfault.shroomite.blocks;

import com.javasegfault.shroomite.IWorld;
import com.javasegfault.shroomite.util.Position;

public abstract class Block implements IBlock {
    protected IWorld world;
	protected Position position;
    protected IBlockVisitor visitor;

    // whether the block can move
    public boolean movable = false;
    public boolean flammable = false;
    // whether the block is part of a rigid body (in case we want
    // to do stuff with Box2D later)
    public boolean rigid = false;
    public boolean solid = true;
    public boolean liquid = false;

	public Block(Position position, IWorld world) {
		this.position = position;
        this.world = world;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    // TODO: this should probably be delegated to the graphics component
    // maybe the block should only store a reference to a TextureName object
	// public void render() {
	// 	game.drawBlockRegion(getTexture(), position.getX(), position.getY());
	// }

    public void render() {

    }

    @Override
    public void destroySelf() {
        world.removeBlockAt(position);
    }

    /**
     * Deprecated because it checks for the absence of block with a direct
     * comparison to null, breaking the abstraction for absence of blocks.
     */
    @Deprecated
    public void interactBlock(Block block) {
        if (block == null) {
            return;
        } else {
            interact(block);
        }
    };

    @Override
    public void move(Position pos) {
        world.moveBlock(this, pos);
    }

    public abstract void interact(Block block);
}
