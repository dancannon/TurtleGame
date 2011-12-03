package main;

import turtle.*;

import entities.Ghost;

public class Game
{
	public static Game game;
	public static boolean soundPlaying = false;
	private GameManager gameManager;
	private GameWindow window;
	
	final static int START_TIME = 50;
		
	public Game(GameManager gameManager)
	{
		this.gameManager = gameManager;
	}
	
	public static void main(String[] args)
	{
		Turtle turtle = new Turtle();
		
		GameManager gameManager = new GameManager(turtle);
		game = new Game(gameManager);
		
		game.window = new GameWindow(game, turtle);
		game.window.start();
	}
	
	public void tick(boolean[] keys) {
		try{
			if(getGameManager().isGameOver()) {
				getGameManager().setStatus("Game Over!");
				getGameManager().setTime(-1);
			}
			
			//If game time is at -1 then end tick now
			if(getGameManager().getTime() == -1) {
				return;
			}
			
			getGameManager().incrementTime();
			
			if(getGameManager().getTime() == 1) {
	        	SoundEffect.START.play();
			}			
			if(getGameManager().getTime() < START_TIME) {
				return;
			}
			if(getGameManager().getTime() == START_TIME) {
				getGameManager().setStatus("");
			}
			
			if(getGameManager().getLevel().isLevelComplete()) {
				if(getGameManager().getLevelID() == 10) {
					getGameManager().setStatus("Epic Win!");
					getGameManager().setTime(-1);
				} else {
					getGameManager().setStatus("Level Complete.");
					Thread.sleep(5000);
					getGameManager().newLevel(getGameManager().getLevelID() + 1);
				}
			}
			
			getGameManager().getPlayer().tick();
			
			Ghost[] ghosts = getGameManager().getLevel().getGhosts();
			if(getGameManager().getTime() > START_TIME + ghosts[0].getWait()) {
				ghosts[0].tick();
			}
			if(getGameManager().getTime() > START_TIME+ ghosts[1].getWait()) {
				ghosts[1].tick();
			}
			if(getGameManager().getTime() > START_TIME + ghosts[2].getWait()) {
				ghosts[2].tick(); 
			}
			if(getGameManager().getTime() > START_TIME + ghosts[3].getWait()) {
				ghosts[3].tick();
			}
			
			getGameManager().getLevel().tick();
		} catch(Exception e) {
			 e.printStackTrace();
		}
	}

	/**
	 * @return the gameManager
	 */
	public GameManager getGameManager()
	{
		return gameManager;
	}
	

	/**
	 * @return the window
	 */
	public GameWindow getWindow()
	{
		return window;
	}
}
