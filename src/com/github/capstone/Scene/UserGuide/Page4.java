package com.github.capstone.Scene.UserGuide;

import com.github.capstone.Scene.Menus.MainMenu;
import org.lwjgl.opengl.Display;

public class Page4 extends GuidePage
{
    public Page4(MainMenu menu)
    {
        super(new Page5(menu));
        // TODO: Everyone needs to set this text below, and where they want the textX and textY to begin.
        this.textX = 16;
        this.textY = (Display.getHeight() / 8) - (3 * this.font.getHeight());
        this.pageContent = "Learning to play <br> <br> <br> <br> <br>Alright, you've got the game setup just for you," +
				" now it's time to learn how to play! <br>" + 
				"You score points in Twotris by clearing the horizontal lines (or rows) on the gameboard. <br>" + 
				"This is done using pieces (called Tetrominos) to fill empty spaces across those rows ";
    }
}