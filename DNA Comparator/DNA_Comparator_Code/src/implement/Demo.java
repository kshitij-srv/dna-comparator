package implement;

import java.io.*;
import java.awt.FlowLayout;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import static ui.SwingConsole.*;
import ui.Display;

public class Demo {

	public static void main(String args[]) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		run(new Display() ,500,275);
	}
}
