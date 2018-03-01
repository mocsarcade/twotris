package com.github.capstone.Scene.UserGuide;

import com.github.capstone.Scene.Menus.MainMenu;
import org.lwjgl.opengl.Display;

public class Page2 extends GuidePage
{
    public Page2(MainMenu menu)
    {
        super(new Page3(menu));
        // TODO: Everyone needs to set this text below, and where they want the textX and textY to begin.
        this.textX = 16;
        this.textY = (Display.getHeight() / 2) - (3 * this.font.getHeight());
        this.pageContent = "Insert text here, friend. (Page 2 / 7)";
    }
}