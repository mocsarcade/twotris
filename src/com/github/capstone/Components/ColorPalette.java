package com.github.capstone.Components;

import org.lwjgl.util.Color;

import java.util.LinkedHashMap;

public class ColorPalette
{
    private static ColorPalette instance;
    public LinkedHashMap<String, Color[]> colors;

    /**
     * @return none
     * @throws none
     * @ColorPalette This constructor method creates a new instance of the color palette and creates a new linked hashmap for the colors within.
     */
    public ColorPalette()
    {
        instance = this;
        colors = new LinkedHashMap<>();
    }

    /**
     * @return instance
     * @throws none
     * @getInstance This method retrieves an instance of the color palette.
     */
    public static ColorPalette getInstance()
    {
        return instance;
    }

    /**
     * @param name      the name of the color
     * @param colors_in
     * @return none
     * @throws none
     * @addColorPalette This method adds a new color palette, making sure that there are seven colors in the hashmap. Then, the for loop converts the colors to RGB
     * values, and puts the converted colors into the colors list.
     */
    public void addColorPalette(String name, String... colors_in)
    {
        assert colors_in.length == 7;

        Color[] converted = new Color[7];
        for (int i = 0; i < colors_in.length; i++)
        {
            converted[i] = toRGB(colors_in[i]);
        }
        colors.put(name, converted);
    }

    /**
     * @param scheme the color scheme to be looked into.
     * @param index  the index to be looked into in the scheme provided.
     * @return color the color located in the scheme, at the index provided.
     * @throws none
     * @getColor This method retrieves the color found at the index provided in the scheme provided. .
     */
    public Color getColor(String scheme, int index)
    {
        return colors.get(scheme)[index];
    }

    /**
     * @param scheme the color scheme to be looked into.
     * @param index  the index to be looked into in the scheme provided.
     * @return color the color located in the scheme, at the index provided.
     * @throws none
     * @getColor This method retrieves the color found at the index provided in the scheme provided. .
     */
    public org.newdawn.slick.Color getSlickColor(String scheme, int index)
    {
        return new org.newdawn.slick.Color(colors.get(scheme)[index].getRed(), colors.get(scheme)[index].getGreen(), colors.get(scheme)[index].getBlue());
    }

    /**
     * @param colorStr the hex value of the color to be converted.
     * @return color the color converted from hex into RGB.
     * @throws none
     * @toRGB This method converts a color hex string into the RGB values needed.
     */
    private Color toRGB(String colorStr)
    {
        return new Color(
                Integer.valueOf(colorStr.substring(0, 2), 16),
                Integer.valueOf(colorStr.substring(2, 4), 16),
                Integer.valueOf(colorStr.substring(4, 6), 16));
    }
}
