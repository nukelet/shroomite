package com.javasegfault.shroomite.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.javasegfault.shroomite.IWorld;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.TextureName;
import com.javasegfault.shroomite.util.Position;

public class WoodBlock extends Block {
	private boolean onFire;

	public WoodBlock(Position position, IWorld world) {
        super(position, world);
        onFire = true;
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
	public String toString() {
		return String.format("WoodBlock(position=%s, onFire=%b)", position.toString(), onFire);
	}

    @Override
    public void interact(Block block) {

    }

    @Override
    public void accept(IBlockVisitor visitor) {
        visitor.visitWoodBlock(this);
    }
}
