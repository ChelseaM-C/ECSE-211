import java.util.Stack;
     



    import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
     /**
      * Navigator for the robot
      * @author DPM team 4
      *
      */
    public class Navigation extends Thread{
           
            private double x,y,theta;
            Odometer odometer;
            UltrasonicSensor usensor;
            WheelDriver driver;
           
            //tolerance / error
            private static final double RADS = 0.05;
            private static final int FORWARD_SPEED = 250;
            private static final int speedDifference = 3;
            private static final int ROTATE_SPEED = 150;
            private static final double leftRadius = 2.19;
            private static final double rightRadius = 2.192;
            private static final double width = 9.85;
            private boolean navigating,line;
            public Stack<Waypoint> waypoints;
            
            private FindLine left,right;
            
            private CorrectionAngel angel;
     
           
            /**
             * Create a new Navigation
             * @param x starting x
             * @param y starting y
             * @param odometer odometer
             * @param driver wheel driver
             * @param left FindLine left
             * @param right FindLine right
             */
            public Navigation(double x, double y, Odometer odometer, WheelDriver driver,FindLine left, FindLine right){
                    this.x = odometer.getX();
                    this.y = odometer.getY();
                    theta = odometer.getTheta();
                    line = false;
                   
                    this.left = left;
                    this.right = right;
                    this.driver = driver;
                   
                    waypoints = new Stack<Waypoint>();
                   
                    this.odometer = odometer;
                   
                    navigating = true;
                   
                    //initialize the motors acceleration
                    Motor.A.setAcceleration(3000);
                    Motor.B.setAcceleration(3000);
                   
                   
            }
           /**
            * set the correctionAngel
            * @param a the new correction
            */
            public void setAngel(CorrectionAngel a){
            	angel = a;
            }
           
            
           
            //Gets the angle between robot and destination
    
            
            private void correct(){
            	if(odometer.getTheta() > Math.PI){
            		driver.setSpeed(20, -20);
            	}
            	else{
            		driver.setSpeed(-20, 20);
            	}
            	while((odometer.getTheta() > RADS)){
            		if(odometer.getTheta() < RADS){
            			break;
            		}
            	}
            	driver.setSpeed(0, 0);
            }
            //Turns to angle theta (absolute)

           
            //Returns true if there is another thread using travelto or turnto
            boolean isNavigating(){
                    return navigating;
            }
           
            //Main Runnable

            /**
             * If line is found, call this
             */
            public void foundLine(){
            	line = true;
            	
            }
            /**
             * Moves robot forward with correction on
             */
            public void testTile(){
            	angel = null;
            	angel = new CorrectionAngel(this.odometer,left,right, this);
            	angel.start();
            	//angel.toggle();
            	odometer.setTheta(0);
            	oneTileForward();
//            	while(line == false){
//                	if(line){
//                		angel.toggle();
//                		break;
//                	}
//            	}
            	
            	if(line){
            		//angel.toggle();
            		odometer.pauseTheta();
            		correct();
            		odometer.pauseTheta();
            	}
            	line = false;
            	
            }
            /**
             * turns the robot clockwise by 90 degrees
             */
            public void turnCW(){
            				Motor.A.setAcceleration(500);
            				Motor.B.setAcceleration(500);
                            Motor.A.setSpeed(ROTATE_SPEED);
                            Motor.B.setSpeed(ROTATE_SPEED);
     
                            Motor.A.rotate(convertAngle(leftRadius, width, 95.5), true);
                            Motor.B.rotate(-convertAngle(rightRadius, width, 95.5), false);
            }
            /**
             *  turns the robot counterclockwise by 90 degrees
             */
            public void turnCCW() {
            				Motor.A.setAcceleration(500);
							Motor.B.setAcceleration(500);
                            Motor.A.setSpeed(ROTATE_SPEED);
                            Motor.B.setSpeed(ROTATE_SPEED);
     
                            Motor.A.rotate(-convertAngle(leftRadius, width, 95.5), true);
                            Motor.B.rotate(convertAngle(rightRadius, width, 95.5), false);
            }
     		/**
     		 * method to move the robot 1 tile forward (30.24 centimeters)
     		 */
            public void oneTileForward() {
							Motor.A.setAcceleration(500);
							Motor.B.setAcceleration(500);
                            Motor.A.setSpeed(FORWARD_SPEED);
                            Motor.B.setSpeed(FORWARD_SPEED - speedDifference);
     
                            Motor.A.rotate(convertDistance(leftRadius, 30.24 +2), true);
                            Motor.B.rotate(convertDistance(rightRadius, 30.24 +2), false);
            }
            public void BBBACKDATASSUP() {
				Motor.A.backward();
				Motor.B.backward();
				Motor.A.setAcceleration(500);
				Motor.B.setAcceleration(500);
                Motor.A.setSpeed(FORWARD_SPEED);
                Motor.B.setSpeed(FORWARD_SPEED - speedDifference);

                Motor.A.rotate(-convertDistance(leftRadius, 30.24 +2), true);
                Motor.B.rotate(-convertDistance(rightRadius, 30.24 +2), false);
}
            /**
             * Turns 180 degrees
             */
            public void turnAround(){
				Motor.A.setAcceleration(500);
				Motor.B.setAcceleration(500);
                Motor.A.setSpeed(ROTATE_SPEED);
                Motor.B.setSpeed(ROTATE_SPEED);

                Motor.A.rotate(-convertAngle(leftRadius, width, 95.5*2), true);
                Motor.B.rotate(convertAngle(rightRadius, width, 95.5*2), false);
            }
            private static int convertDistance(double radius, double distance) {
                    return (int) ((180.0 * distance) / (Math.PI * radius));
            }
     
            private static int convertAngle(double radius, double width, double angle) {
                    return convertDistance(radius, Math.PI * width * angle / 360.0);
            }
            
//            public void travelPath(Path p){
//            	for(Waypoint point : p.getPoints()){
//            		travelTo(point);
//            	}
//            }
            

     
    }