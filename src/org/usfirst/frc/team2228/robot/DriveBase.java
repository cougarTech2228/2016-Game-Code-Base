package org.usfirst.frc.team2228.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class DriveBase
{

	private CANTalon leftMotor0;
	private CANTalon rightMotor1;
	private CANTalon leftMotor2;
    private CANTalon rightMotor3;
    
    /**
     * DriveBase constructor assigning ports to all 
     * @param portLeftMotor0
     * @param portRightMotor1
     * @param portLeftMotor2
     * @param portRightMotor3
     */
    public DriveBase(int portLeftMotor0, int portRightMotor1, int portLeftMotor2, int portRightMotor3){
    	leftMotor0 = new CANTalon(portLeftMotor0);
    	rightMotor1 = new CANTalon(portRightMotor1);
    	leftMotor2= new CANTalon(portLeftMotor2);
    	rightMotor3 = new CANTalon(portRightMotor3);
    	
    	 leftMotor0.setFeedbackDevice(FeedbackDevice.QuadEncoder);
         leftMotor0.reverseSensor(false);
         leftMotor0.configEncoderCodesPerRev(250); 
         leftMotor0.configNominalOutputVoltage(+0.0f, -0.0f);
         leftMotor0.configPeakOutputVoltage(+12.0f, -12.0f);
         
         leftMotor0.setProfile(0);
         leftMotor0.setF(1.5345);
         leftMotor0.setP(0.3);
         leftMotor0.setI(0); 
         leftMotor0.setD(0);
         leftMotor0.setCloseLoopRampRate(24);
         
         leftMotor0.changeControlMode(TalonControlMode.Speed);
 		
         //-----------------------------------------------------------------------------------------
         
         rightMotor1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
         rightMotor1.reverseSensor(false);
         rightMotor1.configEncoderCodesPerRev(250); 
         rightMotor1.configNominalOutputVoltage(+0.0f, -0.0f);
         rightMotor1.configPeakOutputVoltage(+12.0f, -12.0f);
         
         rightMotor1.setProfile(0);
         rightMotor1.setF(1.5345);
         rightMotor1.setP(0.3);
         rightMotor1.setI(0); 
         rightMotor1.setD(0);
         rightMotor1.setCloseLoopRampRate(24);
         
         //-----------------------------------------------------------------------------------------
         
         leftMotor2.setFeedbackDevice(FeedbackDevice.QuadEncoder);
         leftMotor2.reverseSensor(false);
         leftMotor2.configEncoderCodesPerRev(250); 
         leftMotor2.configNominalOutputVoltage(+0.0f, -0.0f);
         leftMotor2.configPeakOutputVoltage(+12.0f, -12.0f);
         
         leftMotor2.setProfile(0);
         leftMotor2.setF(1.5345);
         leftMotor2.setP(0.3);
         leftMotor2.setI(0); 
         leftMotor2.setD(0);
         leftMotor2.setCloseLoopRampRate(24);
         
         //------------------------------------------------------------------------------------------
         
         rightMotor3.setFeedbackDevice(FeedbackDevice.QuadEncoder);
         rightMotor3.reverseSensor(false);
         rightMotor3.configEncoderCodesPerRev(250); 
         rightMotor3.configNominalOutputVoltage(+0.0f, -0.0f);
         rightMotor3.configPeakOutputVoltage(+12.0f, -12.0f);
         
         rightMotor3.setProfile(0);
         rightMotor3.setF(1.5345);
         rightMotor3.setP(0.3);
         rightMotor3.setI(0); 
         rightMotor3.setD(0);
         rightMotor3.setCloseLoopRampRate(24);

    }
	
    
    public void doDrive(double leftVelocity, double rightVelocity){
    	
    	
    	
    }
    
}


