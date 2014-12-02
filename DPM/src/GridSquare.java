import java.util.ArrayList;

/**Represents one tile on the map
 * 
 * @author Daniel
 *
 */
public class GridSquare {
	private boolean isWall,visited;
	private int x,y;
	private Map map;
	
	/**
	 * 
	 * @param map map the square is part of
	 * @param x x coord
	 * @param y y coord
	 * @param wall true if the tile is occupied by a wall
	 */
	public GridSquare(Map map,int x, int y, boolean wall){
		this.x = x;
		this.y = y;
		this.map = map;
		this.isWall = wall;
		visited = false;
		
	}
	
	/**
	 * For pathfinding, calling this will set the node to visited status
	 */
	
	public void reInit(){
		visited = false;
	}
	public void visit(){
		visited = true;
	}
	/**
	 * Gets whether or not the node has been visited
	 * @return visited (true = visited)
	 */
	public boolean isVisited(){
		return visited;
	}
	
	/**
	 * 
	 * @return x position
	 */
	public int getX(){
		return x;
	}
	/**
	 * 
	 * @return y position
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * 
	 * @return whether gridSquare contains a wall
	 */
	public boolean isWall(){
		return isWall;
	}
	
	/**
	 * 
	 * @return x position (cm)
	 */
	public int getXCoord(){
		return (x+1)*15;
	}
	
	/**
	 * 
	 * @return y position (cm)
	 */
	public int getYCoord(){
		return (y+1)*15;
	}
	
	/**Fetches a list of all the surrounding gridSquares if they are not walls
	 * 
	 * @return movable gridSquares
	 */
	public ArrayList<GridSquare> getSquares(){
		ArrayList<GridSquare> adj = new ArrayList<GridSquare>();
		if(map.getSquare(x+1, y) != null){	
			if(!map.getSquare(x+1, y).isWall()){
				adj.add(map.getSquare(x+1, y));
			}
		}
		if(map.getSquare(x-1, y) != null){
			if(!map.getSquare(x-1, y).isWall()){
				adj.add(map.getSquare(x-1, y));
			}
		}
		if(map.getSquare(x, y+1) != null){
			if(!map.getSquare(x, y+1).isWall()){
				adj.add(map.getSquare(x, y+1));
			}
		}
		if(map.getSquare(x, y-1) != null){
			if(!map.getSquare(x, y-1).isWall()){
				adj.add(map.getSquare(x, y-1));
			}
		}
		return adj;
	}
	
	/**
	 * 
	 * @return the map the gridSquare is on
	 */
	public Map getMap() {
		return map;
	}
	
	
}
