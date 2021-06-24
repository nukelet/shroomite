package com.javasegfault.shroomite.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.javasegfault.shroomite.BlockType;
import com.javasegfault.shroomite.util.Position;

public interface BlockInterface {
    public Position getPosition();

	public abstract BlockType getType();
	
	public abstract Texture getTexture();

    public void render();

    public void destroySelf();

    public void move(Position pos);

    public abstract void accept(BlockVisitorInterface visitor);
}
