package com.javasegfault.shroomite.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.javasegfault.shroomite.blocks.BlockType;
import com.javasegfault.shroomite.util.Position;

public interface IBlock {
    public Position getPosition();

	public abstract BlockType getType();
	
	public abstract Texture getTexture();

    public void render();

    public void destroySelf();

    public void move(Position pos);

    public abstract void accept(IBlockVisitor visitor);
}
