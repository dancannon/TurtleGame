package shapes;

import java.awt.Color;
import java.awt.geom.Point2D;

import turtle.Turtle;
import turtle.TurtleShape;

public class BigDot implements TurtleShape
{
	static final double RADIUS = 5;
	
	@Override
	public void drawMe(Turtle turtle)
	{
		Point2D center = new Point2D.Double(turtle.getPosition().getX() + 10, turtle.getPosition().getY() - 10);

		for(int i = 0; i<360; i++) {
			turtle.movePen(center);
			turtle.move(RADIUS, new Color(248, 176, 144));
			turtle.turn(1);
		}
	}
}
