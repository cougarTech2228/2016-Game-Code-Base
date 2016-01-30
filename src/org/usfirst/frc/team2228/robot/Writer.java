package org.usfirst.frc.team2228.robot;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;
 
public class Writer{
	


	public Writer(){
		
		
		
	}
	static long lastTimeILogged = 0;
	public void log(int l, String string, int howOften){
		
			long timePassed = System.currentTimeMillis() - lastTimeILogged;
			if(timePassed > howOften){
				lastTimeILogged = System.currentTimeMillis();

				String content = string;
				File file = new File("/U/Data"+2+".txt");
				boolean append = true;
				
				try (FileOutputStream fop = new FileOutputStream(file, append)) {
					
					// if file doesn't exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					}
					
					// get the content in bytes
					byte[] contentInBytes = content.getBytes();
		 
					fop.write(contentInBytes);
					fop.write(System.getProperty("line.separator").getBytes());
					fop.flush();
					fop.close();
					
					System.out.println("Done");
		 
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
	}
	

	
}