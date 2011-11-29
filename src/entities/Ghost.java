package entities;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
	
	public Ghost(Color color, Point2D position, GameManager gm)
	{
		super(position, gm);
		this.color = color;
	}
	
	public void tick()
	{
		time++;
		Random rand = new Random();
		setDirection(rand.nextInt(4 - 0 + 1) + 0);
		
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
	
	public void render(Turtle t)
	{
		Point2D point = new Point2D.Double(position.getX()*20, position.getY()*20);
		t.movePen(point);
		
		t.draw(new shapes.Ghost(color));
	} 
	
	protected boolean move(Point2D position)
	{
		Level level = getGameManager().getLevel();
		if(checkMove(position) == false) {
			return false;
		}

		if(level.getTile(position).getType() == LevelTile.TYPE_DOOR && left == true) {
			return false;
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
}
