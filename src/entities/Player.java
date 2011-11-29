package entities;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import main.GameManager;
import main.Level;
import main.LevelTile;

import turtle.Turtle;

public class Player extends MoveableEntity
{	
	public int lastMove;
	public int time;
	private int lives = 3;
	public boolean dead = false;
	public boolean playing = false;
	private int lastKey;
	
	public Player(Point2D position, GameManager gm)
	{
		super(position, gm);
	}
	
	public void tick()
	{
		time++;

		Point2D newPosition = (Point2D) getPosition().clone(); 
		if(direction == 0) {
			newPosition.setLocation(position.getX(), position.getY() - 1);
		} else if(direction == 1) {
			newPosition.setLocation(position.getX() + 1, position.getY());
		} else if(direction == 2) {
			newPosition.setLocation(position.getX(), position.getY() + 1);
		} else if(direction == 3) {
			newPosition.setLocation(position.getX() - 1, position.getY());
		}
		
		move(newPosition);
	}
	
	public void setLastKeyPressed(int code)
	{
		this.lastKey = code;
		
		if(lastKey != 0) {
			boolean up = lastKey == KeyEvent.VK_W || lastKey == KeyEvent.VK_UP || lastKey == KeyEvent.VK_NUMPAD8;
			boolean down = lastKey == KeyEvent.VK_S || lastKey == KeyEvent.VK_DOWN || lastKey == KeyEvent.VK_NUMPAD2;
			boolean left = lastKey == KeyEvent.VK_A || lastKey == KeyEvent.VK_LEFT || lastKey == KeyEvent.VK_NUMPAD4;
			boolean right = lastKey == KeyEvent.VK_D || lastKey == KeyEvent.VK_RIGHT || lastKey == KeyEvent.VK_NUMPAD6;

			Point2D newPosition = getPosition(); 
			if(up) {
				setDirection(0);
			} else if(right) {
				setDirection(1);
			} else if(down) {
				setDirection(2);
			} else if(left) {
				setDirection(3);
			}
			
			lastKey = 0;
		}
	}
	
	public void render(Turtle t)
	{
		Point2D point = new Point2D.Double(position.getX()*20+2, (position.getY()*20)+20);
		t.movePen(point);
		
		t.draw(new shapes.Player());
	}
	
	protected boolean move(Point2D position)
	{
		Level level = getGameManager().getLevel();
		if(checkMove(position) == false) {
			return false;
		}

		if(level.getTile(position).getType() == LevelTile.TYPE_DOOR) {
			return false;
		}

		
		//If entity lands on pipe tile teleport to exit
		try {
			if(level.getTile(position).getType() == LevelTile.TYPE_PIPE) {
				position = level.getExitPipe(position);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		if(level.getTile(position).getType() == LevelTile.TYPE_DOT) {
			level.setTile(position, new LevelTile(LevelTile.TYPE_AIR));
			getGameManager().incrementScore(10);
			System.out.println("Current Score:" + getGameManager().getScore());
		}
		
		setPosition(position);
		return true;
	}


	/**
	 * @param lives the lives to set
	 */
	public void setLives(int lives)
	{
		this.lives = lives;
	}

	/**
	 * @return the lives
	 */
	public int getLives()
	{
		return lives;
	}
}
