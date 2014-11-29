/**The controller class that takes data from the poller, and filters the data.
 *
 * @author Daniel
 *
 */
public class UltrasonicController {
        private UltrasonicPoller poller;
        private int distance;
       
        public UltrasonicController(UltrasonicPoller p){
                this.poller = p;
        }
       
        /**
         *
         * @return the filtered distance from the ultrasonic sensor
         */
        public int getFilteredDist(){
                int distance = poller.getDist();
               
                //Filter Code Here
               
                return distance;
        }
}