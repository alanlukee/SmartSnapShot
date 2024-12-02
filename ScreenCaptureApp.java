package com.smartSnapShot;
import javax.swing.*;
import java.awt.*;

public class ScreenCaptureApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(450,150);
		frame.setLayout(null);
		frame.getContentPane().setBackground(Color.BLACK);
		
		int startButtonWidth = 80;  
        int startButtonHeight = 75;

       
        ImageIcon originalIcon = new ImageIcon("/Users/alanluke/Desktop/LukZ/QuEST/snapshot_icon.png"); 
        Image scaledImage = originalIcon.getImage().getScaledInstance(startButtonWidth, startButtonHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        
		//start button
		JButton startButton = new JButton(scaledIcon);
		startButton.setBounds(50, 20, startButtonWidth, startButtonWidth);
		startButton.setToolTipText("Start screen capture");
		frame.add(startButton);
		
		int endButtonWidth = 60;  
        int endButtonHeight = 65;
		
        ImageIcon originalIcon_2 = new ImageIcon("/Users/alanluke/Desktop/LukZ/QuEST/stop_icon.jpg"); 
        Image scaledImage_2 = originalIcon_2.getImage().getScaledInstance(endButtonWidth, endButtonHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon_2 = new ImageIcon(scaledImage_2);
		
		//end button
		JButton endButton = new JButton(scaledIcon_2);
		endButton.setBounds(200, 28, endButtonWidth, endButtonHeight);
	
		endButton.setToolTipText("Stop screen capture");
		frame.add(endButton);
		
		int timerButtonWidth = 60;  
        int timerButtonHeight = 65;
		
        ImageIcon originalIcon_3 = new ImageIcon("/Users/alanluke/Desktop/LukZ/QuEST/timer.png"); 
        Image scaledImage_3 = originalIcon_3.getImage().getScaledInstance(endButtonWidth, endButtonHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon_3 = new ImageIcon(scaledImage_3);
		
		//end button
		JButton timerButton = new JButton(scaledIcon_3);
		timerButton.setBounds(320, 25, timerButtonWidth, timerButtonHeight);
	
		timerButton.setToolTipText("snapshot intervals");
		frame.add(timerButton);
		
		
		
		
		
		frame.setVisible(true);
		
		

	}

}
