package Project_01_2021_v2;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

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
	private static int albumsCount, chosenHeaviness, chosenMood, chosenEnergy;
	private static boolean parentFriendly;
	private static Album[] allAlbums;
	//Most appropriate album given the heaviness, mood and energy
	private static Album bestAlbum;
	
	public AlbumChooser() {
		setAlbumsCount();
		setAllAlbums();
		parentFriendly = false;

		// A GridBagLayout places components in a grid of rows and columns.
		//Not all rows necessarily have the same height
		//and not all columns necessarily have the same width.
		setLayout(new GridBagLayout());
		setCheckBox();
		setSliders();
		setButtons();
		setLabels();
		
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
			for(int i = 0; i < 5; i++) {
				sc.nextLine();
			}
			ctr++;
		}
		albumsCount = ctr;
		sc.close();
		return;
	}
	
	public void setAllAlbums() {
		allAlbums = new Album [albumsCount];
		Scanner sc = new Scanner(AlbumChooser.class.getResourceAsStream("Albums.txt"));
		//For each album
		for(int i = 0; i < albumsCount; i++) {
			allAlbums[i] = new Album();
			//For each row
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
	    add(giveAlbum, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 9;
		surpriseMe = new JButton("Surprise me");  
	    surpriseMe.setBounds(100, 100, 50,50);  
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
		add(purposeLabel, gbc);
		
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 3;
		heavinessLabel= new JLabel("Choose the heaviness (1 - Lady Gaga, Elvis Presley, 10 - barely listenable, grindcore)");
		add(heavinessLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 5;
		energyLabel= new JLabel("Choose the energy (1 - calm, 2 - neurtral, 3 - energetic)");
		add(energyLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 7;
		moodLabel= new JLabel("Choose the mood (1 - sad, 2- neutral, 3- happy)");
		add(moodLabel, gbc);
		  
		gbc.gridx = 0;
		gbc.gridy = 10;
		//The label will take up 2 spaces on the x axis
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
	    noteLabel = new JLabel("*The albums chosen are based on personal preference.");
		add(noteLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 11;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		resultLabel = new JLabel("");
		add(resultLabel, gbc);
		
	}
	
	public void setSliders() {
		gbc.gridx = 1;
		gbc.gridy = 4;
		heavinessSlider = new JSlider(JSlider.HORIZONTAL,
                1, 10, 5);
		add(heavinessSlider, gbc);
		heavinessSlider.addChangeListener(new HeavinessSliderChange());
		
		gbc.gridx = 1;
		gbc.gridy = 6;
		energySlider = new JSlider(JSlider.HORIZONTAL,
                1, 3, 2);
		energySlider.addChangeListener(new EnergySliderChange());
		add(energySlider, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 8;
		moodSlider = new JSlider(JSlider.HORIZONTAL,
                1, 3, 2);
		moodSlider.addChangeListener(new MoodSliderChange());
		add(moodSlider, gbc);
	}
	
	public void setCheckBox() {
		gbc.gridx = 0;
		gbc.gridy = 9;
	    parentCheckBox = new JCheckBox("Parent-friendly");  
		parentCheckBox.setBounds(100, 100, 50,50); 
		add(parentCheckBox, gbc);
		parentCheckBox.addActionListener(new ParentAction());
	}
	
	public static class GiveAlbumAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//If the parent-friendly checkbox is chosen, the heaviness should be between 5 and 0
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
			resultLabel.setText("The best album for you is " + bestAlbum.name + " by " + bestAlbum.artist);
		}
		
	}

	
	public static class SurpriseAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//We choose a random album from all albums 
			Random r = new Random();
			int ind = r.nextInt(albumsCount);
			if(parentFriendly) {
				do {
					ind = r.nextInt(albumsCount);
				}
				//In case the parent-friendly option is chosen
				while(allAlbums[ind].heaviness > 5);
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
			System.out.println("Heaviness is at " + chosenHeaviness);
			
		}
	}
	public class EnergySliderChange implements ChangeListener{
		@Override
		public void stateChanged(ChangeEvent e) {
			chosenEnergy = energySlider.getValue();
			System.out.println("Energy is at " + chosenEnergy);
			
		}
	}	
	public class MoodSliderChange implements ChangeListener{
		@Override	
		public void stateChanged(ChangeEvent e) {
			chosenMood = moodSlider.getValue();
			System.out.println("Mood is at " + chosenMood);
			
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
		System.out.println("shufflin");
		Random rand = new Random();
		//For each album in the vector 
		for(int i = 0; i < albumsCount; i++) {
			int ind = rand.nextInt(albumsCount);
			Album temp = allAlbums[ind];
			allAlbums[ind] = allAlbums[i];
			allAlbums[i] = temp;
			
		}
	}

}
