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
    private float flowCoefficient = 0.25f;
    private int flowMass = (int) flowCoefficient * maxMass;
    public boolean pressurized = false;

	public WaterBlock(Position position, World world) {
        super(position, world);
        this.movable = true;
        
        this.mass = maxMass;
	}

    public int getMass() {
        return mass;
    }

    public void addMass(int mass) {
        this.mass += mass;
        if (this.mass > maxMass) {
            pressurized = true;
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
            block.addMass(flowMass);
            this.removeMass(flowMass);
        }        
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

    @Override
    public void interact(Block block) {

    }
}
