package com.SmartSnapShot;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ScreenShotApp {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("smart screenshot");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(450,150);
		frame.setLayout(null);
		frame.getContentPane().setBackground(Color.white);
		
		// start button 
		int startButtonHeight = 80;
		int startButtonWidth = 75;
		
		ImageIcon startIcon = new ImageIcon("C:\\Users\\2021615\\eclipse-workspace\\com.SmartSnapShot\\src\\assets\\camera_icon.jpg");
		Image scaledStartImage = startIcon.getImage().getScaledInstance(startButtonWidth, startButtonHeight, Image.SCALE_SMOOTH);
		ImageIcon scaledStartIcon = new ImageIcon(scaledStartImage);
		
		// active icon for the start button
		ImageIcon activeIcon = new ImageIcon("C:\\Users\\2021615\\eclipse-workspace\\com.SmartSnapShot\\src\\assets\\camera_active.png");
		Image scaledActiveImage = activeIcon.getImage().getScaledInstance(startButtonWidth, startButtonHeight,  Image.SCALE_SMOOTH);
		ImageIcon scaledActiveIcon = new ImageIcon(scaledActiveImage);
		
		JButton startButton = new JButton(scaledStartIcon);
		startButton.setBounds(50,20,startButtonWidth,startButtonHeight);
		startButton.setBorderPainted(false);
		startButton.setToolTipText("start screen capture");
		frame.add(startButton);
		
		// end button 
		
		int endButtonWidth = 60;
		int endButtonHeight = 65;
		
		ImageIcon originalIcon1 = new ImageIcon("C:\\Users\\2021615\\eclipse-workspace\\com.SmartSnapShot\\src\\assets\stop.png");
		Image scaledImage1 = originalIcon1.getImage().getScaledInstance(endButtonWidth, endButtonHeight,Image.SCALE_SMOOTH);
		ImageIcon scaledIcon1 = new ImageIcon(scaledImage1);
		
		JButton endButton = new JButton(scaledIcon1);
		endButton.setBounds(200,28,endButtonWidth,endButtonHeight);
		
		endButton.setToolTipText("stop screen capture");
		frame.add(endButton);
		
		// timer Button
		
		int timerButtonWidth =  60;
		int timerButtonHeight = 65;
		
		ImageIcon originalIcon2 = new ImageIcon("C:\\Users\\2021615\\eclipse-workspace\\com.SmartSnapShot\\src\\assets\\timer.jpg");
		Image scaledImage2 = originalIcon2.getImage().getScaledInstance(timerButtonWidth, timerButtonHeight, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon2 = new ImageIcon(scaledImage2);
		
		JButton timerButton = new JButton(scaledIcon2);
		timerButton.setBounds(320,25,timerButtonWidth,timerButtonHeight);
		timerButton.setToolTipText(" screenshot interval");
		frame.add(timerButton);
		
		// default list model to dynamically take intervals from the end user
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
		for(int i = 1; i <= 10;i++) {
			listModel.addElement(i+" second"+ (i >1 ? "s":""));
		}
		
		JList<String> timeList = new JList<>(listModel);
		
		timeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// add the list to scroll pane
		
		JScrollPane scrollPane = new JScrollPane(timeList);
		
		// create a pop up menu and add the scroll pane to it
		
		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.add(scrollPane);
		
		// show the popup menu when timer button is clicked
		
		timerButton.addActionListener(e ->{
			popupMenu.show(timerButton,0,timerButton.getHeight());
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
		
		// button to dynamically add new intervals
		
		JButton addButton = new JButton("Add interval ");
		addButton.setBounds(150,110,120,30);
		frame.add(addButton);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newInterval = JOptionPane.showInputDialog(frame,"Enter new Interval(in seconds)");
				if(newInterval != null && !newInterval.trim().isEmpty()) {
					try {
						
						int interval = Integer.parseInt(newInterval.trim());
						listModel.addElement(interval + "second"+(interval > 1?"s" :""));
						
					} catch(NumberFormatException ex) {
						
						JOptionPane.showMessageDialog(frame,"Please enter a valid number!","Invalid input",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		frame.setLocation(1050,650);
		
		frame.setVisible(true);

	}

}
