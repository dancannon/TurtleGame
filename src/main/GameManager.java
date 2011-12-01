package main;

import java.awt.geom.Point2D;

import turtle.Turtle;
import entities.Player;

public class GameManager
{
	private Player player;	
	private Level level;
	private int levelID = 1;
	private int score;
	private int ghostsDead;
	
	private double speed = 1;
	static final double[] SPEED_MODIFIERS = {Double.NaN, 1, 1.1, 1.2, 1.3, 1.4, 1.5, 1.75, 2.0, 2.5, 5};
	
	private Turtle turtle;
	
	public GameManager(Turtle turtle)
	{
		setTurtle(turtle);
		
		//Start new game
		newGame();
	}
	
	public void newGame()
	{
		level = Level.loadLevel(this);
		setSpeed(getSpeed() * SPEED_MODIFIERS[levelID]);
		
		player = new Player(new Point2D.Double(level.getSpawn().getX(), level.getSpawn().getY()), this);
		level.setPlayer(player);
		
		System.out.println("Current Score:" + getScore());
	}
	
	public void newLevel(int levelID)
	{		
		setSpeed(getSpeed() * SPEED_MODIFIERS[levelID]);
		
		this.level.resetMap();
		this.level.getPlayer().setPosition(new Point2D.Double(this.level.getSpawn().getX(), this.level.getSpawn().getY()));
		
		System.out.println("Starting level " + levelID);
		
		this.setLevelID(levelID);
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

	/**
	 * @param score the score to set
	 */
	public void setScore(int score)
	{
		this.score = score;
	}
	
	/**
	 * @param amount The amount to increment the score by
	 */
	public void incrementScore(int amount)
	{
		this.score += amount;
	}

	/**
	 * @return the score
	 */
	public int getScore()
	{
		return score;
	}
	
	public boolean isGameOver()
	{
		if(getPlayer().getLives() <= 0) {
			return true;
		}
		
		return false;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(double speed)
	{
		this.speed = speed;
	}

	/**
	 * @return the speed
	 */
	public double getSpeed()
	{
		return speed;
	}

	/**
	 * @param levelID the levelID to set
	 */
	public void setLevelID(int levelID)
	{
		this.levelID = levelID;
	}

	/**
	 * @return the levelID
	 */
	public int getLevelID()
	{
		return levelID;
	}

	/**
	 * @return the ghostsDead
	 */
	public int getGhostsDead()
	{
		return ghostsDead;
	}

	/**
	 * @param ghostsDead the ghostsDead to set
	 */
	public void incrementGhostsDead()
	{
		ghostsDead++;
	}
}
