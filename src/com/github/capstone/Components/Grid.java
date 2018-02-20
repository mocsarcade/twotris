package com.github.capstone.Components;

import com.github.capstone.Manager.AudioManager;
import com.github.capstone.Twotris;
import com.github.capstone.Util.Helper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Grid
{
    private boolean[][] pieceGrid;
    private Rectangle hitbox;
    private ArrayList<Tetronimo> pieces;
    private Tetronimo activePiece;
    private int gridSize;
    private boolean isGameOver;
    private long lastKeypress;

    public Grid()
    {
        // The grid should be 24 rows tall, 10 wide:
        pieceGrid = new boolean[24][10];
        this.hitbox = new Rectangle();
        int h = Display.getHeight();
        while (h % 24 != 0)
        {
            h--;
        }
        this.hitbox.setHeight(h);
        this.hitbox.setWidth((int) (this.hitbox.getHeight() / 2.4));
        this.hitbox.setX((Display.getWidth() / 2) - (this.hitbox.getWidth() / 2));
        this.hitbox.setY(0);
        this.gridSize = this.hitbox.getWidth() / 10;
        this.pieces = new ArrayList<>();
        this.activePiece = new Tetronimo(this);
        this.pieces.add(this.activePiece);
        this.isGameOver = false;
    }

    /**
     * @return The current grid size
     */
    public int getGridSize()
    {
        return this.gridSize;
    }

    /**
     * @param colNum the column which should be converted to the cartesian coordinate
     * @return an X value adapted from the row number given, based on grid size
     */
    public int getXForCol(int colNum)
    {
        return this.hitbox.getX() + (colNum * gridSize);
    }

    /**
     * @param rowNum the row which should be converted to the cartesian coordinate
     * @return a Y value adapted from the row number given, based on grid size
     */
    public int getYForRow(int rowNum)
    {
        return this.hitbox.getY() + (rowNum * gridSize);
    }

    public void update(float delta)
    {
        if (Keyboard.isKeyDown(Keyboard.KEY_A) && Helper.getTime() - lastKeypress > 250)
        {
            if (canMove("left"))
            {
                this.activePiece.moveLeft();
                lastKeypress = Helper.getTime();
            }
            else
            {
                // TODO: play sound here
            }
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_D) && Helper.getTime() - lastKeypress > 250)
        {
            if (canMove("right"))
            {
                this.activePiece.moveRight();
                lastKeypress = Helper.getTime();
            }
            else
            {
                // TODO: play sound here
            }
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_R) && Helper.getTime() - lastKeypress > 250)
        {
            // Create a rotated clone rectangle, and see where we end up
            boolean canRotate = true;
            Rectangle rotatedClone = new Rectangle(this.activePiece.getHitBox().getX(), this.activePiece.getHitBox().getY(), this.activePiece.getHitBox().getHeight(), this.activePiece.getHitBox().getWidth());
            int rowsTall = rotatedClone.getHeight() / this.gridSize;
            int colsWide = rotatedClone.getWidth() / this.gridSize;
            int startRow = this.activePiece.getHitBox().getY() / gridSize;
            int startCol = (this.activePiece.getHitBox().getX() - this.hitbox.getX()) / gridSize;

            for (int i = 0; i < rowsTall; i++)
            {
                for (int j = 0; j < colsWide; j++)
                {
                    if (startCol + j >= pieceGrid[0].length)
                    {
                        canRotate = false;
                        break;
                    }
                    if (pieceGrid[startRow + i][startCol + j])
                    {
                        canRotate = false;
                        break;
                    }
                }
            }
            if (canRotate)
            {
                this.activePiece.rotate();
                lastKeypress = Helper.getTime();
            }
            else
            {
                // TODO: Play a sound here or something
            }
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
        {
            this.activePiece.speed = 3;
        }
        else
        {
            this.activePiece.speed = 1;
        }
        for (Tetronimo t : pieces)
        {
            t.update(delta);
        }
        if (activePiece.getState() == Tetronimo.State.IDLE)
        {
            // Step 1: Determine where the hitbox is in the grid
            TetronimoPiece[][] m = activePiece.getPieceMatrix();
            for (TetronimoPiece[] pieceRow : m)
            {
                for (TetronimoPiece piece : pieceRow)
                {
                    if (piece != null)
                    {
                        int row = piece.getHitBox().getY() / gridSize;
                        int col = (piece.getHitBox().getX() - this.hitbox.getX()) / gridSize;
                        piece.getHitBox().setY((row * gridSize));
                        // Step 2: occupy the grid
                        this.pieceGrid[row][col] = true;
                    }
                }
            }
            // Step 3: regular update stuff:
            this.checkRows();
            // Step 4: Generate a new piece
            for (int col = 0; col < pieceGrid[0].length; col++)
            {
                // Game over if any of the top row is full
                if (pieceGrid[0][col])
                {
                    this.isGameOver = true;
                    break;
                }
            }
            if (!this.isGameOver)
            {
                this.activePiece = new Tetronimo(this);
                this.pieces.add(this.activePiece);
            }
        }
        else
        {
            // Step 1: Determine where the hitbox is in the grid
            TetronimoPiece[][] m = activePiece.getPieceMatrix();
            for (TetronimoPiece[] pieceRow : m)
            {
                for (TetronimoPiece pieceCol : pieceRow)
                {
                    if (pieceCol != null)
                    {
                        // Step 2: Check to see if the NEXT slot it can fall in is occupied
                        int rowCheck = ((pieceCol.getHitBox().getY()) / gridSize) + 1;
                        int colCheck = (pieceCol.getHitBox().getX() - this.hitbox.getX()) / gridSize;
                        try
                        {
                            if (rowCheck < this.pieceGrid.length && this.pieceGrid[rowCheck][colCheck])
                            {
                                this.activePiece.setState(Tetronimo.State.IDLE);
                                AudioManager.getInstance().play("place");
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException ignored)
                        {
                        }
                    }
                }
            }
        }
    }

    public void draw()
    {
        GL11.glColor3f(1F, 1F, 1F);

        GL11.glBegin(GL11.GL_LINES);
        // Outline:
        GL11.glVertex2f(this.hitbox.getX() - 1, this.hitbox.getY());
        GL11.glVertex2f(this.hitbox.getX() + this.hitbox.getWidth() + 1, this.hitbox.getY());
        GL11.glVertex2f(this.hitbox.getX() + this.hitbox.getWidth() + 1, this.hitbox.getY());
        GL11.glVertex2f(this.hitbox.getX() + this.hitbox.getWidth() + 1, this.hitbox.getY() + this.hitbox.getHeight());
        GL11.glVertex2f(this.hitbox.getX() + this.hitbox.getWidth() + 1, this.hitbox.getY() + this.hitbox.getHeight());
        GL11.glVertex2f(this.hitbox.getX() - 1, this.hitbox.getY() + this.hitbox.getHeight());
        GL11.glVertex2f(this.hitbox.getX() - 1, this.hitbox.getY() + this.hitbox.getHeight());
        GL11.glVertex2f(this.hitbox.getX() - 1, this.hitbox.getY());

        if (Twotris.getInstance().config.grid)
        {
            // Gridlines:
            for (int rows = 1; rows < 25; rows++)
            {
                GL11.glVertex2f(this.hitbox.getX(), rows * gridSize);
                GL11.glVertex2f(this.hitbox.getX() + this.hitbox.getWidth(), rows * gridSize);
            }
            for (int cols = 1; cols < 11; cols++)
            {
                GL11.glVertex2f(this.hitbox.getX() + (cols * gridSize), this.hitbox.getY());
                GL11.glVertex2f(this.hitbox.getX() + (cols * gridSize), this.hitbox.getY() + this.hitbox.getHeight());
            }
        }
        GL11.glEnd();

        for (Tetronimo t : pieces)
        {
            t.draw();
        }
    }

    /**
     * @return true if the game has ended
     */
    public boolean isGameOver()
    {
        return this.isGameOver;
    }

    /**
     * @param row the row to obliterate
     * @obliterate removes <code>row</code> both graphically, and in the back-end <code>pieceMatrix</code>
     */
    private void obliterate(int row)
    {
        Random rand = new Random();
        AudioManager.getInstance().play("obliterate_" + (rand.nextInt(3) + 1));
        for (int col = 0; col < pieceGrid[row].length; col++)
        {
            pieceGrid[row][col] = false;
        }
        // Shift the grid down:
        for (int i = row; i > 0; i--)
        {
            System.arraycopy(pieceGrid[i - 1], 0, pieceGrid[i], 0, pieceGrid[i].length);
        }
        Arrays.fill(pieceGrid[0], false);

        // Graphically shift the pieces:
        int yCap = this.getYForRow(row);
        for (Tetronimo t : pieces)
        {
            TetronimoPiece[][] m = t.getPieceMatrix();
            for (int i = 0; i < m.length; i++)
            {
                for (int j = 0; j < m[i].length; j++)
                {
                    if (m[i][j] != null)
                    {
                        // REMOVE the piece if it's the row being obliterated
                        if (m[i][j].getHitBox().getY() == yCap)
                        {
                            m[i][j] = null;
                        }
                        // SHIFT the piece if it's above the row being obliterated
                        else if (m[i][j].getHitBox().getY() < yCap)
                        {
                            m[i][j].getHitBox().translate(0, gridSize);
                        }
                    }
                }
            }
        }
    }


    /**
     * @checkRows checks every row to in the grid to see if it is full, and obliterates it if so
     */
    private void checkRows()
    {
        for (int i = 0; i < pieceGrid.length; i++)
        {
            if (isRowFull(i))
            {
                obliterate(i);
            }
        }
    }

    /**
     * @param row the row to check for completeness
     * @return true if the row in the pieceGrid is all true
     */
    private boolean isRowFull(int row)
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

    /**
     * @return the Grid's current height
     */
    public int getHeight()
    {
        return this.hitbox.getHeight();
    }

    /**
     * @param direction the direction to check
     * @return true if the current active piece can move <code>direction</code>
     */
    private boolean canMove(String direction)
    {

        // Create a rectangle clone and move it according to the movement we're wanting
        for (TetronimoPiece[] row : this.activePiece.getPieceMatrix())
        {
            for (TetronimoPiece col : row)
            {
                if (col == null)
                {
                    continue;
                }

                Rectangle movedClone = new Rectangle(col.getHitBox().getX(), col.getHitBox().getY(), col.getHitBox().getWidth(), col.getHitBox().getHeight());
                movedClone.translate(direction.equalsIgnoreCase("left") ? -this.gridSize : this.gridSize, 0);

                int movedRow = movedClone.getY() / gridSize;
                int movedCol = (movedClone.getX() - this.hitbox.getX()) / gridSize;

                if (movedCol >= pieceGrid[0].length)
                {
                    return false;
                }
                if (movedCol < 0)
                {
                    return false;
                }
                if (pieceGrid[movedRow][movedCol])
                {
                    return false;
                }
            }
        }

        return true;
    }

}
