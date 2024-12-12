package com.view;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class PreviewWindow extends JFrame {

    private JPanel imagePanel;
    private ArrayList<File>  selectedFiles ;
   

    public PreviewWindow(ArrayList<File> screenshots) {
    	
        setTitle("Image Viewer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setSize(1000, 600);
        setLocationRelativeTo(null);

        imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS)); 
        
        imagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 

        JScrollPane scrollPane = new JScrollPane(imagePanel);
        add(scrollPane, BorderLayout.CENTER);

        loadImages(screenshots);  
    }
    
    public void refresh(ArrayList<File> screenshots) {
    	 loadImages(screenshots);
    }
    
    private void loadImages(ArrayList<File> screenshots) {
    	imagePanel.removeAll();
    	if(screenshots.isEmpty()) {
    		JOptionPane.showMessageDialog(this, "No images found", "Info", JOptionPane.INFORMATION_MESSAGE);
    	}
    	else {
    		for(File file: screenshots) {
    			addImageToPanel(file);
    		}
    	}
        
    }

    private void addImageToPanel(File file) {
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            if (bufferedImage == null) {
                System.out.println("Failed to load image: " + file.getAbsolutePath());
                return;
            }

            int windowWidth = getWidth() - 40; 
            int scaledHeight = (bufferedImage.getHeight() * windowWidth) / bufferedImage.getWidth();
            Image scaledImage = bufferedImage.getScaledInstance(windowWidth, scaledHeight, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImage);

            JLabel imageLabel = new JLabel(icon);

            
            JCheckBox checkBox = new JCheckBox(file.getName());
            checkBox.setFont(new Font("Arial", Font.BOLD, 14));
            checkBox.setHorizontalAlignment(SwingConstants.CENTER);
            checkBox.setPreferredSize(new Dimension(windowWidth, 30));

            
            JPanel imageContainer = new JPanel();
            imageContainer.setLayout(new BorderLayout());
            
            imageContainer.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY, 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            imageContainer.setBackground(new Color(240, 248, 255)); //light blue

            imageContainer.add(imageLabel, BorderLayout.CENTER);
            imageContainer.add(checkBox, BorderLayout.SOUTH);
            
            selectedFiles = new ArrayList<>() ;

           
            imageLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    checkBox.setSelected(!checkBox.isSelected());
                    if (checkBox.isSelected()) {
                        selectedFiles.add(file);
                    } else {
                        selectedFiles.remove(file);
                    }
                }
            });

            checkBox.addActionListener(e -> {
                if (checkBox.isSelected()) {
                    selectedFiles.add(file);
                } else {
                    selectedFiles.remove(file);
                }
            });

            imagePanel.add(imageContainer);
            imagePanel.add(Box.createVerticalStrut(15)); //to add spacing between the images
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
