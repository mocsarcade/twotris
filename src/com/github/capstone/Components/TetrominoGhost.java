package com.github.capstone.Components;

import com.github.capstone.Util.Helper;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.TrueTypeFont;

public class TetrominoGhost extends Tetromino
{
    private Grid grid;
    private TrueTypeFont font;

/**
@param grid the Grid for the ghost piece
@param type the type of the next tetromino piece. 
@return none
@throws none
@TetrominoGhost This constructor method is used to create the ghost tetromino piece for the next piece selection. 
*/

    public TetrominoGhost(Grid grid, int type)
    {
        super(grid, type);
        this.font = Helper.getFont();
        this.grid = grid;
        super.getHitBox().setLocation(Display.getWidth() - this.getHitBox().getWidth() - 16, 16);
        for (int i = 0; i < super.getPieceMatrix().length; i++)
        {
            for (int j = 0; j < super.getPieceMatrix()[i].length; j++)
            {
                if (super.getPieceMatrix()[i][j] != null)
                {
                    super.getPieceMatrix()[i][j].getHitBox().setLocation(this.getHitBox().getX() + (j * this.size), 16 + (i * this.size));
                }
            }
        }
    }
/**
@param floating point number delta
@return none
@throws none
@update This method is updating the hitbox and piece matrix the class is used for. 
*/

    @Override
    public void update(float delta)
    {
        super.getHitBox().setLocation(Display.getWidth() - this.getHitBox().getWidth() - 16, 16);
        for (int i = 0; i < super.getPieceMatrix().length; i++)
        {
            for (int j = 0; j < super.getPieceMatrix()[i].length; j++)
            {
                if (super.getPieceMatrix()[i][j] != null)
                {
                    super.getPieceMatrix()[i][j].getHitBox().setLocation(this.getHitBox().getX() + (j * this.size), 16 + (i * this.size));
                }
            }
        }
    }
/** 
@param none
@return none
@throws none
@draw This method is used for drawing the next piece space, and its font text.
*/

    @Override
    public void draw()
    {
        // Draw some text:
        font.drawString(this.getHitBox().getX() - font.getWidth("Next Piece:") - 8, 16, "Next Piece:");

        for (TetrominoPiece[] column : super.getPieceMatrix())
        {
            for (TetrominoPiece row : column)
            {
                if (row != null)
                {
                    row.draw(this.getColor(), .5F);
                }
            }
        }
    }
/**
@param none 
@return none
@throws none
@reloadFont This method is used for reloading the font.  
*/

    public void reloadFont()
    {
        this.font = Helper.getFont();
    }
}
