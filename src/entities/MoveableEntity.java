package entities;

import java.awt.geom.Point2D;

import main.GameManager;
import main.LevelTile;

abstract public class MoveableEntity extends Entity
{
	protected Point2D position;
	protected int direction = 3;
	
	public MoveableEntity(Point2D position, GameManager gm)
	{
		super(gm);
		
		this.setPosition(position);
	}

	/**
	 * @return the position
	 */
	public Point2D getPosition()
	{
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Point2D position)
	{
		this.position = position;
	}
	
	public void setDirection(int direction)
	{
		this.direction = direction;
	}
	
	protected boolean checkMove(Point2D position)
	{
		try {		
			//Check if move is within the bounds
			if(position.getX() < 0.0 && position.getX() > 26.0 && position.getY() < 0.0 && position.getY() > 23) {
				return false;
			}
			
			//Check if move is blocked
			if(getGameManager().getLevel().getTile(position).getType() == LevelTile.TYPE_WALL) {
				return false;
			}
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
