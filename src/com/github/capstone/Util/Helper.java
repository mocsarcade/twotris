package com.github.capstone.Util;

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
    public static long getTime()
    {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    public static String errTexture()
    {
        return "assets/textures/err.png".replace("/", File.separator);
    }

    public static Color getRandomColor()
    {
        Random rand = new Random();
        return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

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

    public static TrueTypeFont getFont()
    {
        try
        {
            File fontFile = new File("natives/blocks.ttf".replace("/", File.separator)).getAbsoluteFile();
            if (!fontFile.exists())
            {
                System.out.println("Font did not exist; copying...");
                FileUtils.copyStream(ResourceLoader.getResourceAsStream("assets/blocks.ttf"), new File("natives/blocks.ttf".replace("/", File.separator)));
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
