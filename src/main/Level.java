package main;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import entities.Ghost;
import entities.Player;

import turtle.Turtle;

public class Level
{
	static final String name = "default";
	
	private GameManager gm;
	private Player player;
	private Ghost[] ghosts = new Ghost[4];
	private Point2D spawn = new Point2D.Double(13, 14);
	private boolean redraw = true;
	
	private BufferedImage bufferedImage;
	private LevelTile[][] map;
	
	public Level(GameManager gm)
	{
		this.gm = gm;
	}
	
	public void resetMap()
	{
		getLevelTiles();
	}
	
	public void tick()
	{
		if(isLevelComplete()) {
			if(getGameManager().getLevelID() == 10) {
				System.out.println("Epic Win! Your score is " + getGameManager().getScore());
				Runtime.getRuntime().exit(0);
			} else {
				System.out.println("Level Complete. Score: " + getGameManager().getScore());
				getGameManager().newLevel(getGameManager().getLevelID() + 1);
			}
		}
		
		for(Ghost ghost : ghosts) {
			if(ghost.getPosition().equals(getPlayer().getPosition())) {
				if(ghost.isScared()) {
					ghost.setScared(false);
					ghost.respawn();
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
					getGameManager().getLevel().getPlayer().decrementLives();
					getGameManager().newLevel(getGameManager().getLevelID());
				}
			}
		}
	}
	
	public void render(Turtle t)
	{
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
					case LevelTile.TYPE_POWER_DOT:
						t.draw(new shapes.BigDot());
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
	
	public void setTile(Point2D position, LevelTile tile)
	{
		this.map[(int) position.getY()][(int) position.getX()] = tile;
	}
	
	public boolean isLevelComplete()
	{
		boolean levelComplete = true;
		
		for(int i=0; i<map.length;i++) {
			for(int j=0; j<map[i].length;j++) {
				if(map[i][j].getType() == LevelTile.TYPE_DOT) {
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
			BufferedImage img = ImageIO.read(Level.class.getResource("/res/level/" + name + ".png"));

			int w = img.getWidth();
			int h = img.getHeight();
			int[] pixels = new int[w * h];
			img.getRGB(0, 0, w, h, pixels, 0, w);

			this.map = new LevelTile[h][w];
			//Create map from pixels
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					int color = pixels[x + y * w ] & 0xffffff;
					
					if(color == 0x000000) this.map[y][x] = new LevelTile(LevelTile.TYPE_AIR);
					else if(color == 0xf4ba98) this.map[y][x] = new LevelTile(LevelTile.TYPE_DOT);
					else if(color == 0x959595) this.map[y][x] = new LevelTile(LevelTile.TYPE_PIPE);
					else if(color == 0xee145b) this.map[y][x] = new LevelTile(LevelTile.TYPE_POWER_DOT);
					else if(color == 0x0000ff) this.map[y][x] = new LevelTile(LevelTile.TYPE_WALL);
					else if(color == 0x555555) this.map[y][x] = new LevelTile(LevelTile.TYPE_DOOR);
					else if(color == 0xffff00) {
						this.map[y][x] = new LevelTile(LevelTile.TYPE_AIR);
						this.setSpawn(new Point2D.Double(x, y));
					} else if(color == 0xff0000) {
						this.map[y][x] = new LevelTile(LevelTile.TYPE_AIR);
						this.ghosts[0] = new Ghost(new Color(255,0,0), new Point2D.Double(x, y), 0, gm);
						this.ghosts[0].setLeft(true);
						this.ghosts[0].setDirection(Ghost.DIRECTION_LEFT);
					} else if(color == 0x00ffde) {
						this.map[y][x] = new LevelTile(LevelTile.TYPE_AIR);
						this.ghosts[1] = new Ghost(new Color(0,255,222), new Point2D.Double(x, y), 15, gm);
					} else if(color == 0xffb7de) {
						this.map[y][x] = new LevelTile(LevelTile.TYPE_AIR);
						this.ghosts[2] = new Ghost(new Color(255,183,222), new Point2D.Double(x, y), 30, gm);
					} else if(color == 0xffb847) {
						this.map[y][x] = new LevelTile(LevelTile.TYPE_AIR);
						this.ghosts[3] = new Ghost(new Color(255,184,71), new Point2D.Double(x, y), 45, gm);
					} else {
						this.map[y][x] = new LevelTile(LevelTile.TYPE_AIR);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param gameManager the gameManager to set
	 */
	public void setGameManager(GameManager gameManager)
	{
		this.gm = gameManager;
	}

	/**
	 * @return the gameManager
	 */
	public GameManager getGameManager()
	{
		return gm;
	}

	/**
	 * @param ghosts the ghosts to set
	 */
	public void setGhosts(Ghost[] ghosts)
	{
		this.ghosts = ghosts;
	}

	/**
	 * @return the ghosts
	 */
	public Ghost[] getGhosts()
	{
		return ghosts;
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
}
