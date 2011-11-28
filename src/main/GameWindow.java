package main;

import turtle.*;
import javax.swing.*;
import javax.swing.event.*;
import java.lang.Thread;

public class GameWindow extends TurtleGUI implements Runnable
{
	private static final long serialVersionUID = 1L;
	static final int UPDATE_RATE = 1;  // number of game update per second
	static final long UPDATE_PERIOD = 1000000000L / UPDATE_RATE;  // nanoseconds
	
	private Game game;
	private Thread thread;
	private boolean running = false;
	
	private InputHandler inputHandler;
	
	public GameWindow(Game game, Turtle t)
	{	
		super(t);
		setGame(game);
		inputHandler = new InputHandler(game.getGameManager());

		addKeyListener(inputHandler);
		addFocusListener(inputHandler);
	}
	
	public synchronized void start() {
		if (running) return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!running) return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long beginTime, timeTaken, timeLeft;
		
		while (true) {
			beginTime = System.nanoTime();
			
			tick();
			// Refresh the display
			repaint();
			
			// Delay timer to provide the necessary delay to meet the target rate
			timeTaken = System.nanoTime() - beginTime;
			timeLeft = (UPDATE_PERIOD - timeTaken) / 1000000;  // in milliseconds
			if (timeLeft < 10) timeLeft = 10;   // set a minimum
			try {
				// Provides the necessary delay and also yields control so that other thread can do work.
				Thread.sleep(timeLeft);
			} catch (InterruptedException ex) { }
		}
	}
	
	public void tick() {
		if (hasFocus()) {
			getGame().tick(inputHandler.keys);
		}
	}
	
	public void repaint()
	{
		GameManager gm = game.getGameManager();
		
		gm.getTurtle().restart();
		gm.getPlayer().render(gm.getTurtle());
		gm.getLevel().render();
	}

	/**
	 * @return the game
	 */
	public Game getGame()
	{
		return game;
	}

	/**
	 * @param game the game to set
	 */
	public void setGame(Game game)
	{
		this.game = game;
	}
	
	public GameManager getGameManager()
	{
		return game.getGameManager();
	}
	
	public boolean isRunning()
	{
		return running;
	}
}
