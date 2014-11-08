
public class OdometryCorrection extends Thread{
	private Odometer odo;
	
	private LightSensorController right,left;
	
	private static final int WIDTH = 3;
	
	private static final int LINE = 50;
	
	public OdometryCorrection(Odometer odo, LightSensorController right, LightSensorController left){
		this.odo = odo;
		this.right = right;
		this.left = left;
	}
	
	public double calculate(int left,int right){
		 double result = odo.getTheta();
		 return result;
	}
	
	
	public void run(){
		while(true){
			
		}
	}
	
}