package turtle;

import java.awt.BorderLayout;
import javax.swing.*;

import fyw.turtles.TurtleControlPanel;

public class TurtleGUIPane extends javax.swing.JPanel
{
	private static final long serialVersionUID = 1L;

    private final Turtle model;
    
    /**
     * Constructor, creating new turtle as model.
     */
    public TurtleGUIPane() {
        this(new Turtle());
    }
    
    /**
     * Constructor, using given turtle as model.
     * @param tm turtle to use
     */
    public TurtleGUIPane(Turtle tm) {
        super(new BorderLayout());
        
        model = tm;
        TurtleView view = new TurtleView(model);
        add(view, BorderLayout.CENTER);
        
        setOpaque(true);        
    }
}
