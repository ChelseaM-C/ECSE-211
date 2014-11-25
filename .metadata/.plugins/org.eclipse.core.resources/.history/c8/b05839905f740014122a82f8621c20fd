import java.util.ArrayList;


public class Map {
	private ArrayList<ArrayList<GridSquare>> grid;
	private int mapID;
	
	private boolean[][] walls;
	
	private int size;
	
	public Map(int size, int id){
		this.size = size;
		mapID = id;
		
		grid = new ArrayList<ArrayList<GridSquare>>();
	}
	
	public void addWalls(boolean[][] walls){
		this.walls = walls;
	}
	
	public int getSize(){
		return size;
	}
	
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
	
	public GridSquare getSquare(int i, int j){
		if(i < size && j < size && i >= 0 && j >= 0){
			return grid.get(i).get(j);
		}
		return null;
	}
	
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
