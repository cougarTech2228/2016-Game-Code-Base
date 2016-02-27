package org.usfirst.frc.team2228.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Talon;

public class Aquirer
{


	
	
	private CANTalon elevatorMotor;

	
	private VictorSP spinspinwinwin;
	




	private boolean first = false;

	private boolean here = false;

	private boolean once = false;

	private boolean here4 = false;


	
	/**
	 * Constructor giving ports to all motor controllers and sensors of robot
	 * Updates CANTalons to be compatible with encoders
	 * @param portAngle
	 * @param portLeft
	 * @param portRight
	 * @param sonarPort
	 * @param servoPort
	 */
	public Aquirer(int portAngle, int portSpin){
		
		
		//Create all motors


		spinspinwinwin = new VictorSP(portSpin);
		
		elevatorMotor = new CANTalon(portAngle);
		
        elevatorMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        elevatorMotor.reverseSensor(true);

        elevatorMotor.configNominalOutputVoltage(+0.0f, -0.0f);
        elevatorMotor.configPeakOutputVoltage(+12.0f, -12.0f);
        elevatorMotor.setProfile(0);
        elevatorMotor.setF(0);
        elevatorMotor.setP(0.6);
        elevatorMotor.setI(0.00005); 
        elevatorMotor.setD(0);
        elevatorMotor.setPosition(0);
        SmartDashboard.putNumber("angle", 0);
        SmartDashboard.putNumber("negate", 1);
        

	}
	
	public void gather(){
//		spinspinwinwin.set(-1);
		elevatorMotor.changeControlMode(TalonControlMode.Position);
//		System.out.println("HERE!!!333");

		
    	
    	elevatorMotor.set(6100);
//		System.out.println("HERE!!!");

		if(elevatorMotor.isFwdLimitSwitchClosed()){
			elevatorMotor.setPosition(6200);
		}
    	
//    	if(elevatorMotor.getError()<200){
    		spinspinwinwin.set(-1);
//    	}else{
//    		spinspinwinwin.set(0);
//    	}
//		System.out.println(elevatorMotor.getPosition());

    
    }
	
	public void reset(){
		elevatorMotor.changeControlMode(TalonControlMode.PercentVbus);
		spinspinwinwin.set(0);
		elevatorMotor.set(0);
	}
	
	
	public void calibrate(){
		elevatorMotor.changeControlMode(TalonControlMode.PercentVbus);
		if(elevatorMotor.isFwdLimitSwitchClosed()){
			elevatorMotor.setPosition(6200);
			elevatorMotor.set(-0);
			first = true;

		}else{
			elevatorMotor.set(1);

		}
	}
	
	public void raise(){
		elevatorMotor.changeControlMode(TalonControlMode.Position);
		elevatorMotor.set(3637);
		spinspinwinwin.set(0);
		if(elevatorMotor.isRevLimitSwitchClosed()){
			elevatorMotor.setPosition(0);
		}
		
	}
	
	public void fightdaPOWA(){
		elevatorMotor.changeControlMode(TalonControlMode.Position);
//		System.out.println("HERE!!!333");
		spinspinwinwin.set(0);
		
    	if(!elevatorMotor.isFwdLimitSwitchClosed()){
    		elevatorMotor.set(6000);
//			System.out.println("HERE!!!");
			first  = true;

    	}else{
    		elevatorMotor.setPosition(6200);

    	}
		if(elevatorMotor.getPosition()>5900){
			here = true;
		}


    	
    }

	public void overCheval()
	{
		elevatorMotor.changeControlMode(TalonControlMode.Position);
//		System.out.println("HERE!!!333");
		spinspinwinwin.set(0);
		
		if(!elevatorMotor.isRevLimitSwitchClosed()){
	
    		elevatorMotor.set(1700);
//			System.out.println("HERE!!!");

    	}else{
    		elevatorMotor.changeControlMode(TalonControlMode.PercentVbus);
//			System.out.println("HERE!!!777");
			elevatorMotor.setPosition(0);
    		elevatorMotor.set(0);
    	}
		if(elevatorMotor.getPosition()>1800){
			here4 = true;
		}
		
	}

	public boolean checkLimitStatusForward()
	{
		return elevatorMotor.isFwdLimitSwitchClosed();
	}
	
	public boolean checkLimitStatusReverse()
	{
		return elevatorMotor.isRevLimitSwitchClosed();
	}

	public boolean calibrated()
	{
		return first;
	}

	public void setFirst(boolean b)
	{
		first = b;
		
	}

		public void calibrateUp(){
		elevatorMotor.changeControlMode(TalonControlMode.PercentVbus);
		if(elevatorMotor.isRevLimitSwitchClosed()){
			elevatorMotor.setPosition(0);
			elevatorMotor.set(-0);
			first = true;

		}else{
			elevatorMotor.set(-0.7);

		}
	}

		public boolean  getHere()
		{
			return here ;
		}
		public void setHere(boolean b)
		{
			here = b;
		}

		public void downFast()
		{
			elevatorMotor.changeControlMode(TalonControlMode.PercentVbus);
			if(elevatorMotor.getPosition()<5500 && !once){
				elevatorMotor.set(1);
				once = true;
			}
		
		}
		public boolean getOnce(){
			return once;
		}
		public void setOnce(boolean b){
			once = b;
		}

		public void setHere4(boolean b)
		{
			here4 = b;
			
		}
		public boolean getHere4()
		{

			return here4;
			
		}

		public double getPosition()
		{
			return elevatorMotor.getPosition();
		}

		public void overWall()
		{
			elevatorMotor.changeControlMode(TalonControlMode.Position);
//			System.out.println("HERE!!!333");
			spinspinwinwin.set(0);
			
			if(!elevatorMotor.isRevLimitSwitchClosed()){
		
	    		elevatorMotor.set(3000);
//				System.out.println("HERE!!!");

	    	}else{
	    		elevatorMotor.changeControlMode(TalonControlMode.PercentVbus);
//				System.out.println("HERE!!!777");
				elevatorMotor.setPosition(0);
	    		elevatorMotor.set(0);
	    	}
			if(elevatorMotor.getPosition()>1800){
				here4 = true;
			}
			
		}
}
