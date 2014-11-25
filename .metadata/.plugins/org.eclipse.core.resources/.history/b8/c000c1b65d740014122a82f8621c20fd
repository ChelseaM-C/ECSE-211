import java.util.ArrayList;


public class GridSquare {
	private boolean isWall,visited;
	private int x,y;
	private Map map;
	
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
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public boolean isWall(){
		return isWall;
	}
	
	public int getXCoord(){
		return (x+1)*15;
	}
	
	public int getYCoord(){
		return (y+1)*15;
	}
	
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

	public Map getMap() {
		return map;
	}
	
	
}
