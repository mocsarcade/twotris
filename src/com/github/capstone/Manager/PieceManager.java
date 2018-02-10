package com.github.capstone.Manager;

import com.github.capstone.Entity.EntityTetromino;

import java.util.ArrayList;

public class PieceManager
{
    private static PieceManager instance;
    public ArrayList<EntityTetromino> piecelist;
    public EntityTetromino activePiece;

    public PieceManager()
    {
        this.piecelist = new ArrayList<>();
        generateNewTetronimo();
        instance = this;
    }

    public static PieceManager getInstance()
    {
        return instance;
    }

    public void draw()
    {
        for (EntityTetromino tet : piecelist)
        {
            tet.draw();
        }
    }

    public void update(float delta)
    {
        for (EntityTetromino tet : piecelist)
        {
            tet.update(delta);
        }
    }

    public void clear()
    {
        this.piecelist.clear();
    }

    public void generateNewTetronimo()
    {
        this.activePiece = new EntityTetromino();
        this.piecelist.add(activePiece);
    }


}
