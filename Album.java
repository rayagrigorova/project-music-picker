package Project_01_2021_v2;

import java.util.Scanner;

public class Album {
	String name, artist;
	int heaviness, mood, energy;
	
	Album(){
		name = "";
		artist = "";
		heaviness = 0;
		mood = 0;
		energy = 0;
	}
	
	public void print() {
		System.out.println(name + " " + artist + " " + heaviness + " " + mood + " " + energy + " ");
	}
}
