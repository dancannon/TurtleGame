package shapes;

import java.awt.Color;

import turtle.Turtle;
import turtle.TurtleShape;

public class Door implements TurtleShape
{	
	@Override
	public void drawMe(Turtle turtle)
	{
		for(int i = 0; i<4; i++) {
			turtle.move(19.0, Color.pink);
			turtle.turn(90);
		}
	}

}
