package shapes;

import java.awt.Color;

import turtle.Turtle;
import turtle.TurtleShape;

public class Dot implements TurtleShape
{
	@Override
	public void drawMe(Turtle turtle)
	{
		for(int i = 0; i<360; i++) {
			turtle.move(0.03, new Color(248, 176, 144));
			turtle.turn(1);
		}
	}
}
