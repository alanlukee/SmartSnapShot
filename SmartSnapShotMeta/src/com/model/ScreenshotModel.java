package com.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JWindow;

public class ScreenshotModel {

	private int screenshotCounter = 1;
	private int interval = 2000;
	private boolean isActive = false;
	private boolean userDemandMode = false;
	private String folderPath = "src\\main\\java\\snapShots";

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public void takeScreenshot() {

		try {

			flashScreen();
			Robot robot = new Robot();

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

			Rectangle screenRect = new Rectangle(screenSize);

			BufferedImage screenshot = robot.createScreenCapture(screenRect);

			String fileName = "screenshot_" + screenshotCounter + ".png";

			File file = new File(folderPath + File.separator + fileName);
			ImageIO.write(screenshot, "png", file);
			System.out.println("Screen captured");
			System.out.println("Screenshot saved: " + file.getAbsolutePath());

			screenshotCounter++;

		} catch (Exception ex) {

			JOptionPane.showMessageDialog(null, "Failed to capture screen" + ex.getMessage(), "error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void flashScreen() {

		JWindow flashWindow = new JWindow();
		flashWindow.setBackground(new Color(255, 255, 255, 150));
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

	public ArrayList<File> getScreenShots() {

		File folder = new File(folderPath);
		ArrayList<File> screenshotFiles = new ArrayList<>();
		if (folder.exists() && folder.isDirectory()) {
			File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png")
					|| name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".jpeg"));
			if (files != null) {
				for (File file : files) {
					screenshotFiles.add(file);
				}
			}

		}
		return screenshotFiles;

	}

	public void resetScreenshotCounter() {
		screenshotCounter = 1;
	}

	public boolean isUserDemandMode() {
		return userDemandMode;
	}

	public void setUserDemandMode(boolean userDemandMode) {
		this.userDemandMode = userDemandMode;
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

}
