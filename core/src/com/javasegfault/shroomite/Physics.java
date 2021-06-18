package com.javasegfault.shroomite;

import com.javasegfault.shroomite.blocks.Block;
import com.javasegfault.shroomite.blocks.WaterBlock;
import com.javasegfault.shroomite.util.Position;

public class Physics {
    World world;

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

    public void updatePositions() {
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
                    Position pos = block.getPosition();
                    Position posUp = pos.up();
                    Position posLeft = pos.left();
                    Position posRight = pos.right();
                    Position posDown = pos.down();

                    if (world.getBlockAt(posDown) == null) {
                        block.move(posDown);
                    } else if (world.getBlockAt(posLeft) == null) {
                        // TODO: here
                        world.addBlock(new WaterBlock(posLeft, world));
                    }
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
