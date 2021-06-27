package com.javasegfault.shroomite;

import com.javasegfault.shroomite.blocks.Block;
import com.javasegfault.shroomite.util.Position;

public interface IWorld {
    int getWidth();

    int getHeight();

    /**
     * Adds block to world at the current block position.
     *
     * @param block
     * @return True if position is valid, false otherwise.
     */
    boolean setBlock(Block block);

    /**
     * Adds block to world at position and sets block position to
     * position.
     *
     * @param position
     * @param block
     * @return True if position is valid, false otherwise.
     */
    boolean setBlockAt(Position position, Block block);

    /**
     * Adds block to world at the current block position if world has
     * no block at position.
     *
     * @param block
     * @return True if position is valid and world has no block at
     * position, false otherwise.
     */
    boolean addBlock(Block block);

    /**
     * Adds block to world at position and sets block position to
     * position if world has no blocks at position.
     *
     * @param position
     * @param block
     * @return True if position is valid and world has no block at
     * position, false otherwise.
     */
    boolean addBlockAt(Position position, Block block);

    /**
     * @param position
     * @return Block at position or null if position is not valid.
     */
    Block getBlockAt(Position position);

    /**
     * @param position
     * @return False if position is not valid or there is no block at
     * position, false otherwise.
     */
    boolean hasBlockAt(Position position);

    /**
     * Removes block from world at the given position.
     *
     * @param position
     * @return True if position is valid, false otherwise.
     */
    boolean removeBlockAt(Position position);
}
