package com.github.capstone.Grid;

import java.util.Arrays;
import java.util.Random;

public class Grid
{
    public static void main(String args[])
    {
        Grid grid = new Grid();
        boolean[][] pieceGrid = grid.getPieceGrid();
        for (int i = 0; i < pieceGrid.length; i++)
        {
            for (int j = 0; j < pieceGrid[i].length; j++)
            {
                if (pieceGrid[i][j])
                {
                    System.out.print(1);
                }
                else
                {
                    System.out.print(0);
                }
            }
            System.out.println();
        }

        grid.obliterate(4);
        System.out.println("Obliterated row 4");

        for (int i = 0; i < pieceGrid.length; i++)
        {
            for (int j = 0; j < pieceGrid[i].length; j++)
            {
                if (pieceGrid[i][j])
                {
                    System.out.print(1);
                }
                else
                {
                    System.out.print(0);
                }
            }
            System.out.println();
        }

    }

    private boolean[][] pieceGrid;

    public Grid()
    {
        Random rand = new Random();
        // The grid should be 24 rows tall, 10 wide
        pieceGrid = new boolean[24][10];
        for (int i = 0; i < pieceGrid.length; i++)
        {
            for (int j = 0; j < pieceGrid[i].length; j++)
            {
                pieceGrid[i][j] = rand.nextBoolean();
            }
        }
    }

    public void update(float delta)
    {

    }

    public void draw()
    {

    }

    public void obliterate(int row)
    {
        for (int col = 0; col < pieceGrid[row].length; col++)
        {
            pieceGrid[row][col] = false;
        }
        // Shift the grid down:
        for (int i = row - 1; i > 0; i--)
        {
            System.arraycopy(pieceGrid[i - 1], 0, pieceGrid[i], 0, pieceGrid[i].length);
        }
        Arrays.fill(pieceGrid[0], false);
    }

    public boolean[][] getPieceGrid()
    {
        return pieceGrid;
    }

    public void checkRows()
    {
        for (int i = 0; i < pieceGrid.length; i++)
        {
            if (isRowFull(i))
            {
                obliterate(i);
            }
        }
    }

    public boolean isRowFull(int row)
    {
        for (int col = 0; col < pieceGrid[row].length; col++)
        {
            if (!pieceGrid[row][col])
            {
                return false;
            }
        }
        return true;
    }
}
