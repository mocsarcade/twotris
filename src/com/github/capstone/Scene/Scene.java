package com.github.capstone.Scene;

import com.github.capstone.Twotris;
import com.github.capstone.Manager.AudioManager;
import com.github.capstone.Util.Helper;
import org.lwjgl.opengl.Display;

public abstract class Scene
{
    private boolean doExit = false;

    // return false if the game should be quit
    public abstract boolean drawFrame(float delta);

    // null typically means Twotris should load menu
    public Scene nextScene()
    {
        return null;
    }

    protected void exit()
    {
        doExit = true;
    }

    // returns false when game should be exited
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
}
