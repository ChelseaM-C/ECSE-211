import lejos.nxt.*;
 
   public class FindLine extends Thread{
 
    /**
     *  Light Sensor
     */
    protected LightSensorPoller lightSensor;
 
 
 
    /**
     * The integer time period in milliseconds of the LineFinder update cycle.
     * @value
     */
    protected static final int PERIOD = 12;
 
    /**
     * Light Sensor value threshold between regular floor and a black line.
     * @value
     */
    protected static final int THRESHOLD = -28;
                ;    //derivative threshold
       
        private int [] readings = new int[4] ;     //{0, 0.0, 0.0, 0.0, 0.0};
 
       
        private double derivative1 ;
        private double derivative2 ;
       
        private boolean isline = false ;
 
 
       
        public  FindLine( LightSensorPoller lightSensor) {
            this.lightSensor = lightSensor;
       
        }
       
        public void run()   {
       
       
       
             long updateStart, updateEnd;
             
                readings[0] = 0;
                readings[1]  =  0;
                readings[2]  =  0;
                        readings[3]  = 0;
 
                        LCD.clear();
                       
         while (true) {
                        updateStart = System.currentTimeMillis();
       
                 readings[3] = lightSensor.getLight();
 
                 this.update() ;
     //  this.isLine() ;
                 updateEnd = System.currentTimeMillis();
                 this.beepLine() ;
 
                 
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
           derivative1 =  (readings[1] - readings[0]);
           derivative2 =   (readings[2] - readings[1]) ;
 
 
         //do not detect a line at the begining when the array has been initialized to 0
             if(readings[0] ==0 || readings[1] == 0 ||  readings[2] == 0  || readings[3] == 0 ){ return false;}
           
             if ( (derivative2 - derivative1) <  THRESHOLD ) {     //if value started dropping
                 
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
               
                        readings[0]  =  readings[1] ;
                readings[1]  =  readings[2] ;
                readings[2]  = readings[3] ;  
                       
           }
       
           public void beepLine()  {
              if ( isline ) {  
                  Sound.beep() ;
                 
              }
             
              }
           public void setlightToFalse()  {
                 
                        this.isline =false ;  
                       
           }
           
       
           }