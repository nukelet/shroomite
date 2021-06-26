package com.javasegfault.shroomite.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.javasegfault.shroomite.blocks.BlockType;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.TextureName;
import com.javasegfault.shroomite.World;
import com.javasegfault.shroomite.util.Position;

public class LavaBlock extends LiquidBlock {
	public LavaBlock(Position position, World world) {
        super(position, world);
        this.solid = false;
        this.liquid = true;

        this.maxMass = 100;
        this.flowCoefficient = 0.7f;
        this.mass = this.maxMass;
	}

    public LavaBlock spawnNewBlock(Position position) {
        LavaBlock newBlock = new LavaBlock(position, world);
        world.addBlock(newBlock);
        return newBlock;
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
		return String.format("LavaBlock(position=%s, mass = %d, maxMass = %d, massRatio = %.2f)",
                position.toString(), getMass(), getMaxMass(), (mass/(float) maxMass));
	}

    @Override
    public void interact(Block block) {

    }

    @Override
    public void accept(IBlockVisitor visitor) {
        visitor.visitLavaBlock(this);
    }
}
