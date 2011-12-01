package shapes;

import java.awt.Color;
import java.awt.geom.Point2D;

import entities.MoveableEntity;

import turtle.Turtle;
import turtle.TurtleShape;

public class Player implements TurtleShape
{
	static final double RADIUS = 8;
	public int direction;
	
	public Player(int direction)
	{
		this.direction = direction;
	}
	
	@Override
	public void drawMe(Turtle turtle)
	{
		Point2D center = new Point2D.Double(turtle.getPosition().getX() + 10, turtle.getPosition().getY() - 10);

		if(direction == MoveableEntity.DIRECTION_UP) 	turtle.turn(30);
		if(direction == MoveableEntity.DIRECTION_RIGHT) turtle.turn(120);
		if(direction == MoveableEntity.DIRECTION_DOWN) 	turtle.turn(210);
		if(direction == MoveableEntity.DIRECTION_LEFT) 	turtle.turn(-60);
		
		for(int i = 0; i<290; i++) {
			turtle.movePen(center);
			turtle.move(RADIUS, Color.yellow);
			turtle.turn(1);
		}
	}
}
