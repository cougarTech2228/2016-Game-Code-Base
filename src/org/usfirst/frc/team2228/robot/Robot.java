
package org.usfirst.frc.team2228.robot;

import java.util.Date;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	private Joystick joy;

    
	private Shooter shooter;
	private IMU imu;
	DriveBase drive;
	CANTalon leftMotor;
	CANTalon rightMotor;
	CANTalon left2;
    CANTalon right2;

    AnalogInput sonarDistance;
    
    double rSpeed = 0;
    double lSpeed = 0;
    double mode = 2;
    
    Aquirer aquire;
	
    public void robotInit() {
    	
    	joy = new Joystick(0);
    	SmartDashboard.putString("Key123", "TestValue");
    
    	
    	rightMotor = new CANTalon(1);
    	right2 = new CANTalon(2);
    	leftMotor = new CANTalon(3);
    	left2= new CANTalon(4);
    	
    	sonarDistance = new AnalogInput(0);
    	
    	//1: port for angle motor
    	//2: port for left shooter wheel
    	//3: port for right motor wheel
    	//4: port for sonar sensor digital input
    	//5: port for servo motor pwm
    	shooter = new Shooter(7,5,6,0,0);
    	aquire = new Aquirer(8,1,2,3);
    	
    	imu = new IMU();

    }
    
  
    /**
     * 
     */
    public void autonomousInit() {


    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }


    
    /**
     * This function is called periodically during operator control
     */
    @SuppressWarnings("deprecation")
	public void teleopPeriodic() {

    	
    	rSpeed = -joy.getMagnitude();
    	lSpeed = -joy.getMagnitude();
    	
		
    	if(joy.getRawButton(11)){
//    		System.out.println(SmartDashboard.getDouble("boneX"));
//    		System.out.println(SmartDashboard.getDouble("boneY"));
//    		System.out.println(SmartDashboard.getInt("boneFound"));
    		System.out.println(SmartDashboard.getNumber("boneFound"));

    	}

//    	if(joy.getRawButton(10)){
//    		SmartDashboard.putString("Key123", "work");
//    		System.out.println( (sonarDistance.getVoltage()*102.4));
//    		
//    	}
//    	
    	
    	if(joy.getRawButton(1)){
			shooter.setMode("shootOptimal");
		}
    	
    	if(joy.getRawButton(2)){
			shooter.setMode("gather");
		}
    	
        if(joy.getRawButton(3)){
        	shooter.setMode("aimhigh");
        }
        
		if(joy.getRawButton(4)){
			shooter.setMode("reset");
		}
		
		if(joy.getRawButton(5)){
			shooter.setMode("calibrate");
		}
		
		if(joy.getRawButton(6)){
			shooter.setMode("fight");
		}
		
		
		if(shooter.getMode().equals("startup")){
			shooter.calibrate();
			aquire.reset();
		}else if(shooter.getMode().equals("gather")){
			shooter.gather();
			aquire.gather();
		}else if(shooter.getMode().equals("shootOptimal")){
			
			shooter.startShoot();
			aquire.reset();
		}else if(shooter.getMode().equals("aimhigh")){
			shooter.aimUp();
			aquire.reset();
		}else if(shooter.getMode().equals("calibrate")){
			shooter.calibrate();
			aquire.calibrate();
		}else if(shooter.getMode().equals("fight")){
			shooter.fightdaPOWA();
			aquire.fightdaPOWA();
		}else{
			shooter.reset();
			if(shooter.getMode().equals("port")){
				System.out.println("here2");
				aquire.raise();
			}else{
				System.out.println("here1");
				aquire.reset();
			}
		}
		
		
		
		if(joy.getAxis(Joystick.AxisType.kY) > 0.15){
    		
    		lSpeed *= 1;
    		rSpeed *= -1;
    		
    	}else if(joy.getAxis(Joystick.AxisType.kY) < -0.15){
    		
    		lSpeed *= -1;
    		rSpeed *= 1;
    		
    	}
    	
    	if(joy.getTwist()>0.15){
    		lSpeed+= joy.getTwist()/1.5;
    		rSpeed+= joy.getTwist()/2;

    	}else if(joy.getTwist() < -0.15){
    		lSpeed+= joy.getTwist()/2;
    		rSpeed+= joy.getTwist()/2;

    	}
    		
    	
    	leftMotor.set(lSpeed/mode);
    	//WIRED BACKWARDS!!!!!!!!
    	left2.set(lSpeed/mode);
    	rightMotor.set(rSpeed/mode);
    	right2.set(rSpeed/mode);
    	
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    LiveWindow.run();
    }
    
}
