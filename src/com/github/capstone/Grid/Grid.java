package com.github.capstone.Grid;

import com.github.capstone.Entity.EntityTetromino;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;

public class Grid
{
    private boolean[][] pieceGrid;
    private Rectangle hitbox;
    private ArrayList<EntityTetromino> pieces;

    public Grid()
    {
        // The grid should be 24 rows tall, 10 wide
        pieceGrid = new boolean[24][10];
        this.hitbox = new Rectangle();
        this.hitbox.setHeight(Display.getHeight() - 8);
        this.hitbox.setWidth((int) (this.hitbox.getHeight() / 2.4));
        this.hitbox.setX((Display.getWidth() / 2) - (this.hitbox.getWidth() / 2));
        this.hitbox.setY(4);
        this.pieces = new ArrayList<>();
    }

    public void update(float delta)
    {
        for (EntityTetromino t : pieces)
        {
            t.update(delta);
        }

        if (Display.wasResized())
        {
            // Do recalculation algorithm
            this.hitbox.setHeight(Display.getHeight() - 8);
            this.hitbox.setWidth((int) (this.hitbox.getHeight() / 2.4));
            this.hitbox.setX((Display.getWidth() / 2) - (this.hitbox.getWidth() / 2));
            this.hitbox.setY(4);
        }
    }

    public void draw()
    {
        float x = (float) hitbox.getX();
        float y = (float) hitbox.getY();
        float w = (float) hitbox.getWidth();
        float h = (float) hitbox.getHeight();

        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
        GL11.glColor3f(1f, 1f, 1f);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x + w, y);
        GL11.glVertex2f(x + w, y + h);
        GL11.glVertex2f(x, y + h);
        GL11.glEnd();

        for (EntityTetromino t : pieces)
        {
            t.draw();
        }
    }

    public void obliterate(int row)
    {
        for (int col = 0; col < pieceGrid[row].length; col++)
        {
            pieceGrid[row][col] = false;
        }
        // Shift the grid down:
        for (int i = row; i > 0; i--)
        {
            System.arraycopy(pieceGrid[i - 1], 0, pieceGrid[i], 0, pieceGrid[i].length);
        }
        Arrays.fill(pieceGrid[0], false);
    }

    public boolean[][] getPieceGrid()
    {
        return pieceGrid;
    }

    public void checkRows()
    {
        for (int i = 0; i < pieceGrid.length; i++)
        {
            if (isRowFull(i))
            {
                obliterate(i);
            }
        }
    }

    public boolean isRowFull(int row)
    {
        for (int col = 0; col < pieceGrid[row].length; col++)
        {
            if (!pieceGrid[row][col])
            {
                return false;
            }
        }
        return true;
    }
}
