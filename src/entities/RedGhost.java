package entities;

import java.awt.Color;
import java.awt.geom.Point2D;
import main.GameManager;

public class RedGhost extends Ghost
{	
	private boolean left = true;
	
	public RedGhost(Point2D position, int wait, GameManager gm)
	{
		super(new Color(255,0,0), position, wait, gm);
	}

	public void respawn()
	{
		position = getSpawn();
	}
	
	/**
	 * @return the left
	 */
	public boolean hasLeft()
	{
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(boolean left)
	{
		this.left = left;
	}
}
