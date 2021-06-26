package com.javasegfault.shroomite.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.javasegfault.shroomite.blocks.BlockType;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.TextureName;
import com.javasegfault.shroomite.World;
import com.javasegfault.shroomite.util.Position;

public class RockBlock extends Block {
	public RockBlock(Position position, World world) {
        super(position, world);
	}

	@Override
	public BlockType getType() {
		return BlockType.ROCK;
	}
	
	@Override
	public Texture getTexture() {
		return Shroomite.textures.get(TextureName.ROCK);
	}
	
	@Override
	public String toString() {
		return String.format("CobblestoneBlock(position=%s)", position.toString());
	}

    @Override
    public void interact(Block block) {

    }

    public void accept(BlockVisitorInterface visitor) {
        visitor.visitRockBlock(this);
    }
}
