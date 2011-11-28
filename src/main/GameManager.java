package main;

import java.awt.Color;
import java.awt.geom.Point2D;

import turtle.Turtle;
import entities.Ghost;
import entities.Player;

public class GameManager
{
	private Player player;	
	private Ghost[] ghosts;
	private Level level;
	
	private Turtle turtle;
	
	public GameManager(Turtle turtle)
	{
		setTurtle(turtle);
		
		//Start new game
		newGame();
	}
	
	public void newGame()
	{
		level = new Level();
		
		player = new Player(new Point2D.Double(level.spawn.getX(), level.spawn.getY()), this);
		ghosts = new Ghost[4];
		ghosts[0] = new Ghost(Color.red, new Point2D.Double(10, 13), this);
		ghosts[1] = new Ghost(Color.pink, new Point2D.Double(11, 13), this);
		ghosts[2] = new Ghost(Color.cyan, new Point2D.Double(11, 12), this);
		ghosts[3] = new Ghost(Color.orange, new Point2D.Double(11, 14), this);
		level.setPlayer(player);
	}

	/**
	 * @return the turtle
	 */
	public Turtle getTurtle()
	{
		return turtle;
	}

	/**
	 * @param turtle the turtle to set
	 */
	public void setTurtle(Turtle turtle)
	{
		this.turtle = turtle;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer()
	{
		return player;
	}

	/**
	 * @return the level
	 */
	public Level getLevel()
	{
		return level;
	}
}
