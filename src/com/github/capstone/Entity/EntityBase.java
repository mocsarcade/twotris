package com.github.capstone.Entity;

import org.lwjgl.util.Rectangle;

public abstract class EntityBase
{
    public abstract void update(float delta);

    public abstract void draw();

    public abstract Rectangle getHitBox();
}
