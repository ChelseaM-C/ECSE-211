
public class Ghost {
	private int x,y;
	private boolean valid;
	
	private String orientation;
	
	private Map map;
	
	public Ghost(int x, int y, String o, Map m){
		this.x = x;
		this.y = y;
		this.orientation = o;
		this.map = m;
		this.valid = true;
	}
	
	public void invalid(){
		valid = false;
	}
	
	public boolean isValid(){
		return valid;
	}
	
	public int wallinFront(){
		if(orientation.equals("N")){
			if(map.getSquare(x, y+1) == null){
				return 1;
			}
			else if(map.getSquare(x,y+1).isWall()){
				return 1;
			}
			else if(map.getSquare(x, y+2) == null){
				return 2;
			}
			else if(map.getSquare(x,y+2).isWall()){
				return 2;
			}
		}
		if(orientation.equals("S")){
			if(map.getSquare(x, y-1) == null){
				return 1;
			}
			else if(map.getSquare(x,y-1).isWall()){
				return 1;
			}
			else if(map.getSquare(x, y-2) == null){
				return 2;
			}
			else if(map.getSquare(x,y-2).isWall()){
				return 2;
			}
		}
		if(orientation.equals("E")){
			if(map.getSquare(x+1, y) == null){
				return 1;
			}
			else if(map.getSquare(x+1,y).isWall()){
				return 1;
			}
			else if(map.getSquare(x+2, y) == null){
				return 2;
			}
			else if(map.getSquare(x+2,y).isWall()){
				return 2;
			}
		}
		if(orientation.equals("W")){
			if(map.getSquare(x-1, y) == null){
				return 1;
			}
			else if(map.getSquare(x-1,y).isWall()){
				return 1;
			}
			if(map.getSquare(x-2, y) == null){
				return 2;
			}
			else if(map.getSquare(x-2,y).isWall()){
				return 2;
			}
		}
		return 0;
	}
	
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public String getOrientation(){
		return orientation;
	}
	
	public void move(){
		if(orientation.equals("N")){
			y++;
		}
		if(orientation.equals("S")){
			y--;
		}
		if(orientation.equals("E")){
			x++;
		}
		if(orientation.equals("W")){
			x--;
		}
	}
	
	public void turn(){
		if(orientation.equals("N")){
			orientation = "E";
		}
		else if(orientation.equals("S")){
			orientation = "W";
		}
		else if(orientation.equals("E")){
			orientation = "S";
		}
		else if(orientation.equals("W")){
			orientation = "N";
		}
	}
	
	public void turnLeft(){
		if(orientation.equals("N")){
			orientation = "W";
		}
		else if(orientation.equals("S")){
			orientation = "E";
		}
		else if(orientation.equals("E")){
			orientation = "N";
		}
		else if(orientation.equals("W")){
			orientation = "S";
		}
	}
}
