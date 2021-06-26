package com.javasegfault.shroomite;

import com.javasegfault.shroomite.blocks.Block;
import com.javasegfault.shroomite.util.Position;

public interface IWorld {
    int getWidth();

    int getHeight();

    /**
     * Adds block to world at the given position.
     * 
     * @param position
     * @param block
     * @return True if position is valid, false otherwise.
     */
    boolean setBlockAt(Position position, Block block);

    void addBlock(Block block);

    /**
     * Adds block to world at the given position if the position is empty.
     * 
     * @param position
     * @param block
     * @return True if position is valid and there is no block at given position,
     *         false otherwise.
     */
    boolean addBlockAt(Position position, Block block);

    /**
     * @param position
     * @return Block at given position or null if position is not valid.
     */
    Block getBlockAt(Position position);

    boolean hasBlockAt(Position position);

    /**
     * Removes block from world at the given position.
     * 
     * @param position
     * @return True if given position is valid, false otherwise.
     */
    boolean removeBlockAt(Position position);
}
