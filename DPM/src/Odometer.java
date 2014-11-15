//Daniel Burke 260507112
//Mariane Kim 260498054
//Group 12
//TAKEN FROM LAB 2
import lejos.nxt.Motor;

/*
 * Odometer.java
 */
/**Odometer Class gets the tachometer readings from the motors, and calculates the relative position of the robot.
 * 
 * @author Daniel
 *
 */
public class Odometer extends Thread {
	// robot position
	private double x, y, theta;

	// odometer update period, in ms
	private static final long ODOMETER_PERIOD = 25;

	// lock object for mutual exclusion
	private Object lock;
	
	private int lastTachoLeft,  lastTachoRight, nowTachoL, nowTachoR;
	private double rightArcLength, leftArcLength, deltaTheta, deltaArcLength;

	// default constructor
	/**Initializes an Odometer with starting position at [0,0], heading 0
	 * 
	 */
	public Odometer() {
		x = 0.0;
		y = 0.0;
		theta = 0.0;
		lock = new Object();
	}
	
	//theta correction(wraps around 2pi)
	public double thetaCorrection(double radians){
		double t = radians;
		if(t < 0){
			t =(2*Math.PI) + t;
		}
		else if(t >= (2*Math.PI)){
			t = t - (2*Math.PI);
		}
		return t;
	}
	
	
	// run method (required for Thread)
	public void run() {
		long updateStart, updateEnd;

		while (true) {
			updateStart = System.currentTimeMillis();
			// put (some of) your odometer code here

			//define the new tacho count
			nowTachoL = Motor.A.getTachoCount();
			nowTachoR = Motor.B.getTachoCount();

			//difference between the previous tacho count and the current one
			int differenceTachoL = nowTachoL - lastTachoLeft;
			int differenceTachoR = nowTachoR - lastTachoRight;

			//set the current tacho to the previous tacho count
			lastTachoLeft = Motor.A.getTachoCount();
			lastTachoRight = Motor.B.getTachoCount();

			//calculate the arc length traveled by each wheel
			leftArcLength = ((differenceTachoL * 2 * Math.PI) /360) * 2.12;
			rightArcLength = ((differenceTachoR * 2 * Math.PI) / 360) * 2.12;

			//determine the change in angle and arc length
			deltaTheta = (leftArcLength - rightArcLength) / 10.3;
			deltaArcLength = (leftArcLength + rightArcLength) / 2;

			synchronized (lock) {
				// don't use the variables x, y, or theta anywhere but here!
				//calculations based on tutorial slides
				x = x + deltaArcLength * Math.sin(theta + (deltaTheta / 2));
				y = y + deltaArcLength * Math.cos(theta + (deltaTheta / 2));
				theta = thetaCorrection(theta + deltaTheta);
			}

			// this ensures that the odometer only runs once every period
			updateEnd = System.currentTimeMillis();
			if (updateEnd - updateStart < ODOMETER_PERIOD) {
				try {
					Thread.sleep(ODOMETER_PERIOD - (updateEnd - updateStart));
				} catch (InterruptedException e) {
					// there is nothing to be done here because it is not
					// expected that the odometer will be interrupted by
					// another thread
				}
			}
		}
	}

	// accessors
	/**Given a double array, and boolean array, will update certain positions of the array
	 * 
	 * @param position	array of positions [x,y,theta]
	 * @param update	array of booleans [x,y,theta]
	 */
	public void getPosition(double[] position, boolean[] update) {
		// ensure that the values don't change while the odometer is running
		synchronized (lock) {
			if (update[0])
				position[0] = x;
			if (update[1])
				position[1] = y;
			if (update[2])
				position[2] = theta;
		}
	}
	
	/**Synchronized x return
	 * 
	 * @return Value of x
	 */
	public double getX() {
		double result;

		synchronized (lock) {
			result = x;
		}

		return result;
	}

	/**Synchronized y return
	 * 
	 * @return Value of y
	 */
	public double getY() {
		double result;

		synchronized (lock) {
			result = y;
		}

		return result;
	}
	
	/**Synchronized theta return
	 * 
	 * @return Value of theta
	 */
	public double getTheta() {
		double result;

		synchronized (lock) {
			result = theta;
		}

		return result;
	}

	// mutators
	/**Changes the position of the odometer, given a double array
	 * 
	 * @param position position to change [x,y,theta]
	 * @param update boolean of positions that should be changed [x,y,theta]
	 */
	public void setPosition(double[] position, boolean[] update) {
		// ensure that the values don't change while the odometer is running
		synchronized (lock) {
			if (update[0])
				x = position[0];
			if (update[1])
				y = position[1];
			if (update[2])
				theta = position[2];
		}
	}
	
	/**Synchronized x mutator
	 * 
	 * @param x 
	 */
	public void setX(double x) {
		synchronized (lock) {
			this.x = x;
		}
	}
	
	/**Synchronized y mutator
	 * 
	 * @param y
	 */
	public void setY(double y) {
		synchronized (lock) {
			this.y = y;
		}
	}
	
	/**Synchronized theta mutator
	 * 
	 * @param theta 
	 */
	public void setTheta(double theta) {
		synchronized (lock) {
			this.theta = theta;
		}
	}
	
	public int getLeft(){
		return Motor.A.getTachoCount();
	}
	
	public int getRight(){
		return Motor.B.getTachoCount();
	}
}