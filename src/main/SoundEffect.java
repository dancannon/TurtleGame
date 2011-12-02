package main;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

/**
 * I take no credit for this code! REFERENCE:
 * http://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
 * 
 * This enum encapsulates all the sound effects of a game, so as to separate the
 * sound playing codes from the game codes. 1. Define all your sound effect
 * names and the associated wave file. 2. To play a specific sound, simply
 * invoke SoundEffect.SOUND_NAME.play(). 3. You might optionally invoke the
 * static method SoundEffect.init() to pre-load all the sound files, so that the
 * play is not paused while loading the file for the first time. 4. You can use
 * the static variable SoundEffect.volume to mute the sound.
 */
public enum SoundEffect {
	DIEING("/res/sounds/PacmanDieing.wav"),
	START("/res/sounds/PacmanStart.wav"), 
	CHOMP("/res/sounds/PacmanChomp.wav");

	// Nested class for specifying volume
	public static enum Volume {
		MUTE, LOW, MEDIUM, HIGH
	}

	public static Volume volume = Volume.LOW;

	// Each sound effect has its own clip, loaded with its own sound file.
	private Clip clip;
	
	public boolean loop = false;
	private boolean loopStarted;

	// Constructor to construct each element of the enum with its own sound
	// file.
	SoundEffect(String soundFileName)
	{
		try {
			// Use URL (instead of File) to read from disk and JAR.
			URL url = SoundEffect.class.getResource(soundFileName);
			// Set up an audio input stream piped from the sound file.
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
			// Get a clip resource.
			clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	// Play or Re-play the sound effect from the beginning, by rewinding.
	public void play()
	{
		if (volume != Volume.MUTE) {
			if (clip.isRunning())
				clip.stop(); // Stop the player if it is still running
			clip.setFramePosition(0); // rewind to the beginning
			clip.start(); // Start playing
		}
	}
	
	public void loop() 
	{
		new Thread(new Runnable() {
			public void run() {
				while(loop) {
					if (volume != Volume.MUTE && !clip.isRunning()) {
						clip.loop(Clip.LOOP_CONTINUOUSLY); // Start playing
					}
					
					try {
						Thread.sleep(clip.getMicrosecondLength() / clip.getFrameLength());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	public void startLoop()
	{
		if(loopStarted != true) {
			loop = true;
			loop();
		}
	}
	
	public void stopLoop()
	{
		loop = false;
		clip.loop(0);
	}
	
	public void stop()
	{
		clip.stop();
		clip.setFramePosition(0);
	}

	// Optional static method to pre-load all the sound files.
	static void init()
	{
		values(); // calls the constructor for all the elements
	}
}