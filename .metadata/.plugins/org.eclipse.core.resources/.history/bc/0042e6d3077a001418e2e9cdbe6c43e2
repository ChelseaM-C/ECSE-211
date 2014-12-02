import lejos.nxt.ColorSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.Color;


public class LightSensorPoller extends Thread{
	private ColorSensor sensor;
	private int lightval;
	private static final long UPDATE_PERIOD = 20;
	
	/**Ultrasonic Controller takes a ultrasonic sensor, and collects data from it.
	 * @param port 		The Ultrasonic sensor which will be polled.
	 */
	public LightSensorPoller(ColorSensor cs){
		sensor = cs;
//		cs.setFloodlight(Color.GREEN);
		cs.setFloodlight(true);
	}
	
	/** Returns the distances stored in the poller
	 * 
	 * @return The distance stored locally (NOT DIRECTLY FROM ULTRASONIC SENSOR)
	 */
	public int getLight(){
		return lightval;
	}
	
	public void run(){
	long start,end;
		while(true){
			start = System.currentTimeMillis();
			this.lightval = sensor.getNormalizedLightValue();
			end = System.currentTimeMillis();
			if(end - start < UPDATE_PERIOD){
				try{
					Thread.sleep(UPDATE_PERIOD - (end - start));
				}
				catch(InterruptedException e){
					//Nothing, mang
				}
			}
		}
	}
}