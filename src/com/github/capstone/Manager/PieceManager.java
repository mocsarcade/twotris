package com.github.capstone.Manager;

import com.github.capstone.Entity.EntityPiece;
import com.github.capstone.Entity.EntityTetromino;
import com.github.capstone.Util.Helper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;

public class PieceManager
{
    private static PieceManager instance;
    private ArrayList<EntityTetromino> piecelist;
    private EntityTetromino activePiece;
    private long lastKeypress = 0;

    public PieceManager()
    {
        this.piecelist = new ArrayList<>();
        generateNewTetronimo();
        instance = this;
    }

    public static PieceManager getInstance()
    {
        return instance;
    }

    public void draw()
    {
        for (EntityTetromino tet : piecelist)
        {
            tet.draw();
        }
    }

    public void update(float delta)
    {
        for (EntityTetromino tet : piecelist)
        {
            tet.update(delta);
        }
        if (this.activePiece != null && (this.activePiece.getHitBox().getY() + this.activePiece.getHitBox().getHeight() < Display.getHeight()))
        {
            if (Helper.getTime() - lastKeypress > 250)
            {
                if (Keyboard.isKeyDown(Keyboard.KEY_R))
                {
                    this.activePiece.rotate();
                    lastKeypress = Helper.getTime();
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_A))
                {
                    lastKeypress = Helper.getTime();
                    this.activePiece.getHitBox().translate(-EntityTetromino.SIZE, 0);
                    for (EntityPiece[] column : this.activePiece.getPieceMatrix())
                    {
                        for (EntityPiece row : column)
                        {
                            if (row != null)
                            {
                                row.getHitBox().translate(-EntityTetromino.SIZE, 0);
                            }
                        }
                    }
                }
                else if (Keyboard.isKeyDown(Keyboard.KEY_D))
                {
                    lastKeypress = Helper.getTime();
                    this.activePiece.getHitBox().translate(EntityTetromino.SIZE, 0);
                    for (EntityPiece[] column : this.activePiece.getPieceMatrix())
                    {
                        for (EntityPiece row : column)
                        {
                            if (row != null)
                            {
                                row.getHitBox().translate(EntityTetromino.SIZE, 0);
                            }
                        }
                    }
                }
            }

            // Let the acceleration happen anytime, not just every 500ms
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
            {
                this.activePiece.speed = 2;
            }
            else
            {
                this.activePiece.speed = 1;
            }
        }
    }

    public void clear()
    {
        this.piecelist.clear();
    }

    public void generateNewTetronimo()
    {
        this.activePiece = new EntityTetromino();
        this.piecelist.add(activePiece);
    }

    public boolean isPieceColliding()
    {
        for (EntityTetromino other : piecelist)
        {
            // We don't compare hitboxes on ourselves..
            if (other != this.activePiece)
            {
                // Ok, so it's not comparing the thing to itself
                if (other.getHitBox().intersects(this.activePiece.getHitBox()))
                {
                    // Ok, so the bigger, bloated hitboxes collide. Do the inner ones?
                    try
                    {
                        for (EntityPiece[] thisRow : this.activePiece.getPieceMatrix())
                        {
                            for (EntityPiece thisCol : thisRow)
                            {
                                for (EntityPiece[] otherRow : other.getPieceMatrix())
                                {
                                    for (EntityPiece otherCol : otherRow)
                                    {
                                        if (thisCol.getHitBox().intersects(otherCol.getHitBox()))
                                        {
                                            // Guess so. Set this piece to idle so it stops.
                                            other.setState(EntityTetromino.State.IDLE);
                                            return true;
                                        }
                                        else
                                        {
                                            System.out.println(thisCol.getHitBox() + " vs " + otherCol.getHitBox());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    catch (NullPointerException ignored)
                    {
                        // We ignore an NPE because I expect it. This method is cleaner than a ton of "!= null" checks
                    }
                }
            }
        }
        return false;
    }

    public boolean isPieceStillFalling()
    {
        return this.activePiece.getState() == EntityTetromino.State.FALLING;
    }

    // TODO: Actually evaluate this at some point. Probably using the top of the hitbox of the last active piece
    public boolean canFitMorePieces()
    {
        return true;
    }
}
