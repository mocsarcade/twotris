package com.github.capstone.Scene;

import com.github.capstone.Manager.AudioManager;
import com.github.capstone.Util.Helper;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.TrueTypeFont;

public abstract class Scene
{
    private boolean doExit = false;

    // return false if the game should be quit
    public abstract boolean drawFrame(float delta);

    /**
@nextScene
The null indicates that Twotris should load the menu. 
@param none 
@return null
@throws none
*/
    public Scene nextScene()
    {
        return null;
    }
/**
@exit 
This exits the selection, setting doExit as true. 
@param none 
@return none
@throws none
*/
    protected void exit()
    {
        doExit = true;
    }
/**
@go
This returns false when the game should be exited.  
@param none 
@return none
@throws none
*/
    public boolean go()
    {
        long lastloop = Helper.getTime();

        boolean keepGoing = true;
        do
        {
            Display.sync(60);
            long now = Helper.getTime();
            long delta = now - lastloop;
            lastloop = now;

            keepGoing = drawFrame(delta);

            // UPDATE DISPLAY
            Display.update();
            AudioManager.getInstance().update();

            if (Display.isCloseRequested() || doExit)
            {
                return false;
            }
        } while (keepGoing);

        return true;
    }
/**
@reloadFont
This method is used for reloading the font, but is not implemented in the abstract. 
@param none 
@return none
@throws none
*/
    public abstract void reloadFont();
}
