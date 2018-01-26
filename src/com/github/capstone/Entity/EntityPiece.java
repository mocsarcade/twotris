package com.github.capstone.Entity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.Rectangle;

public class EntityPiece extends EntityBase
{
    private Rectangle hitbox;
    private Color color;

    EntityPiece(int x, int y, int size)
    {
        this.hitbox = new Rectangle(x, y, size, size);
        this.color = new Color(0, 128, 255);
    }

    @Override
    public String toString()
    {
        return "Piece";
    }

    @Override
    public void update(float delta)
    {
    }

    @Override
    public void draw()
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

    @Override
    public Rectangle getHitBox()
    {
        return this.hitbox;
    }
}
