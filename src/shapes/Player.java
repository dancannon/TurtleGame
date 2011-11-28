package shapes;

import java.awt.Color;

import turtle.Turtle;
import turtle.TurtleShape;

public class Player implements TurtleShape
{	
	@Override
	public void drawMe(Turtle turtle)
	{
		for(int i = 0; i<360; i++) {
			turtle.move(0.15, Color.yellow);
			turtle.turn(1);
		}
	}

}
