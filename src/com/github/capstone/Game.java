package com.github.capstone;

import com.github.capstone.Manager.AudioManager;
import com.github.capstone.Manager.ScreenshotManager;
import com.github.capstone.Scene.Button;
import com.github.capstone.Scene.Menu;
import com.github.capstone.Scene.Scene;
import com.github.capstone.Util.Config;
import com.github.capstone.Util.FileUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.PNGDecoder;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

public class Game
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
        new Game();
    }

    private static Game instance;
    public Config config;
    public ScreenshotManager screenshotManager;
    private boolean fullscreen;

    public Game()
    {
        instance = this;
        this.config = new Config();
        this.screenshotManager = new ScreenshotManager();
        this.fullscreen = false;
        initGL();
        initGame();
    }

    public static Game getInstance()
    {
        return instance;
    }

    private void initGame()
    {
        Menu menu = new Menu();
//        menu.addButton(new Button(256, 64, "Play", new Color(200, 200, 200), new Color(85, 124, 0)), new Dungeon());
        menu.addButton(new Button(256, 64, "Quit Game", new Color(200, 200, 200), new Color(64, 0, 72)), null);
        menu.adjustButtons();
        Scene currScene = menu;

        while (currScene.go())
        {
            currScene = currScene.nextScene();

            if (currScene == null)
            {
                currScene = menu;
            }
        }

        AudioManager.getInstance().destroy();
        Display.destroy();
    }

    public void initGL()
    {
        setDisplayMode(this.config.resolutionWidth, this.config.resolutionHeight, fullscreen);
        // TODO: Official name goes here:
        Display.setTitle("Senior Capstone");
        // Centers the window
        Display.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - (this.config.resolutionWidth / 2),
                (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - (this.config.resolutionHeight / 2));

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
        GL11.glViewport(0, 0, this.config.resolutionWidth, this.config.resolutionHeight);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, this.config.resolutionWidth, this.config.resolutionHeight, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    /**
     * SOURCE: http://wiki.lwjgl.org/wiki/LWJGL_Basics_5_(Fullscreen).html
     *
     * @param width      The width of the display required
     * @param height     The height of the display required
     * @param fullscreen True if we want fullscreen mode
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
     * SOURCE: http://forum.lwjgl.org/index.php?topic=3422.0
     *
     * @return The ByteBuffer of the PNG located at the passed URL
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

    public void initIcon()
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
