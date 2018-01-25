package com.github.capstone.Entity;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.Rectangle;

import java.util.Random;

public class EntityTetromino extends EntityBase
{
    private Type type;
    private State state;
    private EntityPiece[][] pieceMatrix;
    private Rectangle hitBox;

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
        int size = 32;

        switch (this.type)
        {
            case L:
                this.hitBox = new Rectangle(0, 0, 2 * size, 3 * size);
                return new EntityPiece[][]{
                        {new EntityPiece(0, 0, size), null},
                        {new EntityPiece(0, size, size), null},
                        {new EntityPiece(0, 2 * size, size), new EntityPiece(size, 2 * size, size)}
                };
            case S:
                this.hitBox = new Rectangle(0, 0, 2 * size, 3 * size);
                return new EntityPiece[][]{
                        {new EntityPiece(0, 0, size), null, null},
                        {new EntityPiece(0, size, size), new EntityPiece(size, size, size), null},
                        {null, new EntityPiece(size, 2 * size, size), null}
                };
            case J:
                this.hitBox = new Rectangle(0, 0, 2 * size, 3 * size);
                return new EntityPiece[][]{
                        {null, new EntityPiece(size, 0, size)},
                        {null, new EntityPiece(size, size, size)},
                        {new EntityPiece(0, 2 * size, size), new EntityPiece(size, 2 * size, size)}
                };
            case T:
                this.hitBox = new Rectangle(0, 0, 3 * size, 2 * size);
                return new EntityPiece[][]{
                        {new EntityPiece(0, 0, size), new EntityPiece(size, 0, size), new EntityPiece(2 * size, 0, size)},
                        {null, new EntityPiece(size, size, size), null}
                };
            case O:
                this.hitBox = new Rectangle(0, 0, 2 * size, 2 * size);
                return new EntityPiece[][]{
                        {new EntityPiece(0, 0, size), new EntityPiece(size, 0, size)},
                        {new EntityPiece(0, size, size), new EntityPiece(size, size, size)}
                };
            case I:
                this.hitBox = new Rectangle(0, 0, 4 * size, size);
                return new EntityPiece[][]{
                        {new EntityPiece(0, 0, size), new EntityPiece(size, 0, size), new EntityPiece(2 * size, 0, size), new EntityPiece(3 * size, 0, size)}
                };
            case Z:
                this.hitBox = new Rectangle(0, 0, 2 * size, 3 * size);
                return new EntityPiece[][]{
                        {null, new EntityPiece(size, 0, size)},
                        {new EntityPiece(0, size, size), new EntityPiece(size, size, size)},
                        {new EntityPiece(0, 2 * size, size), null}
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

    public void rotate()
    {
        // TODO: Rotate the pieceMatrix clockwise...
        // TODO: This should not only update the pieceMatrix, but also the hitboxes of the individual pieces
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
