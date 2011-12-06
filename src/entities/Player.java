package entities;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

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
	public int powerupLeft = 0;
	private int lastKey;
	
	public Player(Point2D position, GameManager gm)
	{
		super(position, gm);
	}
	
	public void tick()
	{
		time++;
		Point2D tempPosition = (Point2D) position.clone();
		
		if(powerupLeft > 0) {
			powerupLeft--;
			if(powerupLeft == 0) {
				for(Ghost ghost : getGameManager().getLevel().getGhosts()) {
					ghost.setScared(false);
				}
			}
		}
		
		updateDirection();
		move(getNewPosition(tempPosition, direction));
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
	
	public void setLastKeyPressed(int code)
	{
		this.lastKey = code;
	}
	
	public void updateDirection()
	{
		if(lastKey != 0) {
			boolean up = lastKey == KeyEvent.VK_W || lastKey == KeyEvent.VK_UP || lastKey == KeyEvent.VK_NUMPAD8;
			boolean down = lastKey == KeyEvent.VK_S || lastKey == KeyEvent.VK_DOWN || lastKey == KeyEvent.VK_NUMPAD2;
			boolean left = lastKey == KeyEvent.VK_A || lastKey == KeyEvent.VK_LEFT || lastKey == KeyEvent.VK_NUMPAD4;
			boolean right = lastKey == KeyEvent.VK_D || lastKey == KeyEvent.VK_RIGHT || lastKey == KeyEvent.VK_NUMPAD6;

			int tempDirection = direction;
			Point2D tempPosition = position;
			
			if(up) {
				setDirection(Player.DIRECTION_UP);
			} else if(right) {
				setDirection(Player.DIRECTION_RIGHT);
			} else if(down) {
				setDirection(Player.DIRECTION_DOWN);
			} else if(left) {
				setDirection(Player.DIRECTION_LEFT);
			}
			
			//Try to move with this new direction
			if(this.move(getNewPosition(position, direction))) {
				lastKey = 0;
				setPosition(tempPosition);
			} else {
				setDirection(tempDirection);
				setPosition(tempPosition);
			}
		}
	}
	
	public void render(Turtle t)
	{
		Point2D point = new Point2D.Double(position.getX()*20+2, (position.getY()*20)+20);
		t.movePen(point);
		
		t.draw(new shapes.Player(getDirection()));
	}
	
	protected boolean move(Point2D position)
	{
		Level level = getGameManager().getLevel();
		int levelType = level.getTile(position).getType();
		if(checkMove(position) == false) {
			return false;
		}

		if(level.getTile(position).getType() == LevelTile.TYPE_DOOR) {
			return false;
		}

		
		//If entity lands on pipe tile teleport to exit
		try {
			if(levelType == LevelTile.TYPE_PIPE) {
				setPosition(level.getExitPipe(position));
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		
		/**
		 * If the player lands on a dot then replace the tile with air and increment the players score. 
		 * 
		 * If it is a powerup then allow the player to eat ghosts for a brief amount of time
		 */
		if(levelType == LevelTile.TYPE_DOT) {
			level.setTile(position, new LevelTile(LevelTile.TYPE_AIR));
			getGameManager().incrementScore(10);
		}
		
		if(levelType == LevelTile.TYPE_POWER_DOT) {
			powerupLeft = 40;
			for(Ghost ghost : getGameManager().getLevel().getGhosts()) {
				ghost.setScared(true);
			}
			level.setTile(position, new LevelTile(LevelTile.TYPE_AIR));
			getGameManager().incrementScore(50);
		}
		
		setPosition(position);
		return true;
	}


	/**
	 * reset the lives to 3
	 */
	public void resetLives()
	{
		this.lives = 3;
		getGameManager().fireLivesChange();
	}

	public void decrementLives()
	{
		this.lives--;
		getGameManager().fireLivesChange();
	}

	public void incrementLives()
	{
		this.lives++;
		getGameManager().fireLivesChange();
	}

	/**
	 * @return the lives
	 */
	public int getLives()
	{
		return lives;
	}
}
