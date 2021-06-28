package com.javasegfault.shroomite.agents;

import com.badlogic.gdx.math.Vector2;
import com.javasegfault.shroomite.IWorld;

public class PlayerAgent extends Agent {
    public Vector2 speed;

    public PlayerAgent(IWorld world, Vector2 position) {
        super(world, position);
        this.speed = new Vector2(0, 0);
        this.agentBlockVisitor = new PlayerBlockVisitor();
    }

    public void setSpeed(Vector2 speed) {
        this.speed = speed;
    }

    public void setSpeed(float x, float y) {
        setSpeed(new Vector2(x, y));
    }

    public Vector2 getSpeed() {
        return this.speed;
    }
}
