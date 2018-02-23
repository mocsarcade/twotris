package com.github.capstone.Components;

import com.github.capstone.Util.Helper;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.TrueTypeFont;

public class TetrominoGhost extends Tetromino
{
    private Grid grid;

    public TetrominoGhost(Grid grid, int type)
    {
        super(grid, type);
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

    @Override
    public void draw()
    {
        // Draw some text:
        TrueTypeFont f = Helper.getFont();
        f.drawString(this.getHitBox().getX() - f.getWidth("Next Piece:") - 8, 16, "Next Piece:");

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

}
