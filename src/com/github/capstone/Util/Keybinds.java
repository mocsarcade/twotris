package com.github.capstone.Util;

/**
 * @author: Jose Stovall
 * <p>
 * Config files were learned on my own back when I was Googling stuff previously for my own personal projects
 * That being said: this file was written on my own, but how I learned it previous was via Google.
 */

import org.lwjgl.input.Keyboard;

import java.io.*;
import java.util.Properties;

public class Keybinds
{
    public int moveLeft;
    public int moveRight;
    public int rotate;
    public int accelerate;
    public int place;
    public int menuBack;

    public Keybinds()
    {
        loadKeybinds();
    }

    /**
     * @param none
     * @return none
     * @throws none
     * @loadConfig This method loads the configuration file from the text file, initializing the three buttons options, then closes the reader.
     */
    public void loadKeybinds()
    {
        try
        {
            File configFile = new File("twotris.keybinds");
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);

            this.moveLeft = Integer.parseInt(props.getProperty("moveLeft", "" + Keyboard.KEY_LEFT));
            this.moveRight = Integer.parseInt(props.getProperty("moveRight", "" + Keyboard.KEY_RIGHT));
            this.rotate = Integer.parseInt(props.getProperty("rotate", "" + Keyboard.KEY_TAB));
            this.accelerate = Integer.parseInt(props.getProperty("accelerate", "" + Keyboard.KEY_LSHIFT));
            this.place = Integer.parseInt(props.getProperty("place", "" + Keyboard.KEY_GRAVE));
            this.menuBack = Integer.parseInt(props.getProperty("menuBack", "" + Keyboard.KEY_ESCAPE));

            reader.close();
        }
        catch (FileNotFoundException fnfex)
        {
            try
            {
                createConfig();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch (IOException | NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @param none
     * @return none
     * @throws IOException
     * @createConfig This method creates a new file, called twotris.keybinds and a props properties. The props gets set with volume, colors, and fonts. Then these three options are further refined , such as adjusting volume from 0.0-1.0 to 0-100.
     */
    public void createConfig() throws IOException
    {
        File configFile = new File("twotris.keybinds");

        Properties props = new Properties();

        props.setProperty("moveLeft", "" + Keyboard.KEY_LEFT);
        props.setProperty("moveRight", "" + Keyboard.KEY_RIGHT);
        props.setProperty("rotate", "" + Keyboard.KEY_TAB);
        props.setProperty("accelerate", "" + Keyboard.KEY_LSHIFT);
        props.setProperty("place", "" + Keyboard.KEY_GRAVE);
        props.setProperty("menuBack", "" + Keyboard.KEY_ESCAPE);

        this.moveLeft = Integer.parseInt(props.getProperty("moveLeft", "" + Keyboard.KEY_LEFT));
        this.moveRight = Integer.parseInt(props.getProperty("moveRight", "" + Keyboard.KEY_RIGHT));
        this.rotate = Integer.parseInt(props.getProperty("rotate", "" + Keyboard.KEY_TAB));
        this.accelerate = Integer.parseInt(props.getProperty("accelerate", "" + Keyboard.KEY_LSHIFT));
        this.place = Integer.parseInt(props.getProperty("place", "" + Keyboard.KEY_GRAVE));
        this.menuBack = Integer.parseInt(props.getProperty("menuBack", "" + Keyboard.KEY_ESCAPE));

        FileWriter writer = new FileWriter(configFile);
        props.store(writer, "Twotris Keybinds");
        writer.close();
    }

    /**
     * @param none
     * @return none
     * @throws none
     * @updateConfig This method updates the existing config files if the selections are changed.
     */
    public void updateConfig()
    {
        try
        {
            File configFile = new File("twotris.keybinds");
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);

            props.setProperty("moveLeft", "" + this.moveLeft);
            props.setProperty("moveRight", "" + this.moveRight);
            props.setProperty("rotate", "" + this.rotate);
            props.setProperty("accelerate", "" + this.accelerate);
            props.setProperty("place", "" + this.place);
            props.setProperty("menuBack", "" + this.menuBack);

            props.store(new FileWriter(configFile), "Twotris Keybinds");
            loadKeybinds();
        }
        catch (IOException ignored)
        {

        }
    }
}
