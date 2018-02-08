package com.github.capstone.Entity;

import org.lwjgl.util.Rectangle;

public abstract class EntityBase
{
	/**
		@update
		This method is updating the entity the class is used for. 
		@param floating point number delta
		@return none
		@throws none
	*/
    public abstract void update(float delta);
	/**
		@draw
		This method is used for drawing the entity the class is used for, setting the color, and size/shape of the entity. 
		@param none
		@return none
		@throws none
	*/
    public abstract void draw();
	/**
		@getHitBox
		This method is used for getting the hitbox the entity  possesses. 
		@param none
		@return none in abstract
		@throws none
	*/
    public abstract Rectangle getHitBox();
}
