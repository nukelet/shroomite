package com.javasegfault.shroomite.block;

import com.badlogic.gdx.graphics.Texture;
import com.javasegfault.shroomite.BlockType;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.util.Position;

public abstract class Block {
    protected Shroomite game;
    protected Position position;

    public Block(Shroomite game, Position position) {
        this.game = game;
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public abstract BlockType getType();

    public abstract Texture getTexture();

    public void render() {
        game.drawBlockRegion(getTexture(), position.getX(), position.getY());
    }
}
