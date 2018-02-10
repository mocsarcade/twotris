package com.github.capstone.Util;

/**
 * @author: Jose Stovall
 * <p>
 * Config files were learned on my own back when I was Googling stuff previously for my own personal projects
 * That being said: this file was written on my own, but how I learned it previous was via Google.
 */

import com.github.capstone.Scene.Button;
import com.github.capstone.Scene.Options;

import java.io.*;
import java.util.Properties;

public class Config
{
    public float volume;
    public boolean colorblind;
    public String font;
    private String volume_label = "Volume";
    private String colorblind_label = "Colors";
    private String font_label = "Font";
    private LoopArrayList<String> font_options;

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
        options.addButton(colorblind_label + ":" + colorblind);
        options.addButton(font_label + ":" + font);
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
            this.colorblind = Boolean.parseBoolean(props.getProperty(colorblind_label, "false"));
            this.font = props.getProperty(font_label, "Chickenpox");

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
        props.setProperty(colorblind_label, "false");
        props.setProperty(font_label, "Chickenpox");

        this.volume = Integer.parseInt(props.getProperty(volume_label, "50")) / 100F; // divide by one hundred because users understand 0 -> 100 better than 0.0 -> 1.0
        this.colorblind = Boolean.parseBoolean(props.getProperty(colorblind_label, "false"));
        this.font = props.getProperty(font_label, "Chickenpox");

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
            props.setProperty(colorblind_label, "" + colorblind);
            props.setProperty(font_label, font);

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
            this.colorblind = !this.colorblind;
            button.setButtonText(colorblind_label + ":" + this.colorblind);
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
    }
}