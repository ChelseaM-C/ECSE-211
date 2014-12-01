import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

/**Controls the motors for the wheels
 * 
 * @author Daniel
 * yyolo
 * richard is here
 * wei is here
 * Jessy is here
 * denis is here
 */
public class WheelDriver {
	private NXTRegulatedMotor leftMotor,rightMotor;
	private static final int FORWARD_SPEED = 300;
	private static final int ROTATION_SPEED = 200;
	private static final int ACCELERATION_SPEED = 3000;
	
	public WheelDriver(NXTRegulatedMotor left, NXTRegulatedMotor right){
		this.leftMotor = left;
		this.rightMotor = right;
	}
	
	public void setSpeed(int left, int right){
		if(left == 0){
			Motor.A.stop();
		}
		else if(left > 0){
			Motor.A.forward();
			Motor.A.setSpeed(left);
		}
		else{
			Motor.A.backward();
			Motor.A.setSpeed(Math.abs(left));
		}
		
		if(right == 0){
			Motor.B.stop();
		}
		else if(right > 0){
			Motor.B.forward();
			Motor.B.setSpeed(right);  //changed made
		}
		else{
			Motor.B.backward();
			Motor.B.setSpeed(Math.abs(right));
		}
	}
	
	
}
