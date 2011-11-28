package entities;

import turtle.TurtleShape;
import main.GameManager;

public abstract class Entity
{
	private GameManager gm;
	
	public Entity(GameManager gm)
	{
		this.gm = gm;
	}

	/**
	 * @return the gm
	 */
	public GameManager getGameManager()
	{
		return gm;
	}
}
