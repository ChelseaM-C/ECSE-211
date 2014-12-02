import java.util.ArrayList;

/**Map which contains all of the gridSquares
 * 
 * @author Daniel
 *
 */
public class Map {
	private ArrayList<ArrayList<GridSquare>> grid;
	private int mapID;
	
	private boolean[][] walls;
	
	private int size;
	
	/**Creates a new map given the size and ID
	 * 
	 * @param size (Square) size in terms of tiles
	 * @param id map ID number
	 */
	public Map(int size, int id){
		this.size = size;
		mapID = id;
		
		grid = new ArrayList<ArrayList<GridSquare>>();
	}
	
	public void reInitialize(){
		for(ArrayList<GridSquare> row : grid){
			for(GridSquare s : row){
				s.reInit();
			}
		}
	}
	
	/**Sets the walls of the map
	 * 
	 * @param walls 2D bool array, true represents the walls
	 */
	public void addWalls(boolean[][] walls){
		this.walls = walls;
	}
	
	/**
	 * 
	 * @return map tile size
	 */
	public int getSize(){
		return size;
	}
	
	/**
	 * Returns all of the squares that are walls
	 * @return array list of GridSquares that are walls
	 */
	public ArrayList<GridSquare> getWalls(){
		ArrayList<GridSquare> walls = new ArrayList<GridSquare>();
		for(ArrayList<GridSquare> row : grid){
			for(GridSquare s : row){
				if(s.isWall()){
					walls.add(s);
				}
			}
		}
		return walls;
	}
	
	/**Gets a gridSquare from the given coords
	 * 
	 * @param i x coord
	 * @param j y coord
	 * @return GridSquare at [x,y]
	 */
	public GridSquare getSquare(int i, int j){
		if(i < size && j < size && i >= 0 && j >= 0){
			return grid.get(i).get(j);
		}
		return null;
	}
	/**
	 * MUST BE CALLED TO INITIALIZE MAP, CALL AFTER ADDWALLS
	 */
	public void populate(){
		for(int i = 0; i < size; i++){
			grid.add(new ArrayList<GridSquare>());
			for(int j = 0; j < size; j++){
				if(walls[i][j] == true){
					GridSquare square = new GridSquare(this,i,j,true);
					grid.get(i).add(square);
				}
				else{
					GridSquare square = new GridSquare(this,i,j,false);
					grid.get(i).add(square);
				}
				
			}
		}
	}
	
}
