import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.nxt.comm.RConsole;
public class CorrectionAngel extends Thread{
        private Odometer odo;
       
        private LightSensorController right,left;
       
        private static final double WIDTH = 17.4;
       
        private static final int LINE = 470;
       
        private static final int PERIOD = 12;
       
        private int waitCounter = 0 ;
        private double waitLimit = 15;
       
        private double xLast,yLast;
       
        private double x,y;
        private boolean leftFound = false, rightFound = false ;
        private Object lock ;
        
        private boolean locked;
        
        private Navigation nav;
        private WheelDriver driver;
        private FindLine lineFinderRight ;
        private FindLine lineFinderLeft ;
       
       
        public CorrectionAngel(Odometer odo, LightSensorController left, LightSensorController right, WheelDriver driver, FindLine lineFinderLeft ,FindLine lineFinderRight, Navigation nav ){
                this.odo = odo;
                this.right = right;
                this.left = left;
                this.lineFinderLeft = lineFinderLeft ;
                this.lineFinderRight = lineFinderRight ;
               
                locked = true;
                
                this.nav = nav;
                this.driver = driver;
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
               
                LCD.drawString(" pos"+position, 0, 4) ;
                 return result;
                 
        }
       
       public void toggle(){
    	   locked = !locked;
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
                LCD.drawString(" Calc"+Math.toDegrees(theta), 0, 5) ;
                LCD.drawString(oldTheta +" "+ newTheta, 0, 6);
               
                return newTheta;
        }
       
        public void run(){
                double newTheta = 0;
                int rightValue = right.getFilteredVal();
                int leftValue = left.getFilteredVal();
                long updateStart, updateEnd;
               
                while(true){
                        //Right sensor passes first
//                      LCD.drawInt(left.getFilteredVal(), 0, 4);
//              LCD.drawInt(right.getFilteredVal(), 4, 4);
               
                        updateStart = System.currentTimeMillis() ;
                       
                        if(lineFinderRight.line() && !locked) {
                               
                                yLast = odo.getY();
                                xLast = odo.getX();
                               
                                 RConsole.println("  Right") ;
                               
                                //Wait for left Sensor
                                while(!leftFound){
                                        if(lineFinderLeft.line()){
                                                y = odo.getY();
                                                x = odo.getX();
                               
       
                                                nav.foundLine();
                                                newTheta = calculate(true);
                                                leftFound = true;
                                                Sound.beep() ;
                                        }
                                //       waitCounter++ ;
                                         
                                         RConsole.println("  waiting for lef") ;
                                //       if( waitCounter == waitLimit){  break;}
                                }
                               
                                odo.setTheta(getNewTheta(newTheta));
                                LCD.drawString("Final "+ Math.toDegrees(odo.getTheta()), 4, 6);
                                Sound.twoBeeps() ;
                               
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
                               
                        }else  if(lineFinderLeft.line() && !locked){
                                                        //Left Sensor passes first                                                     
                                   yLast = odo.getY();
                                   xLast = odo.getX();
//                                  LCD.drawString("xLa "+xLast , 0, 0) ;
//                                  LCD.drawString("yLa "+yLast , 0, 1) ;
                                   
                                   RConsole.println("  left") ;
                                //Wait for right Sensor
                                   while(!rightFound){
                                          if(lineFinderRight.line()){
                                                 y = odo.getY();
                                                 x = odo.getX();                                 
//                                               LCD.drawString("x "+x , 0, 2) ;
//                                               LCD.drawString("y "+y , 0, 3) ;
                                             newTheta = calculate(false);
                                                 rightFound = true;
                                                 Sound.buzz() ;
                                                 nav.foundLine();
                                            }
                                          RConsole.println("  waiting for right") ;
                                    }  
                                       
                                odo.setTheta(getNewTheta(newTheta));                   
                                LCD.drawString("Final "+ Math.toDegrees(odo.getTheta()), 4, 6);
                               
                                updateEnd = System.currentTimeMillis() ;
                                Sound.twoBeeps() ;
                               
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
               
                        //*
                        /*
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
                        */
                       
                        }
                       
        }
         
       
       
       
       
       
       
        }