package Project_01_2021_v2;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;

//Extends JPanel so we don't need a JPanel object
public class AlbumChooser extends JPanel {
	private static JLabel purposeLabel, heavinessLabel, energyLabel, moodLabel, noteLabel, resultLabel;
	private static JButton giveAlbum, surpriseMe;
	private static JCheckBox parentCheckBox;
	private static JSlider heavinessSlider, energySlider, moodSlider;
	
	//Specifies constraints for components that are laid out using the GridBagLayout.
	GridBagConstraints gbc = new GridBagConstraints();

	//Heaviness, mood and energy chosen by the user 
	private static int chosenHeaviness, chosenMood, chosenEnergy;
	private static boolean parentFriendly;
	
	private static Album[] allAlbums;
	private static int albumsCount;
	
	//Most appropriate album based on the heaviness, mood and energy chosen by the user. 
	private static Album bestAlbum;
	
	private Image backgroundImage;
	
	public AlbumChooser() throws IOException {
		setAlbumsCount();
		setAllAlbums();
		
		parentFriendly = false;

		// A GridBagLayout places components in a grid of rows and columns.
		//Not all rows necessarily have the same height
		//and not all columns necessarily have the same width.
		setLayout(new GridBagLayout());

		setButtons();
		setCheckBox();
		setSliders();
		setLabels();
		 
		System.out.println("Your background image is at " + getClass().getResource("bgr.jpg"));
	    backgroundImage = ImageIO.read(getClass().getResource("bgr.jpg"));

		chosenHeaviness = heavinessSlider.getValue();
		chosenEnergy = energySlider.getValue();
		chosenMood = moodSlider.getValue();
	}

	public int getAlbumsCount() {
		return albumsCount;
	}
	
	public void setAlbumsCount() {
		Scanner sc;
		sc = new Scanner (AlbumChooser.class.getResourceAsStream("Albums.txt"));
		int ctr = 0;
		while(sc.hasNext()) {
			//Every album in the file has 5 lines 
			for(int i = 0; i < 5; i++) {
				sc.nextLine();
			}
			ctr++;
		}
		albumsCount = ctr;
		sc.close();
		return;
	}
	
	//We read the albums from the file 
	public void setAllAlbums() {
		allAlbums = new Album [albumsCount];
		Scanner sc = new Scanner(AlbumChooser.class.getResourceAsStream("Albums.txt"));
		//For each album (Each having 5 lines)
		for(int i = 0; i < albumsCount; i++) {
			allAlbums[i] = new Album();
			//For each line
			for(int j = 0; j < 5 && sc.hasNext(); j++) {
				switch (j) {
				case 0: allAlbums[i].name = sc.nextLine(); break;
				case 1: allAlbums[i].artist = sc.nextLine(); break;
				case 2: allAlbums[i].heaviness = Integer.parseInt(sc.nextLine()); break;
				case 3: allAlbums[i].energy = Integer.parseInt(sc.nextLine()); break;
				case 4: allAlbums[i].mood = Integer.parseInt(sc.nextLine()); break;
				}

			}
		}
		sc.close();
	}
	
	public void setButtons() {
		gbc.gridx = 1;
		gbc.gridy = 9;
		giveAlbum = new JButton("Give me an album");  
	    giveAlbum.setBounds(100, 100, 50, 50);  
	    giveAlbum.addActionListener(new GiveAlbumAction());
	    giveAlbum.setFont(new Font("Serif", Font.PLAIN, 20));
	    add(giveAlbum, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 9;
		surpriseMe = new JButton("Surprise me");  
	    surpriseMe.setBounds(100, 100, 50,50); 
	    surpriseMe.setFont(new Font("Serif", Font.PLAIN, 20));
	    surpriseMe.addActionListener(new SurpriseAction());
	    add(surpriseMe, gbc);
	}
	
	public void setLabels() {
		// Insets add a blank space 
		gbc.insets = new Insets(5, 5, 20, 5);
		//gridx and gridy set the coordinates of the component 
		gbc.gridx = 1;
		gbc.gridy = 0;
		purposeLabel = new JLabel("This program chooses an appropriate album depending on what you want to listen to."); 
		purposeLabel.setFont(new Font("Serif", Font.PLAIN, 22));
		add(purposeLabel, gbc);
		
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 3;
		heavinessLabel= new JLabel("Choose the heaviness (1 - Lady Gaga, Elvis Presley, 10 - barely listenable, grindcore)");
		heavinessLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		add(heavinessLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 5;
		energyLabel= new JLabel("Choose the energy (1 - calm, 2 - neurtral, 3 - energetic)");
		energyLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		add(energyLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 7;
		moodLabel= new JLabel("Choose the mood (1 - sad, 2- neutral, 3- happy)");
		moodLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		add(moodLabel, gbc);
		  
		gbc.gridx = 0;
		gbc.gridy = 10;
		//The label will take up 2 spaces on the x axis
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
	    noteLabel = new JLabel("*The albums chosen are based on personal preference.");
		noteLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		add(noteLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 11;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		resultLabel = new JLabel("");
		resultLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		add(resultLabel, gbc);
		
	}
	
	public void setSliders() {
		gbc.gridx = 1;
		gbc.gridy = 4;
		heavinessSlider = new JSlider(JSlider.HORIZONTAL,
                1, 10, 5);
		//Sets the width and height for the slider
		Dimension d = heavinessSlider.getPreferredSize();
		heavinessSlider.setPreferredSize(new Dimension(d.width+200,d.height));
		//Makes the area behind the slider transparent 
		heavinessSlider.setOpaque(false);
		
		add(heavinessSlider, gbc);
		heavinessSlider.addChangeListener(new HeavinessSliderChange());
		
		gbc.gridx = 1;
		gbc.gridy = 6;
		energySlider = new JSlider(JSlider.HORIZONTAL,
                1, 3, 2);
		energySlider.setPreferredSize(new Dimension(d.width+200,d.height));
		energySlider.setOpaque(false);
		energySlider.addChangeListener(new EnergySliderChange());
		add(energySlider, gbc);
		
		gbc.insets = new Insets(5, 5, 20, 5);
		gbc.gridx = 1;
		gbc.gridy = 8;
		moodSlider = new JSlider(JSlider.HORIZONTAL,
                1, 3, 2);
		moodSlider.setPreferredSize(new Dimension(d.width+200,d.height));
		moodSlider.setOpaque(false);
		moodSlider.addChangeListener(new MoodSliderChange());
		add(moodSlider, gbc);
	}
	
	public void setCheckBox() {
		gbc.gridx = 0;
		gbc.gridy = 9;
	    parentCheckBox = new JCheckBox("Parent-friendly");  
	    parentCheckBox.setFont(new Font("Serif", Font.PLAIN, 20));
		//Makes the area behind the check box transparent 
	    parentCheckBox.setOpaque(false);
		parentCheckBox.setBounds(100, 100, 50,50); 
		add(parentCheckBox, gbc);
		parentCheckBox.addActionListener(new ParentAction());
	}
	
	public static class GiveAlbumAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//If the parent-friendly check box is chosen, the heaviness should be between 5 and 0
			if(parentFriendly) {
				chosenHeaviness = Math.max(chosenHeaviness - 5, 0);
			}
			//For each album
			for(int i = 0; i <  albumsCount; i++) {
				//We initially set the bestAlbum to the first album
				if(i == 0) {
					bestAlbum = allAlbums[i];
				}
				//We compare the characteristics with the ones of the best one 
				else if(isBetterThanBestAlbum(i)) {
					bestAlbum = allAlbums[i];
					//If we find a perfect album, we stop searching
					if(compareToChosen(bestAlbum.heaviness, bestAlbum.energy, bestAlbum.mood) == 0) {
						break;
					}
				}
			}
			shuffleVector();
			resultLabel.setText("The best album for you is " + bestAlbum.name + " by " + bestAlbum.artist);
		}
		
	}

	
	public static class SurpriseAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//We choose a random album from all albums 
			Random r = new Random();
			int ind = r.nextInt(albumsCount);
			//In case the parent-friendly option is chosen, we search until we find an album with heaviness <= 5
			if(parentFriendly) {
				while(allAlbums[ind].heaviness > 5) {
					ind = r.nextInt(albumsCount);
				}
			}
			resultLabel.setText("A surprise album for you is  " + allAlbums[ind].name + " by " + allAlbums[ind].artist);
		}
		
	}
	
	public class ParentAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if( parentCheckBox.isSelected()) {
				parentFriendly = true;
			}
			else {
				parentFriendly = false;
			}
			
		}
		
	}
	
	
	public class HeavinessSliderChange implements ChangeListener{
		@Override
		public void stateChanged(ChangeEvent e) {
			chosenHeaviness = heavinessSlider.getValue();
		}
	}
	public class EnergySliderChange implements ChangeListener{
		@Override
		public void stateChanged(ChangeEvent e) {
			chosenEnergy = energySlider.getValue();
		}
	}	
	public class MoodSliderChange implements ChangeListener{
		@Override	
		public void stateChanged(ChangeEvent e) {
			chosenMood = moodSlider.getValue();
		}
	}	

	// Compares an album to the album chosen by the user. For example, the album chosen by the user is 111(heaviness, energy, mood).
	//The album we compare it to is 632. The difference between them is (6 - 1) + (3 - 1) + (2 - 1) = 8. The smaller the difference, the better.
	public static int compareToChosen(int heaviness, int energy, int mood) {
		return Math.abs(chosenHeaviness - heaviness ) + Math.abs(chosenEnergy - energy) + Math.abs(chosenMood - mood);    
	}
	
	//Returns if the album at index i is better than the best album by comparing the differences. The smaller the difference, the better.
	public static boolean isBetterThanBestAlbum(int i) {
		return(compareToChosen(allAlbums[i].heaviness, allAlbums[i].energy, allAlbums[i].mood) < 
		compareToChosen(bestAlbum.heaviness, bestAlbum.energy, bestAlbum.mood));
	}	
	
	public static void shuffleVector() {
		Random rand = new Random();
		//For each album in the vector 
		for(int i = 0; i < albumsCount; i++) {
			//We choose a random number between 0 and albumsCount and swap the current album with the album at the random position.
			int ind = rand.nextInt(albumsCount);
			Album temp = allAlbums[ind];
			allAlbums[ind] = allAlbums[i];
			allAlbums[i] = temp;
			
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw the background image.
		g.drawImage(backgroundImage, 0, 0, this);
	}

}
