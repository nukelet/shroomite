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

    @Override
    public boolean setBlock(Block block) {
        Position blockPosition = block.getPosition();
        if (!isValidPosition(blockPosition)) {
            System.err.printf("Invalid block add at %s: invalid position\n", blockPosition);
            return false;
        }

        setBlockAtNoCheck(blockPosition, block);
        return true;
    }

    @Override
    public boolean setBlockAt(Position position, Block block) {
        if (!isValidPosition(position)) {
            System.err.printf("Invalid block add at %s: invalid position\n", position);
            return false;
        }

        setBlockAtNoCheck(position, block);
        block.move(position);
        return true;
    }

    @Override
    public boolean addBlock(Block block) {
        Position blockPosition = block.getPosition();
        if (!isValidPosition(blockPosition)) {
            System.err.printf("Invalid block add at %s: invalid position\n", blockPosition);
            return false;
        }

        if (hasBlockAtNoCheck(blockPosition)) {
            System.err.printf("Invalid block add at %s: position not empty\n", blockPosition);
            return false;
        }

        setBlockAtNoCheck(blockPosition, block);
        return true;
    }

    @Override
    public boolean addBlockAt(Position position, Block block) {
        if (!isValidPosition(position)) {
            System.err.printf("Invalid block add at %s: invalid position\n", position);
            return false;
        }

        if (hasBlockAtNoCheck(position)) {
            System.err.printf("Invalid block add at %s: position not empty\n", position);
            return false;
        }

        setBlockAtNoCheck(position, block);
        block.move(position);
        return true;
    }

    @Deprecated
    public Block getBlockAt(int x, int y) {
        return getBlockAt(new Position(x, y));
    }

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
        removeBlockAt(block.getPosition());
    }

    @Override
    public boolean removeBlockAt(Position position) {
        if (!isValidPosition(position)) {
            return false;
        }

        removeBlockAtNoCheck(position);
        return true;
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
    }

    private boolean hasBlockAtNoCheck(Position position) {
        return getBlockAtNoCheck(position) != null;
    }

    private void removeBlockAtNoCheck(Position position) {
        blocks[position.getX()][position.getY()] = null;
    }
}
