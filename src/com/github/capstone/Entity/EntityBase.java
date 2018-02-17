package com.github.capstone.Entity;

import org.lwjgl.util.Rectangle;

public abstract class EntityBase
{
    /**
     * @param floating point number delta
     * @return none
     * @throws none
     * @update This method is updating the entity the class is used for.
     */
    public abstract void update(float delta);

    /**
     * @param none
     * @return none
     * @throws none
     * @draw This method is used for drawing the entity the class is used for, setting the color, and size/shape of the entity.
     */
    public abstract void draw();

    /**
     * @param none
     * @return none in abstract
     * @throws none
     * @getHitBox This method is used for getting the hitbox the entity  possesses.
     */
    public abstract Rectangle getHitBox();
}
