package main;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import entities.Ghost;
import entities.Player;

import turtle.Turtle;
import turtle.TurtleGUI;

public class Level extends TurtleGUI
{
	private Player player;
	private Ghost[] ghosts;
	public Point2D spawn = new Point2D.Double(13, 14);
	private boolean redraw = true;
	
	private BufferedImage bufferedImage;
	
	private LevelTile air = new LevelTile(LevelTile.TYPE_AIR);
	private LevelTile wall = new LevelTile(LevelTile.TYPE_WALL);
	private LevelTile pipe = new LevelTile(LevelTile.TYPE_PIPE);
	private LevelTile door = new LevelTile(LevelTile.TYPE_DOOR);
	private LevelTile dot = new LevelTile(LevelTile.TYPE_DOT);
	private LevelTile powerup = new LevelTile(LevelTile.TYPE_POWER_DOT);
	private LevelTile[][] map =
		{
			/*      0     1     2     3     4     5     6     7     8     9    10    11    12    13    14    15    16    17    18    19    20    21    22    23    24    25    26*/  
			/*0*/ {wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall},
			/*1*/ {wall, dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , wall, dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , wall},
			/*2*/ {wall, dot , wall, wall, wall, wall, dot , wall, wall, wall, wall, wall, dot , wall, dot , wall, wall, wall, wall, wall, dot , wall, wall, wall, wall, dot , wall},
			/*3*/ {wall, dot , wall, wall, wall, wall, dot , wall, wall, wall, wall, wall, dot , wall, dot , wall, wall, wall, wall, wall, dot , wall, wall, wall, wall, dot , wall},
			/*4*/ {wall, dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , wall},
			/*5*/ {wall, dot , wall, wall, wall, wall, dot , wall, dot , wall, wall, wall, wall, wall, wall, wall, wall, wall, dot , wall, dot , wall, wall, wall, wall, dot , wall},
			/*6*/ {wall, dot , dot , dot , dot , dot , dot , wall, dot , dot , dot , dot , dot , wall, dot , dot , dot , dot , dot , wall, dot , dot , dot , dot , dot , dot , wall},
			/*7*/ {wall, wall, wall, wall, wall, wall, dot , wall, wall, wall, wall, wall, dot , wall, dot , wall, wall, wall, wall, wall, dot , wall, wall, wall, wall, wall, wall},
			/*8*/ {air , air , air , air , air , wall, dot , wall, dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , wall, dot , wall, air , air , air , air , air},
			/*9*/ {air , air , air , air , air , wall, dot , wall, dot , wall, wall, wall, wall, door, wall, wall, wall, wall, dot , wall, dot , wall, air , air , air , air , air},
			/*10*/{air , air , air , air , air , wall, dot , wall, dot , wall, air , air , air , air , air , air , air , wall, dot , wall, dot , wall, air , air , air , air , air},
			/*11*/{wall, wall, wall, wall, wall, wall, dot , wall, dot , wall, air , air , air , air , air , air , air , wall, dot , wall, dot , wall, wall, wall, wall, wall, wall},
			/*12*/{pipe, dot , dot , dot , dot , dot , dot , dot , dot , wall, air , air , air , air , air , air , air , wall, dot , dot , dot , dot , dot , dot , dot , dot , pipe},
			/*13*/{wall, wall, wall, wall, wall, wall, dot , wall, dot , wall, wall, wall, wall, wall, wall, wall, wall, wall, dot , wall, dot , wall, wall, wall, wall, wall, wall},
			/*14*/{air , air , air , air , air , wall, dot , wall, dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , wall, dot , wall, air , air , air , air , air},
			/*15*/{wall, wall, wall, wall, wall, wall, dot , wall, dot , wall, wall, wall, wall, wall, wall, wall, wall, wall, dot , wall, dot , wall, wall, wall, wall, wall, wall},
			/*16*/{wall, dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , wall, dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , wall},
			/*17*/{wall, dot , wall, wall, wall, wall, dot , wall, wall, wall, wall, wall, dot , wall, dot , wall, wall, wall, wall, wall, dot , wall, wall, wall, wall, dot , wall},
			/*18*/{wall, dot , dot , dot , dot , wall, dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , wall, dot , wall, dot , dot , dot , dot , wall},
			/*19*/{wall, wall, wall, wall, dot , wall, dot , wall, dot , wall, wall, wall, wall, wall, wall, wall, wall, wall, dot , wall, dot , wall, dot , wall, wall, wall, wall},
			/*20*/{wall, dot , dot , dot , dot , dot , dot , wall, dot , dot , dot , dot , dot , wall, dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , wall},
			/*21*/{wall, dot , wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, dot , wall, dot , wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, dot , wall},
			/*22*/{wall, dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , dot , wall},
			/*23*/{wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall, wall},
		};
	
	public Level()
	{
		
	}
	
	public void tick()
	{
		
	}
	
	public void render()
	{
		Turtle t = getTurtle();
		if(bufferedImage == null || redraw == true) {
			for(int i=0;i<map.length;i++) {
				for(int j=0;j<map[i].length;j++) {
					Point2D point = new Point2D.Double(j*20+2, (i*20)+20);
					t.movePen(point);
					
					switch (map[i][j].getType()) {
					case LevelTile.TYPE_DOOR:
						t.draw(new shapes.Door());
						break;
					case LevelTile.TYPE_WALL:
						t.draw(new shapes.Wall());
						break;
					case LevelTile.TYPE_DOT:
						t.draw(new shapes.Dot());
						break;
					default:
						break;
					}
				}
			}
		} else {
			
		}
	}

	/**
	 * @return the player
	 */
	public Player getPlayer()
	{
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player)
	{
		this.player = player;
	}
	
	public LevelTile getTile(Point2D position)
	{
		return this.map[(int) position.getY()][(int) position.getX()];
	}
	
	public Point2D getExitPipe(Point2D position) throws Exception
	{
		System.out.println(map[12][0]);
		if(position.equals(new Point2D.Double(0, 12))) {
			return new Point2D.Double(26, 12);
		} else if(position.equals(new Point2D.Double(26, 12))) {
			return new Point2D.Double(0, 12);
		} else {
			throw new Exception("Invalid coordinate");
		}
	}
}
