package com.javasegfault.shroomite;

import com.javasegfault.shroomite.agents.Agent;
import com.javasegfault.shroomite.agents.PlayerAgent;
import com.javasegfault.shroomite.blocks.Block;
import com.javasegfault.shroomite.blocks.WaterBlock;
import com.javasegfault.shroomite.util.Position;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;

public class Physics {
    World world;

    ObjectSet<WaterBlock> newWaterBlocks = new ObjectSet<WaterBlock>();

    Array<Block> collidingBlocks = new Array<Block>();

    public Physics(World world) {
        this.world = world;
    }

    public void updateInteractions() {
        for (int i = 0; i < world.getWidth(); i++) {
            for (int j = 0; j < world.getHeight(); j++) {
                Block block = world.getBlockAt(i, j);

                if (block == null) {
                    return;
                }

                Position pos = block.getPosition();

                block.interactBlock(world.getBlockAt(pos.up()));
                block.interactBlock(world.getBlockAt(pos.down()));
                block.interactBlock(world.getBlockAt(pos.left()));
                block.interactBlock(world.getBlockAt(pos.right()));
            }
        }
    }

    public void updateCollidingBlocks(Agent player) {
        Vector2 pos = player.getPosition();

        collidingBlocks.clear();

        // bottom left corner coordinates
        int gridPosX0 = (int) (pos.x/(float) Shroomite.BLOCK_WIDTH);
        int gridPosY0 = (int) (pos.y/(float) Shroomite.BLOCK_HEIGHT);
        // top right corner coordinates
        int gridPosX1 = (int) ((pos.x + player.getWidth())/(float) Shroomite.BLOCK_WIDTH);
        int gridPosY1 = (int) ((pos.y + player.getHeight())/(float) Shroomite.BLOCK_HEIGHT);

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
        // float accel = -1000.0f;
        float accel = -1000f;

        speed.y += accel * delta;
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

        updateCollidingBlocks(player);
    }
    
    public void updateWaterBlock(WaterBlock block) {
        if (newWaterBlocks.contains(block)) {
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

        // TODO: the water needs to flow down to the water blocks underneath
        if (blockDown == null) {
            block.move(posDown);
            return;
        } else if (blockDown.getType() == BlockType.WATER) {
            WaterBlock thisBlock = (WaterBlock) block;
            WaterBlock thatBlock = (WaterBlock) blockDown;
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

            // if (thatBlock.getPosition().isEqual(14, 1)) {
            //     System.out.printf("Mass flow: %d\n", flowMass);
            //     System.out.printf("From:\n%s\nto:\n%s", thisBlock, thatBlock);
            // }
        }

        if (blockLeft == null) {
            WaterBlock newBlock = new WaterBlock(posLeft, world);
            newBlock.setMass(0);
            WaterBlock thisBlock = (WaterBlock) block;
            thisBlock.exchangeMass(newBlock);
            world.addBlock(newBlock);
            newWaterBlocks.add(newBlock);
        } else if (blockLeft.getType() == BlockType.WATER) {
            WaterBlock thisBlock = (WaterBlock) block;
            WaterBlock thatBlock = (WaterBlock) blockLeft;
            thisBlock.exchangeMass(thatBlock);
        }

        if (blockRight == null) {
            WaterBlock newBlock = new WaterBlock(posRight, world);
            newBlock.setMass(0);
            WaterBlock thisBlock = (WaterBlock) block;
            thisBlock.exchangeMass(newBlock);
            world.addBlock(newBlock);
            newWaterBlocks.add(newBlock);
        } else if (blockRight.getType() == BlockType.WATER) {
            WaterBlock thisBlock = (WaterBlock) block;
            WaterBlock thatBlock = (WaterBlock) blockRight;
            thisBlock.exchangeMass(thatBlock);
        }
    }

    public void updatePositions() {
        newWaterBlocks.clear();
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
                if (block.getType() == BlockType.SAND) {
                    Position pos = block.getPosition();
                    Position posDown = pos.down();
                    Position posDownLeft = posDown.left();
                    Position posDownRight = posDown.right();
                    if (world.getBlockAt(posDown) == null) {
                        block.move(posDown);
                    } else if (world.getBlockAt(posDownLeft) == null) {
                        block.move(posDownLeft);
                    } else if (world.getBlockAt(posDownRight) == null) {
                        block.move(posDownRight);
                    }        
                } else if (block.getType() == BlockType.WATER) {
                    // TODO: CODE SMELL
                    updateWaterBlock((WaterBlock) block);
                } else if (block.movable) {
                    Position pos = block.getPosition();
                    Position posDown = pos.down();
                    if (world.getBlockAt(posDown) == null) {
                        block.move(posDown);
                    }
                }
            }
        }
    }
}
