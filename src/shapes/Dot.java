package shapes;

import java.awt.Color;

import turtle.Turtle;
import turtle.TurtleShape;

public class Dot implements TurtleShape
{
	static final double RADIUS = 2;
	
	@Override
	public void drawMe(Turtle turtle)
	{
		
		double moveSize = (2*Math.PI*RADIUS) / 360;
		
		turtle.penUp();
		turtle.move(10);
		turtle.turn(90);
		turtle.move(10 - RADIUS);
		turtle.turn(-90);
		turtle.penDown();
		for(int i = 0; i<360; i++) {
			turtle.move(moveSize, new Color(248, 176, 144));
			turtle.turn(1);
		}
	}
}
