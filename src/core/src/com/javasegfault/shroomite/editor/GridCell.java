package com.javasegfault.shroomite.editor;

import com.javasegfault.shroomite.blocks.BlockType;
import com.javasegfault.shroomite.TextureName;
import com.javasegfault.shroomite.util.Position;

public class GridCell {
    private Position position;
    private BlockType blockType;
    private String entityType;

    public GridCell(Position position, BlockType blockType) {
        this.position = position;
        this.blockType = blockType;
        this.entityType = null;
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

    public void removeBlockType() {
        this.blockType = BlockType.AIR;
    }

    public boolean hasBlockType() {
        return blockType != BlockType.AIR;
    }

    public TextureName getBlockTypeTextureName() {
        return blockType.getTextureName();
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public void removeEntityType() {
        this.entityType = null;
    }

    public boolean hasEntity() {
        return entityType != null;
    }

    public TextureName getEntityTypeTextureName() {
        if (entityType.equals("PLAYER")) {
            return TextureName.ADVENTURER_IDLE;
        } else if (entityType.equals("LEVEL_EXIT")) {
            return TextureName.LEVEL_EXIT;
        } else if (entityType.equals("LEVER")) {
            return TextureName.LEVER;
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("GridCell(position=%s, blockType=%s)", position, blockType);
    }
}
