package com.view;

import com.controller.ScreenshotController;


import java.awt.BorderLayout;

import javax.swing.JFrame;


public class SnapShotAppMeta extends JFrame{
	
	private ButtonsBar buttonsBar;
	private ScreenshotController controller;
	
	public SnapShotAppMeta() {
		
		super("Smart SnapShot App");
		buttonsBar = new ButtonsBar();
		controller = new ScreenshotController(buttonsBar);
				
		setLayout(new BorderLayout());

		add(buttonsBar,BorderLayout.CENTER);
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(460,150);
		setLocation(1050, 650);
		//setLocationRelativeTo(null);
		setVisible(true);
		

	}
	
	

}