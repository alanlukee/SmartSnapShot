package com.SmartSnapShot;

import javax.swing.*;
import java.awt.*;


public class ScreenShotApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Smart SnapShot");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(460,150);
		frame.setLayout(null);
		frame.getContentPane().setBackground(Color.white);
		
		
		// start button 
		int startButtonHeight = 85;
		int startButtonWidth = 80;
		
		ImageIcon orginalIcon = new ImageIcon("C:\\Users\\2021603\\eclipse-workspace\\SmartSnapShot\\src\\assets\\camera_icon.jpg");
		
		Image scaledImage = orginalIcon.getImage().getScaledInstance(startButtonWidth, startButtonHeight, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		
		JButton startButton = new JButton(scaledIcon);
		startButton.setBounds(50,20,startButtonWidth,startButtonHeight);
		startButton.setBorderPainted(false);
		startButton.setToolTipText("start screen capture");
		frame.add(startButton);
		
		
		//end button
		int endButtonWidth = 60;
		int endButtonHeight = 65;
		
		ImageIcon originalIcon1 = new ImageIcon("C:\\Users\\2021603\\eclipse-workspace\\SmartSnapShot\\src\\assets\\stop.png");
		Image scaledImage1 = originalIcon1.getImage().getScaledInstance(endButtonWidth, endButtonHeight,Image.SCALE_SMOOTH);
		ImageIcon scaledIcon1 = new ImageIcon(scaledImage1);
		
		JButton endButton = new JButton(scaledIcon1);
		endButton.setBounds(190,33,endButtonWidth,endButtonHeight);
		endButton.setBorderPainted(false);
		endButton.setToolTipText("stop screen capture");
		frame.add(endButton);
		
		// timer Button
		
		int timerButtonWidth =  60;
		int timerButtonHeight = 65;
		
		ImageIcon originalIcon2 = new ImageIcon("C:\\Users\\2021603\\eclipse-workspace\\SmartSnapShot\\src\\assets\\timer.jpg");
		Image scaledImage2 = originalIcon2.getImage().getScaledInstance(timerButtonWidth, timerButtonHeight, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon2 = new ImageIcon(scaledImage2);
		
		JButton timerButton = new JButton(scaledIcon2);
		timerButton.setBounds(310,30,timerButtonWidth,timerButtonHeight);
		timerButton.setBorderPainted(false);
		timerButton.setToolTipText(" screenshot interval");
		frame.add(timerButton);
		
		
		//time interval list
		String[] timeIntervals = {"1 seconds","2 seconds","3 seconds","4 seconds","5 seconds"};
		JList<String> timeList = new JList<>(timeIntervals);
				
		timeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		JScrollPane scrollPane = new JScrollPane(timeList);
						
		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.add(scrollPane);
				
		timerButton.addActionListener(e ->{
				popupMenu.show(timerButton,60,0);
				});
				
		// add list selection listener to handle the selection of time intervals	
		timeList.addListSelectionListener(event ->{
			if(! event.getValueIsAdjusting()) {
				String selectedInterval = timeList.getSelectedValue();
						
				if(selectedInterval != null) {
					timerButton.setToolTipText("selected interval:"+ selectedInterval);
							// close the popup menu
					popupMenu.setVisible(false);
						}
					}
				});
		
		frame.setVisible(true);

	}

}
