package com.SmartSnapShot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class ScreenShotPreviewer extends JFrame {
 
	 private JPanel imagePanel;
	 private ArrayList<File> selectedFiles = new ArrayList<>();

	    public ScreenShotPreviewer() {
	        setTitle("Image Viewer");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(1000, 600);
	        setLocationRelativeTo(null);
	       
	        imagePanel = new JPanel();
	        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS)); // Vertical layout for images

	        JScrollPane scrollPane = new JScrollPane(imagePanel);
	        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	        add(scrollPane, BorderLayout.CENTER);

	        JPanel buttonPanel = new JPanel(new FlowLayout());
	        JButton loadButton = new JButton("Load Images");
	        loadButton.addActionListener(e -> loadImages());
	        buttonPanel.add(loadButton);
	        add(buttonPanel, BorderLayout.NORTH);
	    }

	    private void loadImages() {
	        JFileChooser fileChooser = new JFileChooser();
	        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	        int returnValue = fileChooser.showOpenDialog(this);

	        if (returnValue == JFileChooser.APPROVE_OPTION) {
	            File folder = fileChooser.getSelectedFile();
	            System.out.println("Selected folder: " + folder.getAbsolutePath());
	            File[] files = folder.listFiles((dir, name) -> 
	                name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".jpeg") || name.toLowerCase().endsWith(".png")
	            );

	            if (files != null && files.length > 0) {
	                System.out.println("Found " + files.length + " image(s).");
	                imagePanel.removeAll();
	                selectedFiles.clear();
	                for (File file : files) {
	                    addImageToPanel(file);
	                }
	                imagePanel.revalidate();
	                imagePanel.repaint();
	            } else {
	                JOptionPane.showMessageDialog(this, "No images found in the selected folder.", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    }
        // add image panel
	    private void addImageToPanel(File file) {
	        try {
	            BufferedImage bufferedImage = ImageIO.read(file);
	            if (bufferedImage == null) {
	                System.out.println("Failed to load image: " + file.getAbsolutePath());
	                return;
	            }
	            // Scale the image to fit the full width of the window while maintaining aspect ratio
	            int windowWidth = getWidth();  // Leave some padding
	            int scaledHeight = (bufferedImage.getHeight() * windowWidth) / bufferedImage.getWidth();
	            Image scaledImage = bufferedImage.getScaledInstance(windowWidth, scaledHeight, Image.SCALE_SMOOTH);
	            ImageIcon icon = new ImageIcon(scaledImage);

	            JLabel imageLabel = new JLabel(icon);
	            // checkbox
	            JCheckBox checkBox = new JCheckBox(file.getName());
	            checkBox.setBackground(Color.LIGHT_GRAY);
	            checkBox.setOpaque(true);
	            //checkBox.setAlignmentX(Component.CENTER_ALIGNMENT);
	            checkBox.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	            checkBox.setFocusPainted(false);
	            
	            JPanel imageContainer = new JPanel();
	            imageContainer.setLayout(new BoxLayout(imageContainer, BoxLayout.Y_AXIS));
	            imageContainer.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY,1),BorderFactory.createEmptyBorder(10, 10, 10, 10))); // Add some padding
	            imageContainer.add(imageLabel);
	            imageContainer.add(Box.createVerticalStrut(10));
	            imageContainer.add(checkBox);

	            checkBox.addActionListener(e -> {
	                if (checkBox.isSelected()) {
	                    selectedFiles.add(file);
	                } else {
	                    selectedFiles.remove(file);
	                }
	            });

	            imagePanel.add(imageContainer);
	        } catch (Exception ex) {
	        }
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	        	ScreenShotPreviewer previewer = new ScreenShotPreviewer();
	            previewer.setVisible(true);
	        });
	    }
}
