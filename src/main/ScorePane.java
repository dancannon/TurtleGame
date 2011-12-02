package main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePane extends JPanel
{
	private static final long serialVersionUID = 1L;

	private final JButton newGameButton;
	private final JLabel scoreLabel;
	private final JLabel statusLabel;
	private final JLabel livesLabel;
	private GameManager gm;
	
	public ScorePane(GameManager gm)
	{
		super(new GridLayout(1, 4));
		
		this.gm = gm;
		
		//Initialize the new game button
		newGameButton = new JButton("New Game");
		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				ScorePane.this.gm.newGame();
				Game.game.getWindow().requestFocusInWindow();
			}
		});
        add(newGameButton);
        
        //Initialize the score label
        scoreLabel = new JLabel("Score: " + this.gm.getScore());
        add(scoreLabel);
        
        //Initialize the lives label
        livesLabel = new JLabel("Lives: " + gm.getPlayer().getLives());
        add(livesLabel);
        
        //Initialize the status label 
        statusLabel = new JLabel("Ready!");
        add(statusLabel);
        
        gm.setScoreListener(new ScoreListener());
        gm.setStatusListener(new StatusListener());
        gm.setLivesListener(new LivesListener());        
	}
	
	public void updateScoreLabel()
	{
		scoreLabel.setText("Score: " + gm.getScore());
	}
	
	public void updateStatusLabel()
	{
		statusLabel.setText(gm.getStatus());
	}
	
	public void updateLivesLabel()
	{
		livesLabel.setText("Lives: " + gm.getPlayer().getLives());
	}
	
	private class ScoreListener  implements ChangeListener
	{
		@Override
		public void propertyChanged()
		{
			updateScoreLabel();
		}
	}
	
	private class StatusListener  implements ChangeListener
	{
		public void propertyChanged()
		{
			updateStatusLabel();			
		}
	}

	private class LivesListener  implements ChangeListener
	{
		public void propertyChanged()
		{
			updateLivesLabel();			
		}
	}
}
