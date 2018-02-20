package com.github.capstone.Components;

import org.lwjgl.util.Color;

import java.util.HashMap;

public class ColorPalette
{
    public HashMap<String, Color[]> colors;
    private static ColorPalette instance;

    public ColorPalette()
    {
        instance = this;
        colors = new HashMap<>();
    }

    public static ColorPalette getInstance()
    {
        return instance;
    }

    public void addColorPallete(String name, String... colors_in)
    {
        assert colors_in.length == 7;

        Color[] converted = new Color[7];
        for (int i = 0; i < colors_in.length; i++)
        {
            converted[i] = toRGB(colors_in[i]);
        }
        colors.put(name, converted);
    }

    public Color getColor(String scheme, int index)
    {
        return colors.get(scheme)[index];
    }

    private Color toRGB(String colorStr)
    {
        return new Color(
                Integer.valueOf(colorStr.substring(0, 2), 16),
                Integer.valueOf(colorStr.substring(2, 4), 16),
                Integer.valueOf(colorStr.substring(4, 6), 16));
    }
}
