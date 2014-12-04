import lejos.nxt.NXTRegulatedMotor;

/**Controls the opening and closing of the claw
 * 
 * @author Daniel
 *
 */
public class ClawDriver {
	private NXTRegulatedMotor claw;
	
	private boolean isClosed;
	
	private static final int CLOSING_SPEED = 300;
	
	private static final int CLOSING_DIST = 10;
	
	/**Creates a new claw driver from a motor
	 * 
	 * @param claw the motor which is connected to the claw
	 */
	public ClawDriver(NXTRegulatedMotor claw){
		this.claw = claw;
		isClosed = false;
	}
	
	/**
	 * Closes the claw
	 */
	public void close(){
		if(!isClosed){
			//close here
			claw.setSpeed(CLOSING_SPEED);
			claw.rotate(1100);
			isClosed = true;
		}
	}
	
	/**
	 * Opens the claw
	 */
	public void open(){
		if(isClosed){
			//open here
			claw.setSpeed(CLOSING_SPEED);
			claw.rotate(-1100);
			isClosed = false;
		}
	}
	
}
