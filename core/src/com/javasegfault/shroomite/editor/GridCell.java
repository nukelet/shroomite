package com.javasegfault.shroomite.editor;

import com.javasegfault.shroomite.blocks.BlockType;
import com.javasegfault.shroomite.TextureName;
import com.javasegfault.shroomite.util.Position;

public class GridCell {
    private Position position;
    private BlockType blockType;

    public GridCell(Position position, BlockType blockType) {
        this.position = position;
        this.blockType = blockType;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    public TextureName getBlockTypeTextureName() {
        return blockType.getTextureName();
    }

    @Override
    public String toString() {
        return String.format("GridCell(position=%s, blockType=%s)", position, blockType);
    }
}
