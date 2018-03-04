package com.github.capstone.Components;

import com.github.capstone.Util.Helper;
import com.github.capstone.Util.Textures;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.opengl.Texture;

public class TetrominoPiece
{
    private Rectangle hitbox;
    private Texture sprite;
    private float wr, hr;

    /**
     * @param x    X is the initial X location of the piece.
     * @param y    Y is the initial Y location of the piece
     * @param size Length of the sides, to create the piece’s shape.
     * @return none
     * @throws none
     * @EntityPiece This constructor method is used constructing the piece’s hitbox.
     */
    public TetrominoPiece(int x, int y, int size)
    {
        this.hitbox = new Rectangle(x, y, size, size);
        this.sprite = Textures.TETRABIT;
        this.wr = 1.0F * sprite.getImageWidth() / sprite.getTextureWidth();
        this.hr = 1.0F * sprite.getImageHeight() / sprite.getTextureHeight();
    }

    /**
     * @param none
     * @return none
     * @throws none
     * @draw This method is used for drawing the piece, setting the color, and size/shape.
     */
    public void draw(Color color, float alpha)
    {
        float x = (float) this.hitbox.getX();
        float y = (float) this.hitbox.getY();
        float w = (float) this.hitbox.getWidth();
        float h = (float) this.hitbox.getHeight();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, sprite.getTextureID());
        GL11.glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, alpha);

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

    /**
     * @param none
     * @return hitbox of the piece
     * @throws none
     * @getHitBox This method is used for getting the hitbox the piece possesses.
     */
    public Rectangle getHitBox()
    {
        return this.hitbox;
    }
}
