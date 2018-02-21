package com.github.capstone.Util;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class Controllers
{
    private static Controllers instance;
    private Controller[] controllers;
    public int controller_a = -1;
    public int controller_b = -1;

    public static void init()
    {
        instance = new Controllers();
    }

    public static Controllers getInstance()
    {
        return instance;
    }

    private Controllers()
    {
        controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        for (int i = 0; i < controllers.length; i++)
        {
            if (controllers[i].getType().toString().toLowerCase().contains("gamepad"))
            {
                if (controller_a == -1)
                {
                    controller_a = i;
                }
                else if (controller_b == -1)
                {
                    controller_b = i;
                    break;
                }
            }
        }
        if (controller_b == -1)
        {
            // Make them equal
            controller_b = controller_a;
        }
    }

    public Event pollPlayerOne()
    {
        // drop out if we don't have a gamepad
        if (controller_a == -1)
        {
            return null;
        }

        controllers[controller_a].poll();
        EventQueue queue_a = controllers[controller_a].getEventQueue();
        Event event_a = new Event();
        if (queue_a.getNextEvent(event_a))
        {
            return event_a;
        }

        return null;
    }

    public Event pollPlayerTwo()
    {
        // drop out if we don't have a gamepad
        if (controller_b == -1)
        {
            return null;
        }

        controllers[controller_b].poll();
        EventQueue queue_b = controllers[controller_b].getEventQueue();
        Event event_b = new Event();
        if (queue_b.getNextEvent(event_b))
        {
            return event_b;
        }

        return null;
    }
}
