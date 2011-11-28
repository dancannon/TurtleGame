package main;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener, FocusListener
{
	private GameManager gm;
	
	public InputHandler(GameManager gm)
	{
		this.gm = gm;
	}
	
	public boolean[] keys = new boolean[65536];

	public void focusGained(FocusEvent arg0) {
	}

	public void focusLost(FocusEvent arg0) {
		for (int i=0; i<keys.length; i++) {
			keys[i] = false;
		}
	}

	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode(); 
		if (code>0 && code<keys.length) {
			gm.getPlayer().setLastKeyPressed(code);
			keys[code] = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode(); 
		if (code>0 && code<keys.length) {
			keys[code] = false;
		}
	}

	public void keyTyped(KeyEvent arg0) {
	}
}

