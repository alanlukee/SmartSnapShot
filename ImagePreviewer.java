package com.SmartSnapShot;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.*;

public class ImagePreviewer extends JFrame{
	
//	JFrame frame = new JFrame("Image Previewer");
	private JPanel imagePanel;

	public ImagePreviewer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);
		setLayout(new BorderLayout());
		
		imagePanel = new JPanel();
		imagePanel.setLayout(new GridLayout(0,3,10,10));
		
		JScrollPane scrollPane = new JScrollPane(imagePanel);
		add(scrollPane,BorderLayout.CENTER);
		
		JButton loadButton = new JButton("Load Images");
		loadButton.addActionListener(e -> loadImages());
		add(loadButton,BorderLayout.CENTER);
		
	}
	
	//  method to load the images
	
	private void loadImages() {
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnValue = fileChooser.showOpenDialog(this);
		if( returnValue == JFileChooser.APPROVE_OPTION) {
			File folder = fileChooser.getSelectedFile();
			File[] files = folder.listFiles((dir,name)->
				name.toLowerCase().endsWith(".jpg")|| name.toLowerCase().endsWith(".png"));
			
			if(files != null) {
				imagePanel.removeAll();
				for(File file : files) {
					addImageToPanel(file);
				}
				imagePanel.revalidate();
				imagePanel.repaint();
			}
		}
	}
	
	// add image panel
	
	private void addImageToPanel(File file) {
		try {
			ImageIcon icon = new ImageIcon(file.getAbsolutePath());
			JLabel imageLabel = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(100, 100,Image.SCALE_SMOOTH)));
			JCheckBox checkBox = new JCheckBox(file.getName());
			JPanel imageContainer = new JPanel(new BorderLayout());
			
			imageContainer.add(imageLabel,BorderLayout.CENTER);
			imageContainer.add(checkBox,BorderLayout.SOUTH);
			
			imageLabel.addMouseListener(new MouseAdapter() {
				private boolean zoomed = false;
				public void mouseClicked(MouseEvent e) {
					if(!zoomed) {
						imageLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH)));
						zoomed = true;
					} else {
						imageLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
						zoomed = false;
					}
					imagePanel.revalidate();
					imagePanel.repaint();
				}
			});
			
			imagePanel.add(imageContainer);
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			ImagePreviewer previewer = new ImagePreviewer();
			previewer.setVisible(true);
		});
	}
}
