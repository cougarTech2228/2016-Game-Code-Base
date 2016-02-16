package org.usfirst.frc.team2228.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Servo;

public class Aquirer
{

	private final int ZERO_SPD = 0;

	private double gatherSpeed = 0.2;
	
	
	private CANTalon angleMotor;
	private double gatherAngleStpt = 0;

	
	private PWM spinspinwinwin;
	private int rampRateStpt = 24;
	
	private int ifReverse = 1;


	private int verticalPosStpt = 512 * ifReverse;

	private int transportPosStpt = 200;




	
	/**
	 * Constructor giving ports to all motor controllers and sensors of robot
	 * Updates CANTalons to be compatible with encoders
	 * @param portAngle
	 * @param portLeft
	 * @param portRight
	 * @param sonarPort
	 * @param servoPort
	 */
	public Aquirer(int portAngle, int portLeft){
		
		
		//Create all motors
		angleMotor = new CANTalon(portAngle);
		spinspinwinwin = new PWM(1);
		


		//set servo position initially

		
        angleMotor.changeControlMode(TalonControlMode.Position);
        angleMotor.reverseSensor(true);
        angleMotor.configNominalOutputVoltage(+0.0f, -0.0f);
        
        //set max output voltage both positive and negative at 12volts to motor controllers
        angleMotor.configPeakOutputVoltage(+3f, -3f);

        angleMotor.setProfile(0);
        angleMotor.setF(0);
        angleMotor.setP(0.3);
        angleMotor.setI(0); 
        angleMotor.setD(0);
        angleMotor.setCloseLoopRampRate(rampRateStpt);
       
       
        

	}


	
}
