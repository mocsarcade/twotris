package com.github.capstone.Scene;

public class MainMenu extends Menu
{
	/**
@Menu
This constructor method adds button to a linkedhashmap, and creates a titlesprite. 
@param title The String include for the title.  
@return none
@throws none
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
