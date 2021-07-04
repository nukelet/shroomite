package com.javasegfault.shroomite.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public interface IEntity {
    public void setPosition(Vector2 vec);
    public Vector2 getPosition();
    public void move(Vector2 vec);
    public Rectangle getHitbox();
    public Rectangle getTextureRect();
    public boolean overlaps(IEntity entity);
    public void accept(IEntityVisitor visitor);
    public Texture getTexture();
}
