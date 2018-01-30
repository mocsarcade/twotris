package com.github.capstone.Scene;

public class MainMenu extends Menu
{
    public MainMenu()
    {
        super("gui/title");
        this.addButton(new Button(256, 64, "Play Co-Op"), new Game());
        this.addButton(new Button(256, 64, "Options"), new Options());
        this.addButton(new Button(256, 64, "Quit Game"), null);
        this.adjustButtons();
    }
}
