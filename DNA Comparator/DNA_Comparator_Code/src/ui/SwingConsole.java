package ui;
import java.awt.FlowLayout;

import javax.swing.*;

public class SwingConsole{
	
	public static void run(final JFrame f, final int width, final int height)
	{
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				f.setTitle("DNA Sequence Comparator");
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setSize(width, height);
				f.setVisible(true);
				f.setResizable(false);
				f.pack();
			}
		});
	}
}