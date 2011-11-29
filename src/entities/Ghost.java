package entities;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import main.GameManager;
import main.LevelTile;

import turtle.Turtle;

public class Ghost extends MoveableEntity
{	
	public int time;
	public int lives = 3;
	public boolean dead = false;
	public boolean playing = false;
	private int lastKey;
	private Color color;
	
	public Ghost(Color color, Point2D position, GameManager gm)
	{
		super(position, gm);
		this.color = color;
	}
	
	public void tick()
	{
		
		time++;
		
		if(lastKey != 0) {
			boolean up = lastKey == KeyEvent.VK_W || lastKey == KeyEvent.VK_UP || lastKey == KeyEvent.VK_NUMPAD8;
			boolean down = lastKey == KeyEvent.VK_S || lastKey == KeyEvent.VK_DOWN || lastKey == KeyEvent.VK_NUMPAD2;
			boolean left = lastKey == KeyEvent.VK_A || lastKey == KeyEvent.VK_LEFT || lastKey == KeyEvent.VK_NUMPAD4;
			boolean right = lastKey == KeyEvent.VK_D || lastKey == KeyEvent.VK_RIGHT || lastKey == KeyEvent.VK_NUMPAD6;
			lastKey = 0;
			
			Point2D newPosition = getPosition(); 
			if(up) {
				newPosition.setLocation(position.getX(), position.getY() - 1);
			} else if(right) {
				newPosition.setLocation(position.getX() + 1, position.getY());
			} else if(down) {
				newPosition.setLocation(position.getX(), position.getY() + 1);
			} else if(left) {
				newPosition.setLocation(position.getX() - 1, position.getY());
			}
			
			if(move(newPosition)) {
				setPosition(newPosition);
			}
		}
	}
	
	public void setLastKeyPressed(int code)
	{
		this.lastKey = code;
	}
	
	public void render(Turtle t)
	{
		Point2D point = new Point2D.Double(position.getX()*20, position.getY()*20);
		t.movePen(point);
		
		t.draw(new shapes.Player());
	} 
	
	protected boolean move(Point2D position)
	{
		if(!super.checkMove(position)) {
			return false;
		}
		
		//If entity lands on pipe tile teleport to exit
		try {
			if(this.getGameManager().getLevel().getTile(position).getType() == LevelTile.TYPE_PIPE) {
				position = this.getGameManager().getLevel().getExitPipe(position);
			}
		} catch (Exception e) {
			return false;
		}
		
		setPosition(position);
		return true;
	}
}
