package com.github.capstone.Entity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.Rectangle;

public class EntityPiece
{
    private Rectangle hitbox;
	/**
		@EntityPiece
		This constructor method is used constructing the piece’s hitbox. 
		@param x     X is the initial X location of the piece. 
		@param y     Y is the initial Y location of the piece
		@param size 	Length of the sides, to create the piece’s shape. 
		@return none
		@throws none
	*/
    EntityPiece(int x, int y, int size)
    {
        this.hitbox = new Rectangle(x, y, size, size);
    }
	/**
		@toString
		This method is used for returning the presence of a piece during testing. 
		@param none
		@return “Piece”
		@throws none
	*/
    @Override
    public String toString()
    {
        return "Piece";
    }
	/**
		@update
		This method is used for updating the piece’s position and rotation.  
		@param delta
		@return none
		@throws none
	*/
    public void update(float delta)
    {
    }
	/**
		@draw
		This method is used for drawing the piece, setting the color, and size/shape. 
		@param none
		@return none
		@throws none
	*/
    public void draw(Color color)
    {
        float x = (float) this.hitbox.getX();
        float y = (float) this.hitbox.getY();
        float w = (float) this.hitbox.getWidth();
        float h = (float) this.hitbox.getHeight();

        GL11.glColor3f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x + w, y);
        GL11.glVertex2f(x + w, y + h);
        GL11.glVertex2f(x, y + h);

        GL11.glEnd();
    }
	/**
		@getHitBox
		This method is used for getting the hitbox the piece possesses. 
		@param none
		@return hitbox of the piece
		@throws none
	*/
    public Rectangle getHitBox()
    {
        return this.hitbox;
    }
}
