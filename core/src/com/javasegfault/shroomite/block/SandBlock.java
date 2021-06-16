package com.javasegfault.shroomite.block;

import com.badlogic.gdx.graphics.Texture;
import com.javasegfault.shroomite.BlockType;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.TextureName;
import com.javasegfault.shroomite.util.Position;

public class SandBlock extends Block {
    public SandBlock(Shroomite game, Position position) {
        super(game, position);
    }

    @Override
    public BlockType getType() {
        return BlockType.SAND;
    }

    @Override
    public Texture getTexture() {
        return Shroomite.textures.get(TextureName.SAND);
    }

    @Override
    public String toString() {
        return String.format("SandBlock(position=%s)", position.toString());
    }
}
