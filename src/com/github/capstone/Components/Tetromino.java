package com.github.capstone.Components;

import com.github.capstone.Manager.AudioManager;
import com.github.capstone.Twotris;
import org.lwjgl.util.Color;
import org.lwjgl.util.Rectangle;

public class Tetromino
{
    public int speed;
    int size;
    private State state;
    private Type type;
    private TetrominoPiece[][] pieceMatrix;
    private Rectangle hitBox;
    private int rotation;
    private Color color;
    private Grid grid;

    /**
     * @param none
     * @return none
     * @throws none
     * @EntityTetromino This constructor method is used for setting the type, state, size, speed and color of the piece being made. It also initializes the rotation and keypress to zero.
     */
    Tetromino(Grid grid, int type)
    {
        this.grid = grid;
        this.size = grid.getGridSize();
        this.type = Type.values()[type];
        this.state = State.FALLING;
        this.speed = 1;
        this.pieceMatrix = generateFromType();
        this.moveToTop();
        this.rotation = 0;
        this.color = ColorPalette.getInstance().getColor(Twotris.getInstance().config.colorscheme, this.type.ordinal());
    }

    public Type getType()
    {
        return this.type;
    }

    /**
     * @moveToTop According to code guru: a lazy method to start all pieces to be ABOVE the grid, instead of within it
     */
    private void moveToTop()
    {
        this.hitBox.translate(0, -(this.hitBox.getHeight()));
        for (TetrominoPiece[] row : pieceMatrix)
        {
            for (TetrominoPiece col : row)
            {
                if (col != null)
                {
                    col.getHitBox().translate(0, -pieceMatrix.length * this.size);
                }
            }
        }
    }

    /**
     * @moveLeft move the piece (and sub-pieces) left by <code>size</code> amount
     */
    public void moveLeft()
    {
        this.hitBox.translate(-size, 0);
        for (TetrominoPiece[] row : pieceMatrix)
        {
            for (TetrominoPiece col : row)
            {
                if (col != null)
                {
                    col.getHitBox().translate(-size, 0);
                }
            }
        }
    }

    /**
     * @moveRight move the piece (and sub-pieces) right by <code>size</code> amount
     */
    public void moveRight()
    {
        this.hitBox.translate(size, 0);
        for (TetrominoPiece[] row : pieceMatrix)
        {
            for (TetrominoPiece col : row)
            {
                if (col != null)
                {
                    col.getHitBox().translate(size, 0);
                }
            }
        }
    }

    /**
     * @param none
     * @return TetrominoPiece
     * @throws none
     * @generateFromType This method is used to actually construct the pieces the tetromino uses, based on a variety of options.
     */

    private TetrominoPiece[][] generateFromType()
    {
        switch (this.type)
        {
            case L:
                this.hitBox = new Rectangle(this.grid.getXForCol(4), 0, 2 * size, 3 * size);
                return new TetrominoPiece[][]{
                        {new TetrominoPiece(this.grid.getXForCol(4), 0, size), null},
                        {new TetrominoPiece(this.grid.getXForCol(4), size, size), null},
                        {new TetrominoPiece(this.grid.getXForCol(4), 2 * size, size), new TetrominoPiece(this.grid.getXForCol(4) + size, 2 * size, size)}
                };
            case S:
                this.hitBox = new Rectangle(this.grid.getXForCol(4), 0, 2 * size, 3 * size);
                return new TetrominoPiece[][]{
                        {new TetrominoPiece(this.grid.getXForCol(4), 0, size), null, null},
                        {new TetrominoPiece(this.grid.getXForCol(4), size, size), new TetrominoPiece(this.grid.getXForCol(4) + size, size, size), null},
                        {null, new TetrominoPiece(this.grid.getXForCol(4) + size, 2 * size, size), null}
                };
            case J:
                this.hitBox = new Rectangle(this.grid.getXForCol(4), 0, 2 * size, 3 * size);
                return new TetrominoPiece[][]{
                        {null, new TetrominoPiece(this.grid.getXForCol(4) + size, 0, size)},
                        {null, new TetrominoPiece(this.grid.getXForCol(4) + size, size, size)},
                        {new TetrominoPiece(this.grid.getXForCol(4), 2 * size, size), new TetrominoPiece(this.grid.getXForCol(4) + size, 2 * size, size)}
                };
            case T:
                this.hitBox = new Rectangle(this.grid.getXForCol(4), 0, 3 * size, 2 * size);
                return new TetrominoPiece[][]{
                        {new TetrominoPiece(this.grid.getXForCol(4), 0, size), new TetrominoPiece(this.grid.getXForCol(4) + size, 0, size), new TetrominoPiece(this.grid.getXForCol(4) + (2 * size), 0, size)},
                        {null, new TetrominoPiece(this.grid.getXForCol(4) + size, size, size), null}
                };
            case O:
                this.hitBox = new Rectangle(this.grid.getXForCol(4), 0, 2 * size, 2 * size);
                return new TetrominoPiece[][]{
                        {new TetrominoPiece(this.grid.getXForCol(4), 0, size), new TetrominoPiece(this.grid.getXForCol(4) + size, 0, size)},
                        {new TetrominoPiece(this.grid.getXForCol(4), size, size), new TetrominoPiece(this.grid.getXForCol(4) + size, size, size)}
                };
            case I:
                this.hitBox = new Rectangle(this.grid.getXForCol(4), 0, 4 * size, size);
                return new TetrominoPiece[][]{
                        {new TetrominoPiece(this.grid.getXForCol(4), 0, size), new TetrominoPiece(this.grid.getXForCol(4) + size, 0, size), new TetrominoPiece(this.grid.getXForCol(4) + (2 * size), 0, size), new TetrominoPiece(this.grid.getXForCol(4) + (3 * size), 0, size)}
                };
            case Z:
                this.hitBox = new Rectangle(this.grid.getXForCol(4), 0, 2 * size, 3 * size);
                return new TetrominoPiece[][]{
                        {null, new TetrominoPiece(this.grid.getXForCol(4) + size, 0, size)},
                        {new TetrominoPiece(this.grid.getXForCol(4), size, size), new TetrominoPiece(this.grid.getXForCol(4) + size, size, size)},
                        {new TetrominoPiece(this.grid.getXForCol(4), 2 * size, size), null}
                };
        }
        return new TetrominoPiece[][]{};
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
            for (TetrominoPiece[] column : pieceMatrix)
            {
                for (TetrominoPiece row : column)
                {
                    if (row != null)
                    {
                        row.getHitBox().translate(0, this.speed);
                    }
                }
            }

            if (this.getHitBox().getY() + this.getHitBox().getHeight() >= grid.getHeight())
            {
                this.getHitBox().setY(grid.getHeight() - this.getHitBox().getHeight());

                // Correct individual pieces too..
                int offset = 0;
                for (TetrominoPiece[] column : pieceMatrix)
                {
                    for (TetrominoPiece row : column)
                    {
                        if (row != null)
                        {
                            if (row.getHitBox().getY() + row.getHitBox().getHeight() > grid.getHeight())
                            {
                                offset = Math.abs((row.getHitBox().getY() + row.getHitBox().getHeight()) - grid.getHeight());
                                break;
                            }
                        }
                    }
                }

                for (TetrominoPiece[] column : pieceMatrix)
                {
                    for (TetrominoPiece row : column)
                    {
                        if (row != null)
                        {
                            row.getHitBox().translate(0, -offset);
                        }
                    }
                }

                AudioManager.getInstance().play("place");
                this.state = State.IDLE;
            }
            this.getHitBox().translate(0, this.speed);
        }
    }

    /**
     * @param none
     * @return pieceMatrix The piece matrix in question.
     * @throws none
     * @getPieceMatrix This method retrieves the piece matrix.
     */
    public TetrominoPiece[][] getPieceMatrix()
    {
        return this.pieceMatrix;
    }

    /**
     * @param none
     * @return state the state of the piece.
     * @throws none
     * @getState This method retrieves the state of the piece.
     */
    public State getState()
    {
        return this.state;
    }

    /**
     * @param state the new state to set the piece to
     * @return none
     * @throws none
     * @setState This method sets the state of the piece in question
     */
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
        for (TetrominoPiece[] column : pieceMatrix)
        {
            for (TetrominoPiece row : column)
            {
                if (row != null)
                {
                    row.draw(this.color, 1F);
                }
            }
        }
    }

    /**
     * @param direc either 1 or -1 for rotating one of two ways
     * @return none
     * @throws none
     * @rotate This method is used for rotating the piece, based on 0, 90, 180, and 270 degrees.  Each of the individual shapes is accorded a
     * different case, based on the available positions that particular piece is able to have. Also, rotates the hitbox to accurately
     * receive collision instances.
     */
    public void rotate(short direc)
    {
        int newHeight = this.hitBox.getWidth();
        this.hitBox.setWidth(this.hitBox.getHeight());
        this.hitBox.setHeight(newHeight);

        this.rotation += direc;
        if (this.rotation == 4)
        {
            this.rotation = 0;
        }
        if (this.rotation == -1)
        {
            this.rotation = 3;
        }
        // Rotate the piece matrix:
        TetrominoPiece[][] temp = new TetrominoPiece[this.pieceMatrix[0].length][this.pieceMatrix.length];
        for (int i = 0; i < this.pieceMatrix[0].length; i++)
        {
            for (int j = 0; j < this.pieceMatrix.length; j++)
            {
                if(direc == 1) {
                    if (this.pieceMatrix[this.pieceMatrix.length - 1 - j][i] != null)
                    {
                        temp[i][j] = new TetrominoPiece(this.hitBox.getX() + (j * this.size), this.getHitBox().getY() + (i * this.size), this.size);
                    }
                    else
                    {
                        temp[i][j] = null;
                    }
                } else {
                    if (this.pieceMatrix[j][this.pieceMatrix[0].length - 1 - i] != null)
                    {
                        temp[i][j] = new TetrominoPiece(this.hitBox.getX() + (j * this.size), this.getHitBox().getY() + (i * this.size), this.size);
                    }
                    else
                    {
                        temp[i][j] = null;
                    }
                }
            }
        }
        this.pieceMatrix = temp;
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
     * @return color
     * @throws none
     * @getColor This method is used for getting the color the tetromino piece possesses.
     */
    public Color getColor()
    {
        return this.color;
    }

    public void recolor()
    {
        this.color = ColorPalette.getInstance().getColor(Twotris.getInstance().config.colorscheme, this.type.ordinal());
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
        L(3),
        S(4),
        J(3),
        T(2),
        O(1),
        I(1),
        Z(4);

        int scoreValue;

        Type(int scoreVal)
        {
            this.scoreValue = scoreVal;
        }
    }
}
