import java.util.ArrayList;



public class Localizer extends Thread{
	
	boolean running;
	
	private Ghost robot;
	
	private static final int WALL = 20;
	
	int turncount,thresh;
	
	private Map map;
	
	private ArrayList<Ghost> ghosts;
	
	
	public Localizer(Map map, Ghost robot){
		this.map = map;
		running = true;
		this.robot = robot;
		
		turncount = 0;
		
		thresh = 3;
		
		ghosts = new ArrayList<Ghost>();
		
		for(int i = 0; i < map.getSize(); i++){
			for(int j = 0; j < map.getSize(); j++){
				if(!map.getSquare(i,j).isWall()){
					Ghost n = new Ghost(i,j,"N",map);
					Ghost s = new Ghost(i,j,"S",map);
					Ghost w = new Ghost(i,j,"W",map);
					Ghost e = new Ghost(i,j,"E",map);
					ghosts.add(n);
					ghosts.add(s);
					ghosts.add(w);
					ghosts.add(e);
				}
			}
		}
	}
	
	public boolean isRunning(){
		return running;
	}
	
	public int numValid(){
		int num = 0;
		for(Ghost g : ghosts){
			if(g.isValid()){
				num++;
			}
		}
		return num;
	}
	
	public void noWall(){
		for(Ghost g: ghosts){
			if(g.wallinFront() != 0){
				g.invalid();
			}
		}
	}
	
	public void wall(){
		for(Ghost g : ghosts){
			if(g.wallinFront() == 0){
				g.invalid();
			}
		}
	}
	
	public void wall2(){
		for(Ghost g : ghosts){
			if(g.wallinFront() != 2){
				g.invalid();
			}
		}
	}
	
	public void run(){
		while(numValid() > 1){
			System.out.println(robot.getX() + " , " + robot.getY() + " , " + robot.getOrientation() + " , " + robot.wallinFront());
			//System.out.println(numValid());
			if(robot.wallinFront() == 1){
				wall();
				robot.turnLeft();
				for(Ghost g : ghosts){
					if(g.isValid()){
						g.turnLeft();
					}
				}
			}
			else if(robot.wallinFront() == 2){
				wall2();
				if(turncount >= 3){
					turncount = 0;
					//thresh++;
					robot.turnLeft();
					for(Ghost g : ghosts){
						if(g.isValid()){
							g.turnLeft();
						}
					}
				}
				else{
					turncount++;
					robot.move();
					for(Ghost g : ghosts){
						if(g.isValid()){
							g.move();
						}
					}
				}
			}
			else{
				
				noWall();
				if(turncount >= 3){
					//thresh++;
					turncount = 0;
					robot.turnLeft();
					for(Ghost g : ghosts){
						if(g.isValid()){
							g.turnLeft();
						}
					}
				}
				else{
					turncount++;
					robot.move();
					for(Ghost g : ghosts){
						if(g.isValid()){
							g.move();
						}
					}
				}
			}
		}
		for(Ghost g : ghosts){
			if(g.isValid()){
				System.out.println(g.getX());
				System.out.println(g.getY());
				System.out.println(g.getX()*30 + 15);
				System.out.println(g.getY()*30 + 15);
				System.out.println(g.getOrientation());
			}
		}
		
		//Sound.beep();
		running = false;
	}
}
