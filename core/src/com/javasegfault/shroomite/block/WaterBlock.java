package com.javasegfault.shroomite.block;

import com.badlogic.gdx.graphics.Texture;
import com.javasegfault.shroomite.BlockType;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.TextureName;
import com.javasegfault.shroomite.util.Position;

public class WaterBlock extends Block {
	public WaterBlock(Shroomite game, Position position) {
		super(game, position);
	}

	@Override
	public BlockType getType() {
		return BlockType.WATER;
	}
	
	@Override
	public Texture getTexture() {
		return Shroomite.textures.get(TextureName.WATER);
	}
	
	@Override
	public String toString() {
		return String.format("WaterBlock(position=%s)", position.toString());
	}
}
