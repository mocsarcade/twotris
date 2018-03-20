package com.github.capstone.Scene.UserGuide;

import com.github.capstone.Scene.Menus.MainMenu;
import com.github.capstone.Twotris;
import com.github.capstone.Util.Helper;
import org.lwjgl.opengl.Display;

public class Page5 extends GuidePage
{
    public Page5(MainMenu menu)
    {
        super(new Page6(menu));
        this.textX = 16;
        this.textY = (Display.getHeight() / 4);
        this.title = "How to move";
        this.pageContent = "As the pieces fall, your goal is to:<br>" +
                "     - Left, using the " + Helper.keyBeautify(Twotris.getInstance().keybinds.moveLeft) + " key<br>" +
                "     - Right, using the " + Helper.keyBeautify(Twotris.getInstance().keybinds.moveRight) + " key<br>" +
                "     - Rotate, using the " + Helper.keyBeautify(Twotris.getInstance().keybinds.rotate) + " key<br>" +
                "    so that they fill in those rows at the bottom of the board.";
    }
}
