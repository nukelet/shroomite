package mc322.lab07.blocks;

import com.badlogic.gdx.graphics.Texture;

import mc322.lab07.World;
import mc322.lab07.util.Position;

public class Rock extends Block {
    public Rock(Position position, World world) {
        super(position, world);
        texture = new Texture("cobblestone.png");
        movable = false;
    }

    protected void setType() {
        this.type = BlockType.ROCK;
    }

    public void updateState() {
        this.isUpdated = true;
    }

    public void interact(Block block) {
        return;
    }
}
