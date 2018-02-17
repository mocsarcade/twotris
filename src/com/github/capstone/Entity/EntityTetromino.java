package com.github.capstone.Entity;

import com.github.capstone.Util.Palette;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Color;
import org.lwjgl.util.Rectangle;

import java.util.Random;

public class EntityTetromino extends EntityBase
{
    private int size;
    public int speed;
    private Type type;
    private State state;
    private EntityPiece[][] pieceMatrix;
    private Rectangle hitBox;
    private int rotation;
    private Color color;
    private int center;

    /**
     * @param none
     * @return none
     * @throws none
     * @EntityTetromino This constructor method is used for setting the type, state, size, speed and color of the piece being made. It also initializes the rotation and keypress to zero.
     */
    public EntityTetromino(int size, int center)
    {
        this.size = size;
        this.center = center;
        this.type = randomType();
        this.state = State.FALLING;
        this.speed = 1;
        this.pieceMatrix = generateFromType();
        this.rotation = 0;
        this.color = Palette.values()[type.index].color;
    }

    public void moveLeft()
    {
        this.hitBox.translate(-size, 0);
        for (EntityPiece[] row : pieceMatrix)
        {
            for (EntityPiece col : row)
            {
                if (col != null)
                {
                    col.getHitBox().translate(-size, 0);
                }
            }
        }
    }

    public void moveRight()
    {
        this.hitBox.translate(size, 0);
        for (EntityPiece[] row : pieceMatrix)
        {
            for (EntityPiece col : row)
            {
                if (col != null)
                {
                    col.getHitBox().translate(size, 0);
                }
            }
        }
    }

    /**
     * @param newSize The new size of each piece of the tetronimo
     * @updateSize updates the size of each piece
     */
    public void updateSize(int newSize)
    {
        this.size = newSize;

        // rotate 4 times to automatically update the render
        for (int i = 0; i < 4; i++)
        {
            this.rotate();
        }
    }

    /**
     * @param none
     * @return EntityPiece
     * @throws none
     * @generateFromType This method is used to actually construct the pieces the tetromino uses, based on a variety of options.
     */

    private EntityPiece[][] generateFromType()
    {
        switch (this.type)
        {
            case L:
                this.hitBox = new Rectangle(Display.getHeight() / 2, 0, 2 * size, 3 * size);
                return new EntityPiece[][]{
                        {new EntityPiece(this.center, 0, size), null},
                        {new EntityPiece(this.center, size, size), null},
                        {new EntityPiece(this.center, 2 * size, size), new EntityPiece(this.center + size, 2 * size, size)}
                };
            case S:
                this.hitBox = new Rectangle(Display.getHeight() / 2, 0, 2 * size, 3 * size);
                return new EntityPiece[][]{
                        {new EntityPiece(this.center, 0, size), null, null},
                        {new EntityPiece(this.center, size, size), new EntityPiece(this.center + size, size, size), null},
                        {null, new EntityPiece(this.center + size, 2 * size, size), null}
                };
            case J:
                this.hitBox = new Rectangle(Display.getHeight() / 2, 0, 2 * size, 3 * size);
                return new EntityPiece[][]{
                        {null, new EntityPiece(this.center + size, 0, size)},
                        {null, new EntityPiece(this.center + size, size, size)},
                        {new EntityPiece(this.center, 2 * size, size), new EntityPiece(this.center + size, 2 * size, size)}
                };
            case T:
                this.hitBox = new Rectangle(Display.getHeight() / 2, 0, 3 * size, 2 * size);
                return new EntityPiece[][]{
                        {new EntityPiece(this.center, 0, size), new EntityPiece(this.center + size, 0, size), new EntityPiece(this.center + (2 * size), 0, size)},
                        {null, new EntityPiece(this.center + size, size, size), null}
                };
            case O:
                this.hitBox = new Rectangle(Display.getHeight() / 2, 0, 2 * size, 2 * size);
                return new EntityPiece[][]{
                        {new EntityPiece(this.center, 0, size), new EntityPiece(this.center + size, 0, size)},
                        {new EntityPiece(this.center, size, size), new EntityPiece(this.center + size, size, size)}
                };
            case I:
                this.hitBox = new Rectangle(Display.getHeight() / 2, 0, 4 * size, size);
                return new EntityPiece[][]{
                        {new EntityPiece(this.center, 0, size), new EntityPiece(this.center + size, 0, size), new EntityPiece(this.center + (2 * size), 0, size), new EntityPiece(this.center + (3 * size), 0, size)}
                };
            case Z:
                this.hitBox = new Rectangle(Display.getHeight() / 2, 0, 2 * size, 3 * size);
                return new EntityPiece[][]{
                        {null, new EntityPiece(this.center + size, 0, size)},
                        {new EntityPiece(this.center, size, size), new EntityPiece(this.center + size, size, size)},
                        {new EntityPiece(this.center, 2 * size, size), null}
                };
        }
        return new EntityPiece[][]{};
    }

    /**
     * @param delta
     * @return none
     * @throws none
     * @update This method is used for updating the piece’s position and rotation, using the state, x/y, height, and hitbox. This method also allows for the player to speed up the fall, and correct each individual piece accordingly.
     */

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
                        row.getHitBox().translate(0, this.speed);
                        row.update(delta);
                    }
                }
            }

            if (this.getHitBox().getY() + this.getHitBox().getHeight() >= Display.getHeight())
            {
                this.getHitBox().setY(Display.getHeight() - this.getHitBox().getHeight());

                // Correct individual pieces too..
                int offset = 0;
                for (EntityPiece[] column : pieceMatrix)
                {
                    for (EntityPiece row : column)
                    {
                        if (row != null)
                        {
                            if (row.getHitBox().getY() + row.getHitBox().getHeight() > Display.getHeight())
                            {
                                offset = Math.abs((row.getHitBox().getY() + row.getHitBox().getHeight()) - Display.getHeight());
                                break;
                            }
                        }
                    }
                }

                for (EntityPiece[] column : pieceMatrix)
                {
                    for (EntityPiece row : column)
                    {
                        if (row != null)
                        {
                            row.getHitBox().translate(0, -offset);
                        }
                    }
                }

                this.state = State.IDLE;
            }
            this.getHitBox().translate(0, this.speed);
        }
        else
        {
            if (Display.wasResized())
            {

            }
        }
    }

    public EntityPiece[][] getPieceMatrix()
    {
        return this.pieceMatrix;
    }

    public State getState()
    {
        return this.state;
    }

    public void setState(State state)
    {
        this.state = state;
    }

    /**
     * @param none
     * @return none
     * @throws none
     * @draw This method is used for drawing the tetromino piece, setting the color, and size/shape.
     */
    public void draw()
    {
        for (EntityPiece[] column : pieceMatrix)
        {
            for (EntityPiece row : column)
            {
                if (row != null)
                {
                    row.draw(this.color);
                }
            }
        }
    }

    /**
     * @param none
     * @return none
     * @throws none
     * @rotate This method is used for rotating the piece, based on 0, 90, 180, and 270 degrees.  Each of the individual shapes is accorded a different case, based on the available positions that particular piece is able to have. Also, rotates the hitbox to accurately receive collision instances.
     */
    public void rotate()
    {
        switch (this.type)
        {
            case L:
                if (this.rotation == 0)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), size), new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY(), size), new EntityPiece(this.getHitBox().getX() + (2 * size), this.getHitBox().getY(), size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + size, size), null, null}
                    };
                    break;
                }
                else if (this.rotation == 90)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), size), new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY(), size)},
                            {null, new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY() + size, size)},
                            {null, new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY() + (2 * size), size)}
                    };
                    break;
                }
                else if (this.rotation == 180)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {null, null, new EntityPiece(this.getHitBox().getX() + (size * 2), this.getHitBox().getY(), size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + size, size), new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY() + size, size), new EntityPiece(this.getHitBox().getX() + (2 * size), this.getHitBox().getY() + size, size)}
                    };
                    break;
                }
                else if (this.rotation == 270)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), size), null},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + size, size), null},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + (2 * size), size), new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY() + (2 * size), size)}
                    };
                    break;
                }
            case S:
                if (this.rotation == 0 || this.rotation == 180)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {null, new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY(), size), new EntityPiece(this.getHitBox().getX() + (2 * size), this.getHitBox().getY(), size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + size, size), new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY() + size, size), null}
                    };
                    break;
                }
                else if (this.rotation == 90 || this.rotation == 270)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), size), null, null},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + size, size), new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY() + size, size), null},
                            {null, new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY() + (2 * size), size), null}
                    };
                    break;
                }
            case J:
                if (this.rotation == 0)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), size), null, null},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + size, size), new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY() + size, size), new EntityPiece(this.getHitBox().getX() + (2 * size), this.getHitBox().getY() + size, size)}
                    };
                    break;
                }
                else if (this.rotation == 90)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), size), new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY(), size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + size, size), null},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + (2 * size), size), null}
                    };
                    break;
                }
                else if (this.rotation == 180)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), size), new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY(), size), new EntityPiece(this.getHitBox().getX() + (2 * size), this.getHitBox().getY(), size)},
                            {null, null, new EntityPiece(this.getHitBox().getX() + (2 * size), this.getHitBox().getY() + size, size)}
                    };
                    break;
                }
                else if (this.rotation == 270)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {null, new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY(), size)},
                            {null, new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY() + size, size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + (2 * size), size), new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY() + (2 * size), size)}
                    };
                    break;
                }
            case T:
                if (this.rotation == 0)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {null, new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY(), size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + size, size), new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY() + size, size)},
                            {null, new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY() + (2 * size), size)}
                    };
                    break;
                }
                else if (this.rotation == 90)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {null, new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY(), size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + size, size), new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY() + size, size), new EntityPiece(this.getHitBox().getX() + (2 * size), this.getHitBox().getY() + size, size)}
                    };
                    break;
                }
                else if (this.rotation == 180)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), size), null},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + size, size), new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY() + size, size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + (2 * size), size), null}
                    };
                    break;
                }
                else if (this.rotation == 270)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), size), new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY(), size), new EntityPiece(this.getHitBox().getX() + (2 * size), this.getHitBox().getY(), size)},
                            {null, new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY() + size, size), null}
                    };
                    break;
                }
            case O:
                break;
            case I:
                if (this.rotation == 0 || this.rotation == 180)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + size, size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + (2 * size), size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + (3 * size), size)}
                    };
                    break;
                }
                else if (this.rotation == 90 || this.rotation == 270)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), size), new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY(), size), new EntityPiece(this.getHitBox().getX() + (2 * size), this.getHitBox().getY(), size), new EntityPiece(this.getHitBox().getX() + (3 * size), this.getHitBox().getY(), size)}
                    };
                    break;
                }
            case Z:
                if (this.rotation == 0 || this.rotation == 180)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), size), new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY(), size), null},
                            {null, new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY() + size, size), new EntityPiece(this.getHitBox().getX() + (2 * size), this.getHitBox().getY() + size, size)}
                    };
                    break;
                }
                else if (this.rotation == 90 || this.rotation == 270)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {null, new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY(), size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + size, size), new EntityPiece(this.getHitBox().getX() + size, this.getHitBox().getY() + size, size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + (2 * size), size), null}
                    };
                    break;
                }
        }

        // Increment rotation by 90 degrees unless it's going to be more than 360deg
        this.rotation = this.rotation + 90 >= 360 ? 0 : this.rotation + 90;

        // Rotate the hitbox too:
        int w = this.hitBox.getWidth();
        this.hitBox.setWidth(this.hitBox.getHeight());
        this.hitBox.setHeight(w);
    }

    /**
     * @param none
     * @return hitbox
     * @throws none
     * @getHitBox This method is used for getting the hitbox the tetromino piece possesses.
     */
    public Rectangle getHitBox()
    {
        return this.hitBox;
    }

    /**
     * @param none
     * @return The type of piece found at the location in the array at the index generated.
     * @throws none
     * @randomType This method is used for selecting a type for the tetromino. Uses random numbers to select which tetromino piece is next to be selected.
     */
    private Type randomType()
    {
        Random rand = new Random();
        int rng = rand.nextInt(Type.values().length);
        return Type.values()[rng];
    }

    /**
     * @param none
     * @state This offers two options, falling or idle.
     * @return none
     * @throws none
     */
    public enum State
    {
        FALLING, IDLE
    }

    /**
     * @param none
     * @type This method carries the various types of tetromino pieces available, with a private index variable to find the index of the piece given an integer.
     * @return none
     * @throws none
     */
    public enum Type
    {
        L(0),
        S(1),
        J(2),
        T(3),
        O(4),
        I(5),
        Z(6);

        private int index;

        Type(int i)
        {
            this.index = i;
        }
    }
}
