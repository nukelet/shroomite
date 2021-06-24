package com.javasegfault.shroomite.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.javasegfault.shroomite.BlockType;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.TextureName;
import com.javasegfault.shroomite.World;
import com.javasegfault.shroomite.util.Position;

public class WaterBlock extends Block {
    private int mass;
    private final int maxMass = 100;
    private float flowCoefficient = 0.8f;
    public boolean pressurized = false;

	public WaterBlock(Position position, World world) {
        super(position, world);
        this.movable = true;
        this.solid = false;
        this.liquid = true;
        
        this.mass = maxMass;
	}
    
    public void setMass(int mass) {
        this.mass = mass;
    }

    public int getMass() {
        return mass;
    }

    public void addMass(int mass) {
        this.mass += mass;
        if (this.mass > maxMass) {
            pressurized = true;
        } else if (this.mass <= 0) {
            this.destroySelf();
        }
    }

    public void removeMass(int mass) {
        this.mass -= mass;
        if (this.mass <= 0) {
            destroySelf();
        }
    }

    public void exchangeMass(WaterBlock block) {
        if (block.getMass() < mass) {
            int freeMass = block.getMaxMass() - block.getMass();
            int flowMass = (int) ((mass - block.getMass()) * flowCoefficient);
            block.addMass(flowMass);
            this.removeMass(flowMass);
        }
    }

    public int getMaxMass() {
        return maxMass;
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
		return String.format("WaterBlock(position=%s, mass = %d, massRatio = %.2f)",
                position.toString(), getMass(), (mass/(float) maxMass));
	}

    @Override
    public void interact(Block block) {

    }

    public void accept(BlockVisitorInterface visitor) {
        visitor.visitWaterBlock(this);
    }
}
