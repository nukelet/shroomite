package com.javasegfault.shroomite.physics;

import com.badlogic.gdx.utils.ObjectSet;
import com.javasegfault.shroomite.World;
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
    private World world;
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
        Position posDownLeft = posDown.left();
        Position posDownRight = posDown.right();  
        if (!world.hasBlockAt(posDown)) {
            block.move(posDown);
        } else if (!world.hasBlockAt(posDownLeft)) {
            block.move(posDownLeft);
        } else if (!world.hasBlockAt(posDownRight)) {
            block.move(posDownRight);
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
        
        Position pos = block.getPosition();
        Position posUp = pos.up();
        Position posLeft = pos.left();
        Position posRight = pos.right();
        Position posDown = pos.down();

        Block blockUp = world.getBlockAt(pos.up());
        Block blockDown = world.getBlockAt(pos.down());
        Block blockLeft = world.getBlockAt(pos.left());
        Block blockRight = world.getBlockAt(pos.right());

        // TODO: there are still some typecasts...
        if (blockDown == null) {
            block.move(posDown);
            return;
        } else if (blockDown.getType() == block.getType()) {
            LiquidBlock thisBlock = block;
            LiquidBlock thatBlock = (LiquidBlock) blockDown;
            int thisMass = thisBlock.getMass();
            int thatMass = thatBlock.getMass();
            int thatMassFree = thatBlock.getMaxMass() - thatMass;

            int flowMass;
            if (thatMassFree >= thisMass) {
                flowMass = thisMass;
            } else {
                flowMass = thatMassFree;
            }

            thisBlock.removeMass(flowMass);
            thatBlock.addMass(flowMass);
        }

        if (blockLeft == null) {
            LiquidBlock newBlock = block.spawnNewBlock(posLeft);
            // LiquidBlock newBlock = new LiquidBlock(posLeft, world);
            newBlock.setMass(0);
            LiquidBlock thisBlock = block;
            thisBlock.exchangeMass(newBlock);
            newLiquidBlocks.add(newBlock);
            // world.addBlock(newBlock);
        } else if (blockLeft.getType() == block.getType()) {
            LiquidBlock thisBlock = block;
            LiquidBlock thatBlock = (LiquidBlock) blockLeft;
            thisBlock.exchangeMass(thatBlock);
        }

        if (blockRight == null) {
            LiquidBlock newBlock = block.spawnNewBlock(posRight);
            // LiquidBlock newBlock = new LiquidBlock(posRight, world);
            newBlock.setMass(0);
            LiquidBlock thisBlock = block;
            thisBlock.exchangeMass(newBlock);
            newLiquidBlocks.add(newBlock);
            // world.addBlock(newBlock);
        } else if (blockRight.getType() == block.getType()) {
            LiquidBlock thisBlock = block;
            LiquidBlock thatBlock = (LiquidBlock) blockRight;
            thisBlock.exchangeMass(thatBlock);
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
