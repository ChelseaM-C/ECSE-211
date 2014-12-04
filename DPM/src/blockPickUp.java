import lejos.nxt.*;
/** Detect the block and pick up
*
* @author Richard Wu, Wei Wang
*
*/
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
    
    /**
     * Constructor for pick up a block
     *
     * @param claw
     * @param leftMotor left motor
     * @param rightMotor right motor
     * @param usController get distance between the robot and the object
     */
    public blockPickUp (ClawDriver claw, NXTRegulatedMotor leftMotor, NXTRegulatedMotor rightMotor, UltrasonicController usController) {
        this.claw = claw;
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
        this.usController = usController;
    }

    /**
     * For scan the pick up area, if there is a block in front, claw closes and the robot goes back to the original coordinates
     */
    public void scanRange() {
        int threshold = 20;
        double turnAngle = -10;
        int storeI = 0;
        int storeW = 0;
        int loopBreak = 0;
        int secondLap = 0;
        int leftRight = -1;
        
        boolean[] turnDirection = new boolean [14];

        while (true){
            
            boolean[] blockDetect = new boolean[12];

            //if the block is in front, exit the while loop
            if (usController.getFilteredDist() < threshold) {
                break;
            }
            //turn to -60 and scan every angle in the interval of 10 degrees from -60 to +60 degrees
            rotateCCW(60);
            for (int i = 0 ; i < 11 ; i++){
                rotateCCW(turnAngle);

                //true if the sensor detects somthing
                if (usController.getFilteredDist() < threshold){
                    blockDetect[i] = true;

                    //if there are two consecutive true in the array blockDetect, turn to a certain angle
                    //store the index of the array and break the loop
                    if (i > 0){
                        if (blockDetect[i-1] && blockDetect[i]){
                            rotateCCW(- 0.5 * turnAngle);
                            storeI = i;
                            loopBreak = 1;
                            break;
                        }
                    }
                }
            }
            //if loop is not broken, turn back to forward direction from 60 degrees CW
            //move forward by certain distance, and scan again
            if (loopBreak != 1){
                rotateCCW(60);
                travelForward(forwardDistance);
                storeW++;

                if (storeW > 4){
                    //turn to -110 and scan every angle in the interval of 10 degrees from -70 to +70 degrees
                    //in order to determine the block is on its left or right
                    rotateCCW(110);
                    for (int i = 0 ; i < 15; i++){
                        rotateCCW(-turnAngle);
                        if (usController.getFilteredDist() < threshold + 10){
                            Sound.beep();
                            turnDirection[i] = true;
                            leftRight = i;
                            break;
                        }
                    }
                    if (leftRight == -1){
                        rotateCCW(-70);
                        travelForward(storeW*forwardDistance);
                        rotateCCW(-180);
                    }
                    //if the block is on the left, move a tile towards left and scan again
                    //otherwise, move a tile towards right and scan again
                    if (leftRight > -1 && leftRight <= 7){
                        rotateCCW((leftRight - 7)* turnAngle);
                        travelForward(storeW*forwardDistance);
                        rotateCCW(-90);
                        travelForward(30);
                        rotateCCW(-90);
                        secondLap++;
                    } else if (leftRight > 7){
                        rotateCCW((leftRight - 7)*turnAngle);
                        travelForward(storeW*forwardDistance);
                        rotateCCW(90);
                        travelForward(30);
                        rotateCCW(90);
                        secondLap++;
                    }
                    storeI = 0;
                    storeW = 0;
                    scanRange();
                }
            } else{
                break;
            }
        }
        travelForward(forwardThreshold);
        claw.close();
        
        //retrace the steps and goes to the starting position
        if (loopBreak > 0 && storeI < 6){
            rotateCCW(180);
            travelForward(forwardThreshold);
            rotateCCW((6.5 - storeI)*turnAngle);
            travelForward(storeW*forwardDistance);
            
        } else if (loopBreak > 0 && storeI > 6){
            rotateCCW(180);
            travelForward(forwardThreshold);
            rotateCCW((6.5 - storeI)*turnAngle);
            travelForward(storeW*forwardDistance);
        }

        if (secondLap > 0 && leftRight <= 6){
            rotateCCW(90);
            travelForward(30);
            rotateCCW(-90);
        }
        if (secondLap > 0 && leftRight > 6){
            rotateCCW(-90);
            travelForward(30);
            rotateCCW(90);
        }
    }

    /**
     * @param forwardDistance distance to travel in cm
     */
    public void travelForward(double forwardDistance){
        leftMotor.setSpeed(FORWARD_SPEED);
        rightMotor.setSpeed(FORWARD_SPEED);
        leftMotor.rotate(convertDistance(leftRadius, forwardDistance), true);
        rightMotor.rotate(convertDistance(rightRadius, forwardDistance), false);
    }

    /**
     * @param angle angle in degrees
     */
    public void rotateCCW(double angle) {
        leftMotor.setSpeed(ROTATE_SPEED);
        rightMotor.setSpeed(ROTATE_SPEED);
        leftMotor.rotate(-convertAngle(leftRadius, width, angle), true);
        rightMotor.rotate(convertAngle(rightRadius, width, angle), false);
    }

    /**
     * Calculate the distance for motor's rotation
     * @return distance
     */
    
    private static int convertDistance(double radius, double distance) {
        return (int) ((180.0 * distance) / (Math.PI * radius));
    }

    /**
     * Calculate the angle for motor's rotation
     * @return angle
     */
    private static int convertAngle(double radius, double width, double angle) {
        return convertDistance(radius, Math.PI * width * angle / 360.0);
    }
}
