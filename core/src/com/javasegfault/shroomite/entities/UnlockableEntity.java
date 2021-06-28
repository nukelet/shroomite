package com.javasegfault.shroomite.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.javasegfault.shroomite.IWorld;
import com.javasegfault.shroomite.World;

public abstract class UnlockableEntity extends Entity {
    private boolean locked;
    private long lastToggleTick;

    public UnlockableEntity(IWorld world, Vector2 position) {
        super(world, position);
        this.locked = true;
        lastToggleTick = 0;
    }

    public boolean isLocked() {
        return locked;
    }

    public void toggleLock() {
        if (TimeUtils.timeSinceMillis(lastToggleTick) > 200) {
            locked = !locked;
            lastToggleTick = TimeUtils.millis();
        }
    }
}
