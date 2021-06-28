package com.javasegfault.shroomite.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.javasegfault.shroomite.IWorld;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.TextureName;
import com.javasegfault.shroomite.World;

public class Lever extends UnlockableEntity {
    public Lever(IWorld world, Vector2 position) {
        super(world, position);
        setHitboxSize(16, 16);
        setTextureSize(16, 16);
    }

    public void accept(IEntityVisitor visitor) {
        visitor.visitLever(this);
    }

    public Texture getTexture() {
        return Shroomite.textures.get(TextureName.LEVER);
    }
}
