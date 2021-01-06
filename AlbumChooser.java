package Project_01_2021_v1;

import java.awt.*;
import javax.swing.*;

//Extends JPanel so we don't need a JPanel object
public class AlbumChooser extends JPanel {
	JLabel purposeLabel, heavinessLabel, energyLabel, moodLabel, noteLabel;
	JButton giveAlbum, surpriseMe;
	JCheckBox parentCheckBox;
	JSlider heavinessSlider, energySlider, moodSlider;
	//Specifies constraints for components that are laid out using the GridBagLayout.
	GridBagConstraints gbc = new GridBagConstraints();
	
	public AlbumChooser() {
		// A GridBagLayout places components in a grid of rows and columns.
		//Not all rows necessarily have the same height
		//and not all columns necessarily have the same width.
		setLayout(new GridBagLayout());
		
		// Insets add a blank space 
		gbc.insets = new Insets(5, 5, 20, 5);
		//gridx and gridy set the coordinates of the component 
		gbc.gridx = 1;
		gbc.gridy = 0;
		purposeLabel = new JLabel("This program chooses an appropriate album depending on what you want to listen to."); 
		add(purposeLabel, gbc);
		
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 3;
		heavinessLabel= new JLabel("Choose the heaviness (1 - Lady Gaga, Elvis Presley, 10 - barely listenable, grindcore)");
		add(heavinessLabel, gbc);
		heavinessSlider = new JSlider(JSlider.HORIZONTAL,
                1, 10, 5);
		gbc.gridx = 1;
		gbc.gridy = 4;
		add(heavinessSlider, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 5;
		energyLabel= new JLabel("Choose the energy (1 - calm, 2 - neurtral, 3 - energetic)");
		add(energyLabel, gbc);
		energySlider = new JSlider(JSlider.HORIZONTAL,
                1, 3, 2);
		gbc.gridx = 1;
		gbc.gridy = 6;
		add(energySlider, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 7;
		moodLabel= new JLabel("Choose the mood (1 - sad, 2- neutral, 3- happy)");
		add(moodLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 8;
		JSlider moodSlider = new JSlider(JSlider.HORIZONTAL,
                1, 3, 2);
		add(moodSlider, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 9;
	    parentCheckBox = new JCheckBox("Parent-friendly");  
		parentCheckBox.setBounds(100, 100, 50,50); 
		add(parentCheckBox, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 9;
		giveAlbum = new JButton("Give me an album");  
	    giveAlbum.setBounds(100, 100, 50,50);  
	    add(giveAlbum, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 9;
		surpriseMe = new JButton("Surprise me");  
	    surpriseMe.setBounds(100, 100, 50,50);  
	    add(surpriseMe, gbc);
	    
		gbc.gridx = 0;
		gbc.gridy = 10;
		//The label will take up 2 spaces on the x axis
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
	    noteLabel = new JLabel("*The albums chosen are based on personal preference.");
		add(noteLabel, gbc);
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		AlbumChooser a = new AlbumChooser();
		//Create frame 
		JFrame frame = new JFrame("Album Chooser");
		frame.setSize(600, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(a);
		frame.pack();
		frame.setVisible(true);

	}

}
