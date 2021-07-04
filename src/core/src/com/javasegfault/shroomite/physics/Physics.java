package com.javasegfault.shroomite.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.TimeUtils;
import com.javasegfault.shroomite.IWorld;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.entities.PlayerAgent;
import com.javasegfault.shroomite.blocks.Block;
import com.javasegfault.shroomite.blocks.IBlockVisitor;
import com.javasegfault.shroomite.blocks.LiquidBlock;
import com.javasegfault.shroomite.util.Position;

public class Physics {
    public IWorld world;

    public ObjectSet<LiquidBlock> newLiquidBlocks;
    Array<Block> collidingBlocks;

    IBlockVisitor updateBlockPositionVisitor;

    public float gravity = -1000f;

    public Physics(IWorld world) {
        this.world = world;
        this.newLiquidBlocks = new ObjectSet<LiquidBlock>();
        this.updateBlockPositionVisitor = new UpdateBlockPositionVisitor(this);
        this.collidingBlocks = new Array<Block>();
    }

    public void updateInteractions() {
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                Position pos = new Position(x, y);
                if (!world.hasBlockAt(pos)) {
                    continue;
                }

                Block block = world.getBlockAt(pos);
                Position posUp = pos.up();
                if (world.hasBlockAt(posUp)) {
                    block.interact(world.getBlockAt(posUp));
                }
                Position posDown = pos.down();
                if (world.hasBlockAt(posDown)) {
                    block.interact(world.getBlockAt(posDown));
                }
                Position posLeft = pos.left();
                if (world.hasBlockAt(posLeft)) {
                    block.interact(world.getBlockAt(posLeft));
                }
                Position posRight = pos.right();
                if (world.hasBlockAt(posRight)) {
                    block.interact(world.getBlockAt(posRight));
                }
            }
        }
    }

    public void updatePositions() {
        newLiquidBlocks.clear();
        for (int x = 0; x < world.getWidth(); x++) {
            for  (int y = 0; y < world.getHeight(); y++) {
                Position pos = new Position(x, y);
                if (!world.hasBlockAt(pos)) {
                    continue;
                }

                Block block = world.getBlockAt(pos);
                // System.out.println(block);

                // TODO: i suppose this could be broken down into functions
                // really gotta come up with a way to organize this in a way
                // that makes it scalable i guess
                // if (block.getType() == BlockType.SAND) {
                //     Position pos = block.getPosition();
                //     Position posDown = pos.down();
                //     Position posDownLeft = posDown.left();
                //     Position posDownRight = posDown.right();
                //     if (world.getBlockAt(posDown) == null) {
                //         block.move(posDown);
                //     } else if (world.getBlockAt(posDownLeft) == null) {
                //         block.move(posDownLeft);
                //     } else if (world.getBlockAt(posDownRight) == null) {
                //         block.move(posDownRight);
                //     }        
                // } else if (block.getType() == BlockType.WATER) {
                //     // TODO: CODE SMELL
                //     updateWaterBlock((WaterBlock) block);
                // } else if (block.movable) {
                //     Position pos = block.getPosition();
                //     Position posDown = pos.down();
                //     if (world.getBlockAt(posDown) == null) {
                //         block.move(posDown);
                //     }
                // }

                block.accept(updateBlockPositionVisitor);
            }
        }
    }

    public void updateCollidingBlocks(PlayerAgent agent) {
        collidingBlocks.clear();

        Vector2 agentPosition = agent.getPosition();
        int agentBottomLeftGridPositionX = (int) (agentPosition.x / Shroomite.BLOCK_WIDTH);
        int agentBottomLeftGridPositionY = (int) (agentPosition.y / Shroomite.BLOCK_HEIGHT);
        int agentTopRightGridPositionX = (int) ((agentPosition.x + agent.getWidth()) / Shroomite.BLOCK_WIDTH);
        int agentTopRightGridPositionY = (int) ((agentPosition.y + agent.getHeight()) / Shroomite.BLOCK_HEIGHT);

        for (int x = agentBottomLeftGridPositionX; x <= agentTopRightGridPositionX; x++) {
            for (int y = agentBottomLeftGridPositionY; y <= agentTopRightGridPositionY; y++) {
                Position pos = new Position(x, y);
                if (world.hasBlockAt(pos)) {
                    Block block = world.getBlockAt(pos);
                    collidingBlocks.add(block);
                }
            }
        }
    }

    public Array<Block> getCollidingBlocks(PlayerAgent player) {
        return this.collidingBlocks;
    }

    public void updatePlayer(PlayerAgent player, float delta) {
        // position updating and resolution of collision against blocks
        Vector2 speed = player.getSpeed();
        Vector2 pos = player.getPosition();

        Vector2 prevPos = new Vector2(pos);

        float speedDebuff = 1f;
        updateCollidingBlocks(player);
        for (Block block : collidingBlocks) {
            if (block.liquid) {
                speedDebuff = 0.8f;
                break;
            }
        }

        speed.y += gravity * delta;
        pos.y += speedDebuff * speed.y * delta;
        updateCollidingBlocks(player);

        for (Block block : collidingBlocks) {
            if (block.solid) {
                if (pos.y != prevPos.y) {
                    speed.y = 0f;
                    pos.y = prevPos.y;
                }
            }
        }

        pos.x += speedDebuff * speed.x * delta;
        updateCollidingBlocks(player);
        for (Block block : collidingBlocks) {
            if (block.solid) {
                if (pos.x != prevPos.x) {
                    speed.x = 0f;
                    pos.x = prevPos.x;
                }
            }
        }

        player.setPosition(pos);


        // block interaction update
        updateCollidingBlocks(player);
        for (Block block : collidingBlocks) {
            player.interact(block);
        }
        player.updateStatusEffects();

        if (player.interacting) {
            if (TimeUtils.timeSinceMillis(player.lastInteractionTick) > 100) {
                player.interacting = false;
            }
        }

        if (player.breakingBlock) {
            if (TimeUtils.timeSinceMillis(player.lastBreakingBlockTick) > 100) {
                player.interacting = false;
            }
        }
    }
    
    // public void updateWaterBlock(WaterBlock block) {
    //     if (newLiquidBlocks.contains(block)) {
    //         return;
    //     }

    //     if (block.getMass() < block.getMaxMass() * 0.05f) {
    //         block.destroySelf();
    //         return;
    //     }
        
    //     Position pos = block.getPosition();
    //     Position posUp = pos.up();
    //     Position posLeft = pos.left();
    //     Position posRight = pos.right();
    //     Position posDown = pos.down();

    //     Block blockUp = world.getBlockAt(pos.up());
    //     Block blockDown = world.getBlockAt(pos.down());
    //     Block blockLeft = world.getBlockAt(pos.left());
    //     Block blockRight = world.getBlockAt(pos.right());

    //     if (blockDown == null) {
    //         block.move(posDown);
    //         return;
    //     } else if (blockDown.getType() == BlockType.WATER) {
    //         WaterBlock thisBlock = (WaterBlock) block;
    //         WaterBlock thatBlock = (WaterBlock) blockDown;
    //         int thisMass = thisBlock.getMass();
    //         int thatMass = thatBlock.getMass();
    //         int thatMassFree = thatBlock.getMaxMass() - thatMass;

    //         int flowMass;
    //         if (thatMassFree >= thisMass) {
    //             flowMass = thisMass;
    //         } else {
    //             flowMass = thatMassFree;
    //         }

    //         thisBlock.removeMass(flowMass);
    //         thatBlock.addMass(flowMass);
    //     }

    //     if (blockLeft == null) {
    //         WaterBlock newBlock = new WaterBlock(posLeft, world);
    //         newBlock.setMass(0);
    //         WaterBlock thisBlock = (WaterBlock) block;
    //         thisBlock.exchangeMass(newBlock);
    //         world.addBlock(newBlock);
    //         newLiquidBlocks.add(newBlock);
    //     } else if (blockLeft.getType() == BlockType.WATER) {
    //         WaterBlock thisBlock = (WaterBlock) block;
    //         WaterBlock thatBlock = (WaterBlock) blockLeft;
    //         thisBlock.exchangeMass(thatBlock);
    //     }

    //     if (blockRight == null) {
    //         WaterBlock newBlock = new WaterBlock(posRight, world);
    //         newBlock.setMass(0);
    //         WaterBlock thisBlock = (WaterBlock) block;
    //         thisBlock.exchangeMass(newBlock);
    //         world.addBlock(newBlock);
    //         newLiquidBlocks.add(newBlock);
    //     } else if (blockRight.getType() == BlockType.WATER) {
    //         WaterBlock thisBlock = (WaterBlock) block;
    //         WaterBlock thatBlock = (WaterBlock) blockRight;
    //         thisBlock.exchangeMass(thatBlock);
    //     }
    // }

}
