package com.javasegfault.shroomite.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.javasegfault.shroomite.blocks.BlockType;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.TextureName;
import com.javasegfault.shroomite.util.Position;
import com.javasegfault.shroomite.World;

public class DirtBlock extends Block {
	public DirtBlock(Position position, World world) {
        super(position, world);
	}

	@Override
	public BlockType getType() {
		return BlockType.DIRT;
	}
	
	@Override
	public Texture getTexture() {
		return Shroomite.textures.get(TextureName.DIRT);
	}
	
	@Override
	public String toString() {
		return String.format("DirtBlock(position=%s)", position.toString());
	}

    @Override
    public void interact(Block block) {

    }

    public void accept(BlockVisitorInterface visitor) {
        visitor.visitDirtBlock(this);
    }
}
