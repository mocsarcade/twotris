package com.github.capstone.Scene.UserGuide;

import com.github.capstone.Scene.Menus.MainMenu;
import org.lwjgl.opengl.Display;

public class Page6 extends GuidePage
{
    public Page6(MainMenu menu)
    {
        super(new Page7(menu));
        this.textX = 16;
        this.textY = (Display.getHeight() / 4);
        this.title = "Clearing a Row";
        this.pageContent = "Once you fill up those rows, it means they get eliminated! <br>" +
                "With each eliminated row, you got points added to your score.<br>" +
                "Clearing multiple rows at once gives you a bonus multiplier. <br>" +
                "    Try to get the highest score amongst your friends!";
    }
}
