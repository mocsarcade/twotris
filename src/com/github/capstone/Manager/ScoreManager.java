package com.github.capstone.Manager;

import java.io.*;

public class ScoreManager
{
    private static ScoreManager instance;
    private File highScoreFile;
    private int highScore;

    public ScoreManager()
    {
        this.highScoreFile = getHighScoreFile();
        loadHighScore();
    }

    public int getHighScore()
    {
        return this.highScore;
    }

    public void updateHighScore(int newScore)
    {
        this.highScore = newScore;
        saveHighScore();
    }

    public static ScoreManager getInstance()
    {
        if (instance == null)
        {
            instance = new ScoreManager();
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    private void loadHighScore()
    {
        try
        {
            if (this.highScoreFile.exists())
            {
                FileInputStream fileIn = new FileInputStream(this.highScoreFile);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                this.highScore = (int) in.readObject();
                in.close();
                fileIn.close();
            }
        }
        catch (IOException | ClassNotFoundException ignored)
        {
            this.highScore = 0;
        }
    }

    public void saveHighScore()
    {
        try
        {
            FileOutputStream fileOut = new FileOutputStream(this.highScoreFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.highScore);
            out.close();
            fileOut.close();
        }
        catch (IOException ignored)
        {
        }
    }

    private File getHighScoreFile()
    {
        String filename;

        if (System.getProperty("os.name").toLowerCase().contains("win"))
        {
            filename = System.getenv("APPDATA") + File.separator + "Twotris.dat";
        }
        else if (System.getProperty("os.name").toLowerCase().contains("mac"))
        {
            filename = System.getProperty("user.home") + "/Library/Application Support/Twotris.dat".replace("/", File.separator);
        }
        else if (System.getProperty("os.name").toLowerCase().contains("nix") || System.getProperty("os.name").toLowerCase().contains("nux") || System.getProperty("os.name").toLowerCase().contains("aix"))
        {
            filename = "/tmp/Twotris.dat";
        }
        else
        {
            filename = "Twotris.dat";
        }
        return new File(filename);
    }
}
