import lejos.nxt.*;
 
 
 
public class blockPickUp extends Thread {
        public static final int ROTATE_SPEED = 150;
        public static final int FORWARD_SPEED = 200;
        public static final double forwardDistance = 12.0;
        public static final double forwardThreshold = 15.0;
        public final double leftRadius = 2.1;
        public final double rightRadius = 2.15;
        public final double width = 10.0;
        private ClawDriver claw;
        private UltrasonicSensor sensor;
        private NXTRegulatedMotor leftMotor,rightMotor;
       
        //initiating objects
        WheelDriver wheels = new WheelDriver( leftMotor, rightMotor) ;
        Odometer odo = new Odometer();
        Navigation nav = new Navigation(leftRadius, rightRadius, odo, wheels);
                UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
       
        public blockPickUp (ClawDriver claw, NXTRegulatedMotor leftMotor, NXTRegulatedMotor rightMotor ) {
                this.claw = claw;
                this.leftMotor = leftMotor;
                this.rightMotor= rightMotor;
        }
 
        public void scanRange() {
               
                int threshold = 20;            
                boolean travel = true;
                double turnAngle = -10;
                int storeI = 0;
                int storeW = 0;
                int loopBreak = 0;
                int secondLap = 0;
               
                while (travel){                
                        if (us.getDistance() < threshold) {
                                travelForward(forwardThreshold);
                                claw.close();
                                break;
                        }
                        rotateCCW(60);
                       
                        for (int i = 0 ; i < 9 ; i++){
                                rotateCCW(turnAngle);
                                if (us.getDistance() < threshold){
                                        travelForward(forwardThreshold);
                                        claw.close();
                                        loopBreak = 1;
                                        storeI = i;
                                        break;
                                }
                        }
                       
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
                   
                Sound.beep();
                rotateCCW(-180);
                travelForward(forwardThreshold);
               
                if (storeI <= 5) {
                        Sound.buzz();
                        rotateCCW(storeI*turnAngle);
                }
                if (storeI >= 7){
                        rotateCCW(-storeI*turnAngle);
                }
               
                travelForward(storeW*forwardDistance);
               
                if (secondLap > 0){
                        rotateCCW(90);
                        travelForward(30);
                        rotateCCW(-90);
                }
               
        }
               
        public void travelForward(double forwardDistance){
                leftMotor.setSpeed(FORWARD_SPEED);
                rightMotor.setSpeed(FORWARD_SPEED);
                leftMotor.rotate(convertDistance(leftRadius, forwardDistance), true);
                rightMotor.rotate(convertDistance(rightRadius, forwardDistance), false);
        }
       
        //rotate 45 degrees counterclockwise
        public void rotateCCW(double angle) {
            leftMotor.setSpeed(ROTATE_SPEED);
            rightMotor.setSpeed(ROTATE_SPEED);
            leftMotor.rotate(-convertAngle(leftRadius, width, angle), true);
            rightMotor.rotate(convertAngle(rightRadius, width, angle), false);
           
        }
       
        private int getFilteredData() {
            int distance;
            // do a ping
            us.ping();
            // wait for the ping to complete
            try { Thread.sleep(50); } catch (InterruptedException e) {}
            // there will be a delay here
            distance = us.getDistance();
            return distance;
    }
       
       
        private static int convertDistance(double radius, double distance) {
                return (int) ((180.0 * distance) / (Math.PI * radius));
        }
 
        private static int convertAngle(double radius, double width, double angle) {
                return convertDistance(radius, Math.PI * width * angle / 360.0);
        }
 
}