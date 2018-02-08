package com.github.capstone.Manager;

/**
 * @author Craig Tanis
 * <p>
 * Code obtained from https://github.com/ctanis/cpsc3520_fa17/blob/master/pongy/src/AudioManager.java
 */

import com.github.capstone.Twotris;
import org.lwjgl.openal.AL;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.HashMap;


/**
 * A utility class for caching sounds from files and enabling the
 * straightforward triggering of those sounds (samples and loops)
 */
public class AudioManager
{
    private static AudioManager instance;
    private HashMap<String, AudioWrapper> sounds;
/**
@AudioManager
This constructor method is used creating a new set of sounds given a Hashmap with AudioWrapper and a String. 
@param none
@return none
@throws none
*/
    private AudioManager()
    {
        sounds = new HashMap<String, AudioWrapper>();
    }

   /**
@getInstance
This method gets a new instance of the audiomanager. 
@param none
@return the singleton AudioManager instance
@throws none
*/
    public static AudioManager getInstance()
    {
        if (instance == null) instance = new AudioManager();
        return instance;
    }
/**
   			 @loadSample
This method loads a one-shot sample by extracting the file extension of the file and putting the files into the sounds Hashmap, using the AudioWrapper. 
     			@param name identifier for loaded sound
    			@param path path to file
    		 */
    public void loadSample(String name, String path) throws IOException
    {
        // extract file extension
        String mode = path.substring(path.lastIndexOf('.') + 1).toUpperCase();

        Audio tmp = AudioLoader.getAudio(mode, ResourceLoader.getResourceAsStream(path));
        sounds.put(name, new AudioWrapper(AudioType.SAMPLE, tmp));
    }

/**
   			 @loadLoop
This method loads a looping music track by extracting the file extension of the file and putting the files into the sounds Hashmap, using the AudioWrapper. 
@param name identifier for loaded sound		
@param path path to file
@return none
@throws none
    		 */
    public void loadLoop(String name, String path) throws IOException
    {
        // extract file extension
        String mode = path.substring(path.lastIndexOf('.') + 1).toUpperCase();

        Audio tmp = AudioLoader.getStreamingAudio(mode, ResourceLoader.getResource(path));
        sounds.put(name, new AudioWrapper(AudioType.LOOP, tmp));
    }

   /**
			@get
			This method gets the sound associated with the name identified. 
			@param name the loaded sound identifier. 
			@return the sound found at the index referenced by the name given. 
			@throws none
		*/
    public AudioWrapper get(String name)
    {
        return sounds.get(name);
    }

    /**
			@play
			This method plays the sound associated with the name identified. 
			@param name the loaded sound identifier. 
			@return the sound found at the index referenced by the name given. 
			@throws none
		*/
    public void play(String name)
    {
        sounds.get(name).play();
    }

    /**
			@play
			This method plays the sound associated with the name identified. 
			@param name the loaded sound identifier. 
			@param vol the volume of sound (0.0 to 1.0 floating point number) 
			@return the sound found at the index referenced by the name given. 
			@throws none
		*/
    public void play(String name, float vol)
    {
        sounds.get(name).play(vol);
    }

 /**
@update
This method is used for updating the piece’s position and rotation, called once per frame.  
@param none
@return none
@throws none
*/
    public void update()
    {
        SoundStore.get().poll(0);
    }

/**
			@destroy
			This method destroys the entity it is called for. 
			@param none 
			@return none
			@throws none
		*/
    public void destroy()
    {
        AL.destroy();
    }

/**
			@AudioType
			This allows two types of audio, a loop and a sample.  
			@param none
			@return none 
			@throws none
		*/
    private enum AudioType
    {
        LOOP, SAMPLE
    }

		/**
			This class creates a handle on a loaded audio object, to play the same sound repeatedly without having to look it up each time. Uses the variables sound (Audio) and type (AudioType)
		*/

    public class AudioWrapper
    {
        private Audio sound;
        private AudioType type;
		/**
			@AudioWrapper
			This constructor method collects an Audiotype ‘t’ and an Audio ‘a’ to set as ‘type’ and ‘sound’ respectively. 
			@param t the audiotype to be referenced. 
			@param a the sound being referenced.  
			@return none
			@throws none
		*/
        private AudioWrapper(AudioType t, Audio a)
        {
            type = t;
            sound = a;
        }

       /**
			@play
			This method plays sound at the config’s volume. 
			@param none
			@return none 
			@throws none
		*/
        public void play()
        {
            play(Twotris.getInstance().config.volume);
        }

      /**
			@play
			This method plays sound at the given volume, preventing sound if vol=0.0. 
			@param vol a floating point number from 0.0 to 1.0
			@return none 
			@throws none
		*/
        public void play(float vol)
        {
            // prevents the sound from being played if sound is "off"
            if (vol <= 0F)
                return;

            switch (type)
            {
                case LOOP:
                    sound.playAsMusic(1.0f, vol, true);
                    break;

                case SAMPLE:
                    sound.playAsSoundEffect(1.0f, vol, false);
                    break;
            }
        }

    }
}
