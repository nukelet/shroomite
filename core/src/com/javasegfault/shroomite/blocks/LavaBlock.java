package com.javasegfault.shroomite.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.javasegfault.shroomite.BlockType;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.TextureName;
import com.javasegfault.shroomite.World;
import com.javasegfault.shroomite.util.Position;

public class LavaBlock extends Block {
	public LavaBlock(Position position, World world) {
        super(position, world);
	}

	@Override
	public BlockType getType() {
		return BlockType.LAVA;
	}
	
	@Override
	public Texture getTexture() {
		return Shroomite.textures.get(TextureName.LAVA);
	}
	
	@Override
	public String toString() {
		return String.format("LavaBlock(position=%s)", position.toString());
	}

    @Override
    public void interact(Block block) {

    }
}
