package com.github.capstone.Scene.Components;

import com.github.capstone.Manager.AudioManager;
import com.github.capstone.Twotris;
import com.github.capstone.Util.Keybinds;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Rectangle;

public class ButtonKeybind extends Button
{
    private int keyValue;
    private String title;
    private int stickyX, stickyY;
    private boolean prevState = true;
    private boolean clickedOnce = false;

    public ButtonKeybind(String title, int defaultVal)
    {
        super(0, 0, title + ": " + Keyboard.getKeyName(defaultVal).substring(0, 1) + Keyboard.getKeyName(defaultVal).substring(1).toLowerCase());
        this.title = title;
        this.keyValue = defaultVal;
    }

    @Override
    public void update()
    {
        Rectangle mouse = new Rectangle(Mouse.getX(), Display.getHeight() - Mouse.getY(), 1, 1);
        this.hovering = this.getHitBox().intersects(mouse);
        if (this.isClicked())
        {
            this.stickyX = this.getHitBox().getX() + (this.getHitBox().getWidth() / 2);
            this.stickyY = Display.getHeight() - (this.getHitBox().getY() + (this.getHitBox().getHeight() / 2));
            this.buttonText = title + ": _";
            this.keyValue = -1;
        }
        if (this.keyValue == -1)
        {
            Mouse.setCursorPosition(stickyX, stickyY);
            while (Keyboard.next())
            {
                if (Keyboard.next())
                {
                    continue;
                }
                if (Keyboard.getEventKeyState())
                {
                    this.keyValue = Keyboard.getEventKey();
                }
            }
            if (this.keyValue > -1)
            {
                this.buttonText = title + ": " + intKeyToPrettyStr(this.keyValue);
                updateKeybind(this.title, this.keyValue);
            }
        }
        boolean state = Mouse.isButtonDown(0);
        if (state != prevState && state)
        {
            clickedOnce = true;
        }
        else
        {
            clickedOnce = false;
        }
        prevState = state;
    }

    private String intKeyToPrettyStr(int keyVal)
    {
        String ret = Keyboard.getKeyName(keyVal);
        ret = ret.substring(0, 1) + ret.substring(1).toLowerCase();
        return ret;
    }

    private void updateKeybind(String keybind, int newVal)
    {
        Keybinds kb = Twotris.getInstance().keybinds;
        switch (keybind)
        {
            case "Left":
                kb.moveLeft = newVal;
                break;
            case "Right":
                kb.moveRight = newVal;
                break;
            case "Rotate":
                kb.rotate = newVal;
                break;
            case "Accel":
                kb.accelerate = newVal;
                break;
            case "Place":
                kb.place = newVal;
                break;
            case "Menu/Back":
                kb.menuBack = newVal;
                break;
            case "Screenshot":
                kb.screenshot = newVal;
                break;
        }
        kb.updateConfig();
    }

    /**
     * @param delta
     * @return true/false depending on if the mouse has indeed encountered the button’s hitbox.
     * @throws none
     * @isClicked This method is used to detect whether the button has been clicked by testing whether the hitbox of the mouse has intercepted the hitbox of the button.
     */
    public boolean isClicked()
    {
        Rectangle mouse = new Rectangle(Mouse.getX(), Display.getHeight() - Mouse.getY(), 1, 1);
        if (this.getHitBox().intersects(mouse) && this.clickedOnce && this.keyValue > -1)
        {
            AudioManager.getInstance().play("select");
            return true;
        }
        return false;
    }

    public int getKeyValue()
    {
        return this.keyValue;
    }
}
