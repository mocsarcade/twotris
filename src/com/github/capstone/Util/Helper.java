package com.github.capstone.Util;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Helper
{
    /**
     * @param none
     * @return Sys.getTime() * 1000) / Sys.getTimerResolution()
     * @throws none
     * @getTime This method gathers the system’s time and multiples it by 1000, then divides by the timer resolution.
     */
    public static long getTime()
    {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**
     * @param none
     * @return "assets/textures/err.png".replace("/", File.separator)
     * @throws none
     * @errTexture This method returns an error texture, to report that the original texture was not able to be gathered.
     */
    public static String errTexture()
    {
        return "assets/textures/err.png".replace("/", File.separator);
    }

    /**
     * @param str A string, the name of the texture.
     * @return texture
     * @throws none
     * @loadTexture This method gathers a named texture to use elsewhere in the program.
     */
    public static Texture loadTexture(String str)
    {
        String location = ("assets/textures/" + str + ".png").replace("/", File.separator);
        try
        {
            return TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream(location));
        }
        catch (IOException e)
        {
            try
            {
                return TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream(errTexture()));
            }
            catch (IOException f)
            {
                throw new RuntimeException(f);
            }
        }
    }

    /**
     * @param keyVal the Int value to convert
     * @return the integer value converted to the actual legible key on a keyboard (like "LShift")
     */
    public static String keyBeautify(int keyVal)
    {
        String ret = Keyboard.getKeyName(keyVal);
        ret = ret.substring(0, 1) + ret.substring(1).toLowerCase();
        return ret;
    }

    /**
     * @return A hashmap that can be used with .DeriveFont() to provide an underline
     */
    public static HashMap<TextAttribute, Integer> underlineAttribute()
    {
        HashMap<TextAttribute, Integer> ret = new HashMap<>();
        ret.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        return ret;
    }
}
