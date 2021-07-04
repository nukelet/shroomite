package com.javasegfault.shroomite.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.javasegfault.shroomite.util.Position;

public interface IBlock {
    Position getPosition();

    void setPosition(Position position);

    BlockType getType();

    Texture getTexture();

    void destroySelf();

    void move(Position pos);

    void accept(IBlockVisitor visitor);
}
