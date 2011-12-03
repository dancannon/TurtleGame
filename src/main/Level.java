package main;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import entities.Ghost;
import entities.Player;
import entities.RedGhost;

import turtle.Turtle;

public class Level
{
	private static final String name = "default";
	
	private GameManager gm;
	private Player player;
	private Ghost[] ghosts = new Ghost[4];
	private Point2D spawn = new Point2D.Double(13, 14);
	private LevelTile[][] map;
	
	public Level(GameManager gm)
	{
		this.setGameManager(gm);
	}
	
	/**
	 * Reset the map to the default tiles
	 */
	public void resetMap()
	{
		getLevelTiles();
	}
	
	public void tick()
	{
		for(int i=0; i<getGhosts().length; i++) {
			if(getGhosts()[i].getPosition().equals(getPlayer().getPosition())) {
				if(getGhosts()[i].isScared()) {
					getGhosts()[i].setScared(false);
					getGhosts()[i].respawn();
					if(getGameManager().getGhostsDead() == 0) {
						getGameManager().incrementScore(200);
					} else if(getGameManager().getGhostsDead() == 1) {
						getGameManager().incrementScore(400);
					} else if(getGameManager().getGhostsDead() == 2) {
						getGameManager().incrementScore(800);
					} else if(getGameManager().getGhostsDead() == 3) {
						getGameManager().incrementScore(1600);
					}
					getGameManager().incrementGhostsDead();
				} else {
					SoundEffect.DIEING.play();
					getGameManager().getLevel().getPlayer().decrementLives();
					getPlayer().setPosition(getSpawn());
					for(int j=0; j<getGhosts().length; j++) {
						getGhosts()[j].respawn();
					}
					getGameManager().setTime(40);
				}
			}
		}
	}
	
	public void render(Turtle t)
	{
		for(int i=0;i<getMap().length;i++) {
			for(int j=0;j<getMap()[i].length;j++) {
				Point2D point = new Point2D.Double(j*20+2, (i*20)+20);
				t.movePen(point);
				
				switch (getMap()[i][j].getType()) {
				case LevelTile.TYPE_DOOR:
					t.draw(new shapes.Door());
					break;
				case LevelTile.TYPE_WALL:
					t.draw(new shapes.Wall());
					break;
				case LevelTile.TYPE_DOT:
					t.draw(new shapes.Dot());
					break;
				case LevelTile.TYPE_POWER_DOT:
					t.draw(new shapes.BigDot());
					break;
				default:
					break;
				}
			}
		}
	}
	
	public boolean isLevelComplete()
	{
		boolean levelComplete = true;
		
		for(int i=0; i<getMap().length;i++) {
			for(int j=0; j<getMap()[i].length;j++) {
				if(getMap()[i][j].getType() == LevelTile.TYPE_DOT) {
					levelComplete = false;
				}
			}
		}
		
		return levelComplete;
	}
	
	public Point2D getExitPipe(Point2D position) throws Exception
	{
		if(position.equals(new Point2D.Double(0, 12))) {
			return new Point2D.Double(25, 12);
		} else if(position.equals(new Point2D.Double(26, 12))) {
			return new Point2D.Double(1, 12);
		} else {
			throw new Exception("Invalid coordinate");
		}
	}
	
	public static Level loadLevel(GameManager gm) {
		Level level = new Level(gm);
		level.getLevelTiles();
		
		return level;
	}
	
	public void getLevelTiles()
	{
		try {
			BufferedImage img = ImageIO.read(Level.class.getResource("/res/level/" + getName() + ".png"));

			int w = img.getWidth();
			int h = img.getHeight();
			int[] pixels = new int[w * h];
			img.getRGB(0, 0, w, h, pixels, 0, w);

			this.setMap(new LevelTile[h][w]);
			//Create map from pixels
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					int color = pixels[x + y * w ] & 0xffffff;
					
					if(color == 0x000000) this.getMap()[y][x] = new LevelTile(LevelTile.TYPE_AIR);
					else if(color == 0xf4ba98) this.getMap()[y][x] = new LevelTile(LevelTile.TYPE_DOT);
					else if(color == 0x959595) this.getMap()[y][x] = new LevelTile(LevelTile.TYPE_PIPE);
					else if(color == 0xee145b) this.getMap()[y][x] = new LevelTile(LevelTile.TYPE_POWER_DOT);
					else if(color == 0x0000ff) this.getMap()[y][x] = new LevelTile(LevelTile.TYPE_WALL);
					else if(color == 0x555555) this.getMap()[y][x] = new LevelTile(LevelTile.TYPE_DOOR);
					else if(color == 0xffff00) {
						this.getMap()[y][x] = new LevelTile(LevelTile.TYPE_AIR);
						this.setSpawn(new Point2D.Double(x, y));
					} else if(color == 0xff0000) {
						this.getMap()[y][x] = new LevelTile(LevelTile.TYPE_AIR);
						this.getGhosts()[0] = new RedGhost(new Point2D.Double(x, y), 0, getGameManager());
						this.getGhosts()[0].setDirection(Ghost.DIRECTION_LEFT);
					} else if(color == 0x00ffde) {
						this.getMap()[y][x] = new LevelTile(LevelTile.TYPE_AIR);
						this.getGhosts()[1] = new Ghost(new Color(0,255,222), new Point2D.Double(x, y), 15, getGameManager());
					} else if(color == 0xffb7de) {
						this.getMap()[y][x] = new LevelTile(LevelTile.TYPE_AIR);
						this.getGhosts()[2] = new Ghost(new Color(255,183,222), new Point2D.Double(x, y), 30, getGameManager());
					} else if(color == 0xffb847) {
						this.getMap()[y][x] = new LevelTile(LevelTile.TYPE_AIR);
						this.getGhosts()[3] = new Ghost(new Color(255,184,71), new Point2D.Double(x, y), 45, getGameManager());
					} else {
						this.getMap()[y][x] = new LevelTile(LevelTile.TYPE_AIR);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public LevelTile getTile(Point2D position)
	{
		return this.getMap()[(int) position.getY()][(int) position.getX()];
	}
	
	public void setTile(Point2D position, LevelTile tile)
	{
		this.getMap()[(int) position.getY()][(int) position.getX()] = tile;
	}

	/**
	 * @return the name
	 */
	public static String getName()
	{
		return name;
	}

	/**
	 * @return the gm
	 */
	public GameManager getGameManager()
	{
		return gm;
	}

	/**
	 * @param gm the gm to set
	 */
	public void setGameManager(GameManager gm)
	{
		this.gm = gm;
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

	/**
	 * @return the ghosts
	 */
	public Ghost[] getGhosts()
	{
		return ghosts;
	}

	/**
	 * @param ghosts the ghosts to set
	 */
	public void setGhosts(Ghost[] ghosts)
	{
		this.ghosts = ghosts;
	}

	/**
	 * @return the spawn
	 */
	public Point2D getSpawn()
	{
		return spawn;
	}

	/**
	 * @param spawn the spawn to set
	 */
	public void setSpawn(Point2D spawn)
	{
		this.spawn = spawn;
	}

	/**
	 * @return the map
	 */
	public LevelTile[][] getMap()
	{
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(LevelTile[][] map)
	{
		this.map = map;
	}
}
