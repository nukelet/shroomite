package com.javasegfault.shroomite;

import com.javasegfault.shroomite.blocks.Block;
import com.javasegfault.shroomite.util.Position;

public class World implements IWorld {
    private int width;
    private int height;
    private Block[][] blocks;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.blocks = new Block[width][height];
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    /**
     * Adds block to world at the given position.
     * 
     * @param position
     * @param block
     * @return True if position is valid, false otherwise.
     */
    @Override
    public boolean setBlockAt(Position position, Block block) {
        if (!isValidPosition(position)) {
            return false;
        }

        setBlockAtNoCheck(position, block);
        return true;
    }

    @Override
    public void addBlock(Block block) {
        Position pos = block.getPosition();
        if (!isValidPosition(pos)) {
            System.err.printf("Invalid block add at %s: invalid position\n", pos);
            return;
        }

        if (hasBlockAtNoCheck(pos)) {
            System.err.printf("Invalid block add at %s: position not empty\n", pos);
            return;
        }

        blocks[pos.getX()][pos.getY()] = block;
    }

    /**
     * Adds block to world at the given position if the position is empty.
     * 
     * @param position
     * @param block
     * @return True if position is valid and there is no block at given position,
     *         false otherwise.
     */
    @Override
    public boolean addBlockAt(Position position, Block block) {
        if (!isValidPosition(position)) {
            return false;
        }

        if (hasBlockAtNoCheck(position)) {
            return false;
        }

        setBlockAtNoCheck(position, block);
        return true;
    }

    @Deprecated
    public Block getBlockAt(int x, int y) {
        if (!isValidPosition(x, y)) {
            // System.err.printf("Invalid access to position Position(x=%d, y=%d): out of
            // bounds\n", x, y);
            return null;
        } else {
            return blocks[x][y];
        }
    }

    /**
     * @param position
     * @return Block at given position or null if position is not valid.
     */
    @Override
    public Block getBlockAt(Position position) {
        if (!isValidPosition(position)) {
            return null;
        }

        return getBlockAtNoCheck(position);
    }

    @Override
    public boolean hasBlockAt(Position position) {
        if (!isValidPosition(position)) {
            System.err.printf("Invalid position: %s\n", position);
            return false;
        }

        return hasBlockAtNoCheck(position);
    }

    @Deprecated
    public void removeBlock(Block block) {
        Position pos = block.getPosition();
        if (getBlockAt(pos) == null) {
            return;
        } else {
            blocks[pos.getX()][pos.getY()] = null;
        }
    }

    /**
     * Removes block from world at the given position.
     * 
     * @param position
     * @return True if given position is valid, false otherwise.
     */
    @Override
    public boolean removeBlockAt(Position position) {
        if (!isValidPosition(position)) {
            return false;
        }

        removeBlockAtNoCheck(position);
        return true;
    }

    @Deprecated
    private boolean isValidPosition(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    private boolean isValidPosition(Position position) {
        return position.getX() >= 0 && position.getY() >= 0 && position.getX() < width
                && position.getY() < height;
    }

    private Block getBlockAtNoCheck(Position position) {
        return blocks[position.getX()][position.getY()];
    }

    private void setBlockAtNoCheck(Position position, Block block) {
        blocks[position.getX()][position.getY()] = block;
        block.move(position);
    }

    private boolean hasBlockAtNoCheck(Position position) {
        return getBlockAtNoCheck(position) != null;
    }

    private void removeBlockAtNoCheck(Position position) {
        blocks[position.getX()][position.getY()] = null;
    }
}
