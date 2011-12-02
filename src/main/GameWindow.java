package main;

import turtle.*;

import entities.Ghost;

import java.awt.BorderLayout;
import java.lang.Thread;

public class GameWindow extends TurtleGUI implements Runnable
{
	private static final long serialVersionUID = 1L;
	static final int UPDATE_RATE = 5;  // number of game update per second
	
	private Game game;
	private Thread thread;
	private boolean running = false;
	
	private InputHandler inputHandler;
	
	public GameWindow(Game game, Turtle t)
	{	
		super(t);
		setGame(game);
		inputHandler = new InputHandler(game.getGameManager());

        ScorePane score = new ScorePane(game.getGameManager());
        add(score, BorderLayout.SOUTH);
		
		addKeyListener(inputHandler);
		addFocusListener(inputHandler);
		
		pack();
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
		int frames = 0;

		double unprocessedSeconds = 0;
		long lastTime = System.nanoTime();
		double secondsPerTick = 1 / (UPDATE_RATE * getGameManager().getSpeed());

		requestFocus();

		while (running) {
			long now = System.nanoTime();
			long passedTime = now - lastTime;
			lastTime = now;
			if (passedTime < 0) passedTime = 0;
			if (passedTime > 100000000) passedTime = 100000000;

			unprocessedSeconds += passedTime / 1000000000.0;

			boolean ticked = false;
			while (unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;

				getGameManager().incrementTime();
				if (getGameManager().getTime() % 60 == 0) {
					System.out.println(frames + " fps");
					lastTime += 1000;
					frames = 0;
				}
			}

			if (ticked) {
				render();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	public void tick() {
		if (hasFocus()) {
			getGame().tick(inputHandler.keys);
		}
	}
	
	public void render()
	{
		GameManager gm = game.getGameManager();
		
		gm.getTurtle().restart();
		gm.getPlayer().render(getTurtle());
		gm.getLevel().render(getTurtle());
		Ghost[] ghosts = gm.getLevel().getGhosts();
		for(Ghost ghost : ghosts) {
			ghost.render(getTurtle());
		}
		
		repaint();
		
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
