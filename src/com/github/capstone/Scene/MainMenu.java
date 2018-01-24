package com.github.capstone.Scene;

import org.newdawn.slick.Color;

public class MainMenu extends Menu
{
    public MainMenu()
    {
        super("title");
        this.addButton(new Button(256, 64, "Play Co-Op", new Color(200, 200, 200), new Color(64, 0, 72)), new Game());
        this.addButton(new Button(256, 64, "Quit Game", new Color(200, 200, 200), new Color(64, 0, 72)), null);
        this.adjustButtons();
    }
}
