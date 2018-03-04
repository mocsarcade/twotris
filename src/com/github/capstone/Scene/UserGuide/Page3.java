package com.github.capstone.Scene.UserGuide;

import com.github.capstone.Scene.Menus.MainMenu;
import org.lwjgl.opengl.Display;

public class Page3 extends GuidePage
{
    public Page3(MainMenu menu)
    {
        super(new Page4(menu));
        this.textX = 16;
        this.textY = (Display.getHeight() / 4);
        this.title = "Starting a New Game";
        this.pageContent = "Let's check out the options menu first. <br>" +
                "This is where you can switch up the font choices and color <br>" +
                "schemes, or adjust game-play keys or sound levels to exactly <br>" +
                "    your liking.";
    }
}