package com.github.capstone.Scene.UserGuide;

import com.github.capstone.Scene.Menus.MainMenu;
import org.lwjgl.opengl.Display;

public class Page4 extends GuidePage
{
    public Page4(MainMenu menu)
    {
        super(new Page5(menu));
        this.textX = 16;
        this.textY = (Display.getHeight() / 4);
        this.title = "Learning to Play";
        this.pageContent = "Alright, you've got the game setup just for you, now it's time to<br>" +
                "learn how to play! You score points in Twotris by clearing the<br>" +
                "rows on the gameboard. This is done using pieces<br>" +
                "    (called Tetrominos) to fill the rows.";
    }
}