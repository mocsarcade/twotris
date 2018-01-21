package com.github.capstone.Scene;

import com.github.capstone.Manager.AudioManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureImpl;

import java.awt.Font;

public class Button
{
    private String buttonText;
    private Rectangle box;
    private Color color;
    private Color textColor;
    private TrueTypeFont font;
    private boolean hovering;

    public Button(int w, int h, String text)
    {
        this(0, 0, w, h, text, new Color(0, 0, 0), new Color(0.9F, 0.9F, 0.9F));
    }

    public Button(int w, int h, String text, Color textColor, Color color)
    {
        this(0, 0, w, h, text, textColor, color);
    }

    public Button(int x, int y, int w, int h, String text, Color textColor, Color color)
    {
        this.box = new Rectangle(x, y, w, h);
        this.buttonText = text;
        this.color = color;
        this.textColor = textColor;
        this.font = new TrueTypeFont(new Font("Arial", Font.PLAIN, 32), false);
    }

    public boolean isClicked()
    {
        Rectangle mouse = new Rectangle(Mouse.getX(), Display.getHeight() - Mouse.getY(), 1, 1);
        if (box.intersects(mouse) && Mouse.isButtonDown(0))
        {
            // Sleep before processing the action
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
            }
            return true;
        }
        return false;
    }

    public void update()
    {
        Rectangle mouse = new Rectangle(Mouse.getX(), Display.getHeight() - Mouse.getY(), 1, 1);
        this.hovering = box.intersects(mouse);
    }

    public void draw()
    {
        TextureImpl.bindNone();
        float x = (float) box.getX();
        float y = (float) box.getY();
        float w = (float) box.getWidth();
        float h = (float) box.getHeight();

        if (this.hovering)
        {
            GL11.glColor3f(color.r + (0.1F), color.g + (0.1F), color.b + (0.1F));
        }
        else
        {
            GL11.glColor3f(color.r, color.g, color.b);
        }
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x + w, y);
        GL11.glVertex2f(x + w, y + h);
        GL11.glVertex2f(x, y + h);
        GL11.glEnd();

        font.drawString(x + (w / 2) - (font.getWidth(buttonText) / 2), y + (h / 2) - (font.getHeight(buttonText) / 2), buttonText, textColor);
    }

    public Rectangle getHitBox()
    {
        return box;
    }
}
