package com.controller;

import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import com.model.ScreenshotModel;
import com.view.ButtonsBar;
import com.view.ButtonsListener;
import com.view.PreviewWindow;

public class ScreenshotController {

	private ScreenshotModel model;
	private ButtonsBar buttonsBar;
	private PreviewWindow previewWindow;
	private Timer screenshotTimer;

	JWindow popupFrame ;

	public ScreenshotController(ButtonsBar buttonsBar) {

		model = new ScreenshotModel();
		this.buttonsBar = buttonsBar;

		buttonsBar.setButtonsListener(new ButtonsListener() {

			@Override
			public void stopButtonAction() {
				// TODO Auto-generated method stub
				stopScreenShotProcess();
			}

			@Override
			public void startButtonAction() {
				// TODO Auto-generated method stub
				startScreenShotProcess();
			}

			@Override
			public void timerButtonAction() {
				// TODO Auto-generated method stub
				timerProcess();
			}
		});
	}

	private void startScreenShotProcess() {

		if(!model.isActive()) {
			System.out.println("check-1");
			model.setActive(true);
			
			if(model.isUserDemandMode()) {
				//System.out.println("shit");
				startUserDemandMode();
			}
			else {
				System.out.println("nice");
				startTimerMode();
			}
		}
		else {
			System.out.println("check-2");
			System.out.println("Start button is already gsdfs active");
		}
	}
	
	private void startUserDemandMode() {
		System.out.println("User demand mode activated");
//		model.setActive(true);
		buttonsBar.setActiveButtonIcon();
		buttonsBar.enableStopButon(true);
	
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				popupFrame = new JWindow();
				popupFrame.setSize(50,50);
				popupFrame.setVisible(true);
				popupFrame.setLocation(1200,200);
				
				ImageIcon activeIcon = new ImageIcon("src/assets/snapshot_icon.png");
				Image scaledActiveImage = activeIcon.getImage().getScaledInstance(50, 50,  Image.SCALE_SMOOTH);
				ImageIcon scaledActiveIcon = new ImageIcon(scaledActiveImage);
				
				
				JButton takeScreenShotButton = new JButton(scaledActiveIcon);
				takeScreenShotButton.setBorder(BorderFactory.createEmptyBorder());
				popupFrame.add(takeScreenShotButton);
				popupFrame.setVisible(true);
				popupFrame.setAlwaysOnTop(true);
				popupFrame.toFront();
				popupFrame.requestFocus();
				
				takeScreenShotButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						ImageIcon activeIcon = new ImageIcon("src/assets/snapshot_active.png");
						Image scaledActiveImage = activeIcon.getImage().getScaledInstance(80, 85, Image.SCALE_SMOOTH);

					
						JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(buttonsBar);
						if (mainFrame != null) {
							mainFrame.setState(JFrame.ICONIFIED);
							mainFrame.setIconImage(scaledActiveImage);
						}
						popupFrame.setVisible(false);
						model.takeScreenshot();
						popupFrame.setVisible(true);
						popupFrame.setAlwaysOnTop(true);
						popupFrame.toFront();
						popupFrame.requestFocus();
						
					}
				});
			}
		});
	}

	private void startTimerMode() {
		if (model.isActive()) {
			System.out.println("Snapshot functionality initiated.");
			//model.setActive(true);
			
			buttonsBar.setActiveButtonIcon();
			buttonsBar.enableStopButon(true);

			ImageIcon activeIcon = new ImageIcon("src/assets/snapshot_active.png");
			Image scaledActiveImage = activeIcon.getImage().getScaledInstance(80, 85, Image.SCALE_SMOOTH);

			SwingUtilities.invokeLater(() -> {
				JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(buttonsBar);
				if (mainFrame != null) {
					mainFrame.setState(JFrame.ICONIFIED);
					mainFrame.setIconImage(scaledActiveImage);
				}
			});

			screenshotTimer = new Timer();

			screenshotTimer.scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					model.takeScreenshot();
					;
				}

			}, 0, model.getInterval());

		} else {
			System.out.println("Start button is already active.");
		}
	}

	

	private void stopScreenShotProcess() {

		if (model.isActive()) {
			
			if(model.isUserDemandMode()) {
				model.setUserDemandMode(false);
					
				model.setActive(false);
				buttonsBar.setStartButtonIcon();
				buttonsBar.enableStopButon(false);
				popupFrame.setVisible(false);
					
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						System.out.println("Opening preview window");
						openPreviewWindow();
					}
				});		
			}
			
			else {
				System.out.println("SnapShot stopped");
				model.setActive(false);
				buttonsBar.setStartButtonIcon();
				buttonsBar.enableStopButon(false);

				if (screenshotTimer != null) {
					screenshotTimer.cancel();
				}

				model.resetScreenshotCounter();

				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						System.out.println("Opening preview window");

						openPreviewWindow();
						System.out.println("close");
					}
				});	
			}
		}
	}

	private void timerProcess() {

		String[] options = { "User demand", "Time interval" };
		String selectedOption = (String) JOptionPane.showInputDialog(null, "Choose timer mode", "Timer mode selection",
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

		if (selectedOption != null) {
			switch (selectedOption) {

			case ("Time interval"):
				timeIntervalMode();
			break;

			case("User demand"):
				userDemandMode();
			break;
			
			default:
				JOptionPane.showMessageDialog(null, "Invalid option selected", "Error",JOptionPane.ERROR_MESSAGE);
				break;
			}
		}
	}
	
	private void userDemandMode() {
		System.out.println("Clicked user demand");
		model.setUserDemandMode(true);
	}

	private void timeIntervalMode() {

		String[] options = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

		String selected = (String) JOptionPane.showInputDialog(null, "Select timer interval (seconds):", "Set Timer",
				JOptionPane.PLAIN_MESSAGE, null, options, String.valueOf(model.getInterval() / 1000));

		if (selected != null) {
			try {
				int newInterval = Integer.parseInt(selected) * 1000;
				model.setInterval(newInterval);
				buttonsBar.getTimerButton().setToolTipText("Selected interval: " + selected + " seconds");
				System.out.println("Screenshot interval set to: " + model.getInterval() / 1000 + " seconds");

				if (model.isActive()) {
					restartTimer();
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	private void restartTimer() {

		if (screenshotTimer != null) {
			screenshotTimer.cancel();
		}
		screenshotTimer = new Timer();

		screenshotTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				model.takeScreenshot();
			}

		}, 0, model.getInterval());

		System.out.println("Timer restarted with new interval: " + model.getInterval() / 1000 + " seconds");
	}

	private void openPreviewWindow() {
		if (previewWindow == null || !previewWindow.isVisible()) {
			previewWindow = new PreviewWindow(model.getScreenShots());
			previewWindow.setVisible(true);
		} else {
			previewWindow.refresh(model.getScreenShots());
		}
	}

}
