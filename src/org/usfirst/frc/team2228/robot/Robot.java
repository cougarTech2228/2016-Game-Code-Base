
package org.usfirst.frc.team2228.robot;


import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
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
    double mode = 1;
    
    Aquirer aquire;
    private BuiltInAccelerometer accel;
    String autoMode = "lowbar";
    enum State {APPROACH, CROSSING,APPROACHCHEVAL,ATCHEVAL,APPROACHWALL,CROSSWALL}
    State state = State.APPROACHCHEVAL; 

	private boolean done = false;
	private boolean done2 = false;


	private boolean done3 = false;

    
    public void robotInit() {
    	
    	accel = new BuiltInAccelerometer();
    	joy = new Joystick(0);
    	SmartDashboard.putString("Key123", "TestValue");
    
    	
    	rightMotor = new CANTalon(2);
    	right2 = new CANTalon(1);
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
    	
    	
	  	  rightMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
	      rightMotor.reverseSensor(false);
	//      rightMotor.configEncoderCodesPerRev(225); // if using FeedbackDevice.QuadEncoder
	      //rightMotor.configPotentiometerTurns(XXX), // if using FeedbackDevice.AnalogEncoder or AnalogPot
	
	      /* set the peak and nominal outputs, 12V means full */
	      rightMotor.configNominalOutputVoltage(+0.0f, -0.0f);
	      rightMotor.configPeakOutputVoltage(+12.0f, -12.0f);
	      /* set closed loop gains in slot0 */
	      rightMotor.setProfile(0);
	      rightMotor.setF(0);
	      rightMotor.setP(0.21);
	      rightMotor.setI(0.0001); 
	      rightMotor.setD(0);
//	      rightMotor.setVoltageRampRate(5);
	      
	      

      
      
    	  leftMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
          leftMotor.reverseSensor(false);
//          leftMotor.configEncoderCodesPerRev(225); // if using FeedbackDevice.QuadEncoder
          //leftMotor.configPotentiometerTurns(XXX), // if using FeedbackDevice.AnalogEncoder or AnalogPot

          /* set the peak and nominal outputs, 12V means full */
          leftMotor.configNominalOutputVoltage(+0.0f, -0.0f);
          leftMotor.configPeakOutputVoltage(+12.0f, -12.0f);
          /* set closed loop gains in slot0 */
          leftMotor.setProfile(0);
          leftMotor.setF(0);
          leftMotor.setP(0.41);
          leftMotor.setI(0.0001); 
          leftMotor.setD(0);
         
//          leftMotor.setVoltageRampRate(5);
          
          
        

//          right2.setVoltageRampRate(5);
//          left2.setVoltageRampRate(5);
          
    }
    
  
    /**
     * 
     */
    public void autonomousInit() {

//	      rightMotor.setCloseLoopRampRate(5);
//	      right2.setCloseLoopRampRate(5);
//	      leftMotor.setCloseLoopRampRate(5);
//	      left2.setCloseLoopRampRate(5);
	      rightMotor.setPosition(0);
	    	leftMotor.setPosition(0);
	    	shooter.setFirst(false);
	    	aquire.setFirst(false);

    }
    
    public void autonomousInit2() {

//	      rightMotor.setCloseLoopRampRate(5);
//	      right2.setCloseLoopRampRate(5);
//	      leftMotor.setCloseLoopRampRate(5);
//	      left2.setCloseLoopRampRate(5);
	      rightMotor.setPosition(0);
	    	leftMotor.setPosition(0);
	    	shooter.setFirst(false);
	    	aquire.setFirst(false);
	    	done = false;
	    	done2 = false;
	    	done3 = false;
	    	aquire.setHere(false);
	    	aquire.setHere4(false);

	    	state = State.APPROACH;
	    	System.out.println("AutoInitRun");
	    	aquire.setOnce(false);

  }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    	leftMotor.changeControlMode(TalonControlMode.Position);
		rightMotor.changeControlMode(TalonControlMode.Position);
	      right2.changeControlMode(TalonControlMode.Follower);
	      right2.set(rightMotor.getDeviceID());
	      left2.changeControlMode(TalonControlMode.Follower);
          left2.set(leftMotor.getDeviceID());
//		right2.setVoltageRampRate(5);
//        left2.setVoltageRampRate(5);
		
		System.out.println(shooter.getFirst());
		System.out.println(shooter.checkLimitStatusForward());
		
		System.out.println();

//    	System.out.println("right pos: " + rightMotor.get());
//    	System.out.println("left pos: " + leftMotor.get());
    	
    	if(state == State.APPROACH){
	    	if(!shooter.notFirst()){
	    		shooter.calibrate();
	    		System.out.println("RUNNING THIS");
	    	}else{
	    		if(!autoMode.equals("lowbar")){
	    			shooter.fightdaPOWA();
	    		}
	    	}
	    	
	    	if(!aquire.notFirst()){
	    		aquire.calibrate();
	    	}else{
	    		
	    		if(!autoMode.equals("lowbar")){
	        		aquire.overCheval();
	    		}
	    	}
	    	rightMotor.set(-856.8*2.583);
    		leftMotor.set(856.8*2.583);
	    		if(rightMotor.getPosition() <-856.8*2.583+50){
		    		rightMotor.setPosition(0);
		    		leftMotor.setPosition(0);
		    		rightMotor.set(0);
		    		leftMotor.set(0);
		    		state = State.CROSSING;
		    	}
    	}else if(state == State.APPROACHCHEVAL){
	    	if(!shooter.notFirst()){
	    		shooter.calibrateUp();
	    		System.out.println("RUNNING THIS");
	    	}else{
	    			shooter.fightdaPOWA();
	    		
	    	}
	    	
	    	if(!aquire.notFirst()){
	    		aquire.calibrateUp();
	    	}else{
	        	aquire.overCheval();
	    	}
	    	rightMotor.set(-856.8*2.583+200);
    		leftMotor.set(856.8*2.583-200);
	    		if(rightMotor.getPosition() <-856.8*2.583+700){
		    		rightMotor.setPosition(0);
		    		leftMotor.setPosition(0);
		    		rightMotor.set(0);
		    		leftMotor.set(0);
		    		state = State.ATCHEVAL;
		    		
		    	}
    	}else if(state == State.ATCHEVAL){
    		
    		if(!aquire.getHere()&&!done){
    			aquire.fightdaPOWA();
    			shooter.fightdaPOWA();
    		}else{
    			done = true;
    		}
    		
    		if(!done2){
	    		if(done){
	    			rightMotor.set(-856.8*0.527-900);
	    			leftMotor.set(856.8*0.527+900);
	    		}
	    		if(rightMotor.getPosition()<-856.8*0.527-600){
	    			done2 = true;
	    			aquire.overCheval();
	    			rightMotor.setPosition(0);
		    		leftMotor.setPosition(0);
		    		rightMotor.set(0);
		    		leftMotor.set(0);
	    		}
    		}else if(!done3){
    			aquire.overCheval();

    			rightMotor.set(-856.8*0.527-1300);
    			leftMotor.set(856.8*0.527+1300);
//    			if(aquire.getHere4()){
//    				done3  = true;
//    			}
    		}
//    		else{
//    			aquire.downFast();
//    			rightMotor.set(-856.8*0.527-900);
//    			leftMotor.set(856.8*0.527+900);
//    		}
    		
    	}	
//    	else if(state == State.APPROACHPORTCULLIS){
//    		
////    		rightMotor.set((-856.8*3.6));
////    		leftMotor.set((856.8*3.6));
//    		if(!shooter.notFirst()){
//	    		shooter.calibrate();
//	    		System.out.println("RUNNING THIS");
//	    	}else{
//	    		shooter.fightdaPOWA();	
//	    	}
//	    	
//	    	if(!aquire.notFirst()){
//	    		aquire.calibrate();
//	    	}
//	    	if(aquire.getPosition()>3000){
//	    		rightMotor.set(-856.8*2.583+300);
//	    		leftMotor.set(856.8*2.583-300);
//	    	}
//	    		if(rightMotor.getPosition() <-856.8*2.583+400 && aquire.checkLimitStatusForward()){
//		    		rightMotor.setPosition(0);
//		    		leftMotor.setPosition(0);
//		    		rightMotor.set(0);
//		    		leftMotor.set(0);
//		    		state = State.CROSSINGPORTCULLIS;
//		    	}
//    		
//    	}else if(state == State.CROSSINGPORTCULLIS){
//    		shooter.fightdaPOWA();	
//    		aquire.raise();
//    		
//    		
//    	}
    	
    	else if(state == State.APPROACHWALL){
	    	if(!shooter.notFirst()){
	    		shooter.calibrateUp();
	    		System.out.println("RUNNING THIS");
	    	}else{
	    			shooter.fightdaPOWA();
	    	}
	    	
	    	if(!aquire.notFirst()){
	    		aquire.calibrateUp();
	    	}else{
	        		aquire.overWall();
	    	}
	    	rightMotor.set(-856.8*2.583-200);
    		leftMotor.set(856.8*2.583);
	    		if(rightMotor.getPosition() <(-856.8*2.583-200)+50){
		    		rightMotor.setPosition(0);
		    		leftMotor.setPosition(0);
		    		rightMotor.set(0);
		    		leftMotor.set(0);
		    		state = State.CROSSWALL;
		    	}
    	}else if(state == State.CROSSWALL){
    	
    		shooter.fightdaPOWA();
    		aquire.overWall();
    		
    		if(rightMotor.getPosition() <((-856.8*2.583/2))+50){
	    		rightMotor.setPosition(0);
	    		leftMotor.setPosition(0);
	    		rightMotor.set(0);
	    		leftMotor.set(0);
    		}else{
    			rightMotor.set((856.8*2.583)/2);
    			leftMotor.set((856.8*2.583)/2);
    		}
  
    	}
    	else if(state == State.CROSSING){
    		if(shooter.checkLimitStatusForward() && aquire.checkLimitStatusForward()){

    			rightMotor.set(-856.8*2.489);
    			leftMotor.set(856.8*2.489);
    		}else{
	    		aquire.calibrate();
	    		shooter.calibrate();
    		}
    	}
    	
    	
    	
    	
    	
    	System.out.println("Accel x: " + accel.getX());
    	System.out.println("Accel y: " + accel.getY());
    	System.out.println("Accel z: " + accel.getZ());
    	System.out.println();

    	
    }


    
    /**
     * This function is called periodically during operator control
     */
    @SuppressWarnings("deprecation")
	public void teleopPeriodic() {

	      right2.changeControlMode(TalonControlMode.Follower);
	      right2.set(rightMotor.getDeviceID());
	      left2.changeControlMode(TalonControlMode.Follower);
          left2.set(leftMotor.getDeviceID());
    	done = false;
    	rSpeed = -joy.getMagnitude();
    	lSpeed = -joy.getMagnitude();
    	
		
    	if(joy.getRawButton(12)){

    		autonomousInit2();
    		
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
		
		if(joy.getRawButton(7)){
			shooter.setMode("win");
		}
		
		if(joy.getRawButton(8)){
			shooter.setMode("port");
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
		}else if(shooter.getMode().equals("win")){
			shooter.fightdaPOWA();
			aquire.overCheval();
		}else{
			shooter.reset();
			if(shooter.getMode().equals("port")){
//				System.out.println("here2");
				aquire.raise();
			}else{
//				System.out.println("here1");
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
    		lSpeed+= joy.getTwist();
    		rSpeed+= joy.getTwist();

    	}else if(joy.getTwist() < -0.15){
    		lSpeed+= joy.getTwist();
    		rSpeed+= joy.getTwist();

    	}
    		
    	if(!joy.getRawButton(11)){
    		leftMotor.changeControlMode(TalonControlMode.PercentVbus);
    		rightMotor.changeControlMode(TalonControlMode.PercentVbus);

	    	leftMotor.set(lSpeed/mode);
	    	//WIRED BACKWARDS!!!!!!!!
//	    	left2.set(lSpeed/mode);
	    	rightMotor.set(rSpeed/mode);
//	    	right2.set(rSpeed/mode);
	    	
	    	rightMotor.setPosition(0);
	    	leftMotor.setPosition(0);
    	}else{
    		leftMotor.changeControlMode(TalonControlMode.Position);
    		rightMotor.changeControlMode(TalonControlMode.Position);
//    		right2.setVoltageRampRate(5);
//            left2.setVoltageRampRate(5);
    		rightMotor.set(-856.8*2.583);
    		leftMotor.set(856.8*2.583);
//	    	System.out.println("right pos: " + rightMotor.get());
//	    	System.out.println("left pos: " + leftMotor.get());


    	}
    	
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    	accel.startLiveWindowMode();

    }
    
}
