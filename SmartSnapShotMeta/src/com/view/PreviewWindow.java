//package com.view;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Component;
//import java.awt.Desktop;
//import java.awt.Dimension;
//import java.awt.Font;
//import java.awt.Image;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.imageio.ImageIO;
//import javax.swing.BorderFactory;
//import javax.swing.Box;
//import javax.swing.BoxLayout;
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JCheckBox;
//import javax.swing.JFileChooser;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.SwingConstants;
//
//import com.itextpdf.text.Document;
//import com.itextpdf.text.pdf.PdfWriter;
//
//public class PreviewWindow extends JFrame {
//
//    private JPanel imagePanel;
//    private List<File>  selectedFiles ;
//    private ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
//    
//    private JButton selectAllButton = new JButton("Select All");
//    
//    public PreviewWindow(ArrayList<File> screenshots) {
//    	
//        setTitle("Preview Window");
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
//        setSize(1000, 600);
//        setLocationRelativeTo(null);
//
//        imagePanel = new JPanel();
//        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS)); 
//        imagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
//
//        JScrollPane scrollPane = new JScrollPane(imagePanel);
//        add(scrollPane, BorderLayout.CENTER);
//
//        loadImages(screenshots);  
//    }
//    
//       public void refresh(ArrayList<File> screenshots) {
//    	 loadImages(screenshots);
//    }
//    
//    private void loadImages(ArrayList<File> screenshots) {
//    	imagePanel.removeAll();
//    	if(screenshots.isEmpty()) {
//    		JOptionPane.showMessageDialog(this, "No images found!", "Info", JOptionPane.INFORMATION_MESSAGE);
//    	}
//    	else {
//    		for(File file: screenshots) {
//    			addImageToPanel(file);
//    		}
//    	}
//    }
//
//    private void addImageToPanel(File file) {
//        try {
//            BufferedImage bufferedImage = ImageIO.read(file);
//            if (bufferedImage == null) {
//                System.out.println("Failed to load image: " + file.getAbsolutePath());
//                return;
//            }
//
//            int windowWidth = getWidth() - 40; 
//            int scaledHeight = (bufferedImage.getHeight() * windowWidth) / bufferedImage.getWidth();
//            Image scaledImage = bufferedImage.getScaledInstance(windowWidth, scaledHeight, Image.SCALE_SMOOTH);
//            ImageIcon icon = new ImageIcon(scaledImage);
//            
//            JLabel imageLabel = new JLabel(icon);
//            imageLabel.setIcon(new ImageIcon(scaledImage));
//
//            JCheckBox checkBox = new JCheckBox(file.getName());
//            checkBox.setFont(new Font("Arial", Font.BOLD, 14));
//            checkBox.setHorizontalAlignment(SwingConstants.CENTER);
//            checkBox.setPreferredSize(new Dimension(windowWidth, 30));
//
//            JPanel imageContainer = new JPanel();
//            imageContainer.setLayout(new BorderLayout());
//            imageContainer.setBorder(BorderFactory.createCompoundBorder(
//                    BorderFactory.createLineBorder(Color.GRAY, 1),
//                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
//            ));
//            
//            imageContainer.setBackground(new Color(240, 248, 255)); //light blue
//
//            imageContainer.add(imageLabel, BorderLayout.CENTER);
//            imageContainer.add(checkBox, BorderLayout.SOUTH);
//            
//            selectedFiles = new ArrayList<>() ;
//
//            imageLabel.addMouseListener(new MouseAdapter() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    checkBox.setSelected(!checkBox.isSelected());
//                    if (checkBox.isSelected()) {
//                        selectedFiles.add(file);
//                    } else {
//                        selectedFiles.remove(file);
//                    }
//                }
//            });
//
//            checkBox.addActionListener(e -> {
//                if (checkBox.isSelected()) {
//                    selectedFiles.add(file);
//                } else {
//                    selectedFiles.remove(file);
//                }
//            });
//
//            imagePanel.add(imageContainer);
//            imagePanel.add(Box.createVerticalStrut(15)); //to add spacing between the images
//            
//            JButton exportButton = new JButton("Export-To-PDF");
//            // adding action listener to export button
//            
//            exportButton.addActionListener(e ->{
//            	if(selectedFiles.isEmpty()) {
//            		JOptionPane.showMessageDialog(null, "No images selected!");
//            		return;
//            	}
//            	
//            	JFileChooser fileChooser = new JFileChooser();
//            	fileChooser.setDialogTitle("Save PDF");
//            	fileChooser.setSelectedFile(new File("fileName.pdf"));
//            	int userSelection = fileChooser.showSaveDialog(null);
//            	
//            	if(userSelection == JFileChooser.APPROVE_OPTION) {
//            		File pdfFile = fileChooser.getSelectedFile();
//            		
//            		try {
//            			Document document = new Document();
//            			PdfWriter.getInstance(document,new FileOutputStream(pdfFile));
//            			document.open();
//            			for(File imageFile : selectedFiles) {
//            				com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(imageFile.getAbsolutePath());
//            				image.scaleToFit(500,500);
//            				document.add(image);
//            			}
//            			document.close();
//            			JOptionPane.showMessageDialog(this, "PDF exported succesfully!");
//            			
//            			// opening the pdf
//            			
//            			if(Desktop.isDesktopSupported()) {
//            				Desktop.getDesktop().browse(pdfFile.toURI());
//            			} else {
//            				JOptionPane.showMessageDialog(this, "Desktop operations are not supported on this system");
//            			}
//            		} catch(Exception ex) {
//            			JOptionPane.showMessageDialog(this, "Error while exporting to PDF!"+ex.getMessage());
//            		}
//            	}
//            });
//            
//            //JButton selectAllButton = new JButton("Select All");
//            // adding action listener to select all button
//            selectAllButton.addActionListener(e -> {
//            	selectedFiles.clear();
//            	Component[] components = imagePanel.getComponents();
//            		for(Component component : components) {
//            			if(component instanceof JPanel) {
//            				JPanel imageContainer1 = (JPanel) component;
//            				for(Component subComponent : imageContainer1.getComponents()) {
//            					if(subComponent instanceof JCheckBox) {
//            						JCheckBox checkBox1 = (JCheckBox) subComponent;
//            						//if(!checkBox1.isSelected()) {
//            							 checkBox1.setSelected(true);
//            						//}
//            						if(checkBox1.getClientProperty("file") instanceof File) {
//            							File imageFile = (File) checkBox1.getClientProperty("file");
//            								if(!selectedFiles.contains(imageFile)) {
//            									selectedFiles.add(imageFile);
//            								}
//            						}
//            					}
//            				}
//            			}
//            		}
//            	});
//            
//            // adding export and selectAll buttons to frame
//            add(exportButton,BorderLayout.SOUTH);
//            add(selectAllButton,BorderLayout.NORTH);
//            
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//}
package com.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

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

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

public class PreviewWindow extends JFrame {

    private JPanel imagePanel;
    private List<File> selectedFiles = new ArrayList<>();
    private ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
    private JButton selectAllButton = new JButton("Select All");

    public PreviewWindow(ArrayList<File> screenshots) {

        setTitle("Preview Window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
        imagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(imagePanel);
        add(scrollPane, BorderLayout.CENTER);

        loadImages(screenshots);

        // Add the selectAllButton to the NORTH
        add(selectAllButton, BorderLayout.NORTH);

        // Add action listener for selectAllButton
        selectAllButton.addActionListener(e -> selectAllImages(screenshots));
    }

    public void refresh(ArrayList<File> screenshots) {
        loadImages(screenshots);
    }

    private void loadImages(ArrayList<File> screenshots) {
        imagePanel.removeAll();
        checkBoxes.clear();
        selectedFiles.clear();

        if (screenshots.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No images found!", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (File file : screenshots) {
                addImageToPanel(file);
            }
        }
        imagePanel.revalidate();
        imagePanel.repaint();
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

            imageContainer.setBackground(new Color(240, 248, 255));

            imageContainer.add(imageLabel, BorderLayout.CENTER);
            imageContainer.add(checkBox, BorderLayout.SOUTH);

            // Add to checkbox list for "Select All" functionality
            checkBoxes.add(checkBox);

            // Add or remove files when checkbox state changes
            
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
            imagePanel.add(Box.createVerticalStrut(15)); // Add spacing between images

            // Add export button to SOUTH
            JButton exportButton = new JButton("Export-To-PDF");
            exportButton.addActionListener(e -> exportToPDF());

            add(exportButton, BorderLayout.SOUTH);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void selectAllImages(ArrayList<File> screenshots) {

    	selectedFiles.clear();
        for (File file : screenshots) {
            if (!selectedFiles.contains(file)) {
                selectedFiles.add(file);
            }
        }

    	for(int i = 0; i<checkBoxes.size();i++) {
    		
    		JCheckBox checkBox = checkBoxes.get(i);
    		System.out.println(checkBox.getText());
    		if(!checkBox.isSelected()) {
    			checkBox.setSelected(true);
    		}
    		
    		
    	}
    }

    private void exportToPDF() {
        if (selectedFiles.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No images selected!");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save PDF");
        fileChooser.setSelectedFile(new File("fileName.pdf"));
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File pdfFile = fileChooser.getSelectedFile();

            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
                document.open();
                for (File imageFile : selectedFiles) {
                    com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(imageFile.getAbsolutePath());
                    System.out.println(imageFile.getAbsolutePath());
                    image.scaleToFit(500, 500);
                    document.add(image);
                }
                document.close();
                JOptionPane.showMessageDialog(this, "PDF exported successfully!");

                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(pdfFile.toURI());
                } else {
                    JOptionPane.showMessageDialog(this, "Desktop operations are not supported on this system");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error while exporting to PDF: " + ex.getMessage());
            }
        }
    }
}
