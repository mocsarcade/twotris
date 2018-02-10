package com.github.capstone.Entity;

import com.github.capstone.Util.Pallete;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Color;
import org.lwjgl.util.Rectangle;

import java.util.Random;

public class EntityTetromino extends EntityBase
{
    private Type type;
    private State state;
    private EntityPiece[][] pieceMatrix;
    private Rectangle hitBox;
    public static final int SIZE = 32;
    public int speed;
    private int rotation;
    private Color color;
    private long lastKeypress;

    /**
     * @param none
     * @return none
     * @throws none
     * @EntityTetromino This constructor method is used for setting the type, state, SIZE, speed and color of the piece being made. It also initializes the rotation and keypress to zero.
     */
    public EntityTetromino()
    {
        this.type = randomType();
        this.state = State.FALLING;
        this.speed = 1;
        this.pieceMatrix = generateFromType();
        this.rotation = 0;
        this.lastKeypress = 0;
        this.color = Pallete.values()[type.index].color;
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
                this.hitBox = new Rectangle(Display.getHeight() / 2, 0, 2 * SIZE, 3 * SIZE);
                return new EntityPiece[][]{
                        {new EntityPiece((Display.getHeight() / 2), 0, SIZE), null},
                        {new EntityPiece((Display.getHeight() / 2), SIZE, SIZE), null},
                        {new EntityPiece((Display.getHeight() / 2), 2 * SIZE, SIZE), new EntityPiece((Display.getHeight() / 2) + SIZE, 2 * SIZE, SIZE)}
                };
            case S:
                this.hitBox = new Rectangle(Display.getHeight() / 2, 0, 2 * SIZE, 3 * SIZE);
                return new EntityPiece[][]{
                        {new EntityPiece((Display.getHeight() / 2), 0, SIZE), null, null},
                        {new EntityPiece((Display.getHeight() / 2), SIZE, SIZE), new EntityPiece((Display.getHeight() / 2) + SIZE, SIZE, SIZE), null},
                        {null, new EntityPiece((Display.getHeight() / 2) + SIZE, 2 * SIZE, SIZE), null}
                };
            case J:
                this.hitBox = new Rectangle(Display.getHeight() / 2, 0, 2 * SIZE, 3 * SIZE);
                return new EntityPiece[][]{
                        {null, new EntityPiece((Display.getHeight() / 2) + SIZE, 0, SIZE)},
                        {null, new EntityPiece((Display.getHeight() / 2) + SIZE, SIZE, SIZE)},
                        {new EntityPiece((Display.getHeight() / 2), 2 * SIZE, SIZE), new EntityPiece((Display.getHeight() / 2) + SIZE, 2 * SIZE, SIZE)}
                };
            case T:
                this.hitBox = new Rectangle(Display.getHeight() / 2, 0, 3 * SIZE, 2 * SIZE);
                return new EntityPiece[][]{
                        {new EntityPiece((Display.getHeight() / 2), 0, SIZE), new EntityPiece((Display.getHeight() / 2) + SIZE, 0, SIZE), new EntityPiece((Display.getHeight() / 2) + (2 * SIZE), 0, SIZE)},
                        {null, new EntityPiece((Display.getHeight() / 2) + SIZE, SIZE, SIZE), null}
                };
            case O:
                this.hitBox = new Rectangle(Display.getHeight() / 2, 0, 2 * SIZE, 2 * SIZE);
                return new EntityPiece[][]{
                        {new EntityPiece((Display.getHeight() / 2), 0, SIZE), new EntityPiece((Display.getHeight() / 2) + SIZE, 0, SIZE)},
                        {new EntityPiece((Display.getHeight() / 2), SIZE, SIZE), new EntityPiece((Display.getHeight() / 2) + SIZE, SIZE, SIZE)}
                };
            case I:
                this.hitBox = new Rectangle(Display.getHeight() / 2, 0, 4 * SIZE, SIZE);
                return new EntityPiece[][]{
                        {new EntityPiece((Display.getHeight() / 2), 0, SIZE), new EntityPiece((Display.getHeight() / 2) + SIZE, 0, SIZE), new EntityPiece((Display.getHeight() / 2) + (2 * SIZE), 0, SIZE), new EntityPiece((Display.getHeight() / 2) + (3 * SIZE), 0, SIZE)}
                };
            case Z:
                this.hitBox = new Rectangle(Display.getHeight() / 2, 0, 2 * SIZE, 3 * SIZE);
                return new EntityPiece[][]{
                        {null, new EntityPiece((Display.getHeight() / 2) + SIZE, 0, SIZE)},
                        {new EntityPiece((Display.getHeight() / 2), SIZE, SIZE), new EntityPiece((Display.getHeight() / 2) + SIZE, SIZE, SIZE)},
                        {new EntityPiece((Display.getHeight() / 2), 2 * SIZE, SIZE), null}
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

            if(this.getHitBox().getY() + this.getHitBox().getHeight() >= Display.getHeight())
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
     * @draw This method is used for drawing the tetromino piece, setting the color, and SIZE/shape.
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
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), SIZE), new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY(), SIZE), new EntityPiece(this.getHitBox().getX() + (2 * SIZE), this.getHitBox().getY(), SIZE)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + SIZE, SIZE), null, null}
                    };
                    break;
                }
                else if (this.rotation == 90)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), SIZE), new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY(), SIZE)},
                            {null, new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY() + SIZE, SIZE)},
                            {null, new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY() + (2 * SIZE), SIZE)}
                    };
                    break;
                }
                else if (this.rotation == 180)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {null, null, new EntityPiece(this.getHitBox().getX() + (SIZE * 2), this.getHitBox().getY(), SIZE)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + SIZE, SIZE), new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY() + SIZE, SIZE), new EntityPiece(this.getHitBox().getX() + (2 * SIZE), this.getHitBox().getY() + SIZE, SIZE)}
                    };
                    break;
                }
                else if (this.rotation == 270)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), SIZE), null},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + SIZE, SIZE), null},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + (2 * SIZE), SIZE), new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY() + (2 * SIZE), SIZE)}
                    };
                    break;
                }
            case S:
                if (this.rotation == 0 || this.rotation == 180)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {null, new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY(), SIZE), new EntityPiece(this.getHitBox().getX() + (2 * SIZE), this.getHitBox().getY(), SIZE)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + SIZE, SIZE), new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY() + SIZE, SIZE), null}
                    };
                    break;
                }
                else if (this.rotation == 90 || this.rotation == 270)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), SIZE), null, null},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + SIZE, SIZE), new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY() + SIZE, SIZE), null},
                            {null, new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY() + (2 * SIZE), SIZE), null}
                    };
                    break;
                }
            case J:
                if (this.rotation == 0)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), SIZE), null, null},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + SIZE, SIZE), new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY() + SIZE, SIZE), new EntityPiece(this.getHitBox().getX() + (2 * SIZE), this.getHitBox().getY() + SIZE, SIZE)}
                    };
                    break;
                }
                else if (this.rotation == 90)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), SIZE), new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY(), SIZE)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + SIZE, SIZE), null},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + (2 * SIZE), SIZE), null}
                    };
                    break;
                }
                else if (this.rotation == 180)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), SIZE), new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY(), SIZE), new EntityPiece(this.getHitBox().getX() + (2 * SIZE), this.getHitBox().getY(), SIZE)},
                            {null, null, new EntityPiece(this.getHitBox().getX() + (2 * SIZE), this.getHitBox().getY() + SIZE, SIZE)}
                    };
                    break;
                }
                else if (this.rotation == 270)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {null, new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY(), SIZE)},
                            {null, new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY() + SIZE, SIZE)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + (2 * SIZE), SIZE), new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY() + (2 * SIZE), SIZE)}
                    };
                    break;
                }
            case T:
                if (this.rotation == 0)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {null, new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY(), SIZE)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + SIZE, SIZE), new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY() + SIZE, SIZE)},
                            {null, new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY() + (2 * SIZE), SIZE)}
                    };
                    break;
                }
                else if (this.rotation == 90)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {null, new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY(), SIZE)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + SIZE, SIZE), new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY() + SIZE, SIZE), new EntityPiece(this.getHitBox().getX() + (2 * SIZE), this.getHitBox().getY() + SIZE, SIZE)}
                    };
                    break;
                }
                else if (this.rotation == 180)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), SIZE), null},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + SIZE, SIZE), new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY() + SIZE, SIZE)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + (2 * SIZE), SIZE), null}
                    };
                    break;
                }
                else if (this.rotation == 270)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), SIZE), new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY(), SIZE), new EntityPiece(this.getHitBox().getX() + (2 * SIZE), this.getHitBox().getY(), SIZE)},
                            {null, new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY() + SIZE, SIZE), null}
                    };
                    break;
                }
            case O:
                break;
            case I:
                if (this.rotation == 0 || this.rotation == 180)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), SIZE)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + SIZE, SIZE)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + (2 * SIZE), SIZE)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + (3 * SIZE), SIZE)}
                    };
                    break;
                }
                else if (this.rotation == 90 || this.rotation == 270)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), SIZE), new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY(), SIZE), new EntityPiece(this.getHitBox().getX() + (2 * SIZE), this.getHitBox().getY(), SIZE), new EntityPiece(this.getHitBox().getX() + (3 * SIZE), this.getHitBox().getY(), SIZE)}
                    };
                    break;
                }
            case Z:
                if (this.rotation == 0 || this.rotation == 180)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), SIZE), new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY(), SIZE), null},
                            {null, new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY() + SIZE, SIZE), new EntityPiece(this.getHitBox().getX() + (2 * SIZE), this.getHitBox().getY() + SIZE, SIZE)}
                    };
                    break;
                }
                else if (this.rotation == 90 || this.rotation == 270)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {null, new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY(), SIZE)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + SIZE, SIZE), new EntityPiece(this.getHitBox().getX() + SIZE, this.getHitBox().getY() + SIZE, SIZE)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + (2 * SIZE), SIZE), null}
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
