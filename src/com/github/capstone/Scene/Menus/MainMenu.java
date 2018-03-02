package com.github.capstone.Scene.Menus;

import com.github.capstone.Scene.Components.Button;
import com.github.capstone.Scene.Game;
import com.github.capstone.Scene.UserGuide.Page1;

public class MainMenu extends Menu
{
    public MainMenu()
    {
        super("gui/title");
        this.addButton(new Button(256, 64, "Play Co-Op"), new Game());
        this.addButton(new Button(256, 64, "Options"), new Options(this));
        this.addButton(new Button(256, 64, "Tutorial"), new Page1(this));
        this.addButton(new Button(256, 64, "Quit Game"), null);
        this.adjustButtons();
    }

    @Override
    public void resizeContents()
    {
        clearButtons();
        this.addButton(new Button(256, 64, "Play Co-Op"), new Game());
        this.addButton(new Button(256, 64, "Options"), new Options(this));
        this.addButton(new Button(256, 64, "Tutorial"), new Page1(this));
        this.addButton(new Button(256, 64, "Quit Game"), null);
        this.adjustButtons();
    }

    @Override
    public void reloadFont()
    {
        resizeContents();
    }
}
