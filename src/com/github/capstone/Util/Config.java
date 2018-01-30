package com.github.capstone.Util;

/**
 * @author: Jose Stovall
 * <p>
 * Config files were learned on my own back when I was Googling stuff previously for my own personal projects
 * That being said: this file was written on my own, but how I learned it previous was via Google.
 */

import com.github.capstone.Scene.Button;
import com.github.capstone.Scene.Options;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Config
{
    public float volume;
    private String volume_label = "Volume";

    public boolean colorblind;
    private String colorblind_label = "Colors";

    public String font;
    private String font_label = "Font";
    private LoopArrayList<String> font_options;

    public Config()
    {
        font_options = new LoopArrayList<>();
        font_options.add("Blocks");
        font_options.add("Chickenpox");
        loadConfig();
    }

    public void addButtonsToOptionsGUI(Options options)
    {
        options.addButton(volume_label + ":" + (int) (volume * 100));
        options.addButton(colorblind_label + ":" + colorblind);
        options.addButton(font_label + ":" + font);
    }

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