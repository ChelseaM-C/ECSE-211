	//Waypoint class
/**
 * Waypoint, used by navigator to travel
 * @author Daniel
 *
 */
	public class Waypoint{
		private double wx;
		private double wy;
		/**Create new waypoint with given coordinates (cm)
		 * 
		 * @param x1 x coord
		 * @param y1 y coord
		 */
		public Waypoint(double x1, double y1){
			wx = x1;
			wy = y1;
		}
		/**
		 * 
		 * @return x coord
		 */
		public double getX(){
			return wx;
		}
		 /**
		  * 
		  * @return y coord
		  */
		public double getY(){
			return wy;
		}
	}