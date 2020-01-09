package com.github.capstone.Components;

import com.github.capstone.Manager.AudioManager;
import com.github.capstone.Manager.ScoreManager;
import com.github.capstone.Twotris;
import com.github.capstone.Util.Helper;
import com.github.capstone.Util.Keybinds;
import com.github.capstone.Util.Textures;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.opengl.TextureImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Grid
{
    private int[][] pieceGrid;
    private Rectangle hitbox;
    private ArrayList<Tetromino> pieces;
    private int currentType, nextType;
    private Tetromino activePiece;
    private TetrominoGhost nextPiece;
    private int gridSize;
    private boolean isGameOver;
    private int score;
    private int currentSpeed;
    private long lastKeypress;
    private Random rand;

    /**
     * @return none
     * @throws none
     * @grid This constructor method creates a grid with 24 rows and 10 columns. Also, sets the hitbox, the height, width, x, y, size, number of pieces
     * involved, current type of tetromino being used through the use of a random integer from the available number of pieces, the active piece,
     * the next piece, and adds the active piece to the grid, and then makes sure the game is not over.
     */
    public Grid()
    {
        // The grid should be 24 rows tall, 10 wide:
        rand = new Random();
        pieceGrid = new int[24][10];
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
        this.currentType = rand.nextInt(Tetromino.Type.values().length);
        this.nextType = rand.nextInt(Tetromino.Type.values().length);
        this.activePiece = new Tetromino(this, currentType);
        this.nextPiece = new TetrominoGhost(this, nextType);
        this.pieces.add(this.activePiece);
        this.isGameOver = false;
        this.currentSpeed = 1;
    }

    /**
     * @return gridSize The current grid size
     * @getGridSize
     */
    public int getGridSize()
    {
        return this.gridSize;
    }

    /**
     * @param colNum the column which should be converted to the cartesian coordinate
     * @return an X value adapted from the row number given, based on grid size
     * @getXForCol
     */
    public int getXForCol(int colNum)
    {
        return this.hitbox.getX() + (colNum * gridSize);
    }

    /**
     * @param rowNum the row which should be converted to the cartesian coordinate
     * @return a Y value adapted from the row number given, based on grid size
     * @getYForRow
     */
    public int getYForRow(int rowNum)
    {
        return this.hitbox.getY() + (rowNum * gridSize);
    }

    /**
     * @param floating point number delta
     * @return none
     * @throws none
     * @update This method is used for updating the piece in the grid, and playing a sound if the piece cannot be moved/rotated.
     */
    public void update(float delta)
    {
        Keybinds kb = Twotris.getInstance().keybinds;
        if (Keyboard.isKeyDown(kb.moveLeft) && Helper.getTime() - lastKeypress > 150)
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
        else if (Keyboard.isKeyDown(kb.moveRight) && Helper.getTime() - lastKeypress > 150)
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
        else if ((Keyboard.isKeyDown(kb.rotate) || Keyboard.isKeyDown(kb.rotateBack)) && Helper.getTime() - lastKeypress > 150)
        {
            System.out.println("Clicked rotate");
            short direc = 1;
            if (Keyboard.isKeyDown(kb.rotateBack)) {
                System.out.println("But it's actually rotateBACK");
                direc = -1;
            }
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
                    if (startCol + j >= pieceGrid[0].length || startRow + i < 0)
                    {
                        canRotate = false;
                        break;
                    }
                    if (pieceGrid[startRow + i][startCol + j] > 0)
                    {
                        canRotate = false;
                        break;
                    }
                }
            }
            if (canRotate)
            {
                this.activePiece.rotate(direc);
                lastKeypress = Helper.getTime();
            }
            else
            {
                // TODO: Play a sound here or something
            }
        }
        if (Keyboard.isKeyDown(kb.accelerate))
        {
            this.activePiece.speed = this.currentSpeed + 8;
        }
        else
        {
            this.activePiece.speed = this.currentSpeed;
        }
        for (Tetromino t : pieces)
        {
            t.update(delta);
        }
        this.nextPiece.update(delta);
        if (activePiece.getState() == Tetromino.State.IDLE)
        {
            // Step 1: Determine where the hitbox is in the grid
            TetrominoPiece[][] m = activePiece.getPieceMatrix();
            for (TetrominoPiece[] pieceRow : m)
            {
                for (TetrominoPiece piece : pieceRow)
                {
                    if (piece != null)
                    {
                        int row = piece.getHitBox().getY() / gridSize;
                        int col = (piece.getHitBox().getX() - this.hitbox.getX()) / gridSize;
                        if (row < 0)
                        {
                            continue;
                        }
                        piece.getHitBox().setY((row * gridSize));
                        // Step 2: occupy the grid
                        this.pieceGrid[row][col] = activePiece.getType().scoreValue;
                    }
                }
            }
            // Step 3: regular update stuff:
            this.checkRows();
            // Step 4: Generate a new piece
            for (int col = 0; col < pieceGrid[0].length; col++)
            {
                // Game over if any of the top row is full
                if (pieceGrid[0][col] > 0)
                {
                    this.isGameOver = true;
                    if (this.score > ScoreManager.getInstance().getHighScore())
                    {
                        ScoreManager.getInstance().updateHighScore(this.score);
                    }
                    break;
                }
            }
            if (!this.isGameOver)
            {
                this.currentType = this.nextType;
                this.nextType = this.rand.nextInt(Tetromino.Type.values().length);
                this.activePiece = new Tetromino(this, this.currentType);
                this.nextPiece = new TetrominoGhost(this, this.nextType);
                this.pieces.add(this.activePiece);
            }
        }
        else
        {
            // Step 1: Determine where the hitbox is in the grid
            TetrominoPiece[][] m = activePiece.getPieceMatrix();
            for (TetrominoPiece[] pieceRow : m)
            {
                for (TetrominoPiece pieceCol : pieceRow)
                {
                    if (pieceCol != null)
                    {
                        // Step 2: Check to see if the NEXT slot it can fall in is occupied
                        int rowCheck = ((pieceCol.getHitBox().getY()) / gridSize) + 1;
                        int colCheck = (pieceCol.getHitBox().getX() - this.hitbox.getX()) / gridSize;
                        try
                        {
                            if (rowCheck < this.pieceGrid.length && this.pieceGrid[rowCheck][colCheck] > 0)
                            {
                                this.activePiece.setState(Tetromino.State.IDLE);
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
        if (this.score > ScoreManager.getInstance().getHighScore())
        {
            ScoreManager.getInstance().updateHighScore(this.score);
        }
    }

    /**
     * @param none
     * @return none
     * @throws none
     * @draw This method is used for drawing the piece, setting the color, and size/shape.
     */
    public void draw()
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
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
            GL11.glEnd();
            GL11.glColor4f(1F, 1F, 1F, 0.25F);
            GL11.glBegin(GL11.GL_LINES);
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

        for (Tetromino t : pieces)
        {
            t.draw();
        }
        this.nextPiece.draw();

        TextureImpl.bindNone();
        Textures.FONT.drawString(0, 0, "Score: " + this.score);
    }

    /**
     * @return true if the game has ended
     * @isGameOver This method checks if the game is over or not.
     */
    public boolean isGameOver()
    {
        return this.isGameOver;
    }

    /**
     * @param row the row to obliterate
     * @return the sum of the scores of each piece in the row destroyed
     * @obliterate This method	removes <code>row</code> both graphically, and in the back-end <code>pieceMatrix</code>
     */
    private int obliterate(int row)
    {
        Random rand = new Random();
        AudioManager.getInstance().play("obliterate_" + (rand.nextInt(3) + 1));
        int retSum = 0;
        for (int col = 0; col < pieceGrid[row].length; col++)
        {
            retSum += pieceGrid[row][col];
            pieceGrid[row][col] = 0;
        }
        // Shift the grid down:
        for (int i = row; i > 0; i--)
        {
            System.arraycopy(pieceGrid[i - 1], 0, pieceGrid[i], 0, pieceGrid[i].length);
        }
        Arrays.fill(pieceGrid[0], 0);

        // Graphically shift the pieces:
        int yCap = this.getYForRow(row);
        for (Tetromino t : pieces)
        {
            TetrominoPiece[][] m = t.getPieceMatrix();
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
        return retSum;
    }

    /**
     * @checkRows checks every row to in the grid to see if it is full, and obliterates it if so
     */
    private void checkRows()
    {
        int numRowsObliterated = 0;
        int runningTally = 0;
        for (int i = 0; i < pieceGrid.length; i++)
        {
            if (isRowFull(i))
            {
                numRowsObliterated++;
                runningTally += obliterate(i);
            }
        }
        this.score += numRowsObliterated * runningTally;
        this.currentSpeed = 1 + (this.score / 1000);
    }


    /**
     * @param row the row to check for completeness
     * @return true if the row in the pieceGrid is all true
     * @isRowFull This method checks if the row is full.
     */
    private boolean isRowFull(int row)
    {
        for (int col = 0; col < pieceGrid[row].length; col++)
        {
            if (pieceGrid[row][col] == 0)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * @return the Grid's current height
     * @getHeight This method retrieves the height of the grid
     */
    public int getHeight()
    {
        return this.hitbox.getHeight();
    }

    /**
     * @return the Grid's current width
     * @getWidth This method retrieves the width of the grid.
     */
    public int getWidth()
    {
        return this.hitbox.getWidth();
    }


    /**
     * @param direction the direction to check
     * @return true if the current active piece can move <code>direction</code>
     * @canMove This method checks to see if the piece can move in the direction given.
     */
    private boolean canMove(String direction)
    {
        // Create a rectangle clone and move it according to the movement we're wanting
        for (TetrominoPiece[] row : this.activePiece.getPieceMatrix())
        {
            for (TetrominoPiece col : row)
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
                if (movedCol < 0 || movedRow < 0)
                {
                    return false;
                }
                if (pieceGrid[movedRow][movedCol] > 0)
                {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * @param none
     * @return score the score of the game
     * @throws none
     * @getScore This method retrieves the score of the game being played.
     */
    public int getScore()
    {
        return this.score;
    }

    public void recolor()
    {
        for (Tetromino t : pieces)
        {
            t.recolor();
        }
        this.nextPiece.recolor();
    }
}
