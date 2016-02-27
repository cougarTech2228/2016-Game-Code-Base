package org.usfirst.frc.team2228.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;

public class Shooter
{

	private final int ZERO_SPD = 0;
	private DigitalInput limitFwd;

	
	private String modeStatus = "startup";
	private enum mode {GATHER, TRANSPORT, AIMHIGH, AIMLOW};
	private boolean optConditions = false;
	private double highGoalSpeed = -1;
	private double lowGoalSpeed = -1;
	private double gatherSpeed = 0.4;
	
	private DigitalInput sonarSensor;
	private boolean collectedStatus = false;
	
	private CANTalon angleMotor;
	private double gatherAngleStpt = 0;

	
	private CANTalon leftWheel;
	private CANTalon rightWheel;
	private int rampRateStpt = 24;
	
	private Servo kickServo;
	private double servoPosShootStpt = 0.7;
	private double servoPosBackStpt = 1;
	
	private int ifReverse = 1;


	private int verticalPosStpt = 512 * ifReverse;
	private int highGoalPosStpt = 256 * ifReverse;
	private int lowGoalPosStpt = 128 * ifReverse;
	private int transportPosStpt = 200;
	
	private int time;
	private boolean first = false;





	
	/**
	 * Constructor giving ports to all motor controllers and sensors of robot
	 * Updates CANTalons to be compatible with encoders
	 * @param portAngle
	 * @param portLeft
	 * @param portRight
	 * @param sonarPort
	 * @param servoPort
	 */
	public Shooter(int portAngle, int portLeft, int portRight, int sonarPort, int servoPort){
		
		
		limitFwd = new DigitalInput(1);

		//Create all motors
		angleMotor = new CANTalon(portAngle);
		leftWheel = new CANTalon(portLeft);
		rightWheel = new CANTalon(portRight);
		kickServo = new Servo(servoPort);
		
//		kickServo.

		sonarSensor = new DigitalInput(sonarPort);

		//set servo position initially
    	kickServo.setPosition(1.0);

		
        angleMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        angleMotor.reverseSensor(true);
//        angleMotor.configEncoderCodesPerRev(497); // if using FeedbackDevice.QuadEncoder
        //angleMotor.configPotentiometerTurns(XXX), // if using FeedbackDevice.AnalogEncoder or AnalogPot

        /* set the peak and nominal outputs, 12V means full */
        angleMotor.configNominalOutputVoltage(+0.0f, -0.0f);
        angleMotor.configPeakOutputVoltage(+12.0f, -12.0f);
        /* set closed loop gains in slot0 */
        angleMotor.setProfile(0);
        angleMotor.setF(0);
        angleMotor.setP(0.7);
        angleMotor.setI(0.001); 
        angleMotor.setD(5);
//        angleMotor.setCloseLoopRampRate(24);
        angleMotor.setPosition(0);
        SmartDashboard.putNumber("angle", 0);
        SmartDashboard.putNumber("negate", 1);

   	 angleMotor.enableLimitSwitch(false, true);
       
        
        leftWheel.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        leftWheel.reverseSensor(false);
        leftWheel.configEncoderCodesPerRev(250);
        leftWheel.configNominalOutputVoltage(+0.0f, -0.0f);
        leftWheel.configPeakOutputVoltage(+12.0f, -12.0f);
        
        leftWheel.setProfile(0);
        leftWheel.setF(1.5345);
        leftWheel.setP(0.3);
        leftWheel.setI(0); 
        leftWheel.setD(0);
        leftWheel.setCloseLoopRampRate(rampRateStpt);
        
        
        rightWheel.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        rightWheel.reverseSensor(false);
        rightWheel.configEncoderCodesPerRev(250); 
        rightWheel.configNominalOutputVoltage(+0.0f, -0.0f);
        rightWheel.configPeakOutputVoltage(+12.0f, -12.0f);

        rightWheel.setProfile(0);
        rightWheel.setF(1.5345);
        rightWheel.setP(0.3);
        rightWheel.setI(0); 
        rightWheel.setD(0);
        rightWheel.setCloseLoopRampRate(rampRateStpt);

	}

	public void manualAim(double d){
		
	

	}
	
	public boolean checkLimitStatusForward(){
		
		return angleMotor.isFwdLimitSwitchClosed();
		
	}
	
	public boolean checkLimitStatusReverse(){
		
		if(!angleMotor.isFwdLimitSwitchClosed()){
			
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * Shooter modeStatus which gathers the ball using the shooter motors in reverse direction
	 */
	public void gather(){
		
		kickServo.set(servoPosBackStpt);
		angleMotor.changeControlMode(TalonControlMode.Position);
		if(angleMotor.getPosition() > 1500){
			leftWheel.set(gatherSpeed);
			rightWheel.set(gatherSpeed);
//			System.out.println("RAN!!!!");
		}else{
			leftWheel.set(ZERO_SPD);
			rightWheel.set(ZERO_SPD);
		}
		
		if(!angleMotor.isFwdLimitSwitchClosed()){
			
			angleMotor.set(1750);
//    		System.out.println("RUN ELSE222");
//    		System.out.println(angleMotor.getPosition());
		}else{
    		angleMotor.setPosition(1750);
//    		System.out.println("RUN ELSE");
    		setMode("not");
			angleMotor.changeControlMode(TalonControlMode.PercentVbus);
    		angleMotor.set(0);
    		first = true;
    	}
	}
	
	
	/**
	 * Checks to see if the boulder is gathered then once gathered continues running
	 * for a second to guarantee shooting
	 * @return
	 */
	public boolean isCollected()
	{
		
			if(sonarSensor.get()){
				collectedStatus = true;
			}else{
				collectedStatus = false;
			}
			
		return collectedStatus;
	}

	/**
	 * Sets the shooters angle to transportation position and stops the motors
	 */
	public void goToTransport()
	{

		if(!(angleMotor.getPosition()>transportPosStpt+100 && angleMotor.getPosition() <transportPosStpt-100)){
			angleMotor.set(transportPosStpt);
			leftWheel.set(ZERO_SPD);
			rightWheel.set(ZERO_SPD);
		}else{
			modeStatus = "transport";
		}
		kickServo.set(servoPosBackStpt);
		
	}
	
	
	/**
	 * Aims the shooter for the high goal and speeds up shooter wheels to optimal high goal velocity
	 */
	

	public void spinMotors(){
		leftWheel.changeControlMode(TalonControlMode.PercentVbus);
		rightWheel.changeControlMode(TalonControlMode.PercentVbus);
		
		leftWheel.set(-1);
		rightWheel.set(-1);
//		System.out.println(leftWheel.getSpeed());
	}
	
	public void aimUp(){
		angleMotor.changeControlMode(TalonControlMode.Position);

	
		
		angleMotor.set(725);
	
		
		
		kickServo.set(1);

		
		if(angleMotor.getPosition() >450 && angleMotor.getPosition() < 950){
			leftWheel.set(-1);
			rightWheel.set(-1);
		}else{
			leftWheel.set(0);
			rightWheel.set(0);
		}
		
//		if(angleMotor.getError()<25 && angleMotor.getSpeed() < 50){
//			
//			setMode("shootOptimal");
//		}
		
	}
	
	public void fightdaPOWA(){
		angleMotor.changeControlMode(TalonControlMode.Position);

	
		leftWheel.set(0);
		rightWheel.set(0);
		
		angleMotor.set(725);
	
		
		
		kickServo.set(1);

		
//		if(angleMotor.getError()<25 && angleMotor.getSpeed() < 50){
//			
//			setMode("shootOptimal");
//		}
		
	}

	
//	/**
//	 * Aims the shooter for the low goal and speeds up shooter wheels to optimal low goal velocity
//	 */
//	public void takeInAndGather(){
//		modeStatus = "aimWherever";
//		
//		
//		kickServo.set(1);
//
//		leftWheel.changeControlMode(TalonControlMode.PercentVbus);
//		rightWheel.changeControlMode(TalonControlMode.PercentVbus);
//		
//		if(angleMotor.getPosition()<300){
//			leftWheel.set(0.3);
//			rightWheel.set(0.3);
//		}
//		
//		if(limitFwd.get()){
//			angleMotor.set(0);
//		}else{
//    		angleMotor.setPosition(0);
//    		System.out.println("RUN ELSE");
//			angleMotor.changeControlMode(TalonControlMode.PercentVbus);
//    		angleMotor.set(0);
//    	}
//		
//	}
//

	/**
	 * Checks to see if the bot is in firing position and the shooter wheels are spinning 
	 * @return
	 */
	public boolean getOptConditions()
	{
		return optConditions;
	}
	
	/**
	 * Kickstarts the shot by activating the servo to push the boulder towards the wheels
	 */
	public void startShoot(){
		kickServo.set(0.7);
		
	}
	
	public String getMode()
	{
		return modeStatus;
	}


	public void setMode(String modeStatus)
	{
		this.modeStatus = modeStatus;
	}

	public void reset()
	{
		kickServo.set(1);
		
		
		leftWheel.changeControlMode(TalonControlMode.PercentVbus);
		rightWheel.changeControlMode(TalonControlMode.PercentVbus);
		angleMotor.changeControlMode(TalonControlMode.PercentVbus);

		angleMotor.set(0);
		
		leftWheel.set(0);
		rightWheel.set(0);
		
	}
	
	public void calibrate(){
		angleMotor.changeControlMode(TalonControlMode.PercentVbus);
		if(!angleMotor.isFwdLimitSwitchClosed()){
			angleMotor.set(0.3);
//    		System.out.println("RUN ELSE222");
//    		System.out.println(angleMotor.getPosition());
		}else{
    		angleMotor.setPosition(1750);
//    		System.out.println("RUN ELSE");
    		setMode("not");
			angleMotor.changeControlMode(TalonControlMode.PercentVbus);
    		angleMotor.set(0);
    		first = true;
    	}
	}

	public boolean notFirst()
	{
		// TODO Auto-generated method stub
		return first;
	}

	public void setFirst(boolean b)
	{
		first = b;
		
	}

	public boolean getFirst()
	{
		// TODO Auto-generated method stub
		return first;
	}

	public void calibrateUp(){
		angleMotor.changeControlMode(TalonControlMode.PercentVbus);
		if(!angleMotor.isRevLimitSwitchClosed()){
			angleMotor.set(-0.3);
//    		System.out.println("RUN ELSE222");
//    		System.out.println(angleMotor.getPosition());
		}else{
    		angleMotor.setPosition(0);
//    		System.out.println("RUN ELSE");
    		setMode("not");
			angleMotor.changeControlMode(TalonControlMode.PercentVbus);
    		angleMotor.set(0);
    		first = true;
    	}
	}
	
}
