package com.javasegfault.shroomite;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.javasegfault.shroomite.util.Position;

public class GridBlockImage extends Image {
    private Position position;
    private BlockType blockType;

    public GridBlockImage(Position position, BlockType blockType) {
        this.position = position;
        setBlockType(blockType);
    }

    public Position getPosition() {
        return position;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
        if (blockType == null) {
            this.setDrawable(Shroomite.defaultTextureDrawable);
        } else {
            this.setDrawable(Shroomite.texturesDrawables.get(blockType.getTextureName()));
        }
    }
}
