import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;


public class Simon {
	public static void main(String[] args) {
		int buttonChoice;
		
		//Initialize EVERYTHING HERE DAMMIT
		
		//Motor/Driver initialization
		NXTRegulatedMotor leftMotor = Motor.A;
		NXTRegulatedMotor rightMotor = Motor.B;
		NXTRegulatedMotor clawMotor = Motor.C;
		WheelDriver wheels = new WheelDriver(leftMotor,rightMotor);
		ClawDriver claw = new ClawDriver(clawMotor);
		
		//LightSensor Initialization
		ColorSensor rightCS = new ColorSensor(SensorPort.S2);
		ColorSensor leftCS = new ColorSensor(SensorPort.S3);
		ColorSensor clawCS = new ColorSensor(SensorPort.S4);
		LightSensorPoller rightPoll = new LightSensorPoller(rightCS);
		LightSensorController rightCSControl = new LightSensorController(rightPoll);
		LightSensorPoller leftPoll = new LightSensorPoller(leftCS);
		LightSensorController leftCSControl = new LightSensorController(leftPoll);
		
		//Odometer Initialization
		Odometer odometer = new Odometer();
		OdometryCorrection correction = new OdometryCorrection(odometer, null, null);
		LCDDisplay display = new LCDDisplay(odometer);
		
		//Ultrasonic Initialization
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
		UltrasonicPoller usPoller = new UltrasonicPoller(us);
		UltrasonicController usController = new UltrasonicController(usPoller);
		
		//Initialize map, localization, and navigation.
		Navigation nav = new Navigation(0,0,odometer, null, wheels);
		
		

		do {
			// clear the display
			LCD.clear();

			// ask the user whether the motors should drive in a square or float
			LCD.drawString("< Left | Right >", 0, 0);
			LCD.drawString("       |        ", 0, 1);
			LCD.drawString("  Claw | Drive  ", 0, 2);
			LCD.drawString("       |        ", 0, 3);
			LCD.drawString("       |        ", 0, 4);

			buttonChoice = Button.waitForAnyPress();
		} while (buttonChoice != Button.ID_LEFT
				&& buttonChoice != Button.ID_RIGHT);

		if (buttonChoice == Button.ID_LEFT) {
			NXTRegulatedMotor ClawMotor = Motor.C;
			ClawMotor.flt();
			LCD.clear();
			while(true){
				LCD.drawString("Claw Tachometer", 0, 0);
				LCD.drawInt(ClawMotor.getTachoCount(), 0, 1);
				if(Button.waitForAnyPress() == Button.ID_ESCAPE){
					break;
				}
			}


		} else {

		}
		
		while (Button.waitForAnyPress() != Button.ID_ESCAPE);
		System.exit(0);
	}
}