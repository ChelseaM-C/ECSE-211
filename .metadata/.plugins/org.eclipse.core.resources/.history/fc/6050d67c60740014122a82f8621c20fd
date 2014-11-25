import java.util.ArrayList;


public class Path {
	private ArrayList<GridSquare> squares;
	private ArrayList<Waypoint> points;
	
	private boolean valid;
	
	public Path(){
		squares = new ArrayList<GridSquare>();
		valid = false;
	}
	
		
	
	public void setSquares(ArrayList<GridSquare> s){
		for(GridSquare sq : s){
			GridSquare n = new GridSquare(sq.getMap(),sq.getX(),sq.getY(),sq.isWall());
			if(sq.isVisited()){
				n.visit();
			}
			this.squares.add(sq);
		}
	}
	
	public void addSquare(GridSquare s){
		s.visit();
		squares.add(s);
	}
	
	public Path clone(){
		Path p = new Path();
		p.setSquares(this.squares);
		return p;
	}
	
	public ArrayList<GridSquare> getSquares(){
		return squares;
	}
	
	public ArrayList<Waypoint> getPoints(){
		points = new ArrayList<Waypoint>();
		for(int i = 0; i < squares.size(); i++){
			points.add(squareToPoint(squares.get(i)));
		}
		return points;
	}
	
	private Waypoint squareToPoint(GridSquare s){
		Waypoint p = new Waypoint(15 + s.getX()*30,15 + s.getY()*30);
		return p;
	}
	
	public void valid(){
		valid = true;
	}
	
	public boolean isValid(){
		return valid;
	}
	
	public GridSquare getEnd(){
		return squares.get(squares.size() - 1);
	}
	
	public String toString(){
		String p = "";
		for(int i = 0; i < squares.size(); i++){
			p = ( p + "[" + squares.get(i).getX() + "," + squares.get(i).getY() + "] ");
		}
		
		return p;
	}
}
