package com.javasegfault.shroomite.block;

import com.badlogic.gdx.graphics.Texture;
import com.javasegfault.shroomite.BlockType;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.TextureName;
import com.javasegfault.shroomite.util.Position;

public class DirtBlock extends Block {
    public DirtBlock(Shroomite game, Position position) {
        super(game, position);
    }

    @Override
    public BlockType getType() {
        return BlockType.DIRT;
    }

    @Override
    public Texture getTexture() {
        return Shroomite.textures.get(TextureName.DIRT);
    }

    @Override
    public String toString() {
        return String.format("DirtBlock(position=%s)", position.toString());
    }
}
