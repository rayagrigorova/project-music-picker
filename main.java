package Project_01_2021_v2;

import java.io.IOException;

import javax.swing.JFrame;

public class main {
		public static void main(String[] args) {
			// TODO Auto-generated method stub	
			AlbumChooser a = null;
			
			try {
				a = new AlbumChooser();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			JFrame frame = new JFrame("Album Chooser");
			frame.setSize(1200, 400);
			frame.setResizable(false);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(a);

			frame.setVisible(true);
			System.out.println("Albums total:" + a.getAlbumsCount());	
		}
}
