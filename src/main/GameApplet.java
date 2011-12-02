package main;

import java.applet.Applet;
import java.awt.BorderLayout;

import turtle.Turtle;

public class GameApplet extends Applet {
	private static final long serialVersionUID = 1L;

	private GameWindow gamewindow;
	
	public GameApplet()
	{
		Turtle turtle = new Turtle();
		
		GameManager gameManager = new GameManager(turtle);
		Game game = new Game(gameManager);
		
		gamewindow = new GameWindow(game, turtle);
	}

	public void init() {
		setLayout(new BorderLayout());
		add(gamewindow, BorderLayout.CENTER);
	}

	public void start() {
		gamewindow.start();
	}

	public void stop() {
		gamewindow.stop();
	}

}