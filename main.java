package Project_01_2021_v2;

import javax.swing.JFrame;

public class main {
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			
			AlbumChooser a = new AlbumChooser();
			//Create frame 
			JFrame frame = new JFrame("Album Chooser");
			frame.setSize(800, 400);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(a);

			frame.setVisible(true);
			System.out.println("Albums total:" + a.getAlbumsCount());

			while(true) {
				AlbumChooser.shuffleVector();
	            try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

}
