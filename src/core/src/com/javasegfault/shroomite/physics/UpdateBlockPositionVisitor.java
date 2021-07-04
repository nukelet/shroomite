package com.javasegfault.shroomite.physics;

import com.badlogic.gdx.utils.ObjectSet;
import com.javasegfault.shroomite.IWorld;
import com.javasegfault.shroomite.blocks.Block;
import com.javasegfault.shroomite.blocks.IBlockVisitor;
import com.javasegfault.shroomite.blocks.DirtBlock;
import com.javasegfault.shroomite.blocks.LavaBlock;
import com.javasegfault.shroomite.blocks.LiquidBlock;
import com.javasegfault.shroomite.blocks.RockBlock;
import com.javasegfault.shroomite.blocks.SandBlock;
import com.javasegfault.shroomite.blocks.WaterBlock;
import com.javasegfault.shroomite.blocks.WoodBlock;
import com.javasegfault.shroomite.util.Position;

public class UpdateBlockPositionVisitor implements IBlockVisitor {
    private IWorld world;
    private ObjectSet<LiquidBlock> newLiquidBlocks;

    public UpdateBlockPositionVisitor(Physics physics) {
        this.world = physics.world;
        this.newLiquidBlocks = physics.newLiquidBlocks;
    }

    private void updateMovableBlock(Block block) {
        Position pos = block.getPosition();
        Position posDown = pos.down();
        if (!world.hasBlockAt(posDown)) {
            block.move(posDown);
        }
    }

    private void updateSandBlock(SandBlock block) {
        Position pos = block.getPosition();
        Position posDown = pos.down();
        if (!world.hasBlockAt(posDown)) {
            block.move(posDown);
            return;
        }

        Position posDownLeft = posDown.left();
        if (!world.hasBlockAt(posDownLeft)) {
            block.move(posDownLeft);
            return;
        }

        Position posDownRight = posDown.right();
        if (!world.hasBlockAt(posDownRight)) {
            block.move(posDownRight);
            return;
        }
    }

    private void updateLiquidBlock(LiquidBlock block) {
        if (newLiquidBlocks.contains(block)) {
            return;
        }

        if (block.getMass() < block.getMaxMass() * 0.05f) {
            block.destroySelf();
            return;
        }
        
        // TODO: there are still some typecasts...

        Position pos = block.getPosition();
        Position posDown = pos.down();
        if (!world.hasBlockAt(posDown)) {
            block.move(posDown);
            return;
        }

        Block blockDown = world.getBlockAt(posDown);
        if (blockDown.getType() == block.getType()) {
            LiquidBlock otherLiquidBlock = (LiquidBlock) blockDown;
            int flowMass = Math.min(otherLiquidBlock.getMassFree(), block.getMass());
            block.removeMass(flowMass);
            otherLiquidBlock.addMass(flowMass);
        }

        Position posLeft = pos.left();
        if (!world.hasBlockAt(posLeft)) {
            LiquidBlock newLiquidBlock = block.spawnNewBlock(posLeft);
            // LiquidBlock newLiquidBlock = new LiquidBlock(posLeft, world);
            newLiquidBlock.setMass(0);
            block.exchangeMass(newLiquidBlock);
            newLiquidBlocks.add(newLiquidBlock);
            // world.addBlock(newLiquidBlock);
        } else {
            Block blockLeft = world.getBlockAt(posLeft);
            if (blockLeft.getType() == block.getType()) {
                LiquidBlock otherLiquidBlock = (LiquidBlock) blockLeft;
                block.exchangeMass(otherLiquidBlock);
            }
        }

        Position posRight = pos.right();
        if (!world.hasBlockAt(posRight)) {
            LiquidBlock newLiquidBlock = block.spawnNewBlock(posRight);
            // LiquidBlock newLiquidBlock = new LiquidBlock(posRight, world);
            newLiquidBlock.setMass(0);
            block.exchangeMass(newLiquidBlock);
            newLiquidBlocks.add(newLiquidBlock);
            // world.addBlock(newLiquidBlock);
        } else {
            Block blockRight = world.getBlockAt(posRight);
            if (blockRight.getType() == block.getType()) {
                LiquidBlock otherLiquidBlock = (LiquidBlock) blockRight;
                block.exchangeMass(otherLiquidBlock);
            }
        }
    }

    public void visitRockBlock(RockBlock block) {

    }

    public void visitWaterBlock(WaterBlock block) {
        updateLiquidBlock(block);
    }

    public void visitDirtBlock(DirtBlock block) {

    }

    public void visitLavaBlock(LavaBlock block) {
        updateLiquidBlock(block);
    }

    public void visitWoodBlock(WoodBlock block) {

    }

    public void visitSandBlock(SandBlock block) {
        updateSandBlock(block);
    }
}
