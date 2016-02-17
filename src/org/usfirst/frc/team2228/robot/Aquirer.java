package org.usfirst.frc.team2228.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;

public class Aquirer
{

	private final int ZERO_SPD = 0;

	private double gatherSpeed = 0.2;
	
	
	private CANTalon elevatorMotor;
	private double gatherAngleStpt = 0;

	
	private Talon spinspinwinwin;
	private int rampRateStpt = 24;
	
	private int ifReverse = 1;


	private int verticalPosStpt = 512 * ifReverse;

	private int transportPosStpt = 200;
	private DigitalInput limitFwd;
	private DigitalInput limitBwd;



	
	/**
	 * Constructor giving ports to all motor controllers and sensors of robot
	 * Updates CANTalons to be compatible with encoders
	 * @param portAngle
	 * @param portLeft
	 * @param portRight
	 * @param sonarPort
	 * @param servoPort
	 */
	public Aquirer(int portAngle, int portSpin, int limitFwdP, int limitBwdP){
		
		
		//Create all motors
		limitFwd = new DigitalInput(limitFwdP);
		limitBwd = new DigitalInput(limitBwdP);

		spinspinwinwin = new Talon(portSpin);
		
		elevatorMotor = new CANTalon(portAngle);
		
        elevatorMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        elevatorMotor.reverseSensor(true);
//        _elevatorMotor.configEncoderCodesPerRev(497); // if using FeedbackDevice.QuadEncoder
        //_elevatorMotor.configPotentiometerTurns(XXX), // if using FeedbackDevice.AnalogEncoder or AnalogPot

        /* set the peak and nominal outputs, 12V means full */
        elevatorMotor.configNominalOutputVoltage(+0.0f, -0.0f);
        elevatorMotor.configPeakOutputVoltage(+12.0f, -12.0f);
        /* set closed loop gains in slot0 */
        elevatorMotor.setProfile(0);
        elevatorMotor.setF(0);
        elevatorMotor.setP(0.4);
        elevatorMotor.setI(0.0001); 
        elevatorMotor.setD(0);
        elevatorMotor.setCloseLoopRampRate(24);
        elevatorMotor.setPosition(0);
        SmartDashboard.putNumber("angle", 0);
        SmartDashboard.putNumber("negate", 1);
        

	}
	
	public void gather(){
//		spinspinwinwin.set(-1);
		elevatorMotor.changeControlMode(TalonControlMode.Position);
		System.out.println("HERE!!!333");

		
    	if(limitBwd.get()){
    		elevatorMotor.set(5800);
			System.out.println("HERE!!!");

    	}else{
    		elevatorMotor.changeControlMode(TalonControlMode.PercentVbus);
			System.out.println("HERE!!!777");

    		elevatorMotor.set(0);
    	}
    	
    	if(elevatorMotor.getError()<100){
    		spinspinwinwin.set(-1);
    	}
    
    }
	
	public void reset(){
		elevatorMotor.changeControlMode(TalonControlMode.PercentVbus);
		spinspinwinwin.set(0);
		elevatorMotor.set(0);
	}
	
	
	public void calibrate(){
		elevatorMotor.changeControlMode(TalonControlMode.PercentVbus);
		if(limitFwd.get()){
			elevatorMotor.set(-0.5);
//    		System.out.println("RUN ELSE222");
//    		System.out.println(angleMotor.getPosition());
		}else{
    		elevatorMotor.setPosition(0);
//    		System.out.println("RUN ELSE");
			elevatorMotor.changeControlMode(TalonControlMode.PercentVbus);
    		elevatorMotor.set(0);
    	}
	}
	
	public void raise(){
		if(limitFwd.get()){
			elevatorMotor.changeControlMode(TalonControlMode.Position);
			System.out.println("here3");
    		elevatorMotor.set(0);
    		spinspinwinwin.set(0);
    	}else{
    		System.out.println("here4");
    		elevatorMotor.changeControlMode(TalonControlMode.PercentVbus);
    		elevatorMotor.set(0);
    		spinspinwinwin.set(0);

    	}
	}
	
	public void fightdaPOWA(){
		elevatorMotor.changeControlMode(TalonControlMode.Position);
		System.out.println("HERE!!!333");
		spinspinwinwin.set(0);
		
    	if(limitBwd.get()){
    		elevatorMotor.set(6300);
			System.out.println("HERE!!!");

    	}else{
    		elevatorMotor.changeControlMode(TalonControlMode.PercentVbus);
			System.out.println("HERE!!!777");

    		elevatorMotor.set(0);
    	}
    	
    }
}
