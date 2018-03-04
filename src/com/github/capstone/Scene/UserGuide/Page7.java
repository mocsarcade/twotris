package com.github.capstone.Scene.UserGuide;

import com.github.capstone.Scene.Menus.MainMenu;
import org.lwjgl.opengl.Display;

public class Page7 extends GuidePage
{
    public Page7(MainMenu menu)
    {
        super(menu);
        this.textX = 16;
        this.textY = (Display.getHeight() / 4);
        this.title = "Tips & Tricks";
        this.pageContent = "There are seven different shaped pieces, so the more you have on the board, <br>" +
                "the more challenging the game gets!<br>" +
                "Make sure to keep an eye on the upcoming piece, to help you plan your next move.";
    }
}