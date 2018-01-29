package com.github.capstone.Entity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.Rectangle;

public class EntityPiece
{
    private Rectangle hitbox;

    EntityPiece(int x, int y, int size)
    {
        this.hitbox = new Rectangle(x, y, size, size);
    }

    @Override
    public String toString()
    {
        return "Piece";
    }

    public void update(float delta)
    {
    }

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

    public Rectangle getHitBox()
    {
        return this.hitbox;
    }
}
