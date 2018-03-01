package com.github.capstone.Scene.UserGuide;

import com.github.capstone.Scene.Menus.MainMenu;
import org.lwjgl.opengl.Display;

public class Page1 extends GuidePage
{
    public Page1(MainMenu menu)
    {
        super(new Page2(menu));
        // TODO: Everyone needs to set this text below, and where they want the textX and textY to begin. <br> = \n
        this.textX = 16;
        this.textY = (Display.getHeight() / 4);// - (3 * this.font.getHeight());
        this.pageContent = "Twotris is a highly customizable, student-made variant of a arcade<br>game classic. " +
                "For those players who have yet to experience the fun,<br>here’s a quick walkthrough of our game!";
    }
}