package com.github.capstone.Entity;

import com.github.capstone.Util.Helper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Rectangle;

import java.util.Random;

public class EntityTetromino extends EntityBase
{
    private Type type;
    private State state;
    private EntityPiece[][] pieceMatrix;
    private Rectangle hitBox;
    private int size;
    private int rotation;
    private long lastKeypress;

    public EntityTetromino()
    {
        this.type = Type.Z;
        this.state = State.FALLING;
        this.pieceMatrix = generateFromType();
        this.size = 32;
        this.rotation = 0;
        this.lastKeypress = 0;
    }

    private EntityPiece[][] generateFromType()
    {
        // TODO: X and Y values should be adjusted based on where in the "Grid" they are.
        // Ex: new EntityPiece(0 * gridPlace, size * gridPlace, size)

        switch (this.type)
        {
            case L:
                this.hitBox = new Rectangle(0, 0, 2 * this.size, 3 * this.size);
                return new EntityPiece[][]{
                        {new EntityPiece(0, 0, this.size), null},
                        {new EntityPiece(0, this.size, this.size), null},
                        {new EntityPiece(0, 2 * this.size, this.size), new EntityPiece(this.size, 2 * this.size, this.size)}
                };
            case S:
                this.hitBox = new Rectangle(0, 0, 2 * this.size, 3 * this.size);
                return new EntityPiece[][]{
                        {new EntityPiece(0, 0, this.size), null, null},
                        {new EntityPiece(0, this.size, this.size), new EntityPiece(this.size, this.size, this.size), null},
                        {null, new EntityPiece(this.size, 2 * this.size, this.size), null}
                };
            case J:
                this.hitBox = new Rectangle(0, 0, 2 * this.size, 3 * this.size);
                return new EntityPiece[][]{
                        {null, new EntityPiece(this.size, 0, this.size)},
                        {null, new EntityPiece(this.size, this.size, this.size)},
                        {new EntityPiece(0, 2 * this.size, this.size), new EntityPiece(this.size, 2 * this.size, this.size)}
                };
            case T:
                this.hitBox = new Rectangle(0, 0, 3 * this.size, 2 * this.size);
                return new EntityPiece[][]{
                        {new EntityPiece(0, 0, this.size), new EntityPiece(this.size, 0, this.size), new EntityPiece(2 * this.size, 0, this.size)},
                        {null, new EntityPiece(this.size, this.size, this.size), null}
                };
            case O:
                this.hitBox = new Rectangle(0, 0, 2 * this.size, 2 * this.size);
                return new EntityPiece[][]{
                        {new EntityPiece(0, 0, this.size), new EntityPiece(this.size, 0, this.size)},
                        {new EntityPiece(0, this.size, this.size), new EntityPiece(this.size, this.size, this.size)}
                };
            case I:
                this.hitBox = new Rectangle(0, 0, 4 * this.size, this.size);
                return new EntityPiece[][]{
                        {new EntityPiece(0, 0, this.size), new EntityPiece(this.size, 0, this.size), new EntityPiece(2 * this.size, 0, this.size), new EntityPiece(3 * this.size, 0, this.size)}
                };
            case Z:
                this.hitBox = new Rectangle(0, 0, 2 * this.size, 3 * this.size);
                return new EntityPiece[][]{
                        {null, new EntityPiece(this.size, 0, this.size)},
                        {new EntityPiece(0, this.size, this.size), new EntityPiece(this.size, this.size, this.size)},
                        {new EntityPiece(0, 2 * this.size, this.size), null}
                };
        }
        return new EntityPiece[][]{};
    }

    public void update(float delta)
    {
        if (this.state == State.FALLING)
        {
            for (EntityPiece[] column : pieceMatrix)
            {
                for (EntityPiece row : column)
                {
                    if (row != null)
                    {
                        if (this.hitBox.getY() + this.getHitBox().getHeight() < Display.getHeight())
                        {
                            row.getHitBox().translate(0, 1);
                            if (Keyboard.isKeyDown(Keyboard.KEY_R) && (Helper.getTime() - lastKeypress > 500))
                            {
                                rotate();
                                lastKeypress = Helper.getTime();
                            }
                        }
                        else
                        {
                            this.state = State.IDLE;
                        }
                        row.update(delta);
                    }
                }
            }
            this.getHitBox().translate(0, 1);
        }
    }

    public void draw()
    {
        for (EntityPiece[] column : pieceMatrix)
        {
            for (EntityPiece row : column)
            {
                if (row != null)
                {
                    row.draw();
                }
            }
        }
    }

    private void rotate()
    {
        EntityPiece[][] temp = new EntityPiece[pieceMatrix[0].length][pieceMatrix.length];
        for (int i = 0; i < this.pieceMatrix[0].length; i++)
        {
            for (int j = 0; j < this.pieceMatrix.length; j++)
            {
                temp[i][this.pieceMatrix.length - 1 - j] = this.pieceMatrix[j][i];
            }
        }

        switch (this.type)
        {
            case Z:
                if (this.rotation == 0)
                {
                    // TODO: Find a way to make x and y += something..?
                    /*
                    ##
                     ##*/
                    this.pieceMatrix = new EntityPiece[][]{
                            {},
                            {}
                    };
                }
                else if (this.rotation == 90)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {null, new EntityPiece(this.size, 0, this.size)},
                            {new EntityPiece(0, this.size, this.size), new EntityPiece(this.size, this.size, this.size)},
                            {new EntityPiece(0, 2 * this.size, this.size), null}
                    };
                }
                else if (this.rotation == 180)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {null, new EntityPiece(this.size, 0, this.size)},
                            {new EntityPiece(0, this.size, this.size), new EntityPiece(this.size, this.size, this.size)},
                            {new EntityPiece(0, 2 * this.size, this.size), null}
                    };
                }
                else if (this.rotation == 270)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {null, new EntityPiece(this.size, 0, this.size)},
                            {new EntityPiece(0, this.size, this.size), new EntityPiece(this.size, this.size, this.size)},
                            {new EntityPiece(0, 2 * this.size, this.size), null}
                    };
                }
        }

        // Increment rotation by 90 degrees unless it's going to be more than 360deg
        this.rotation = this.rotation + 90 >= 360 ? 0 : this.rotation + 90;

        // Rotate the hitbox too:
        int w = this.hitBox.getWidth();
        this.hitBox.setWidth(this.hitBox.getHeight());
        this.hitBox.setHeight(w);
    }

    public Rectangle getHitBox()
    {
        return this.hitBox;
    }

    private Type randomType()
    {
        Random rand = new Random();
        int rng = rand.nextInt(Type.values().length);
        return Type.values()[rng];
    }

    public enum State
    {
        FALLING, IDLE;
    }

    public enum Type
    {
        L("L"),
        S("S"),
        J("J"),
        T("T"),
        O("O"),
        I("I"),
        Z("Z");

        private String name;

        Type(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return this.name;
        }
    }
}
