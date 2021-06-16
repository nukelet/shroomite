package com.javasegfault.shroomite.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.javasegfault.shroomite.BlockType;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.TextureName;
import com.javasegfault.shroomite.World;
import com.javasegfault.shroomite.util.Position;

public class Sand extends Block {
    public Sand(Position position, World world) {
        super(position, world);
    }

    @Override
    public BlockType getType() {
    	return BlockType.SAND;
    }
    
    @Override
    public Texture getTexture() {
    	return Shroomite.textures.get(TextureName.SAND);
    }

    public void interact(Block block) {
        return;
    }
}
