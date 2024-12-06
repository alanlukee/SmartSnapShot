package com.SmartSnapShot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;


public class ScreenShotApp extends JFrame {
	
	private static int screenshotCounter = 1;
	private Timer screenshotTimer;
	private int interval =2000; //default interval
	
	public ScreenShotApp() {
		
		super("Smart SnapShot");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(460,150);
		setLayout(null);
		getContentPane().setBackground(Color.BLACK);
		
		// start button 
		int startButtonHeight = 85;
		int startButtonWidth = 80;
		
		ImageIcon startIcon = new ImageIcon("src\\assets\\snapshot_icon.png");
		Image scaledStartImage = startIcon.getImage().getScaledInstance(startButtonWidth, startButtonHeight, Image.SCALE_SMOOTH);
		ImageIcon scaledStartIcon = new ImageIcon(scaledStartImage);
		
		//active icon for the start button.
		
		ImageIcon activeIcon = new ImageIcon("src\\assets\\snapshot_active.png");
		Image scaledActiveImage = activeIcon.getImage().getScaledInstance(startButtonWidth, startButtonHeight,  Image.SCALE_SMOOTH);
		ImageIcon scaledActiveIcon = new ImageIcon(scaledActiveImage);
		
		JButton startButton = new JButton(scaledStartIcon);
		startButton.setBounds(50,20,startButtonWidth,startButtonHeight);
		startButton.setBorderPainted(false);
		startButton.setToolTipText("start screen capture");
		startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(startButton);
		
		//end button
		
		int endButtonWidth = 75;
		int endButtonHeight = 84;
		
		ImageIcon stopIcon = new ImageIcon("src\\assets\\snapshot_stop.png");
		Image scaledStopImage = stopIcon.getImage().getScaledInstance(endButtonWidth, endButtonHeight,Image.SCALE_SMOOTH);
		ImageIcon scaledStopIcon = new ImageIcon(scaledStopImage);
		
		JButton endButton = new JButton(scaledStopIcon);
		endButton.setBounds(190,20,endButtonWidth,endButtonHeight);
		endButton.setBorderPainted(false);
		endButton.setToolTipText("Stop screen capture");
		endButton.setEnabled(false);
		endButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		add(endButton);
		
		// timer Button
		int timerButtonWidth =  75;
		int timerButtonHeight = 80;
		
		ImageIcon timerIcon = new ImageIcon("src\\assets\\timer.png");
		Image timerImage = timerIcon.getImage().getScaledInstance(timerButtonWidth, timerButtonHeight, Image.SCALE_SMOOTH);
		ImageIcon scaledTimerIcon = new ImageIcon(timerImage);
		
		JButton timerButton = new JButton(scaledTimerIcon);
		timerButton.setBounds(310,18,timerButtonWidth,timerButtonHeight);
		timerButton.setBorderPainted(false);
		timerButton.setToolTipText("Snapshot interval");
		timerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		add(timerButton);

		//time interval list
		DefaultListModel<String> listModel = new DefaultListModel<String>();
			
			for(int i = 1; i <= 10;i++) {
				listModel.addElement(Integer.toString(i));
			}
			
		JList<String> timeList = new JList<>(listModel);
				
		timeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		JScrollPane scrollPane = new JScrollPane(timeList);
						
		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.add(scrollPane);
				
		timerButton.addActionListener(e ->
		{
			popupMenu.show(timerButton,60,0);
			
		});
				
		// add list selection listener to handle the selection of time intervals
		boolean[] isActive = {false};
		
		timeList.addListSelectionListener(event ->{
			
			if(!event.getValueIsAdjusting()) {
				String selectedInterval = timeList.getSelectedValue();
						
				if(selectedInterval != null) {
					interval = Integer.parseInt(selectedInterval)*1000;
					timerButton.setToolTipText("selected interval:"+ selectedInterval);
					System.out.println("Screenshot interval:"+selectedInterval);
					
					if(isActive[0]) {
						System.out.println("New snapshot interval selected..");
						restartTimer();
					}
					
					//close the popup menu
					popupMenu.setVisible(false);
						}
					}
				});
		
		//adding action listener to start button to change its icon.
		startButton.addActionListener(e->
		{
			if(!isActive[0]) { //start button is not active now
				
				startButton.setIcon(scaledActiveIcon);
				System.out.println("Snapshot functionality initiated");
				setState(Frame.ICONIFIED);
				setIconImage(scaledActiveImage);
				endButton.setEnabled(true);
				
				
			screenshotTimer = new Timer();
			screenshotTimer.scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					takeScreenshot();
				}
				
			}, 0, interval);
			
			isActive[0] = true; //start button is active now
		
			}
			else {
				System.out.println("Start button is already active.");
			}
		});
		
		//adding action listener to stop button---> to restore the start button icon.
		
		endButton.addActionListener(e->
		
		{
			if(isActive[0]){
				startButton.setIcon(scaledStartIcon);
				System.out.println("Snapshot stopped");
				endButton.setEnabled(false);
				
				if(screenshotTimer!=null){
					screenshotTimer.cancel();
				}
				
				isActive[0]=false;
				screenshotCounter=1;
			}
		});
		
			setLocation(1050, 650);
			//setLocationRelativeTo(null);
			setVisible(true);
			//setIconImage(scaledStartImage);
	}
	
		private void takeScreenshot() {
		
				
			try {
				
				flashScreen();
				Robot robot = new Robot(); //interface to interact with the screen
				
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //provides access to the systems graphical environment.
				//getScreenSize() would fetch the dimensions of the entire screen.
			
				Rectangle screenRect = new Rectangle(screenSize); //Rectangle class is used to define a rectangular region.
			
				BufferedImage screenshot = robot.createScreenCapture(screenRect); //captures the screenarea defined by screenrect
				//																	and stored as a bufferedImage object
			
				String folderPath = "src\\snapShots\\";
				String fileName = "screenshot_" +screenshotCounter+".png";
				File file = new File(folderPath+fileName);
				ImageIO.write(screenshot, "png", file);
				System.out.println("Screen captured");
				System.out.println("Screenshot saved: "+file.getAbsolutePath());
			
				//JOptionPane.showMessageDialog(null, "Screenshot saved: "+file.getAbsolutePath());
			
				screenshotCounter++;
			
			}
			catch(Exception ex){
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Failed to capture screen" +ex.getMessage(),"error",JOptionPane.ERROR_MESSAGE);	
			}
	}
		
		
		private void flashScreen() {
			
			JWindow flashWindow = new JWindow();
			flashWindow.setBackground(new Color(255,255,255,150));
			flashWindow.setBounds(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			flashWindow.setAlwaysOnTop(true);
			

			flashWindow.setVisible(true);
			try {
				Thread.sleep(70);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			flashWindow.dispose();
			
			}

		private void restartTimer() {
			
			if(screenshotTimer!=null) {
				screenshotTimer.cancel();
			}
			screenshotTimer = new Timer();
			screenshotTimer.scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					takeScreenshot();
				}
				
			}, 0, interval);
			
			System.out.println("Timer restarted with new interval: "+interval/1000+" seconds");
		}		
	}

