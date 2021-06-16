package com.javasegfault.shroomite.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.javasegfault.shroomite.BlockType;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.TextureName;
import com.javasegfault.shroomite.World;
import com.javasegfault.shroomite.util.Position;

public class Rock extends Block {
    public Rock(Position position, World world) {
        super(position, world);
        movable = false;
    }
    
    @Override
    public BlockType getType() {
    	return BlockType.ROCK;
    }
    
    @Override
    public Texture getTexture() {
    	return Shroomite.textures.get(TextureName.ROCK);
    }

    public void updateState() {
        this.isUpdated = true;
    }

    public void interact(Block block) {
        return;
    }
}
