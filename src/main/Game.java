package main;

import turtle.*;

import java.awt.event.KeyEvent;

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

		boolean up = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_NUMPAD8];
		boolean down = keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_NUMPAD2];
		boolean left = keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_NUMPAD4];
		boolean right = keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_NUMPAD6];
		
//TODO: Decide whether to add pause
//		if (keys[KeyEvent.VK_ESCAPE]) {
//			keys[KeyEvent.VK_ESCAPE] = false;
//			if (menu == null) {
//				setMenu(new PauseMenu());
//			}
//		}
		
		getGameManager().getPlayer().tick();
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
