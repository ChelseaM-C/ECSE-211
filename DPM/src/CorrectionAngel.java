import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.nxt.comm.RConsole;
public class CorrectionAngel extends Thread{
        private Odometer odo;
       
        private LightSensorController right,left;
       
        private static final double WIDTH = 17.4;
       
        private static final int PERIOD = 12;
       
       
        private double xLast,yLast;
       
        private double x,y;
        private boolean leftFound = false, rightFound = false ;
 
        private WheelDriver wheel;
        private FindLine lineFinderRight ;
        private FindLine lineFinderLeft ;
 
       
       
        public CorrectionAngel(Odometer odo, LightSensorController left, LightSensorController right, WheelDriver nav, FindLine lineFinderLeft ,FindLine lineFinderRight ){
                this.odo = odo;
                this.right = right;
                this.left = left;
                this.lineFinderLeft = lineFinderLeft ;
                this.lineFinderRight = lineFinderRight ;
               
                this.wheel = nav ;
        }
       
        public double calculate(boolean right){
                double xDist = x - xLast;
                double yDist = y - yLast;
                double result;
               
                double position = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
               
                if(right){
                        result = 0.0 - Math.atan2(position, WIDTH);
                }
                else{
                        result = 0.0 + Math.atan2(position, WIDTH);
                }
               
               
        //      LCD.drawString(" pos"+position, 0, 4) ;
               
                 return result;
                 
        }
       
       
       
        synchronized public double getNewTheta(double theta){
                double oldTheta = Math.toDegrees(odo.getTheta());
                double newTheta = 0;
               
                if (oldTheta >= 45 && oldTheta <= 135){    /// E
                        newTheta = (Math.PI/2) + theta;
                } else if (oldTheta >= 315 || oldTheta <= 45){         //N
                        if(theta > 0)
                        {                      
                                newTheta = theta;
                        }else{
                               
                                newTheta = (2*Math.PI)+ theta ;
                        }
                } else if (oldTheta >= 225 && oldTheta <= 315){          // W
                        newTheta = ((3/2)*Math.PI) + theta;
                } else if (oldTheta >= 135 && oldTheta <= 225){          //S
                        newTheta = Math.PI + theta;
                }
               
                //debug
//              LCD.drawString(" Calc"+Math.toDegrees(theta), 0, 5) ;
//              LCD.drawString(oldTheta +" "+ newTheta, 0, 6);
               
                return newTheta;
               
        }
       
        public void run(){
                double newTheta = 0;
                int rightValue = right.getFilteredVal();
                int leftValue = left.getFilteredVal();
                long updateStart, updateEnd;
               
                while(true){
               
                        updateStart = System.currentTimeMillis() ;
                       
                        if(lineFinderRight.isLine()) {
                               
                                yLast = odo.getY();
                                xLast = odo.getX();
                               
                               
                                //Wait for left Sensor
                                while(!leftFound){
                                        if(lineFinderLeft.isLine()){
                                                Sound.beep() ;
                                                y = odo.getY();
                                                x = odo.getX();
                                                lineFinderLeft.setlightToFalse() ;
                                lineFinderRight.setlightToFalse() ;
       
                                               
                                                newTheta = calculate(true);
                                                leftFound = true;
                                        }
                       
                                }
                               
                                odo.setTheta(getNewTheta(newTheta));
                               
                               
                                updateEnd = System.currentTimeMillis() ;
                               
                                if (updateEnd - updateStart < PERIOD) {
                                        try {
                                                Thread.sleep(PERIOD - (updateEnd - updateStart));
                                        } catch (InterruptedException e) {
                                                // there is nothing to be done here because it is not
                                                // expected that the odometer will be interrupted by
                                                // another thread
                            }
                                       
                                }
                                   
                                   
                                try {
                                        Thread.sleep(2000);
                                } catch (InterruptedException e) {}
                               
                        }else  if(lineFinderLeft.isLine()){
                                                        //Left Sensor passes first                                                     
                                   yLast = odo.getY();
                                   xLast = odo.getX();
 
                                   
                                   RConsole.println("  left") ;
                                //Wait for right Sensor
                                   
                                   while(!rightFound){
                                           
                                          if(lineFinderRight.isLine()){
                                                 
                                                 Sound.buzz();
                                                 y = odo.getY();
                                                 x = odo.getX();       
 
                                                 lineFinderLeft.setlightToFalse() ;
                                     lineFinderRight.setlightToFalse() ;
                                             newTheta = calculate(false);
                                                 rightFound = true;
                                            }
       
                                       
                                    }  
                                       
                                odo.setTheta(getNewTheta(newTheta));                   
                               
                                updateEnd = System.currentTimeMillis() ;
                               
                                if (updateEnd - updateStart < PERIOD) {
                                        try {
                                                Thread.sleep(PERIOD - (updateEnd - updateStart));
                                        } catch (InterruptedException e) {
                                                // there is nothing to be done here because it is not
                                                // expected that the odometer will be interrupted by
                                                // another thread
                            }
                                }
                                try {
                                        Thread.sleep(2000);
                                } catch (InterruptedException e) {}
                                 
                        }
               
                       
                       
                        }
                       
        }
       
 
        }