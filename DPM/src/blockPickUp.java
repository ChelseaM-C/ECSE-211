import lejos.nxt.*;
 
 
 
public class blockPickUp extends Thread {
        public static final int ROTATE_SPEED = 150;
        public static final int FORWARD_SPEED = 200;
        public static final double forwardDistance = 12.0;
        public static final double forwardThreshold = 15.0;
        public final double leftRadius = 2.19;
        public final double rightRadius = 2.192;
        public final double width = 9.85;
        private ClawDriver claw;
        private UltrasonicController usController;
        private NXTRegulatedMotor leftMotor,rightMotor;
       
        //initiating objects
        WheelDriver wheels = new WheelDriver(leftMotor, rightMotor) ;
        Odometer odo = new Odometer();
       
        public blockPickUp (ClawDriver claw, NXTRegulatedMotor leftMotor, NXTRegulatedMotor rightMotor, UltrasonicController usController) {
                this.claw = claw;
                this.leftMotor = leftMotor;
                this.rightMotor = rightMotor;
                this.usController = usController;
        }
 
        public void scanRange() {
               
                int threshold = 20;            
                double turnAngle = -10;
                int storeI = 0;
                int storeW = 0;
                int loopBreak = 0;
                int secondLap = 0;
 
                boolean[] angleDirection = new boolean[9];
               
                //this loop continues until the block is found
                while (true){
               
                                //first check, if block in front, move forward and exit while loop
                        if (usController.getFilteredDist() < threshold) {
//                                travelForward(forwardThreshold);
//                                claw.close();
                                break;
                        }
                        //else start scanning starting from CCW 60 degrees
                        rotateCCW(60);
                        //check every angle in the interval of 10 degrees between 60 degrees CCW to 30 degree CW
                        //if block is found, exit loop
                        //if not, then enter the next if statement
                        for (int i = 0 ; i < 9 ; i++){
                                rotateCCW(turnAngle);
                                if (usController.getFilteredDist() < threshold){
                                                angleDirection[i] = true;                                              
                                                Sound.beep();
                               
                                                if (i > 0){
 
                                                        if (angleDirection[i-1] && angleDirection[i]){
                                                                Sound.twoBeeps();
                                                                rotateCCW(-turnAngle/2);
                                                                storeI = i;
                                                                LCD.drawString("i:"+storeI, 2, 5);
                                                                LCD.drawString("W: "+storeW, 2, 6);
                                                        loopBreak = 1;
                                                        break;
                                                }
                                                }      
                                }
                                   
                        }
                        //if loop is not broken, turn back to forward direction from 30 degrees CW
                        //move forward by certain distance, and continue scan again
                        if (loopBreak != 1){
                                rotateCCW(30);
                                travelForward(forwardDistance);
                                storeW++;
                               
                                if (storeW >= 5){
                                        rotateCCW(180);
                                        travelForward(storeW*forwardDistance);
                                        rotateCCW(-90);
                                        travelForward(30);
                                        rotateCCW(-90);
                                        storeI = 0;
                                        storeW = 0;
                                        secondLap++;
                                        scanRange();
                                }
                        }
                        else {
                                break;
                        }
                }
               
                travelForward(forwardThreshold);
                claw.close();
 
                Sound.beep();
                rotateCCW(-180);
                travelForward(forwardThreshold);
               
                if (loopBreak > 0 && storeI < 6){
                        Sound.buzz();
                    rotateCCW((6.5 - storeI)*turnAngle);
                    }
                if (loopBreak > 0 && storeI > 6){
                        rotateCCW(-(storeI - 6.5)*turnAngle);
                        }
                Sound.buzz();
                travelForward(storeW*forwardDistance);
               
//                if (secondLap > 0){
//                        rotateCCW(90);
//                        travelForward(30);
//                        rotateCCW(-90);
//                }
               
        }
               
        public void travelForward(double forwardDistance){
                leftMotor.setSpeed(FORWARD_SPEED);
                rightMotor.setSpeed(FORWARD_SPEED);
                leftMotor.rotate(convertDistance(leftRadius, forwardDistance), true);
                rightMotor.rotate(convertDistance(rightRadius, forwardDistance), false);
        }
       
        //rotate counterclockwise
        public void rotateCCW(double angle) {
            leftMotor.setSpeed(ROTATE_SPEED);
            rightMotor.setSpeed(ROTATE_SPEED);
            leftMotor.rotate(-convertAngle(leftRadius, width, angle), true);
            rightMotor.rotate(convertAngle(rightRadius, width, angle), false);
           
        }
       
        private static int convertDistance(double radius, double distance) {
                return (int) ((180.0 * distance) / (Math.PI * radius));
        }
 
        private static int convertAngle(double radius, double width, double angle) {
                return convertDistance(radius, Math.PI * width * angle / 360.0);
        }
 
}