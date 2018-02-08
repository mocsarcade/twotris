package com.github.capstone;

import com.github.capstone.Manager.AudioManager;
import com.github.capstone.Manager.ScreenshotManager;
import com.github.capstone.Scene.MainMenu;
import com.github.capstone.Scene.Scene;
import com.github.capstone.Util.Config;
import com.github.capstone.Util.FileUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.opengl.PNGDecoder;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;


public class Twotris
{
    public static void main(String[] args)
    {
        // Copies the natives folder FROM the compiled jar if it doesn't exist
        if (!(new File("natives").exists()))
        {
            FileUtils.copyResourcesRecursively(ResourceLoader.getResource("natives"), new File("natives"));
        }

        // Sets the LWJGL Libraries path to the natives folder
        System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
        new Twotris();
    }

    private static Twotris instance;
    public Config config;
    public ScreenshotManager screenshotManager;
    private boolean fullscreen;
/**
@Twotris
This is a constructor method, creating a config, a screenshot manager, and not initially setting the game to fullscreen. Also initializes the sounds, the display and the game itself. 
@param none
@return none
@throws none
*/
    private Twotris()
    {
        instance = this;
        this.config = new Config();
        this.screenshotManager = new ScreenshotManager();
        this.fullscreen = false;
        initGL();
        initSounds();
        initGame();
    }
/**
@getInstance
This method returns an instance 
@param none
@return instance
@throws none
*/
    public static Twotris getInstance()
    {
        return instance;
    }
/**
@initSounds
This method loads the sounds for menus.  
@param none
@return none
@throws none
*/
    private void initSounds()
    {
        System.out.println("Loading sounds..");
        try
        {
            AudioManager.getInstance().loadSample("pause", "assets/sounds/menu/game_pause.wav".replace("/", File.separator));
            AudioManager.getInstance().loadSample("resume", "assets/sounds/menu/game_resume.wav".replace("/", File.separator));
            AudioManager.getInstance().loadSample("select", "assets/sounds/menu/menu_select.wav".replace("/", File.separator));
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println("Sounds Loaded!");
    }

/**
@initGame
This method creates the main menu, and proceeds to the next scene when prompted, destroying the old instance of the audio manager and display. 
@param none
@return none
@throws none
*/
    private void initGame()
    {
        MainMenu mainMenu = new MainMenu();
        Scene currScene = mainMenu;

        while (currScene.go())
        {
            currScene = currScene.nextScene();

            if (currScene == null)
            {
                currScene = mainMenu;
            }
        }

        AudioManager.getInstance().destroy();
        Display.destroy();
    }
/**
@initGL
This method is used for displaying the game and centering the window. 
@param none
@return none
@throws none
*/
    public void initGL()
    {
        setDisplayMode(800, 600, fullscreen);
        // TODO: Official name goes here:
        Display.setTitle("Senior Capstone");
        // Centers the window
        Display.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - (800 / 2),
                (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - (600 / 2));

        initIcon();

        try
        {
            Display.create();
        }
        catch (LWJGLException e)
        {
            System.out.println("Caught an LWJGL exception when creating the display. Quitting..");
            System.out.println(e.getMessage());
            System.exit(0);
        }

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glClearColor(0F, 0F, 0F, 0.0f);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glViewport(0, 0, 800, 600);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 800, 600, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

  /**
@setDisplayMode
This method is used for displaying the game, and is sourced from: http://wiki.lwjgl.org/wiki/LWJGL_Basics_5_(Fullscreen).html
@param width      The width of the display required
@param height     The height of the display required
@param fullscreen True if we want fullscreen mode
@return none
@throws none
*/
    public void setDisplayMode(int width, int height, boolean fullscreen)
    {
        if ((Display.getDisplayMode().getWidth() == width) && (Display.getDisplayMode().getHeight() == height) && (Display.isFullscreen() == fullscreen))
            return;

        try
        {
            org.lwjgl.opengl.DisplayMode targetDisplayMode = null;

            if (fullscreen)
            {
                org.lwjgl.opengl.DisplayMode[] modes = Display.getAvailableDisplayModes();
                int freq = 0;

                for (int i = 0; i < modes.length; i++)
                {
                    org.lwjgl.opengl.DisplayMode current = modes[i];

                    if ((current.getWidth() == width) && (current.getHeight() == height))
                    {
                        if ((targetDisplayMode == null) || (current.getFrequency() >= freq))
                        {
                            if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel()))
                            {
                                targetDisplayMode = current;
                                freq = targetDisplayMode.getFrequency();
                            }
                        }

                        if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) && (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency()))
                        {
                            targetDisplayMode = current;
                            break;
                        }
                    }
                }
            }
            else
            {
                targetDisplayMode = new DisplayMode(width, height);
            }

            if (targetDisplayMode == null)
            {
                System.out.println("Failed to find value mode: " + width + "x" + height + " fs=" + fullscreen);
                return;
            }

            Display.setDisplayMode(targetDisplayMode);
            Display.setFullscreen(fullscreen);
            // Stupid LWJGL stuff. Thanks Ellpeck!
            Display.setResizable(false);
            Display.setResizable(true);
            Display.setVSyncEnabled(true);
        }
        catch (LWJGLException e)
        {
            System.err.println("Unable to setup mode " + width + "x" + height + " fullscreen=" + fullscreen + e);
        }
    }

   /**
@loadIcon
This method is used for displaying the icon image. Sourced from: http://forum.lwjgl.org/index.php?topic=3422.0 
@param url the URL of the file needed
@return The ByteBuffer of the PNG located at the passed URL
@throws IOException
*/
    private ByteBuffer loadIcon(URL url) throws IOException
    {
        System.out.println("Loaded icon " + url.getPath().replace("/C:/", "C:/"));
        InputStream is = url.openStream();
        try
        {
            PNGDecoder decoder = new PNGDecoder(is);
            ByteBuffer buffer = ByteBuffer.allocateDirect(decoder.getWidth() * decoder.getHeight() * 4);
            decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.RGBA);
            buffer.flip();
            return buffer;
        }
        finally
        {
            is.close();
        }
    }
/**
@initIcon
This method is used for initializing the icon, setting the image found in loadIcon as the actual icon. 
@param none
@return none
@throws none
*/
    private void initIcon()
    {
        try
        {
            Display.setIcon(new ByteBuffer[]{
                    loadIcon(ResourceLoader.getResource("assets/icon/icon16.png".replace("/", File.separator))),
                    loadIcon(ResourceLoader.getResource("assets/icon/icon32.png".replace("/", File.separator))),
                    });
        }
        catch (IOException e)
        {
            System.out.println("Icon file not found; using default.");
            Display.setIcon(new ByteBuffer[]{});
        }
    }
}
