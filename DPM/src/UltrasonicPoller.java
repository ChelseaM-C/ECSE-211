import lejos.nxt.UltrasonicSensor;

/**This is the Ultrasonic Poller class, which will read data from the sensor at given intervals
 * 
 * @author Daniel
 *
 */
public class UltrasonicPoller extends Thread {
	private UltrasonicSensor sensor;
	private int usDist;
	private static final long UPDATE_PERIOD = 10;
	
	/**Ultrasonic Controller takes a ultrasonic sensor, and collects data from it.
	 * @param port 		The Ultrasonic sensor which will be polled.
	 */
	public UltrasonicPoller(UltrasonicSensor us){
		sensor = us;
	}
	
	/** Returns the distances stored in the poller
	 * 
	 * @return The distance stored locally (NOT DIRECTLY FROM ULTRASONIC SENSOR)
	 */
	public int getDist(){
		return usDist;
	}
	
	public void run(){
	long start,end;
		while(true){
			start = System.currentTimeMillis();
			this.usDist = sensor.getDistance();
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
