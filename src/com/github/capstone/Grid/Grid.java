package com.github.capstone.Grid;

import com.github.capstone.Entity.EntityPiece;
import com.github.capstone.Entity.EntityTetromino;
import com.github.capstone.Util.Helper;
import org.lwjgl.input.Keyboard;
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
    private EntityTetromino activePiece;
    private int gridSize;
    private boolean isGameOver;
    private long lastKeypress;

    public Grid()
    {
        // The grid should be 24 rows tall, 10 wide
        pieceGrid = new boolean[24][10];
        this.hitbox = new Rectangle();
        this.hitbox.setHeight(Display.getHeight());
        this.hitbox.setWidth((int) (this.hitbox.getHeight() / 2.4));
        this.hitbox.setX((Display.getWidth() / 2) - (this.hitbox.getWidth() / 2));
        this.hitbox.setY(4);
        this.gridSize = this.hitbox.getWidth() / 10;
        this.pieces = new ArrayList<>();
        this.activePiece = new EntityTetromino(this.gridSize, this.hitbox.getX() + ((this.hitbox.getWidth() / 2) - (this.gridSize / 2)));
        this.pieces.add(this.activePiece);
        this.isGameOver = false;
    }

    public void update(float delta)
    {
        if (Display.wasResized())
        {
            // Do recalculation algorithm
            this.hitbox.setHeight(Display.getHeight());
            this.hitbox.setWidth((int) (this.hitbox.getHeight() / 2.4));
            this.hitbox.setX((Display.getWidth() / 2) - (this.hitbox.getWidth() / 2));
            this.hitbox.setY(4);
            this.gridSize = this.hitbox.getWidth() / 10;
            for (EntityTetromino t : pieces)
            {
                t.updateSize(this.gridSize);
            }
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_A) && Helper.getTime() - lastKeypress > 250)
        {
            if ((this.activePiece.getHitBox().getX()) > this.hitbox.getX())
            {
                this.activePiece.moveLeft();
                lastKeypress = Helper.getTime();
            }
            else
            {
                System.out.println("Not enough room!");
            }
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_D) && Helper.getTime() - lastKeypress > 250)
        {
            this.activePiece.moveRight();
            lastKeypress = Helper.getTime();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
        {
            this.activePiece.speed = 3;
        }
        else
        {
            this.activePiece.speed = 1;
        }
        for (EntityTetromino t : pieces)
        {
            t.update(delta);
        }
        if (activePiece.getState() == EntityTetromino.State.IDLE)
        {
            // Step 1: Determine where the hitbox is in the grid
            EntityPiece[][] m = activePiece.getPieceMatrix();
            for (int i = 0; i < m.length; i++)
            {
                for (int j = 0; j < m[i].length; j++)
                {
                    if (m[i][j] != null)
                    {
                        int row = ((m[i][j].getHitBox().getY() - this.hitbox.getY()) / gridSize);
                        int col = (m[i][j].getHitBox().getX() - this.hitbox.getX()) / gridSize;
                        m[i][j].getHitBox().setY((row * gridSize) + gridSize);
                        // Step 2: occupy the grid
                        this.pieceGrid[row][col] = true;
                    }
                }
            }
            // Step 3: regular update stuff:
            this.checkRows();
            // Step 4: Generate a new piece
            this.activePiece = new EntityTetromino(this.gridSize, this.hitbox.getX() + ((this.hitbox.getWidth() / 2) - (this.gridSize / 2)));
            if (this.activePiece.getHitBox().getX() < 0)
            {
                this.isGameOver = true;
            }
            this.pieces.add(this.activePiece);
        }
        else
        {
            // Step 1: Determine where the hitbox is in the grid
            EntityPiece[][] m = activePiece.getPieceMatrix();
            for (int i = 0; i < m.length; i++)
            {
                for (int j = 0; j < m[i].length; j++)
                {
                    if (m[i][j] != null)
                    {
                        // Step 2: Check to see if the NEXT slot it can fall in is occupied
                        int rowCheck = ((m[i][j].getHitBox().getY() - this.hitbox.getY()) / gridSize) + 1;
                        int colCheck = (m[i][j].getHitBox().getX() - this.hitbox.getX()) / gridSize;
                        if (rowCheck < this.pieceGrid.length && this.pieceGrid[rowCheck][colCheck])
                        {
                            this.activePiece.setState(EntityTetromino.State.IDLE);
                        }
                    }
                }
            }
        }
    }

    public void draw()
    {
        float x = (float) this.hitbox.getX();
        float y = (float) this.hitbox.getY();
        float w = (float) this.hitbox.getWidth();
        float h = (float) this.hitbox.getHeight();


        GL11.glColor3f(.5F, .5F, .5F);

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

    public boolean isGameOver()
    {
        return this.isGameOver;
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
