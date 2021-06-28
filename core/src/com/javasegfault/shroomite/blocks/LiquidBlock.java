package com.javasegfault.shroomite.blocks;

import com.javasegfault.shroomite.IWorld;
import com.javasegfault.shroomite.util.Position;

public abstract class LiquidBlock extends Block {
    protected int mass;
    protected int maxMass;
    protected float flowCoefficient;
    public boolean pressurized = false;

	public LiquidBlock(Position position, IWorld world) {
        super(position, world);
        this.movable = true;
        this.solid = false;
        this.liquid = true;

        this.maxMass = 10;
        this.flowCoefficient = 0.9f;
        this.mass = this.maxMass;
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

    public void exchangeMass(LiquidBlock block) {
        if (block.getMass() < mass) {
            int flowMass = (int) ((mass - block.getMass()) * flowCoefficient);
            block.addMass(flowMass);
            this.removeMass(flowMass);
        }
    }

    public int getMaxMass() {
        return maxMass;
    }

    public int getMassFree() {
        return maxMass - mass;
    }

    public float getMassRatio() {
        return (float)mass / maxMass;
    }

    public abstract LiquidBlock spawnNewBlock(Position position);
}
