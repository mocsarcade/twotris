package com.github.capstone.Util;

import com.github.capstone.Twotris;
import org.lwjgl.Sys;
import org.lwjgl.util.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Helper
{
    public static String fontName = Twotris.getInstance().config.font;

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
     * @param none
     * @return new Random Color
     * @throws none
     * @getRandomColor This method gathers a random number generator, then generates a random color by inputting random numbers into the red, green and blue value slots.
     */
    public static Color getRandomColor()
    {
        Random rand = new Random();
        return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
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
     * @param none
     * @return TrueTypeFont
     * @throws none
     * @getFont This method returns a TrueTypeFont  specified elsewhere, if unable to do so, the method uses Arial.
     */
    public static TrueTypeFont getFont()
    {
        try
        {
            File fontFile = new File("natives/fontname.ttf".replace("/", File.separator).replace("fontname", fontName)).getAbsoluteFile();
            if (!fontFile.exists())
            {
                System.out.println("Font did not exist; copying...");
                FileUtils.copyStream(ResourceLoader.getResourceAsStream("assets/" + fontName + ".ttf"), new File("natives/fontname.ttf".replace("/", File.separator).replace("fontname", fontName)));
            }

            Font awtFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(32F);
            return new TrueTypeFont(awtFont, false);
        }
        catch (IOException | FontFormatException e)
        {
            return new TrueTypeFont(new Font("Arial", Font.PLAIN, 32), false);
        }
    }
}
