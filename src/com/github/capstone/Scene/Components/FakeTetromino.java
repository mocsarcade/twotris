package com.github.capstone.Scene.Components;

import com.github.capstone.Components.ColorPalette;
import com.github.capstone.Components.TetrominoPiece;
import com.github.capstone.Twotris;
import org.lwjgl.util.Color;
import org.lwjgl.util.Rectangle;

public class FakeTetromino
{
    private int size;
    private Type type;
    private TetrominoPiece[][] pieceMatrix;
    private Rectangle hitBox;
    private int rotation;
    private Color color;

    /**
     * @param none
     * @return none
     * @throws none
     * @EntityTetromino This constructor method is used for setting the type, state, size, speed and color of the piece being made. It also initializes the rotation and keypress to zero.
     */
    public FakeTetromino(int type, int size, int rotation, int startX, int startY)
    {
        this.size = size;
        this.type = Type.values()[type];
        this.pieceMatrix = generateFromType(startX, startY);
        this.rotation = 0;
        for (int i = 0; i < rotation; i++)
        {
            rotate();
        }
        this.color = ColorPalette.getInstance().getColor(Twotris.getInstance().config.colorscheme, this.type.ordinal());
    }

    /**
     * @param none
     * @return TetrominoPiece
     * @throws none
     * @generateFromType This method is used to actually construct the pieces the tetromino uses, based on a variety of options.
     */

    private TetrominoPiece[][] generateFromType(int startX, int startY)
    {
        switch (this.type)
        {
            case L:
                this.hitBox = new Rectangle(startX, startY, 2 * size, 3 * size);
                return new TetrominoPiece[][]{
                        {new TetrominoPiece(startX, startY, size), null},
                        {new TetrominoPiece(startX, startY + size, size), null},
                        {new TetrominoPiece(startX, startY + (2 * size), size), new TetrominoPiece(startX + size, startY + (2 * size), size)}
                };
            case S:
                this.hitBox = new Rectangle(startX, startY, 2 * size, 3 * size);
                return new TetrominoPiece[][]{
                        {new TetrominoPiece(startX, startY, size), null, null},
                        {new TetrominoPiece(startX, startY + size, size), new TetrominoPiece(startX + size, startY + size, size), null},
                        {null, new TetrominoPiece(startX + size, startY + (2 * size), size), null}
                };
            case J:
                this.hitBox = new Rectangle(startX, startY, 2 * size, 3 * size);
                return new TetrominoPiece[][]{
                        {null, new TetrominoPiece(startX + size, 0, size)},
                        {null, new TetrominoPiece(startX + size, size, size)},
                        {new TetrominoPiece(startX, startY + (2 * size), size), new TetrominoPiece(startX + size, startY + (2 * size), size)}
                };
            case T:
                this.hitBox = new Rectangle(startX, startY, 3 * size, 2 * size);
                return new TetrominoPiece[][]{
                        {new TetrominoPiece(startX, startY, size), new TetrominoPiece(startX + size, startY, size), new TetrominoPiece(startX + (2 * size), startY, size)},
                        {null, new TetrominoPiece(startX + size, startY + size, size), null}
                };
            case O:
                this.hitBox = new Rectangle(startX, startY, 2 * size, 2 * size);
                return new TetrominoPiece[][]{
                        {new TetrominoPiece(startX, startY, size), new TetrominoPiece(startX + size, startY, size)},
                        {new TetrominoPiece(startX, startY + size, size), new TetrominoPiece(startX + size, startY + size, size)}
                };
            case I:
                this.hitBox = new Rectangle(startX, startY, 4 * size, size);
                return new TetrominoPiece[][]{
                        {new TetrominoPiece(startX, startY, size), new TetrominoPiece(startX + size, startY, size), new TetrominoPiece(startX + (2 * size), startY, size), new TetrominoPiece(startX + (3 * size), startY, size)}
                };
            case Z:
                this.hitBox = new Rectangle(startX, startY, 2 * size, 3 * size);
                return new TetrominoPiece[][]{
                        {null, new TetrominoPiece(startX + size, startY, size)},
                        {new TetrominoPiece(startX, startY + size, size), new TetrominoPiece(startX + size, startY + size, size)},
                        {new TetrominoPiece(startX, startY + (2 * size), size), null}
                };
        }
        return new TetrominoPiece[][]{};
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
     * @return none
     * @throws none
     * @rotate This method is used for rotating the piece, based on 0, 90, 180, and 270 degrees.  Each of the individual shapes is accorded a
     * different case, based on the available positions that particular piece is able to have. Also, rotates the hitbox to accurately
     * receive collision instances.
     */
    public void rotate()
    {
        int newHeight = this.hitBox.getWidth();
        this.hitBox.setWidth(this.hitBox.getHeight());
        this.hitBox.setHeight(newHeight);

        this.rotation++;
        if (this.rotation == 4)
        {
            this.rotation = 0;
        }
        // Rotate the piece matrix:
        TetrominoPiece[][] temp = new TetrominoPiece[this.pieceMatrix[0].length][this.pieceMatrix.length];
        for (int i = 0; i < this.pieceMatrix[0].length; i++)
        {
            for (int j = 0; j < this.pieceMatrix.length; j++)
            {
                if (this.pieceMatrix[this.pieceMatrix.length - 1 - j][i] != null)
                {
                    temp[i][j] = new TetrominoPiece(this.hitBox.getX() + (j * this.size), this.getHitBox().getY() + (i * this.size), this.size);
                }
                else
                {
                    temp[i][j] = null;
                }
            }
        }
        this.pieceMatrix = temp;
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
