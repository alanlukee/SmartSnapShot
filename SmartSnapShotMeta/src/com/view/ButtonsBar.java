package com.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/*
 * @author Alan Luke
 */


public class ButtonsBar extends JPanel {
	
	private JButton startButton;
	private JButton stopButton;
	private JButton timerButton;
	
	private ButtonsListener buttonsListener;
	
	public ButtonsBar() {
		
		setBackground(Color.black);
		setLayout(new FlowLayout(FlowLayout.LEFT,45,10));
	
		
		ImageIcon startIcon = new ImageIcon("src/assets/snapshot_icon.png");
		Image scaledStartImage = startIcon.getImage().getScaledInstance(80, 85, Image.SCALE_SMOOTH);
		ImageIcon scaledStartIcon = new ImageIcon(scaledStartImage);
		

		startButton = new JButton(scaledStartIcon);
		
		startButton.setBorder(BorderFactory.createEmptyBorder());
		startButton.setToolTipText("start screen capture");
		startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		
		ImageIcon stopIcon = new ImageIcon("src/assets/snapshot_stop.png");
		Image scaledStopImage = stopIcon.getImage().getScaledInstance(75, 84,Image.SCALE_SMOOTH);
		ImageIcon scaledStopIcon = new ImageIcon(scaledStopImage);
		
		stopButton = new JButton(scaledStopIcon);
		
		stopButton.setBorder(BorderFactory.createEmptyBorder());
		stopButton.setToolTipText("Stop screen capture");
		stopButton.setEnabled(false);
		stopButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		
	
		ImageIcon timerIcon = new ImageIcon("src/assets/timer.png");
		Image timerImage = timerIcon.getImage().getScaledInstance(75, 80, Image.SCALE_SMOOTH);
		ImageIcon scaledTimerIcon = new ImageIcon(timerImage);
		
		timerButton = new JButton(scaledTimerIcon);

		timerButton.setBorder(BorderFactory.createEmptyBorder());
		timerButton.setToolTipText("Snapshot interval");
		timerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		
		add(startButton);
		add(stopButton);
		add(timerButton);
	
		
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				buttonsListener.startButtonAction();
				
			}
		});
		
		stopButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				buttonsListener.stopButtonAction();
				
			}
		});
		
		
		timerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				buttonsListener.timerButtonAction();
			}
		});	
	}
	
	public void setButtonsListener(ButtonsListener buttonsListener) {
		this.buttonsListener = buttonsListener;
	}
	
	public JButton getTimerButton() {
		return timerButton;
	}
	
	public JButton getStartButton() {
		return startButton;
	}
	
	public JButton getStopButton() {
		return stopButton;
	}
	
	public void setActiveButtonIcon() {
		
		//active icon for the start button.
		ImageIcon activeIcon = new ImageIcon("src/assets/snapshot_active.png");
		Image scaledActiveImage = activeIcon.getImage().getScaledInstance(80, 85,  Image.SCALE_SMOOTH);
		ImageIcon scaledActiveIcon = new ImageIcon(scaledActiveImage);
		startButton.setIcon(scaledActiveIcon);
		
	}
	
	public void setStopButtonIcon(String path) {
		stopButton.setIcon(new ImageIcon(path));
	}
	
	public void setStartButtonIcon() {
		ImageIcon startIcon = new ImageIcon("src/assets/snapshot_icon.png");
		Image scaledStartImage = startIcon.getImage().getScaledInstance(80, 85, Image.SCALE_SMOOTH);
		ImageIcon scaledStartIcon = new ImageIcon(scaledStartImage);
		startButton.setIcon(scaledStartIcon);

	}
	
	public void enableStopButon(boolean flag) {
		stopButton.setEnabled(flag);
	}



	
}