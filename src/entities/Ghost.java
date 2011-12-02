package entities;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import main.GameManager;
import main.Level;
import main.LevelTile;

import turtle.Turtle;

public class Ghost extends MoveableEntity
{	
	public int time;
	private Color color;
	private boolean left = false;
	private boolean scared = false;
	private Point2D spawn;
	private int wait;
	
	public Ghost(Color color, Point2D position, int wait, GameManager gm)
	{
		super(position, gm);
		this.setSpawn(position);
		this.color = color;
		this.wait = wait;
	}
	
	public void tick()
	{
		time++;

		if(hasLeft() == false) {
			setDirection(Ghost.DIRECTION_UP);
			move(getNewPosition(position, direction));
		} else {
			Random rand = new Random();
			// Get available directions
			Point2D tempPosition = (Point2D) position.clone();
			ArrayList<Integer> possibleDirections = new ArrayList<Integer>();
			 
			if(position.equals(getGameManager().getPlayer().getPosition())) {
				return;
			}
			
			for(int i=0; i<4; i++) {
				Point2D newPosition = getNewPosition(position, i);
				if(newPosition.equals(getGameManager().getPlayer().getPosition())) {
					possibleDirections.clear();
					possibleDirections.add(i);
					break;
				} else if(getOppositeDirection(direction) != i && move(newPosition)) {
					possibleDirections.add(i);
				}
			}
			
			setPosition(tempPosition);
			if(!possibleDirections.isEmpty()) {
				Object[] possibleDirectionsArr = possibleDirections.toArray();
				int direction = (Integer) possibleDirectionsArr[rand.nextInt(possibleDirectionsArr.length)];
				setDirection(direction);
				move(getNewPosition(tempPosition, direction));
			}
		}
	}
	
	private int getOppositeDirection(int direction)
	{
		if(direction == Ghost.DIRECTION_UP) {
			return DIRECTION_DOWN;
		} else if(direction == Ghost.DIRECTION_RIGHT) {
			return DIRECTION_LEFT;
		} else if(direction == Ghost.DIRECTION_DOWN) {
			return DIRECTION_UP;
		} else if(direction == Ghost.DIRECTION_LEFT) {
			return DIRECTION_RIGHT;
		} else {
			return DIRECTION_LEFT;
		}
	}
	
	private Point2D getNewPosition(Point2D position, int direction)
	{
		Point2D newPosition = (Point2D) getPosition().clone(); 
		if(direction == DIRECTION_UP) {
			newPosition.setLocation(position.getX(), position.getY() - 1);
		} else if(direction == DIRECTION_RIGHT) {
			newPosition.setLocation(position.getX() + 1, position.getY());
		} else if(direction == DIRECTION_DOWN) {
			newPosition.setLocation(position.getX(), position.getY() + 1);
		} else if(direction == DIRECTION_LEFT) {
			newPosition.setLocation(position.getX() - 1, position.getY());
		}
		
		return newPosition;
	}
	
	public void render(Turtle t)
	{
		Point2D point = new Point2D.Double(position.getX()*20+2, (position.getY()*20)+20);
		t.movePen(point);
		
		if(isScared()) {
			t.draw(new shapes.Ghost(Color.blue));
		} else {
			t.draw(new shapes.Ghost(color));
		}
	} 
	
	protected boolean move(Point2D position)
	{
		Level level = getGameManager().getLevel();
		if(checkMove(position) == false) {
			return false;
		}

		if(level.getTile(position).getType() == LevelTile.TYPE_DOOR && hasLeft() == true) {
			return false;
		} else if (level.getTile(position).getType() == LevelTile.TYPE_DOOR && hasLeft() == false){
			setLeft(true);
		}
		
		//If entity lands on pipe tile teleport to exit
		try {
			if(level.getTile(position).getType() == LevelTile.TYPE_PIPE) {
				position = level.getExitPipe(position);
			}
		} catch (Exception e) {
			return false;
		}
		
		setPosition(position);
		return true;
	}

	public void respawn()
	{
		left = false;
		position = getSpawn();
	}
	
	/**
	 * @return the scared
	 */
	public boolean isScared()
	{
		return scared;
	}

	/**
	 * @param scared the scared to set
	 */
	public void setScared(boolean scared)
	{
		this.scared = scared;
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

	/**
	 * @return the wait
	 */
	public int getWait()
	{
		return wait;
	}

	/**
	 * @param wait the wait to set
	 */
	public void setWait(int wait)
	{
		this.wait = wait;
	}

	/**
	 * @return the spawn
	 */
	public Point2D getSpawn()
	{
		return spawn;
	}

	/**
	 * @param spawn the spawn to set
	 */
	public void setSpawn(Point2D spawn)
	{
		this.spawn = spawn;
	}
}
