package com.github.capstone.Entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

import java.util.Random;

public class EntityPiece extends EntityBase
{
    private Rectangle hitbox;

    public EntityPiece(int w, int h)
    {
        this.hitbox = new Rectangle(0, 0, w, h);
        this.hitbox.setLocation((Display.getHeight() / 2) - (this.hitbox.getWidth() / 2), (Display.getHeight() / 2) - (this.hitbox.getHeight() / 2));
    }

    @Override
    public void update(float delta)
    {
        if (Keyboard.isKeyDown(Keyboard.KEY_W))
        {
            this.hitbox.translate(0, -1);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S))
        {
            this.hitbox.translate(0, 1);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A))
        {
            this.hitbox.translate(-1, 0);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D))
        {
            this.hitbox.translate(1, 0);
        }
    }

    @Override
    public void draw()
    {
        float x = (float) this.hitbox.getX();
        float y = (float) this.hitbox.getY();
        float w = (float) this.hitbox.getWidth();
        float h = (float) this.hitbox.getHeight();

        GL11.glColor3f(1, 1, 1);
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
