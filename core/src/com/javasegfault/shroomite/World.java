package com.javasegfault.shroomite;

import com.javasegfault.shroomite.blocks.Block;
import com.javasegfault.shroomite.blocks.RockBlock;
import com.javasegfault.shroomite.blocks.SandBlock;
import com.javasegfault.shroomite.util.Position;

public class World {
    private int width;
    private int height;
    private Block[][] blocks;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.blocks = new Block[width][height];
    }

    public World() {
        this(20, 20);

        for (int i = 0; i < 5; i++) {
            Position pos = new Position(10, 19 - i);
            SandBlock sandBlock = new SandBlock(pos, this);
            addBlock(sandBlock);
        }

        for (int i = 0; i < width; i++) {
            Position pos = new Position(i, 0);
            RockBlock rockBlock = new RockBlock(pos, this);
            addBlock(rockBlock);
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    // make sure that no blocks are overwritten?
    public void addBlock(Block block) {
        Position pos = block.getPosition();
        if (isValidPosition(pos)) {
            if (getBlockAt(pos) != null) {
                System.err.printf("Invalid block add at %s: position not empty\n", pos);
                return;
            } else {
                blocks[pos.getX()][pos.getY()] = block;
            }
        } else {
            System.err.printf("Invalid block add at %s: invalid position\n", pos);
        }
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    private boolean isValidPosition(Position position) {
        return isValidPosition(position.getX(), position.getY());
    }

    public Block getBlockAt(int x, int y) {
        if (!isValidPosition(x, y)) {
            System.err.printf("Invalid access to position Position(x=%d, y=%d): out of bounds\n", x, y);
            return null;
        } else {
            return blocks[x][y];
        }
    }

    public Block getBlockAt(Position position) {
        return getBlockAt(position.getX(), position.getY());
    }

    public void removeBlock(Block block) {
        Position pos = block.getPosition();
        if (getBlockAt(pos) == null) {
            return;
        } else {
            blocks[pos.getX()][pos.getY()] = null;
        }
    }
}
