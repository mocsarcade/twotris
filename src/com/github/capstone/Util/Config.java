package com.github.capstone.Util;

/**
 * @author: Jose Stovall
 * <p>
 * Config files were learned on my own back when I was Googling stuff previously for my own personal projects
 * That being said: this file was written on my own, but how I learned it previous was via Google.
 */

import com.github.capstone.Components.ColorPalette;
import com.github.capstone.Scene.Button;
import com.github.capstone.Scene.Options;

import java.io.*;
import java.util.Properties;

public class Config
{
    public float volume;
    public String colorscheme;
    public String font;
    public boolean fullscreen;
    public boolean grid;
    private LoopArrayList<String> font_options;
    private LoopArrayList<String> color_options;
    private String volume_label = "Volume";
    private String colorscheme_label = "Colors";
    private String font_label = "Font";
    private String fullscreen_label = "Fullscreen";
    private String grid_label = "Show Grid";

    /**
     * @return none
     * @throws none
     * @Config This constructor method creates a font_options LoopArrayList, adds font options to the list, and then loads the configuration.
     */

    public Config()
    {
        font_options = new LoopArrayList<>();
        font_options.add("Blocks");
        font_options.add("Chickenpox");
        font_options.add("PixelMecha");
        font_options.add("Heavy");
        font_options.add("T 'n J");
        font_options.add("m5x7");


        // TODO: add more color options!
        new ColorPalette();
        ColorPalette.getInstance().addColorPallete("Default", "fa0012", "00b6a3", "ffa500", "ec0868", "05e662", "c200fb", "ffffff");
        ColorPalette.getInstance().addColorPallete("Darker", "fa1200", "0562e6", "ff00a5", "00a3b6", "c2fb00", "ffffff", "ec6808");
        ColorPalette.getInstance().addColorPallete("CANDYBATS", "d2fdf8", "aae2fb", "ebc2f9", "ffd1ec", "b486a2", "C585DB", "D675B1");
        ColorPalette.getInstance().addColorPallete("Storm", "416168", "99c6c6", "41546d", "b9bbb6", "d2d7dd", "bfcfd9", "7c7a7d");
        color_options = new LoopArrayList<>();
        for (String option : ColorPalette.getInstance().colors.keySet())
        {
            color_options.add(option);
        }

        loadConfig();
    }

    /**
     * @param options An Options entity
     * @return none
     * @throws none
     * @addButtonstoOptionsGUI This method adds buttons to the options interface, including volume control, color options, and the font option button.
     */
    public void addButtonsToOptionsGUI(Options options)
    {
        options.addButton(volume_label + ":" + (int) (volume * 100));
        options.addButton(colorscheme_label + ":" + colorscheme);
        options.addButton(font_label + ":" + font);
        options.addButton(fullscreen_label + ":" + fullscreen);
        options.addButton(grid_label + ":" + grid);
    }

    /**
     * @param none
     * @return none
     * @throws none
     * @loadConfig This method loads the configuration file from the text file, initializing the three buttons options, then closes the reader.
     */
    public void loadConfig()
    {
        try
        {
            File configFile = new File("twotris.settings");
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);

            this.volume = Integer.parseInt(props.getProperty(volume_label, "50")) / 100F; // divide by one hundred because users understand 0 -> 100 better than 0.0 -> 1.0
            this.colorscheme = props.getProperty("Colors", "Default");
            this.font = props.getProperty(font_label, "Chickenpox");
            this.fullscreen = Boolean.parseBoolean(props.getProperty(fullscreen_label, "false"));
            this.grid = Boolean.parseBoolean(props.getProperty("Show_Grid", "false"));

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
     * @createConfig This method creates a new file, called twotris.settings and a props properties. The props gets set with volume, colors, and fonts. Then these three options are further refined , such as adjusting volume from 0.0-1.0 to 0-100.
     */
    public void createConfig() throws IOException
    {
        File configFile = new File("twotris.settings");

        Properties props = new Properties();

        props.setProperty(volume_label, "50");
        props.setProperty("Colors", "Default");
        props.setProperty(font_label, "Chickenpox");
        props.setProperty(fullscreen_label, "false");
        props.setProperty("Show_Grid", "false");

        this.volume = Integer.parseInt(props.getProperty(volume_label, "50")) / 100F; // divide by one hundred because users understand 0 -> 100 better than 0.0 -> 1.0
        this.colorscheme = props.getProperty("Colors", "Default");
        this.font = props.getProperty(font_label, "Chickenpox");
        this.fullscreen = Boolean.parseBoolean(props.getProperty(fullscreen_label, "false"));
        this.grid = Boolean.parseBoolean(props.getProperty("Show_Grid", "false"));

        FileWriter writer = new FileWriter(configFile);
        props.store(writer, "Twotris Settings");
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
            File configFile = new File("twotris.settings");
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);

            props.setProperty(volume_label, "" + (int) (volume * 100));
            props.setProperty("Colors", colorscheme);
            props.setProperty(font_label, font);
            props.setProperty(fullscreen_label, "" + fullscreen);
            props.setProperty("Show_Grid", "" + grid);

            props.store(new FileWriter(configFile), "Twotris Settings");
            loadConfig();
        }
        catch (IOException ignored)
        {

        }
    }

    /**
     * @param option A string equal to volume, colors, or font, which controls the corresponding option.
     * @param button The button attached to the option mentioned above.
     * @return none
     * @throws none
     * @toggleOption This method gathers an option String, and a button and is responsible for adjusting the string visible in the actual button.
     */
    public void toggleOption(String option, Button button)
    {
        if (option.equalsIgnoreCase("volume"))
        {
            this.volume = this.volume == 1 ? 0 : this.volume + 0.1F;
            button.setButtonText(volume_label + ":" + (int) (this.volume * 100));
            this.updateConfig();
        }
        else if (option.equalsIgnoreCase("colors"))
        {
            this.colorscheme = color_options.getNextOption(this.colorscheme);
            button.setButtonText(colorscheme_label + ":" + this.colorscheme);
            this.updateConfig();
        }
        else if (option.equalsIgnoreCase("font"))
        {
            this.font = font_options.getNextOption(this.font);
            button.setButtonText(font_label + ":" + this.font);
            Helper.fontName = this.font.toLowerCase();
            button.setFont(Helper.getFont());
            this.updateConfig();
        }
        else if (option.equalsIgnoreCase("fullscreen"))
        {
            this.fullscreen = !this.fullscreen;
            button.setButtonText(fullscreen_label + ":" + this.fullscreen);
            this.updateConfig();
        }
        else if (option.equalsIgnoreCase("show grid"))
        {
            this.grid = !this.grid;
            button.setButtonText(grid_label + ":" + grid);
            this.updateConfig();
        }
    }
}
