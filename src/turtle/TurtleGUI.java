package turtle;

import javax.swing.JFrame;

public class TurtleGUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	
    private Turtle turtle;

    /**
     * Constructor, creating new turtle.
     */
    public TurtleGUI() {
        this(new Turtle());
    }
    
    /**
     * Constructor, using given turtle.
     * @param tm turtle model to use
     */
    public TurtleGUI(Turtle t) {
    	setTurtle(t);
    	//Set panels
    	setContentPane(new TurtleGUIPane(t));
    	
		//Set window behaviors
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

	/**
	 * @return the turtle
	 */
	public Turtle getTurtle()
	{
		return turtle;
	}

	/**
	 * @param turtle the turtle to set
	 */
	public void setTurtle(Turtle turtle)
	{
		this.turtle = turtle;
	}
}
