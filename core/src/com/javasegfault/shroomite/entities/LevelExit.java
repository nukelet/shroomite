package com.javasegfault.shroomite.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.javasegfault.shroomite.IWorld;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.TextureName;
import com.javasegfault.shroomite.World;

public class LevelExit extends Entity {
    public LevelExit(IWorld world, Vector2 position) {
        super(world, position);
        hitbox.setPosition(position);
        setHitboxSize(48, 64);
        setTextureSize(48, 64);
    }

    public void accept(IEntityVisitor visitor) {
        visitor.visitLevelExit(this);
    }

    public Texture getTexture() {
        return Shroomite.textures.get(TextureName.LEVEL_EXIT);
    }
}
