package com.SmartSnapShot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ScreenShotApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Smart SnapShot");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(460,200);
		frame.setLayout(null);
		frame.getContentPane().setBackground(Color.BLACK);
		
		
		// start button 
		int startButtonHeight = 85;
		int startButtonWidth = 80;
		
		ImageIcon startIcon = new ImageIcon("C:\\Users\\2021603\\eclipse-workspace\\SmartSnapShot\\src\\assets\\snapshot_icon.png");
		
		Image scaledStartImage = startIcon.getImage().getScaledInstance(startButtonWidth, startButtonHeight, Image.SCALE_SMOOTH);
		ImageIcon scaledStartIcon = new ImageIcon(scaledStartImage);
		
		//active icon for the start button.
		
		ImageIcon activeIcon = new ImageIcon("C:\\Users\\2021603\\eclipse-workspace\\SmartSnapShot\\src\\assets\\snapshot_active.png");
		Image scaledActiveImage = activeIcon.getImage().getScaledInstance(startButtonWidth, startButtonHeight,  Image.SCALE_SMOOTH);
		ImageIcon scaledActiveIcon = new ImageIcon(scaledActiveImage);
		
		JButton startButton = new JButton(scaledStartIcon);
		startButton.setBounds(50,20,startButtonWidth,startButtonHeight);
		startButton.setBorderPainted(false);
		startButton.setToolTipText("start screen capture");
		frame.add(startButton);
		
		
		//end button
		
		int endButtonWidth = 75;
		int endButtonHeight = 84;
		
		ImageIcon stopIcon = new ImageIcon("C:\\Users\\2021603\\eclipse-workspace\\SmartSnapShot\\src\\assets\\snapshot_stop.png");
		Image scaledStopImage = stopIcon.getImage().getScaledInstance(endButtonWidth, endButtonHeight,Image.SCALE_SMOOTH);
		ImageIcon scaledStopIcon = new ImageIcon(scaledStopImage);
		
		JButton endButton = new JButton(scaledStopIcon);
		endButton.setBounds(190,20,endButtonWidth,endButtonHeight);
		endButton.setBorderPainted(false);
		endButton.setToolTipText("Stop screen capture");
		frame.add(endButton);
		
		// timer Button

		int timerButtonWidth =  75;
		int timerButtonHeight = 80;
		
		ImageIcon timerIcon = new ImageIcon("C:\\Users\\2021603\\eclipse-workspace\\SmartSnapShot\\src\\assets\\timer.png");
		Image timerImage= timerIcon.getImage().getScaledInstance(timerButtonWidth, timerButtonHeight, Image.SCALE_SMOOTH);
		ImageIcon scaledTimerIcon = new ImageIcon(timerImage);
		
		JButton timerButton = new JButton(scaledTimerIcon);
		timerButton.setBounds(310,18,timerButtonWidth,timerButtonHeight);
		timerButton.setBorderPainted(false);
		timerButton.setToolTipText("Snapshot interval");
		frame.add(timerButton);
		
		
	
		//time interval list
		 DefaultListModel<String> listModel = new DefaultListModel<String>();
			
			for(int i = 1; i <= 10;i++) {
				listModel.addElement(i+" second"+ (i >1 ? "s":""));
			}
			
			JList<String> timeList = new JList<>(listModel);
				
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
							//close the popup menu
					popupMenu.setVisible(false);
						}
					}
				});
		
//		// button to dynamically add new intervals
//		
//		JButton addButton = new JButton("+");
//		addButton.setBounds(350,110,50,50);
//		frame.add(addButton);
//		
//		addButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				String newInterval = JOptionPane.showInputDialog(frame,"Enter new Interval(in seconds)");
//				if(newInterval != null && !newInterval.trim().isEmpty()) {
//					try {
//						
//						int interval = Integer.parseInt(newInterval.trim());
//						listModel.addElement(interval + "second"+(interval > 1?"s" :""));
//						
//					} catch(NumberFormatException ex) {
//						
//						JOptionPane.showMessageDialog(frame,"Please enter a valid number!","Invalid input",JOptionPane.ERROR_MESSAGE);
//					}
//				}
//			}
//		});
		
		
		//adding action listener to start button to change its icon.
		startButton.addActionListener(e->
		{
					startButton.setIcon(scaledActiveIcon);
					System.out.println("Snapshot started");
					//frame.setState(Frame.ICONIFIED);
		});
		
		//adding action listener to stop button---> to restore the start button icon.
		
		endButton.addActionListener(e->
		{
		startButton.setIcon(scaledStartIcon);
		System.out.println("Snapshot stopped");
		});
		
		frame.setLocation(1050, 650);
		//frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

}
