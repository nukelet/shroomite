package com.javasegfault.shroomite.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.javasegfault.shroomite.blocks.BlockType;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.TextureName;
import com.javasegfault.shroomite.World;
import com.javasegfault.shroomite.util.Position;

public class WaterBlock extends LiquidBlock {
	public WaterBlock(Position position, World world) {
        super(position, world);
        this.movable = true;
        this.solid = false;
        this.liquid = true;
        
        this.maxMass = 100;
        this.flowCoefficient = 0.9f;
        this.mass = this.maxMass;
	}
    
    public WaterBlock spawnNewBlock(Position position) {
        WaterBlock newBlock = new WaterBlock(position, world);
        world.addBlock(newBlock);
        return newBlock;
    }

	@Override
	public BlockType getType() {
		return BlockType.WATER;
	}
	
	@Override
	public Texture getTexture() {
        float massRatio = (mass/ (float) maxMass);
        if (massRatio > 1.0f) {
            return Shroomite.textures.get(TextureName.WATER_CRITICAL);
        } else if (massRatio > 0.75f) {
            return Shroomite.textures.get(TextureName.WATER_FULL);
        } else if (massRatio > 0.5f) {
            return Shroomite.textures.get(TextureName.WATER_THREE_QUARTERS);
        } else {
            return Shroomite.textures.get(TextureName.WATER_ONE_QUARTER);
        }
	}
	
	@Override
	public String toString() {
		return String.format("WaterBlock(position=%s, mass = %d, maxMass = %d, massRatio = %.2f)",
                position.toString(), getMass(), getMaxMass(), (mass/(float) maxMass));
	}

    @Override
    public void interact(Block block) {
        if (block.getType() == BlockType.LAVA) {
            Position pos = block.getPosition();
            block.destroySelf();
            RockBlock rockBlock = new RockBlock(pos, world);
            world.addBlock(rockBlock);
        }
    }

    @Override
    public void accept(BlockVisitorInterface visitor) {
        visitor.visitWaterBlock(this);
    }
}
