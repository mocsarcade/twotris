package com.github.capstone.Scene;

import com.github.capstone.Manager.AudioManager;
import com.github.capstone.Util.Helper;
import org.lwjgl.opengl.Display;

public abstract class Scene
{
    private boolean doExit = false;

    // return false if the game should be quit
    public abstract boolean drawFrame(float delta);

    /**
     * @param none
     * @return null
     * @throws none
     * @nextScene The null indicates that Twotris should load the menu.
     */
    public Scene nextScene()
    {
        return null;
    }

    /**
     * @param none
     * @return none
     * @throws none
     * @exit This exits the selection, setting doExit as true.
     */
    protected void exit()
    {
        doExit = true;
    }

    /**
     * @param none
     * @return none
     * @throws none
     * @go This returns false when the game should be exited.
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
     * @param none
     * @return none
     * @throws none
     * @reloadFont This method is used for reloading the font, but is not implemented in the abstract.
     */
    public abstract void reloadFont();

    public void resizeContents()
    {
    }
}
