package com.javasegfault.shroomite.physics;

import com.javasegfault.shroomite.agents.Agent;
import com.javasegfault.shroomite.agents.PlayerAgent;
import com.javasegfault.shroomite.blocks.Block;
import com.javasegfault.shroomite.blocks.WaterBlock;
import com.javasegfault.shroomite.util.Position;
import com.javasegfault.shroomite.World;
import com.javasegfault.shroomite.blocks.BlockType;
import com.javasegfault.shroomite.blocks.IBlockVisitor;
import com.javasegfault.shroomite.blocks.LiquidBlock;
import com.javasegfault.shroomite.Shroomite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;

public class Physics {
    public World world;

    public ObjectSet<LiquidBlock> newLiquidBlocks;
    Array<Block> collidingBlocks;

    IBlockVisitor updateBlockPositionVisitor;

    public float gravity = -1000f;

    public Physics(World world) {
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
                Block block = world.getBlockAt(x, y);

                if (block == null) {
                    continue;
                } else {
                    // System.out.println(block);
                }
                
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

    public void updateCollidingBlocks(Agent agent) {
        Vector2 pos = agent.getPosition();

        collidingBlocks.clear();

        // bottom left corner coordinates
        int gridPosX0 = (int) (pos.x/(float) Shroomite.BLOCK_WIDTH);
        int gridPosY0 = (int) (pos.y/(float) Shroomite.BLOCK_HEIGHT);
        // top right corner coordinates
        int gridPosX1 = (int) ((pos.x + agent.getWidth())/(float) Shroomite.BLOCK_WIDTH);
        int gridPosY1 = (int) ((pos.y + agent.getHeight())/(float) Shroomite.BLOCK_HEIGHT);

        for (int x = gridPosX0; x <= gridPosX1; x++) {
            for (int y = gridPosY0; y <= gridPosY1; y++) {
                Block block = world.getBlockAt(x, y);
                if (block != null) {
                    collidingBlocks.add(block);
                }
            }
        }
    }

    public Array<Block> getCollidingBlocks(Agent player) {
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

        // block interaction update
        updateCollidingBlocks(player);
        for (Block block : collidingBlocks) {
            player.interact(block);
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
