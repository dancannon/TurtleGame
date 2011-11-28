package main;

import java.util.ArrayList;

import entities.Entity;

public class LevelTile
{
	public static final int TYPE_AIR  = 1;
	public static final int TYPE_WALL = 2;
	public static final int TYPE_PIPE = 3;
	public static final int TYPE_DOOR = 4;
	public static final int TYPE_DOT  = 5;
	public static final int TYPE_POWER_DOT = 6;
	
	private int type;
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public LevelTile(int type)
	{
		this.setType(type);
	}

	/**
	 * @return the type
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type)
	{
		this.type = type;
	}

	/**
	 * @return the entities
	 */
	public ArrayList<Entity> getEntities()
	{
		return entities;
	}

	/**
	 * @param entities the entities to set
	 */
	public void setEntities(ArrayList<Entity> entities)
	{
		this.entities = entities;
	}
	
	public void addEntity(Entity entity)
	{
		this.entities.add(entity);
	}
}
