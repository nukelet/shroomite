package com.javasegfault.shroomite.block;

import com.badlogic.gdx.graphics.Texture;
import com.javasegfault.shroomite.BlockType;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.TextureName;
import com.javasegfault.shroomite.util.Position;

public class RockBlock extends Block {
    public RockBlock(Shroomite game, Position position) {
        super(game, position);
    }

    @Override
    public BlockType getType() {
        return BlockType.ROCK;
    }

    @Override
    public Texture getTexture() {
        return Shroomite.textures.get(TextureName.ROCK);
    }

    @Override
    public String toString() {
        return String.format("CobblestoneBlock(position=%s)", position.toString());
    }
}
