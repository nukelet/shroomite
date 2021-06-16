package com.javasegfault.shroomite;

import com.javasegfault.shroomite.blocks.Block;
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
        for (int i = world.getWidth() - 1; i >= 0; i--) {
            for  (int j = world.getHeight() - 1; j >= 0; j--) {
                Block block = world.getBlockAt(i, j);

                if (block == null) {
                    return;
                }

                if (block.movable) {
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
