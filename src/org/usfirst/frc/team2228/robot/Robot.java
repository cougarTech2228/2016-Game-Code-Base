/**
 * Example demonstrating the velocity closed-loop servo.
 * Tested with Logitech F350 USB Gamepad inserted into Driver Station]
 * 
 * Be sure to select the correct feedback sensor using SetFeedbackDevice() below.
 *
 * After deploying/debugging this to your RIO, first use the left Y-stick 
 * to throttle the Talon manually.  This will confirm your hardware setup.
 * Be sure to confirm that when the Talon is driving forward (green) the 
 * position sensor is moving in a positive direction.  If this is not the cause
 * flip the boolena input to the SetSensorDirection() call below.
 *
 * Once you've ensured your feedback device is in-phase with the motor,
 * use the button shortcuts to servo to target velocity.  
 *
 * Tweak the PID gains accordingly.
 */
package org.usfirst.frc.team2228.robot;
import java.util.Date;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class Robot extends IterativeRobot {
  
	CANTalon _talon = new CANTalon(14);	
	Joystick _joy = new Joystick(0);	
	StringBuilder _sb = new StringBuilder();
	int _loops = 0;
	Date nowTime = new Date();
	Date date = new Date();
	int tIme = (int) (System.currentTimeMillis()/1000);

	long autoSTime = nowTime.getTime();
	private double targetSpeed;
	Writer writer;

	
	public boolean autoTest(){
		
		_sb.append("\tout:");
		_sb.append(_talon.getOutputVoltage());
		
        _sb.append("\tspd:");
        _sb.append(_talon.getSpeed() );
    	
        _sb.append("\terr:");
        _sb.append((_talon.getClosedLoopError()*(600.0/1024.0)));
        _sb.append("\ttrg:");
        _sb.append(targetSpeed);
        _sb.append("\tvolt:");
        _sb.append(_talon.getOutputVoltage());
		
        if(++_loops >= 10) {
        	_loops = 0;
        	System.out.println(_sb.toString());
        }
        _sb.setLength(0);		
        
        
        _talon.changeControlMode(TalonControlMode.Speed);
		nowTime = new Date();

		
		if(nowTime.getTime() < autoSTime+2500){
			
			targetSpeed = .75*700;
			_talon.set(.75*700);
			
		}else if(nowTime.getTime() < autoSTime+5000){
			
			targetSpeed = .25*700;
			_talon.set(.25*700);
			
		}else{
			autoSTime = nowTime.getTime();
		}
		
		writer.log(tIme, _talon.getOutputVoltage() + "," + 
		_talon.getSpeed() + "," + _talon.getClosedLoopError() + "," + targetSpeed +"\n", 1);
		
		return true;
	}
	
	
	
	public void robotInit() {
        /* first choose the sensor */
		writer = new Writer();

        _talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        _talon.reverseSensor(true);
        _talon.configEncoderCodesPerRev(256); // if using FeedbackDevice.QuadEncoder
        //_talon.configPotentiometerTurns(XXX), // if using FeedbackDevice.AnalogEncoder or AnalogPot

        /* set the peak and nominal outputs, 12V means full */
        _talon.configNominalOutputVoltage(+0.0f, -0.0f);
        _talon.configPeakOutputVoltage(+12.0f, -12.0f);
        /* set closed loop gains in slot0 */
        _talon.setProfile(0);
        _talon.setF(0.856);
        _talon.setP(0.5);
        _talon.setI(0); 
        _talon.setD(5);
        _talon.setCloseLoopRampRate(24);
	}
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	/* get gamepad axis */
    	double leftYstick = _joy.getAxis(AxisType.kY);
    	double motorOutput = _talon.getOutputVoltage() / _talon.getBusVoltage();
    	/* prepare line to print */
		_sb.append("\tout:");
		_sb.append(motorOutput);
        _sb.append("\tspd:");
        _sb.append(_talon.getSpeed() );
/*
 * 	out:1.0	spd:716.6015625	err:36	trg:738.0
	out:1.0	spd:716.6015625	err:36	trg:738.0
	out:1.0	spd:715.4296875	err:38	trg:738.0
	out:1.0	spd:714.84375	err:38	trg:738.0
	out:1.0	spd:713.0859375	err:42	trg:738.0
	out:1.0	spd:714.84375	err:39	trg:738.0
	out:1.0	spd:713.671875	err:41	trg:738.0
	out:1.0	spd:716.015625	err:37	trg:738.0
	out:1.0	spd:714.2578125	err:40	trg:738.0
	out:1.0	spd:716.6015625	err:36	trg:738.0
	out:1.0	spd:715.4296875	err:38	trg:738.0
	out:1.0	spd:717.1875	err:35	trg:738.0
	out:1.0	spd:716.015625	err:37	trg:738.0
	out:1.0	spd:716.6015625	err:36	trg:738.0
	out:1.0	spd:716.6015625	err:36	trg:738.0
	out:1.0	spd:717.7734375	err:34	trg:738.0
	out:1.0	spd:717.7734375	err:34	trg:738.0
	out:1.0	spd:716.015625	err:37	trg:738.0
 *         
 *         
 */         
        if(_joy.getRawButton(11)){
        	
        	while(autoTest()){
        		
        	}
        	
        }
        
        
         if(_joy.getRawButton(1)){
        	/* Speed mode */
        	double targetSpeed = 700; /* 1500 RPM in either direction */
        	_talon.changeControlMode(TalonControlMode.Speed);
        	_talon.set(targetSpeed); /* 1500 RPM in either direction */
        	/* append more signals to print when in speed mode. */
        	//1024
        	
        	
            _sb.append("\terr:");
            _sb.append((_talon.getClosedLoopError()*(600.0/1440.0)));
            _sb.append("\ttrg:");
            _sb.append(targetSpeed);
            _sb.append("\tvolt:");
            _sb.append(_talon.getOutputVoltage());
        }else if(_joy.getRawButton(2)){
        	
        	_talon.changeControlMode(TalonControlMode.Position);
        	_sb.append("\tpos:");
        	_sb.append(_talon.getPosition());
        	_talon.set(0);
        	
        }else {

        	/* Percent voltage mode */
        	_talon.changeControlMode(TalonControlMode.PercentVbus);
        	_talon.set(leftYstick);
        }

        if(++_loops >= 10) {
        	_loops = 0;
        	System.out.println(_sb.toString());
        }
        _sb.setLength(0);
    }
}