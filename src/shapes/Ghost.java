package shapes;

import java.awt.Color;
import java.awt.geom.Point2D;

import turtle.Turtle;
import turtle.TurtleShape;

public class Ghost implements TurtleShape
{
	private Color color;
	static final double RADIUS = 8;
	
	public Ghost(Color color)
	{
		this.color = color;
	}

	@Override
	public void drawMe(Turtle turtle)
	{
		Point2D center = new Point2D.Double(turtle.getPosition().getX() + 10, turtle.getPosition().getY() - 10);

		for(int i = 0; i<360; i++) {
			turtle.movePen(center);
			turtle.move(RADIUS, color);
			turtle.turn(1);
		}
	}

}
