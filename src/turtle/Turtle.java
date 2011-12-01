package turtle;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.event.ChangeListener;

import fyw.autoevent.AutoChangeEvent;
import fyw.autoevent.AutoEventListenerList;
import fyw.turtles.AutoPenEvent;
import fyw.turtles.PenListener;
import turtle.TurtleShape;

public class Turtle extends fyw.turtles.Turtle
{	
    private double heading = 0.0; //degrees clockwise from North
    private Point2D position;
    private boolean isPenDownField;
    private final ArrayList<Point2D> path; //points visited
    private final ArrayList<Color> colors;
    private final ArrayList<Boolean> linesPresent; //pen up/down on path segments
	
	// listener list for change and pen events
	private final AutoEventListenerList listeners = new AutoEventListenerList();
	// the same event parameter objects can be used each time
	private final AutoChangeEvent theChangeEvent = new AutoChangeEvent(this);
	private final AutoPenEvent thePenEvent = new AutoPenEvent(this);

	/**
	 * Constructor
	 */
	public Turtle() {
		path = new ArrayList<Point2D>();
		colors = new ArrayList<Color>();
		linesPresent = new ArrayList<Boolean>();
		position = new Point2D.Double(0.0, 0.0);
		path.add(position);
		isPenDownField = true;
	}

	/**
	 * Restart turtle at initial position.
	 */
	public void restart() {
		restart(new Point2D.Double(0.0, 0.0));
	}

	/**
	 * Restart turtle at given position, with heading 0.
	 *
	 *@param p position to start at
	 */
	private void restart(Point2D p) {
		synchronized (this) {
			path.clear();
			colors.clear();
			linesPresent.clear();
			position = p;
			heading = 0.0;
			path.add(p);
			colors.add(Color.black);
			isPenDownField = true;
		}
		fireStateChange();
		firePenChange();
	}

	/**
	 * Move turtle forwards.
	 * @param distance distance to move. Moves backward if negative.
	 */
	public void move(double distance) {
		move(distance, Color.black);
	}
	
	/**
	 * Move turtle forwards.
	 * @param distance distance to move. Moves backward if negative.
	 * @param color Color of line to be drawn
	 * @param width Width of line to be drawn
	 */
	public void move(double distance, Color color)
	{
		Point2D oldPosition = position;
		double headingRadians = heading/180*Math.PI;
		position = new Point2D.Double(
			oldPosition.getX()+distance*Math.sin(headingRadians),
			oldPosition.getY()-distance*Math.cos(headingRadians)
		);
		synchronized (this) {
			path.add(position);
			colors.add(color);
			linesPresent.add(new Boolean(isPenDownField));
		}
		fireStateChange();		
	}

	/**
	 * Turn through an angle, at same position.
	 * @param angle angle to turn, in degrees
	 */
	public void turn(double angle) {
		heading = heading + angle;
		fireStateChange();
	}

	/**
	 * Raise pen, so doesn't draw.
	 */
	public void penUp() {
		isPenDownField = false;
		firePenChange();
	}

	/**
	 * Lower pen, so draws.
	 */
	public void penDown() {
		isPenDownField = true;
		firePenChange();
	}

	/**
	 * Find if pen down.
	 * @return true if pen down
	 */
	public boolean isPenDown() {
		return isPenDownField;
	}

	/**
	 * Get points visited.
	 * @return array of points visited
	 */
	public synchronized Point2D[] getPath() {
		return path.toArray(new Point2D[0]);
	}

	/**
	 * Get pen status for path segments.
	 * @return array of booleans to show whether pen up or down
	 */
	public synchronized boolean[] getLinesPresent() {
		boolean[] result = new boolean[linesPresent.size()];

		for (int i = 0; i < linesPresent.size(); i++) {
			result[i] = ((Boolean)(linesPresent.get(i))).booleanValue();
		}
		return result;
	}
	
    void fireStateChange() {
        listeners.fireEvent(ChangeListener.class, theChangeEvent);
    }
    
    void firePenChange() {
        listeners.fireEvent(PenListener.class, thePenEvent);
    }

	/**
	 * Add change listener.
	 * @param l change listener to be added
	 */
	public void addChangeListener(ChangeListener l) {
		listeners.add(ChangeListener.class, l);
	}

	/**
	 * Remove change listener.
	 * @param l change listener to be removed
	 */
	public void removeChangeListener(ChangeListener l) {
		listeners.remove(ChangeListener.class, l);
	}

	/**
	 * Add pen listener.
	 * @param l pen listener to be added
	 */
	public void addPenListener(PenListener l) {
		listeners.add(PenListener.class, l);
	}

	/**
	 * Remove pen listener.
	 * @param l pen listener to be removed
	 */
	public void removePenListener(PenListener l) {
		listeners.remove(PenListener.class, l);
	}

	/**
	 * Use this turtle to draw a turtle shape.
	 * @param theShape turtle shape to be drawn
	 */
	public void draw(TurtleShape theShape) {
		theShape.drawMe(this);
		setHeading(0);
	}

	public void movePen(Point2D point)
	{
		Point2D position = getPosition();
		if(!point.equals(position)) {
			double tempHeading = getHeading();
			double yDistance = -(point.getY() - position.getY());
			double xDistance = point.getX() - position.getX();

			penUp();
			setHeading(0);
			move(yDistance);
			setHeading(90);
			move(xDistance);
			setHeading(tempHeading);
			penDown();
		}
	}

	/**
	 * @return the heading
	 */
	public double getHeading()
	{
		return heading;
	}

	/**
	 * @param heading the heading to set
	 */
	public void setHeading(double heading)
	{
		this.heading = heading;
	}

	/**
	 * @return the position
	 */
	public Point2D getPosition()
	{
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Point2D position)
	{
		this.position = position;
	}

	/**
	 * @return the colors
	 */
	public Color[] getColors()
	{
		return colors.toArray(new Color[colors.size()]);
	}
}