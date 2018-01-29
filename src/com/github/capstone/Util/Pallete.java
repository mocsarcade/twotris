package com.github.capstone.Util;

import org.lwjgl.util.Color;

public enum Pallete
{
    AMAZONITE(0, 182, 163),
    ORANGE(255, 165, 0),
    RED(250, 0, 18),
    ELECTRIC_PURPLE(194, 0, 251),
    RASPBERRY(236, 8, 104),
    EMERALD(5, 230, 98),
    WHITE(255, 255, 255);

    public Color color;

    Pallete(int r, int g, int b)
    {
        this.color = new Color(r, g, b);
    }
}
