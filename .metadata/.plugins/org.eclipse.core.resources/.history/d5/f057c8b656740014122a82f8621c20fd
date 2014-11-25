import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.util.Delay;
 
public class Correction extends Thread{
        private static final long CORRECTION_PERIOD = 100;
 
        private static final int FORWARD_SPEED = 200;
 
        private Odometer odo;
        private LightSensorController right,left;
        private int val, valRight, valLeft, preValRight, preValLeft;
       
        // the midpoint of the tile is set as well as the its width
        private final double tileWidth = 30.48;
        // distance measured between the light sensor's position and the the center between the wheels
        private double sensorCenter = 0.0;
       
        // array containing all the horizontal lines
        private double[] yLines = {0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0/*, 7.0, 8.0, 9.0, 10.0*/};
       
        // array containing all the vertical lines
        private double[] xLines = {0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0/*, 7.0, 8.0, 9.0, 10.0*/};
       
        /**
         * Constructor
         * @param odometer
         * @param Rigth Sensor
         * @param Left Sensor
         */
        public Correction(Odometer odo, LightSensorController right, LightSensorController left) {
                this.odo = odo;
                this.right = right;
                this.left = left;
        }
 
        /**
         * Execute the thread
         */
        public void run() {
                try {Thread.sleep(20);} catch (InterruptedException e) {}
                preValRight = right.getFilteredVal();
                preValLeft = left.getFilteredVal();
               
                while(true) {
                       
                        // important to limit resources from the CPU
                        try {Thread.sleep(10);} catch (InterruptedException e) {}
                       
                        // activate correction only when the robot is moving in a straight line and no line is detected
                        while (Motor.A.getSpeed() >= FORWARD_SPEED && Motor.B.getSpeed() >= FORWARD_SPEED){
//                      while (right.getFilteredVal() < 490){
                                LCD.drawInt(right.getFilteredVal(), 3, 5);
                                valRight = right.getFilteredVal();
                                valLeft = left.getFilteredVal();
                               
                                val = getAverageValue(preValRight, preValLeft, valRight, valLeft);
                                if (right.getFilteredVal() < 490) {
//                                      LCD.drawString("OK", 3, 6);
                                        Sound.beep();
                                                               
                                        double theta = Math.toDegrees(odo.getTheta());
                                                               
                                        if (theta <= 45 || theta >= 315) { // if robot is moving NORTH, correct Y position
                                                double currentY = odo.getY();
                                                double closestLine = ((currentY - sensorCenter)*2) /(tileWidth);
                                                odo.setY(getClosest(yLines, closestLine)*tileWidth + sensorCenter);
                                        }
                                                               
                                        if (theta <= 225 || theta >= 135){ // if robot is moving SOUTH, correct Y position
                                                double currentY = odo.getY();
                                                double closestLine = (currentY + sensorCenter) / tileWidth;
                                                odo.setY(getClosest(yLines, closestLine)*tileWidth - sensorCenter);
                                        }
                                                               
                                        if (theta >= 45 && theta <= 135) { // if robot is moving EAST, correct X position
                                                double currentX = odo.getX();
                                                double closestLine = (currentX - sensorCenter) / tileWidth;
                                                odo.setX(getClosest(xLines, closestLine)*tileWidth + sensorCenter);
                                        }
                                                               
                                        if (theta >= 225 && theta <= 315){ // if robot is moving WEST, correct X position
                                                double currentX = odo.getX();
                                                double closestLine = (currentX + sensorCenter) / tileWidth;
                                                odo.setX(getClosest(xLines, closestLine)*tileWidth - sensorCenter);
                                        }
                                                       
                                        try {Thread.sleep(CORRECTION_PERIOD);} catch (InterruptedException e) {} // correction occurs only once every period
                                        valRight = right.getFilteredVal();
                                        preValRight = right.getFilteredVal();
                                        valLeft = left.getFilteredVal();
                                        preValLeft = left.getFilteredVal();
                                }      
                                preValRight = valRight;
                                preValLeft = valLeft;
//                              Delay.msDelay(42);
                        }
                }
        }
       
        private int getAverageValue(double preValRight, double preValLeft, double valRight, double valLeft){
                val = (int)((valRight - preValRight) + (valLeft - preValLeft))/2;
                return val;
        }
       
        /**
         *
         * @param array
         * @param position
         * @return The closest point to a point in the array
         */
        public double getClosest(double[] array, double position) {
            double lowestDiff = Double.MAX_VALUE;
            double result = 0.0;
            for (double i : array) {
                double diff = Math.abs(position - i);
                if (diff < lowestDiff) {
                    lowestDiff = diff;
                    result = i;
                }
            }
            return result;
        }
       
}