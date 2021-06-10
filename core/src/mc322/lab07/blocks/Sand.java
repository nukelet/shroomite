package mc322.lab07.blocks;

import com.badlogic.gdx.graphics.Texture;

import mc322.lab07.World;
import mc322.lab07.util.Position;

public class Sand extends Block {
    public Sand(Position position, World world) {
        super(position, world);
        texture = new Texture("sand.png");
    }

    protected void setType() {
        this.type = BlockType.SAND;
    }

    public void interact(Block block) {
        return;
    }
}
