package com.github.capstone.Entity;

import com.github.capstone.Util.Helper;
import com.github.capstone.Util.Pallete;
import org.lwjgl.input.Keyboard;
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
    private int size;
    private int speed;
    private int rotation;
    private Color color;
    private long lastKeypress;
	/**
		@EntityTetromino
		This constructor method is used for setting the type, state, size, speed and color of the piece being made. It also initializes the rotation and keypress to zero. 
		@param none
		@return none
		@throws none
	*/
    public EntityTetromino()
    {
        this.type = randomType();
        this.state = State.FALLING;
        this.size = 32;
        this.speed = 1;
        this.pieceMatrix = generateFromType();
        this.rotation = 0;
        this.lastKeypress = 0;
        this.color = Pallete.values()[type.index].color;
    }
	/**
		@generateFromType
		This method is used to actually construct the pieces the tetromino uses, based on a variety of options. 
		@param none
		@return EntityPiece
		@throws none
	*/

    private EntityPiece[][] generateFromType()
    {
        switch (this.type)
        {
            case L:
                this.hitBox = new Rectangle(Display.getHeight() / 2, 0, 2 * this.size, 3 * this.size);
                return new EntityPiece[][]{
                        {new EntityPiece((Display.getHeight() / 2), 0, this.size), null},
                        {new EntityPiece((Display.getHeight() / 2), this.size, this.size), null},
                        {new EntityPiece((Display.getHeight() / 2), 2 * this.size, this.size), new EntityPiece((Display.getHeight() / 2) + this.size, 2 * this.size, this.size)}
                };
            case S:
                this.hitBox = new Rectangle(Display.getHeight() / 2, 0, 2 * this.size, 3 * this.size);
                return new EntityPiece[][]{
                        {new EntityPiece((Display.getHeight() / 2), 0, this.size), null, null},
                        {new EntityPiece((Display.getHeight() / 2), this.size, this.size), new EntityPiece((Display.getHeight() / 2) + this.size, this.size, this.size), null},
                        {null, new EntityPiece((Display.getHeight() / 2) + this.size, 2 * this.size, this.size), null}
                };
            case J:
                this.hitBox = new Rectangle(Display.getHeight() / 2, 0, 2 * this.size, 3 * this.size);
                return new EntityPiece[][]{
                        {null, new EntityPiece((Display.getHeight() / 2) + this.size, 0, this.size)},
                        {null, new EntityPiece((Display.getHeight() / 2) + this.size, this.size, this.size)},
                        {new EntityPiece((Display.getHeight() / 2), 2 * this.size, this.size), new EntityPiece((Display.getHeight() / 2) + this.size, 2 * this.size, this.size)}
                };
            case T:
                this.hitBox = new Rectangle(Display.getHeight() / 2, 0, 3 * this.size, 2 * this.size);
                return new EntityPiece[][]{
                        {new EntityPiece((Display.getHeight() / 2), 0, this.size), new EntityPiece((Display.getHeight() / 2) + this.size, 0, this.size), new EntityPiece((Display.getHeight() / 2) + (2 * this.size), 0, this.size)},
                        {null, new EntityPiece((Display.getHeight() / 2) + this.size, this.size, this.size), null}
                };
            case O:
                this.hitBox = new Rectangle(Display.getHeight() / 2, 0, 2 * this.size, 2 * this.size);
                return new EntityPiece[][]{
                        {new EntityPiece((Display.getHeight() / 2), 0, this.size), new EntityPiece((Display.getHeight() / 2) + this.size, 0, this.size)},
                        {new EntityPiece((Display.getHeight() / 2), this.size, this.size), new EntityPiece((Display.getHeight() / 2) + this.size, this.size, this.size)}
                };
            case I:
                this.hitBox = new Rectangle(Display.getHeight() / 2, 0, 4 * this.size, this.size);
                return new EntityPiece[][]{
                        {new EntityPiece((Display.getHeight() / 2), 0, this.size), new EntityPiece((Display.getHeight() / 2) + this.size, 0, this.size), new EntityPiece((Display.getHeight() / 2) + (2 * this.size), 0, this.size), new EntityPiece((Display.getHeight() / 2) + (3 * this.size), 0, this.size)}
                };
            case Z:
                this.hitBox = new Rectangle(Display.getHeight() / 2, 0, 2 * this.size, 3 * this.size);
                return new EntityPiece[][]{
                        {null, new EntityPiece((Display.getHeight() / 2) + this.size, 0, this.size)},
                        {new EntityPiece((Display.getHeight() / 2), this.size, this.size), new EntityPiece((Display.getHeight() / 2) + this.size, this.size, this.size)},
                        {new EntityPiece((Display.getHeight() / 2), 2 * this.size, this.size), null}
                };
        }
        return new EntityPiece[][]{};
    }
	/**
		@update
		This method is used for updating the piece’s position and rotation, using the state, x/y, height, and hitbox. This method also allows for the player to speed up the fall, and correct each individual piece accordingly.  
		@param delta
		@return none
		@throws none
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
            if (this.hitBox.getY() + this.getHitBox().getHeight() < Display.getHeight())
            {
                if (Helper.getTime() - lastKeypress > 250)
                {
                    if (Keyboard.isKeyDown(Keyboard.KEY_R))
                    {
                        rotate();
                        lastKeypress = Helper.getTime();
                    }
                    if (Keyboard.isKeyDown(Keyboard.KEY_A))
                    {
                        lastKeypress = Helper.getTime();
                        this.getHitBox().translate(-size, 0);
                        for (EntityPiece[] column : pieceMatrix)
                        {
                            for (EntityPiece row : column)
                            {
                                if (row != null)
                                {
                                    row.getHitBox().translate(-size, 0);
                                }
                            }
                        }
                    }
                    else if (Keyboard.isKeyDown(Keyboard.KEY_D))
                    {
                        lastKeypress = Helper.getTime();
                        this.getHitBox().translate(size, 0);
                        for (EntityPiece[] column : pieceMatrix)
                        {
                            for (EntityPiece row : column)
                            {
                                if (row != null)
                                {
                                    row.getHitBox().translate(size, 0);
                                }
                            }
                        }
                    }
                }

                // Let the acceleration happen anytime, not just every 500ms
                if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
                {
                    this.speed = 2;
                }
                else
                {
                    this.speed = 1;
                }
            }
            else
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
	/**
		@draw
		This method is used for drawing the tetromino piece, setting the color, and size/shape. 
		@param none
		@return none
		@throws none
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
		@rotate
		This method is used for rotating the piece, based on 0, 90, 180, and 270 degrees.  Each of the individual shapes is accorded a different case, based on the available positions that particular piece is able to have. Also, rotates the hitbox to accurately receive collision instances. 
		@param none
		@return none
		@throws none
	*/
    private void rotate()
    {
        switch (this.type)
        {
            case L:
                if (this.rotation == 0)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), this.size), new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY(), this.size), new EntityPiece(this.getHitBox().getX() + (2 * this.size), this.getHitBox().getY(), this.size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + this.size, this.size), null, null}
                    };
                    break;
                }
                else if (this.rotation == 90)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), this.size), new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY(), this.size)},
                            {null, new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY() + this.size, this.size)},
                            {null, new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY() + (2 * this.size), this.size)}
                    };
                    break;
                }
                else if (this.rotation == 180)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {null, null, new EntityPiece(this.getHitBox().getX() + (this.size * 2), this.getHitBox().getY(), this.size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + this.size, this.size), new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY() + this.size, this.size), new EntityPiece(this.getHitBox().getX() + (2 * this.size), this.getHitBox().getY() + this.size, this.size)}
                    };
                    break;
                }
                else if (this.rotation == 270)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), this.size), null},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + this.size, this.size), null},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + (2 * this.size), this.size), new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY() + (2 * this.size), this.size)}
                    };
                    break;
                }
            case S:
                if (this.rotation == 0 || this.rotation == 180)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {null, new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY(), this.size), new EntityPiece(this.getHitBox().getX() + (2 * this.size), this.getHitBox().getY(), this.size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + this.size, this.size), new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY() + this.size, this.size), null}
                    };
                    break;
                }
                else if (this.rotation == 90 || this.rotation == 270)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), this.size), null, null},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + this.size, this.size), new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY() + this.size, this.size), null},
                            {null, new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY() + (2 * this.size), this.size), null}
                    };
                    break;
                }
            case J:
                if (this.rotation == 0)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), this.size), null, null},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + this.size, this.size), new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY() + this.size, this.size), new EntityPiece(this.getHitBox().getX() + (2 * this.size), this.getHitBox().getY() + this.size, this.size)}
                    };
                    break;
                }
                else if (this.rotation == 90)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), this.size), new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY(), this.size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + this.size, this.size), null},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + (2 * this.size), this.size), null}
                    };
                    break;
                }
                else if (this.rotation == 180)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), this.size), new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY(), this.size), new EntityPiece(this.getHitBox().getX() + (2 * this.size), this.getHitBox().getY(), this.size)},
                            {null, null, new EntityPiece(this.getHitBox().getX() + (2 * this.size), this.getHitBox().getY() + this.size, this.size)}
                    };
                    break;
                }
                else if (this.rotation == 270)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {null, new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY(), this.size)},
                            {null, new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY() + this.size, this.size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + (2 * this.size), this.size), new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY() + (2 * this.size), this.size)}
                    };
                    break;
                }
            case T:
                if (this.rotation == 0)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {null, new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY(), this.size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + this.size, this.size), new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY() + this.size, this.size)},
                            {null, new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY() + (2 * this.size), this.size)}
                    };
                    break;
                }
                else if (this.rotation == 90)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {null, new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY(), this.size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + this.size, this.size), new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY() + this.size, this.size), new EntityPiece(this.getHitBox().getX() + (2 * this.size), this.getHitBox().getY() + this.size, this.size)}
                    };
                    break;
                }
                else if (this.rotation == 180)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), this.size), null},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + this.size, this.size), new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY() + this.size, this.size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + (2 * this.size), this.size), null}
                    };
                    break;
                }
                else if (this.rotation == 270)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), this.size), new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY(), this.size), new EntityPiece(this.getHitBox().getX() + (2 * this.size), this.getHitBox().getY(), this.size)},
                            {null, new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY() + this.size, this.size), null}
                    };
                    break;
                }
            case O:
                break;
            case I:
                if (this.rotation == 0 || this.rotation == 180)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), this.size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + this.size, this.size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + (2 * this.size), this.size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + (3 * this.size), this.size)}
                    };
                    break;
                }
                else if (this.rotation == 90 || this.rotation == 270)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), this.size), new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY(), this.size), new EntityPiece(this.getHitBox().getX() + (2 * this.size), this.getHitBox().getY(), this.size), new EntityPiece(this.getHitBox().getX() + (3 * this.size), this.getHitBox().getY(), this.size)}
                    };
                    break;
                }
            case Z:
                if (this.rotation == 0 || this.rotation == 180)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY(), this.size), new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY(), this.size), null},
                            {null, new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY() + this.size, this.size), new EntityPiece(this.getHitBox().getX() + (2 * this.size), this.getHitBox().getY() + this.size, this.size)}
                    };
                    break;
                }
                else if (this.rotation == 90 || this.rotation == 270)
                {
                    this.pieceMatrix = new EntityPiece[][]{
                            {null, new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY(), this.size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + this.size, this.size), new EntityPiece(this.getHitBox().getX() + this.size, this.getHitBox().getY() + this.size, this.size)},
                            {new EntityPiece(this.getHitBox().getX(), this.getHitBox().getY() + (2 * this.size), this.size), null}
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
		@getHitBox
		This method is used for getting the hitbox the tetromino piece possesses. 
		@param none
		@return hitbox
		@throws none
	*/
    public Rectangle getHitBox()
    {
        return this.hitBox;
    }
	/**
		@randomType
		This method is used for selecting a type for the tetromino. Uses random numbers to select which tetromino piece is next to be selected. 
		@param none
		@return The type of piece found at the location in the array at the index generated. 
		@throws none
	*/
    private Type randomType()
    {
        Random rand = new Random();
        int rng = rand.nextInt(Type.values().length);
        return Type.values()[rng];
    }
	/**
		@state
		This offers two options, falling or idle. 
		@param none
		@return none
		@throws none
	*/
    public enum State
    {
        FALLING, IDLE
    }
	/**
		@type
		This method carries the various types of tetromino pieces available, with a private index variable to find the index of the piece given an integer. 
		@param none
		@return none
		@throws none
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
