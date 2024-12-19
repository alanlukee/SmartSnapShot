package com.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import com.model.ScreenshotModel;
import com.utils.Constants;
import com.view.ButtonsBar;
import com.view.ButtonsListener;
import com.view.PreviewWindow;

public class ScreenshotController {

	private ScreenshotModel model;
	private ButtonsBar buttonsBar;
	private PreviewWindow previewWindow;
	private Timer screenshotTimer;
	private JFileChooser folderChooser;
	private JWindow popupFrame;

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

		if (!model.isActive()) {

			folderChooser = new JFileChooser();
			folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = folderChooser.showOpenDialog(null);

			if (result == JFileChooser.APPROVE_OPTION) {
				String selectedFolder = folderChooser.getSelectedFile().getAbsolutePath();
				File folder = new File(selectedFolder);
				File[] files = folder.listFiles((dir, name) -> name.endsWith(".png") || name.endsWith(".jpg"));
				if (files != null && files.length > 0) {

					int deleteChoice = JOptionPane.showConfirmDialog(null,
							"Existing Screenshot files detected. Do you want to delete them?",
							"Delete files confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (deleteChoice == JOptionPane.YES_OPTION) {
						for (File file : files) {
							file.delete();
						}
						JOptionPane.showMessageDialog(null, "Existing screenshots are deleted");
					} else {
						JOptionPane.showMessageDialog(null, "Existing screenshots are retained");
					}
				}
				model.setFolderPath(selectedFolder);
				model.setActive(true);
			} else {

				int userChoice = JOptionPane.showConfirmDialog(null,
						"No folder selected, Do you want to save it in default folder?", "Confirmation",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (userChoice == JOptionPane.YES_OPTION) {
					model.setActive(true);
				} else {
					buttonsBar.getStartButton().setEnabled(false);
					model.setActive(false);
					model.setUserDemandMode(false);
				}

			}

			if (model.isUserDemandMode()) {

				startUserDemandMode();

			} else {

				startTimerMode();
			}
		}
	}


	private void startUserDemandMode() {
		System.out.println("User demand mode activated");
		buttonsBar.setActiveButtonIcon();
		buttonsBar.enableStopButon(true);

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				popupFrame = new JWindow();
				
				popupFrame.setSize(50, 50);
				popupFrame.setVisible(true);
				
				SwingUtilities.invokeLater(() -> {
					JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(buttonsBar);
					if (mainFrame != null) {
						mainFrame.setState(JFrame.ICONIFIED);
					}
				});
				
				popupFrame.setLocation(1200, 200);
				popupFrame.setAlwaysOnTop(true);
				JPanel panel = new JPanel();
				
				panel.setLayout(new BorderLayout());
				panel.setBackground(new Color(255,0,0));
			

				ImageIcon cameraIcon = new ImageIcon(Constants.CAMERA_ICON_PATH);
				Image scaledCameraImage = cameraIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
				ImageIcon scaledCameraIcon = new ImageIcon(scaledCameraImage);
				
				JButton takeScreenShotButton = new JButton(scaledCameraIcon);
				takeScreenShotButton.setBorder(BorderFactory.createEmptyBorder());
				
				//takeScreenShotButton.setBounds(0, 10,40,40);
				panel.add(takeScreenShotButton,BorderLayout.CENTER);
				popupFrame.add(panel);
				
				final Point[] initialClick = {null};

				takeScreenShotButton.addMouseListener(new MouseAdapter() {

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						initialClick[0] = e.getPoint();
					}	
				});
				
				
				takeScreenShotButton.addMouseMotionListener(new MouseAdapter() {

					@Override
					public void mouseDragged(MouseEvent e) {
						// TODO Auto-generated method stub
						if(initialClick[0] !=null) {
							int currentX = popupFrame.getLocation().x;
							int currentY = popupFrame.getLocation().y;
							
							int newX = currentX + e.getX() - initialClick[0].x;
							int newY = currentY + e.getY() - initialClick[0].y;
							popupFrame.setLocation(newX, newY);
						}
					}					
				});
				
				takeScreenShotButton.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						//super.mouseClicked(e);
						ImageIcon activeIcon = new ImageIcon(Constants.ACTIVE_ICON_PATH);
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
					}
				});		
			}
		});
	}

	private void startTimerMode() {
		if (model.isActive()) {
			

			buttonsBar.setActiveButtonIcon();
			buttonsBar.enableStopButon(true);

			ImageIcon activeIcon = new ImageIcon(Constants.ACTIVE_ICON_PATH);
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

			}, 1000, model.getInterval());

		} 
	}
	private void stopScreenShotProcess() {

		if (model.isActive()) {

			if (model.isUserDemandMode()) {
				model.setUserDemandMode(false);

				model.setActive(false);
				buttonsBar.setStartButtonIcon();
				buttonsBar.enableStopButon(false);
				popupFrame.setVisible(false);
				
				model.resetScreenshotCounter();

				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						SwingUtilities.invokeLater(() -> {
							JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(buttonsBar);
							if (mainFrame != null) {
								mainFrame.dispose();
		
							}
						});
						openPreviewWindow();
					}
				});
			}

			else {
				
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
						
						SwingUtilities.invokeLater(() -> {
							JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(buttonsBar);
							if (mainFrame != null) {
								mainFrame.dispose();
		
							}
						});
						openPreviewWindow();
						
					}
				});
			}
		}
	}

	private void timerProcess() {

		String selectedOption = (String) JOptionPane.showInputDialog(buttonsBar, "Choose timer mode", "Timer mode selection",
				JOptionPane.PLAIN_MESSAGE, null, Constants.TIMER_OPTIONS, Constants.TIMER_OPTIONS[0]);

		if (selectedOption != null) {
			
			buttonsBar.enableStartButton(true);
			switch (selectedOption) {

			case ("Time interval"):
				timeIntervalMode();
				break;

			case ("User demand"):
				userDemandMode();
				break;

			default:
				JOptionPane.showMessageDialog(null, "Invalid option selected", "Error", JOptionPane.ERROR_MESSAGE);
				break;
			}
		}
	}

	private void userDemandMode() {
		JOptionPane.showMessageDialog(null, "User Demand mode Activated");
		model.setUserDemandMode(true);
	}

	private void timeIntervalMode() {

		String selected = (String) JOptionPane.showInputDialog(buttonsBar, "Select timer interval (seconds):", "Set Timer",
				JOptionPane.PLAIN_MESSAGE, null, Constants.TIMER_INTERVAL_OPTIONS,
				String.valueOf(model.getInterval() / 1000));

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

		JOptionPane.showMessageDialog(null, "Timer restarted with new interval: " + model.getInterval() + " seconds");
	}

	private void openPreviewWindow() {

		if (previewWindow == null || !previewWindow.isVisible()) {
			if(model.getScreenShots().isEmpty()) {
				JOptionPane.showMessageDialog(null, "No images found!", "Info", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				previewWindow = new PreviewWindow(model.getScreenShots());
				previewWindow.setVisible(true);
			}

		} else {
			previewWindow.refresh(model.getScreenShots());
		}
	}

}
