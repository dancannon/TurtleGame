/*
 * TurtleView.java
 *
 * Created 2003
 */
package turtle;

import javax.swing.*;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.*;

public class TurtleView extends JPanel
{
	private static final long serialVersionUID = 1L;
	private final Point origin;
	private final Turtle theTurtle;
	private final int HEIGHT = 470;
	private final int WIDTH = 530;

	/**
	 * Constructor, using given turtle.
	 * 
	 * @param model
	 *            turtle to use
	 */
	public TurtleView(Turtle model)
	{
		super();
		
		setBorder(new LineBorder(Color.black));
		setBackground(Color.black);
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setSize(size);
		setPreferredSize(size);
		origin = new Point(0, 0);
		theTurtle = model;
		model.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e)
			{
				repaint();
			}
		});
	}
	
	 public void update(Graphics g) {
		 paint(g);
	 }
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		Image offscreen = createImage(WIDTH, HEIGHT);
		Graphics offG = offscreen.getGraphics();
		
		//Clear the offscreen image.
		Dimension d = getSize();
		offG.setColor(getBackground());
		offG.fillRect(0, 0, WIDTH, HEIGHT);
		
		//Draw to offscreen
		
		Point2D[] path = theTurtle.getPath();
		Color[] colors = theTurtle.getColors();
		boolean[] linesPresent = theTurtle.getLinesPresent();
		int length = path.length - 1; // number of line segments
		
		//Draw lines		
		int x0, y0, x1, y1;

		offG.translate((int) (Math.round(origin.getX())), (int) (Math.round(origin.getY())));
		x0 = (int) (Math.round(path[0].getX()));
		y0 = (int) (Math.round(path[0].getY()));
		for (int i = 0; i < length; i++) {
			x1 = (int) (Math.round(path[i + 1].getX()));
			y1 = (int) (Math.round(path[i + 1].getY()));
			if (linesPresent[i]) {
				offG.setColor(colors[i + 1]);
				offG.drawLine(x0, y0, x1, y1);
			}
			x0 = x1;
			y0 = y1;
		}
		
		// Put the offscreen image on the screen.
		g2.drawImage(offscreen, 0, 0, null);
	}
}
