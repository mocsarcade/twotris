package com.github.capstone.Util;

import com.github.capstone.Twotris;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Textures
{
    public static String fontName = Twotris.getInstance().config.font.toLowerCase();

    public static final Texture TETRABIT = Helper.loadTexture("piece");
    public static final Texture TITLE = Helper.loadTexture("gui/title");
    public static final Texture PAUSED = Helper.loadTexture("gui/paused");
    public static final Texture GAME_OVER = Helper.loadTexture("gui/game_over");
    public static final Texture BUTTON = Helper.loadTexture("gui/button");

    public static TrueTypeFont FONT = loadFont();
    public static Font AWTFONT = loadAWTFont();

    /**
     * @param none
     * @return TrueTypeFont
     * @throws none
     * @getFont This method returns a TrueTypeFont  specified elsewhere, if unable to do so, the method uses Arial.
     */
    private static TrueTypeFont loadFont()
    {
        try
        {
            File fontFile = new File("natives/fonts/fontname.ttf".replace("/", File.separator).replace("fontname", fontName)).getAbsoluteFile();
            if (!fontFile.exists())
            {
                System.out.println("Font did not exist; copying...");
                File extractedFontFolder = new File("natives/fonts".replace("/", File.separator));
                if (!extractedFontFolder.exists())
                {
                    if (!extractedFontFolder.mkdir())
                    {
                        throw new RuntimeException();
                    }
                }
                FileUtils.copyStream(ResourceLoader.getResourceAsStream("assets/fonts/fontname.ttf".replace("/", File.separator).replace("fontname", fontName)), new File("natives/fonts/fontname.ttf".replace("/", File.separator).replace("fontname", fontName)));
            }

            float fontSize = 32F;

            if (fontName.toLowerCase().contains("t 'n j"))
            {
                fontSize = 18F;
            }
            else if (fontName.toLowerCase().contains("blocks"))
            {
                fontSize = 28F;
            }
            else if (fontName.toLowerCase().contains("chickenpox"))
            {
                fontSize = 28F;
            }
            else if (fontName.toLowerCase().contains("heavy"))
            {
                fontSize = 20F;
            }
            else if (fontName.toLowerCase().contains("pixelmecha"))
            {
                fontSize = 28F;
            }
            Font awtFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(fontSize);
            return new TrueTypeFont(awtFont, false);
        }
        catch (IOException | FontFormatException e)
        {
            e.printStackTrace();
            return new TrueTypeFont(new Font("Arial", Font.PLAIN, 32), false);
        }
    }

    /**
     * @param none
     * @return AWT Font
     * @throws none
     * @getFont This method returns an AWT Font specified elsewhere, if unable to do so, the method uses Arial.
     */
    private static Font loadAWTFont()
    {
        try
        {
            File fontFile = new File("natives/fonts/fontname.ttf".replace("/", File.separator).replace("fontname", fontName)).getAbsoluteFile();
            if (!fontFile.exists())
            {
                System.out.println("Font did not exist; copying...");
                File extractedFontFolder = new File("natives/fonts".replace("/", File.separator));
                if (!extractedFontFolder.exists())
                {
                    if (!extractedFontFolder.mkdir())
                    {
                        throw new RuntimeException();
                    }
                }
                FileUtils.copyStream(ResourceLoader.getResourceAsStream("assets/fonts/fontname.ttf".replace("/", File.separator).replace("fontname", fontName)), new File("natives/fonts/fontname.ttf".replace("/", File.separator).replace("fontname", fontName)));
            }

            return Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(24F);
        }
        catch (IOException | FontFormatException e)
        {
            e.printStackTrace();
            return Font.getFont("Arial");
        }
    }

    public static void reloadFont()
    {
        FONT = loadFont();
        AWTFONT = loadAWTFont();
    }
}
