import lejos.nxt.*;
 
   public class FindLine extends Thread{
 
    //-------------------------------------------------------------------------------//
    //        Robot Constants                                                             //
    //-------------------------------------------------------------------------------//
 
    /**
     * Left Light Sensor
     */
    protected LightSensorPoller left;
 
 
 
    /**
     * The integer time period in milliseconds of the LineFinder update cycle.
     * @value
     */
    protected static final int PERIOD = 25;
 
    /**
     * Light Sensor value threshold between regular floor and a black line.
     * @value
     */
    protected static final int THRESHOLD = -28;
       
        private int [] leftReadings = new int[4] ;     //{0, 0.0, 0.0, 0.0, 0.0};
 
       
        private double derivativeLeft1 ;
        private double derivativeLeft2 ;
       
        private boolean isline = false ;
 
 
       
        public  FindLine( LightSensorPoller left) {
            this.left = left;
        //     this.right = right ;
        }
       
        public void run()   {
       
       
       
             long updateStart, updateEnd;
             
                leftReadings[0] = 0;
                leftReadings[1]  =  0;
                leftReadings[2]  =  0;
                        leftReadings[3]  = 0;
 
                        LCD.clear();
                       
         while (true) {
                        updateStart = System.currentTimeMillis();
       
                 leftReadings[3] = left.getLight();
 
                 this.update() ;
                 this.isLine() ;
                 updateEnd = System.currentTimeMillis();
                 this.beepLine() ;
                 //Debug  
                 
//               LCD.drawString("0| "+leftReadings[0]+" 1| "+leftReadings[1] , 0, 0);
//               LCD.drawString("2| "+leftReadings[2]+" 3| "+leftReadings[3] , 0, 1);
//               
////             LCD.drawString("0| "+rightReadings[0]+" 1| "+rightReadings[1] , 0, 2);
////             LCD.drawString("2| "+rightReadings[2]+" 3| "+rightReadings[3], 0, 3);
//               
//               LCD.drawString("D1| "+derivativeLeft1+" D2"+derivativeLeft2 , 0, 3);
//               LCD.drawString("D3| "+derivativeLeft3, 0, 4);
//               
//               LCD.drawString("Delta| "+(derivativeLeft2 - derivativeLeft1), 0, 5);
//               LCD.drawString("Time| "+((updateEnd - updateStart)/1000), 0, 6);
//               
////             LCD.drawString("D1| "+derivativeRight1+" D2"+derivativeRight2 , 0, 6);
////             LCD.drawString("D3| "+derivativeRight3, 0, 7);
////             
                 
                        if (updateEnd - updateStart < PERIOD) {
                                try {
                                        Thread.sleep(PERIOD - (updateEnd - updateStart));
                                } catch (InterruptedException e) {
                                        // there is nothing to be done here because it is not
                                        // expected that the odometer will be interrupted by
                                        // another thread
                    }
       
                }
                 }
                 
            }
               
           public boolean isLine( ) {
        //derivative
           derivativeLeft1 =  (leftReadings[1] - leftReadings[0]);
           derivativeLeft2 =   (leftReadings[2] - leftReadings[1]) ;
 
 
         
             if(derivativeLeft1 == 0 ||leftReadings[0] ==0 || leftReadings[1] == 0 ||  leftReadings[2] == 0  || leftReadings[3] == 0 ){ return false;}
           
             if ( (derivativeLeft2 - derivativeLeft1) <  THRESHOLD ) {     //if value started dropping
                 
                 isline = true;
                 return true ;
                 }else{
                   isline = false ;
                   return false ;
                 }
           
//         
//         
           
           }
           public void update()  {
                 
                        // moving window : reading 0, reading 1 , reading 2 , reading 3
               
                        leftReadings[0]  =  leftReadings[1] ;
                leftReadings[1]  =  leftReadings[2] ;
                leftReadings[2]  = leftReadings[3] ;
//                      leftReadings[3]  = leftReadings[4] ;
                       
           }
       
           public void beepLine()  {
              if ( isline ) {  
                  //Sound.beep() ;
                 
              }
             
              }
       
            public boolean line() { return isline ;}
       
           }