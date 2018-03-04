package com.github.capstone.Scene.UserGuide;

import com.github.capstone.Scene.Menus.MainMenu;
import org.lwjgl.opengl.Display;

public class Page1 extends GuidePage
{
    public Page1(MainMenu menu)
    {
        super(new Page2(menu));
        this.textX = 16;
        this.textY = (Display.getHeight() / 4);
        this.setTitle("Welcome to Twotris!");
        this.setPageContent("Twotris is a highly customizable, student-made variant of a arcade" +
                "game classic. For those players who have yet to experience the" +
                "fun, here is a quick walkthrough of our game!");
    }
}