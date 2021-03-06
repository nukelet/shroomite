package com.javasegfault.shroomite.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.javasegfault.shroomite.IWorld;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.TextureName;
import com.javasegfault.shroomite.util.Position;

public class SandBlock extends Block {
	public SandBlock(Position position, IWorld world) {
        super(position, world);
        this.movable = true;
	}

	@Override
	public BlockType getType() {
		return BlockType.SAND;
	}

	@Override
	public Texture getTexture() {
		return Shroomite.textures.get(TextureName.SAND);
	}

	@Override
	public String toString() {
		return String.format("SandBlock(position=%s) %s", position.toString(),
                this.movable ? "(movable)" : "(static)");
	}

    @Override
    public void interact(Block block) {

    }

    @Override
    public void accept(IBlockVisitor visitor) {
        visitor.visitSandBlock(this);
    }
}
