package com.github.capstone.Entity;

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
    private int size = 32;

    public EntityTetromino()
    {
        this.type = Type.Z;
        this.state = State.FALLING;
        this.pieceMatrix = generateFromType();
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
                            if (Keyboard.isKeyDown(Keyboard.KEY_R))
                            {
                                rotate();
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
        // TODO: Rotate the pieceMatrix clockwise...
        // TODO: This should not only update the pieceMatrix, but also the hitboxes of the individual pieces
        EntityPiece[][] temp = new EntityPiece[pieceMatrix[0].length][pieceMatrix.length];

        for (int i = 0; i < pieceMatrix[0].length; i++)
        {
            for (int j = pieceMatrix.length - 1; j >= 0; j--)
            {
                temp[i][j] = pieceMatrix[j][i];
                if (temp[i][j] != null)
                {
                    temp[i][j].getHitBox().translate(i * this.size, j * this.size);
                }
            }
        }
        pieceMatrix = temp;
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
