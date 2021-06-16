package com.javasegfault.shroomite.block;

import com.badlogic.gdx.graphics.Texture;
import com.javasegfault.shroomite.BlockType;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.TextureName;
import com.javasegfault.shroomite.util.Position;

public class WoodBlock extends Block {
	private boolean onFire;
	
	public WoodBlock(Shroomite game, Position position) {
		super(game, position);
		onFire = false;
	}
	
	public boolean getOnFire() {
		return onFire;
	}
	
	public void setOnFire(boolean onFire) {
		this.onFire = onFire;
	}
	
	@Override
	public BlockType getType() {
		return BlockType.WOOD;
	}
	
	@Override
	public Texture getTexture() {
		return Shroomite.textures.get(TextureName.WOOD);
	}
	
	@Override
	public void render() {
		super.render();
		if (onFire) {
			game.drawBlockRegion(Shroomite.textures.get(TextureName.FIRE), position.getX(), position.getY());
		}
	}
	
	@Override
	public String toString() {
		return String.format("WoodBlock(position=%s, onFire=%b)", position.toString(), onFire);
	}
}
