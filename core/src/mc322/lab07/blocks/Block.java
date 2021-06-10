package mc322.lab07.blocks;

import com.badlogic.gdx.graphics.Texture;

import mc322.lab07.World;
import mc322.lab07.util.Position;

public abstract class Block {
    public BlockType type;
    protected Position position;
    protected World world;
    public boolean isUpdated;
    public Texture texture = new Texture("missing_texture.png");

    // whether the block interacts with other blocks
    public boolean inert = false;
    // whether the block can move
    public boolean movable = false;
    public boolean flammable = false;
    // whether the block is part of a rigid body (in case we want
    // to do stuff with Box2D later)
    public boolean rigid = false;

    protected abstract void setType();

    public Block(Position position, World world) {
        this.position = position;
        this.world = world;
        setType();
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
