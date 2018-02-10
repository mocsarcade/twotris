package com.github.capstone.Scene;

public class MainMenu extends Menu
{
    /**
     * @param title The String include for the title.
     * @return none
     * @throws none
     * @Menu This constructor method adds button to a linkedhashmap, and creates a titlesprite.
     */

    public MainMenu()
    {
        super("gui/title");
        this.addButton(new Button(256, 64, "Play Co-Op"), new Game());
        this.addButton(new Button(256, 64, "Options"), new Options(this));
        this.addButton(new Button(256, 64, "Quit Game"), null);
        this.adjustButtons();
    }
}
