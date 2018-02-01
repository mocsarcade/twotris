package com.github.capstone.Scene;

import com.github.capstone.Manager.AudioManager;
import com.github.capstone.Util.Helper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
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
    private Texture sprite;
    private float wr, hr;

    public Button(int w, int h, String text, Color textColor, Color color)
    {
        this(0, 0, w, h, text, textColor, color);
    }

    private Button(int x, int y, int w, int h, String text, Color textColor, Color color)
    {
        this.sprite = null;
        this.box = new Rectangle(x, y, w, h);
        this.buttonText = text;
        this.color = color;
        this.textColor = textColor;
        this.font = Helper.getFont();
    }

    public Button(int x, int y, String text)
    {
        this.sprite = Helper.loadTexture("gui/button");
        this.wr = 1.0F * sprite.getImageWidth() / sprite.getTextureWidth();
        this.hr = 1.0F * sprite.getImageHeight() / sprite.getTextureHeight();
        this.box = new Rectangle(x, y, sprite.getImageWidth(), sprite.getImageHeight());
        this.buttonText = text;
        this.color = new Color(255, 255, 255);
        this.textColor = new Color(0, 182, 164);
        this.font = Helper.getFont();
    }

    public void setFont(TrueTypeFont font)
    {
        this.font = font;
    }

    public boolean isClicked()
    {
        Rectangle mouse = new Rectangle(Mouse.getX(), Display.getHeight() - Mouse.getY(), 1, 1);
        if (box.intersects(mouse) && Mouse.isButtonDown(0))
        {
            // Sleep before processing the action
            try
            {
                Thread.sleep(300);
            }
            catch (InterruptedException ignored)
            {
            }
            AudioManager.getInstance().play("select");
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
        float x = (float) this.box.getX();
        float y = (float) this.box.getY();
        float w = (float) this.box.getWidth();
        float h = (float) this.box.getHeight();

        if (this.sprite != null)
        {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, sprite.getTextureID());
            GL11.glColor3f(1F, 1F, 1F);
            if (this.hovering)
            {
                GL11.glColor3f(0.9F, 0.9F, 0.9F);
            }
            GL11.glBegin(GL11.GL_QUADS);

            GL11.glTexCoord2f(0F, 0F);
            GL11.glVertex2f(x, y);

            GL11.glTexCoord2f(wr, 0F);
            GL11.glVertex2f(x + w, y);

            GL11.glTexCoord2f(wr, hr);
            GL11.glVertex2f(x + w, y + h);

            GL11.glTexCoord2f(0F, hr);
            GL11.glVertex2f(x, y + h);


            GL11.glEnd();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        }
        else
        {
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
        }
        TextureImpl.bindNone();
        font.drawString(x + (w / 2) - (font.getWidth(buttonText) / 2), y + (h / 2) - (font.getHeight(buttonText) / 2), buttonText, textColor);
    }

    public Rectangle getHitBox()
    {
        return box;
    }

    public void setButtonText(String newText)
    {
        this.buttonText = newText;
    }

    public void reloadFont()
    {
        this.font = Helper.getFont();
    }
}
