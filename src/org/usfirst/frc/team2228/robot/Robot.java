
package org.usfirst.frc.team2228.robot;

import java.util.Date;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
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
	CANTalon leftMotor;
	CANTalon rightMotor;
	CANTalon left2;
    CANTalon right2;

    double rSpeed = 0;
    double lSpeed = 0;
    double mode = 2;

//	private Joystick joy2;
	
	
    public void robotInit() {
    	
    	joy = new Joystick(0);
//    	joy = new Joystick(1);
    	
    	//1: port for angle motor
    	//2: port for left shooter wheel
    	//3: port for right motor wheel
    	//4: port for sonar sensor digital input
    	//5: port for servo motor pwm
    	rightMotor = new CANTalon(1);
    	right2 = new CANTalon(2);
    	leftMotor = new CANTalon(3);
    	left2= new CANTalon(4);
    	
    	
    	shooter = new Shooter(8,5,6,0,0);
   
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
    public void teleopPeriodic() {

    	rSpeed = -joy.getMagnitude();
    	lSpeed = -joy.getMagnitude();
    	
//    	SmartDashboard.putData("IMU", imu);
//        Timer.delay(0.005);		// wait for a motor update time

    	
//    	//If boulder is not contained in the bot
//		if(!shooter.isCollected()){
//
//			//and button 1 is pressed run the gatherer
//			if(joy.getRawButton(1)){
//				shooter.gather();
//			}
//			
//		//If user is not aiming it will go to transportation mode
//		}else 
//		if(!shooter.getMode().equals("aimhigh") || !shooter.getMode().equals("aimlow")){
//
//			shooter.goToTransport();
//			
//		}else if(joy.getRawButton(2)){
//
//			shooter.aimHigh();
//			
//		}else if(joy.getRawButton(3)){
//			
//			shooter.aimLow();
//			
//		}else if(joy.getRawButton(4)){
//			shooter.goToVertical();
//		}
//		
		
//if(joy.getRawButton(3)){
//			
//			shooter.aimLow();
//}else{
//	shooter.aimHigh();
//	
//}
//
//if(joy.getRawButton(5)){
//	shooter.startShoot();
//}
		
    	 if(joy.getRawButton(2)){
         	shooter.aimLow();
         }
        if(joy.getRawButton(3)){
        	shooter.aimHigh();
        }
        
		//if the shooter is in optimal shooting conditions where both motors are up to speed and 
		//it is angled at the correct height the bot will run the shoot function
		if(joy.getRawButton(4)){
			shooter.startShoot();
		}
		if(joy.getRawButton(5)){
			shooter.reset();
		}
		
		  if(joy.getRawButton(1)){
	        	shooter.spinMotors();
	        }
	        
		
		
		if(joy.getAxis(Joystick.AxisType.kY) > 0.15){
    		
    		lSpeed *= -1;
    		rSpeed *= -1;
    		
    	}else if(joy.getAxis(Joystick.AxisType.kY) < -0.15){
    		
    		lSpeed *= 1;
    		rSpeed *= 1;
    		
    	}
    	
    	if(joy.getTwist()>0.15){
    		lSpeed-= joy.getTwist()/2;
    		rSpeed+= joy.getTwist()/2;

    	}else if(joy.getTwist() < -0.15){
    		lSpeed-= joy.getTwist()/2;
    		rSpeed+= joy.getTwist()/2;

    	}
    		
    	
    	leftMotor.set(lSpeed/mode);
    	//WIRED BACKWARDS!!!!!!!!
    	left2.set(-lSpeed/mode);
    	rightMotor.set(rSpeed/mode);
    	right2.set(rSpeed/mode);
		
		
//		if(joy2.getRawButton(1)){
//			shooter.manualAim(joy2.getMagnitude());
//		}
    	
    	
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
