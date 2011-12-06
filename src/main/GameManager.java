package main;

import java.awt.geom.Point2D;

import turtle.Turtle;
import entities.Player;

public class GameManager
{
	private int time;
	private Player player;	
	private Level level;
	private int levelID = 8;
	public int score;
	public String status = "";
	private int ghostsDead;
	
	private double speed = 1;
	static final double[] SPEED_MODIFIERS = {Double.NaN, 1, 1.2, 1.4, 1.5, 1.6, 1.75, 2.0, 2.5, 3.5, 4};
	
	private Turtle turtle;
	
	// listener list for change and pen events
    private ChangeListener scoreChangeListener;
    private ChangeListener statusChangeListener;
    private ChangeListener livesChangeListener;
	
	public GameManager(Turtle turtle)
	{
		setTurtle(turtle);
		
		//Start new game
		newGame();
	}
	
	public void newGame()
	{
		setTime(0);
    setScore(0);
		level = Level.loadLevel(this);
		setSpeed(getSpeed() * SPEED_MODIFIERS[levelID]);
		
		player = new Player(new Point2D.Double(level.getSpawn().getX(), level.getSpawn().getY()), this);
    player.resetLives();
		level.setPlayer(player);
		
    fireLivesChange();
    fireScoreChange();

		setStatus("Ready!");
	}
	
	public void resetGame()
	{
		setTime(0);
		setSpeed(getSpeed() * SPEED_MODIFIERS[levelID]);
		setLevelID(1);
		setScore(0);
		
		this.level.resetMap();
		this.level.getPlayer().setPosition(new Point2D.Double(this.level.getSpawn().getX(), this.level.getSpawn().getY()));
    this.level.getPlayer().resetLives();
		
    fireLivesChange();
    fireScoreChange();

		setStatus("Ready!");
	}
	
	public void newLevel(int levelID)
	{		
		setTime(0);
    
		setSpeed(getSpeed() * SPEED_MODIFIERS[levelID]);
		
		this.level.resetMap();
		this.level.getPlayer().setPosition(new Point2D.Double(this.level.getSpawn().getX(), this.level.getSpawn().getY()));
	

		setStatus("Starting level " + levelID);
		
		this.setLevelID(levelID);
	}

	public void setScoreListener(ChangeListener l)
	{
		scoreChangeListener = l;
	}
	
	public void setStatusListener(ChangeListener l)
	{
		statusChangeListener = l;
	}
	
	public void setLivesListener(ChangeListener l)
	{
		livesChangeListener = l;
	}
	
	public void fireLivesChange()
	{
		if(livesChangeListener != null) {
			livesChangeListener.propertyChanged();
		}
	}
	
	private void fireScoreChange()
	{
		if(scoreChangeListener != null) {
			scoreChangeListener.propertyChanged();
		}
	}
	
	private void fireStatusChange()
	{
		if(statusChangeListener != null) {
			statusChangeListener.propertyChanged();
		}
	}
	
	public boolean isGameOver()
	{
		if(getPlayer().getLives() <= 0) {
			return true;
		}
		
		return false;
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
		fireScoreChange();
	}
	
	/**
	 * @param amount The amount to increment the score by
	 */
	public void incrementScore(int amount)
	{
		this.score += amount;
		fireScoreChange();
	}

	/**
	 * @return the score
	 */
	public int getScore()
	{
		return score;
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

	/**
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status)
	{
		this.status = status;
		fireStatusChange();
	}

	/**
	 * @return the time
	 */
	public int getTime()
	{
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(int time)
	{
		this.time = time;
	}
	
	public void incrementTime()
	{
		time++;
	}
}
