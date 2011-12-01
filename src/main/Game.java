package main;

import turtle.*;

import entities.Ghost;

public class Game
{
	private int time;
	private GameManager gameManager;
		
	public Game(GameManager gameManager)
	{
		this.gameManager = gameManager;
	}
	
	public static void main(String[] args)
	{
		Turtle turtle = new Turtle();
		
		GameManager gameManager = new GameManager(turtle);
		Game game = new Game(gameManager);
		
		GameWindow window = new GameWindow(game, turtle);
		window.start();
	}
	
	public void tick(boolean[] keys) {
		time++;
		
		if(getGameManager().isGameOver()) {
			System.out.println("Game Over! Score: " + getGameManager().getScore());
			Runtime.getRuntime().exit(0);
		}
		
		getGameManager().getPlayer().tick();
		
		Ghost[] ghosts = getGameManager().getLevel().getGhosts();
		if(time > ghosts[0].getWait()) {
			ghosts[0].tick();
		}
		if(time > ghosts[1].getWait()) {
			ghosts[1].tick();
		}
		if(time > ghosts[2].getWait()) {
			ghosts[2].tick(); 
		}
		if(time > ghosts[3].getWait()) {
			ghosts[3].tick();
		}
		
		getGameManager().getLevel().tick();
	}
	
	/**
	 * @return the time
	 */
	public int getTime()
	{
		return time;
	}

	/**
	 * @return the gameManager
	 */
	public GameManager getGameManager()
	{
		return gameManager;
	}
}
