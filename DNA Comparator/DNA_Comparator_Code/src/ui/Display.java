package ui;
import java.io.*;

import implement.GSA;
import implement.LCS;
import implement.LSA;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Display extends JFrame{
	
	private JButton compare = new JButton("Compare");
	private JButton reset = new JButton("Reset"); 
	private JButton proc = new JButton("Processing");
	private JButton help = new JButton("?");
	private JButton browse1;
	private JButton browse2;
	private JButton reverse;
	private JTextField txt2 = new JTextField(10);
	private JTextField txt3 = new JTextField(10);
	private JTextArea answer = new JTextArea(2, 10);
	private JTextArea txtar = new JTextArea(2, 10);
	JFileChooser chooser1 = new JFileChooser();
	JFileChooser chooser2 = new JFileChooser();
	FileNameExtensionFilter filter = new FileNameExtensionFilter("text files", "txt");
	Font font = new Font("Sans", Font.BOLD, 12);
	Font font1 = new Font("Serif", Font.BOLD, 12);
	Font font2 = new Font("Serif", Font.PLAIN, 16);
	JScrollPane scrollpane1 = new JScrollPane(txtar);
	JScrollPane scrollpane2 = new JScrollPane(answer);
	private JLabel lbl1 = new JLabel();
	private String action[] = {"Longest Common Subsequence", "Global Sequence Alignment", "Local Sequence Alignment"};
	private JComboBox cbox = new JComboBox<>(action);
	private JLabel lbl2 = new JLabel();
	private JLabel lbl3 = new JLabel();
	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JPanel panel3 = new JPanel();
	private JPanel panel4 = new JPanel();
	private JPanel board = new JPanel();
	private JFrame procframe = new JFrame();
	private JPanel procpanel = new JPanel();
	
	class ButtonListener1 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(txtar.getText().length()!=0)
			{
				txtar.setText(null);
			}
			if(answer.getText().length()!=0)
			{
				answer.setText(null);
			}
			
			if(txt2.getText().length()==0||txt3.getText().length()==0)
			{
				txtar.setText("Error!\n--one or more fields are empty--");
				answer.setText("Error!\n--one or more fields are empty--");
			}
			else
			{
				String dna1 = txt2.getText();
				String dna2 = txt3.getText();
				int opt = cbox.getSelectedIndex();
				
				if(opt==0)
				{
					LCS lcs = new LCS();
					int l1 = dna1.length()+1, l2 = dna2.length()+1, i, j;
					char ch1[] = dna1.toCharArray();
					int edtable[][] = new int[l1][l2];
					int dptable[][] = new int[l1][l2];
					char btrack[][] = new char[l1][l2];
					
					edtable = lcs.edtable(dna1, dna2);
					dptable = lcs.dptable(dna1, dna2);
					btrack = lcs.btrack(dna1, dna2);
					
					txtar.append("Score Table\n\n");
					for(i=0;i<l1;i++)
					{
						for(j=0;j<l2;j++)
						{
							txtar.append(dptable[i][j] + "  ");
						}
						txtar.append("\n");
					}
					txtar.append("\n\nEdit Table\n\n");
					for(i=0;i<l1;i++)
					{
						for(j=0;j<l2;j++)
						{
							txtar.append(edtable[i][j] + "  ");
						}
						txtar.append("\n");
					}
					txtar.append("\n\nBacktracking Table\n\n");
					for(i=0;i<l1;i++)
					{
						for(j=0;j<l2;j++)
						{
							txtar.append(btrack[i][j] + "  ");
						}
						txtar.append("\n");
					}
					answer.setText("Longest Common Subsequencce: " + lcs.PrintLCS(btrack, ch1, l1-1, l2-1));
				}
				
				else if(opt==1)
				{
					GSA gsa = new GSA();
					int l1 = dna1.length()+1, l2 = dna2.length()+1, i, j;
					char ch1[] = dna1.toCharArray();
					int edtable[][] = new int[l1][l2];
					int dptable[][] = new int[l1][l2];
					char btrack[][] = new char[l1][l2];
					
					edtable = gsa.edtable(dna1, dna2);
					dptable = gsa.dptable(dna1, dna2);
					btrack = gsa.btrack(dna1, dna2);
					
					txtar.append("Score Table\n\n");
					for(i=0;i<l1;i++)
					{
						for(j=0;j<l2;j++)
						{
							txtar.append(dptable[i][j] + "  ");
						}
						txtar.append("\n");
					}
					txtar.append("\n\nEdit Table\n\n");
					for(i=0;i<l1;i++)
					{
						for(j=0;j<l2;j++)
						{
							txtar.append(edtable[i][j] + "   ");
						}
						txtar.append("\n");
					}
					txtar.append("\n\nBacktracking Table\n\n");
					for(i=0;i<l1;i++)
					{
						for(j=0;j<l2;j++)
						{
							txtar.append(btrack[i][j] + "  ");
						}
						txtar.append("\n");
					}

					answer.setText("Global Sequence Alignment: " + gsa.PrintGSA(btrack, ch1, l1-1, l2-1));
				}
				
				else if(opt==2)
				{
					LSA lsa = new LSA();
					String out = "";
					int l1 = dna1.length()+1, l2 = dna2.length()+1, i, j;
					char ch1[] = dna1.toCharArray();
					int dptable[][] = new int[l1][l2];
					char btrack[][] = new char[l1][l2];
					int maxtable[][] = {};
					int max = 0;
					
					dptable = lsa.dptable(dna1, dna2);
					btrack = lsa.btrack(dna1, dna2);
					maxtable = lsa.maxtable(dptable, l1, l2); //to get the indices of max score occurrences
					int count = maxtable[0].length;
					String align[] = new String[count];
					
					if(maxtable[0][0]==0 && maxtable[1][0]==0)
					{
						align[0] = "";
						count=1;
					}
					
					else
					{
						for(i=0;i<count;i++)
						{
							//to print the best alignments
							lsa.lsa="";
							align[i] = lsa.PrintLSA(dptable, ch1, maxtable[0][i], maxtable[1][i]);
						}
					}
					
					txtar.append("Score Table\n\n");
					for(i=0;i<l1;i++)
					{
						for(j=0;j<l2;j++)
						{
							txtar.append(dptable[i][j] + "  ");
							if(dptable[i][j]>max)
							{
								max = dptable[i][j];
							}
						}
						txtar.append("\n");
					}
					txtar.append("\n\nBacktracking Table\n\n");
					for(i=0;i<l1;i++)
					{
						for(j=0;j<l2;j++)
						{
							txtar.append(btrack[i][j] + "  ");
						}
						txtar.append("\n");
					}
					
					for(i=0;i<count;i++)
					{
						out = out + align[i] + "\n"; //printing the best alignment;
					}
					
					answer.setText("Local Sequence Alignment: \n" + out + "\nBest Alignment Score: " + max);
				}
			}
		}
	}
	
	class ButtonListener2 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			cbox.setSelectedIndex(2);
			txt2.setText(null);
			txt3.setText(null);
			txtar.setText(null);
			answer.setText(null);
		}
	}
	
	class ButtonListener3 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			GroupLayout proclayout = new GroupLayout(procpanel);
			procpanel.setLayout(proclayout);
			
			procframe.setTitle("Processing");
			procframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			procframe.setResizable(false);
			procframe.setSize(300, 300);
			
			procpanel.setBackground(Color.WHITE);
			txtar.setEditable(false);
			txtar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			txtar.setCaretPosition(txtar.getDocument().getLength());
			txtar.setBackground(Color.lightGray);
			txtar.setForeground(Color.WHITE);
			txtar.setFont(font2);
			scrollpane1.setPreferredSize(new Dimension(200, 200));
			proclayout.setAutoCreateGaps(true);
			proclayout.setAutoCreateContainerGaps(true);
			proclayout.setHorizontalGroup(
					proclayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(proclayout.createSequentialGroup()
								.addComponent(scrollpane1))
					);
			proclayout.setVerticalGroup(
					proclayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(proclayout.createSequentialGroup()
							.addComponent(scrollpane1))
					);
			procframe.add(procpanel);
			procframe.setVisible(true);
		}
	}
	
	class ButtonListener4 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			final File f = new File(Display.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			String path = ""+f;
			path = path.replace("DNA_Comparator.jar", "");
			try {
				Runtime.getRuntime().exec("cmd.exe /C start AcroRD32 " + path + "About.pdf");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				try {
					Runtime.getRuntime().exec("evince " + path + "About.pdf");
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		}
	}
	
	class ButtonListener5 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			chooser1.setFileFilter(filter);
			chooser1.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			 int returnVal = chooser1.showOpenDialog(getParent());
			    if(returnVal == JFileChooser.APPROVE_OPTION)
			    {
			    	txt2.setText(null);
			    	BufferedReader reader1=null;
			    	String s1 = "";
					try {
						reader1 = new BufferedReader(new FileReader(chooser1.getSelectedFile().getCanonicalPath()));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						while((s1 = reader1.readLine())!=null)
						{
							StringTokenizer st = new StringTokenizer(s1, "");
							txt2.setText(txt2.getText() + st.nextToken());
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    }
		}
	}
	
	class ButtonListener6 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			chooser2.setFileFilter(filter);
			chooser2.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			 int returnVal = chooser2.showOpenDialog(getParent());
			    if(returnVal == JFileChooser.APPROVE_OPTION) 
			    {
			    	txt3.setText(null);
			    	BufferedReader reader2=null;
			    	String s2 = "";
					try {
						reader2 = new BufferedReader(new FileReader(chooser2.getSelectedFile().getCanonicalPath()));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						while((s2 = reader2.readLine())!=null)
						{
							StringTokenizer st = new StringTokenizer(s2, "");
							txt3.setText(txt3.getText() + st.nextToken());
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    }
		}
	}
	
	class ButtonListener7 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String temp=null;
			temp = txt2.getText();
			txt2.setText(txt3.getText());
			txt3.setText(temp);
		}
	}
	
	private ButtonListener1 bl1 = new ButtonListener1(); 
	private ButtonListener2 bl2 = new ButtonListener2(); 
	private ButtonListener3 bl3 = new ButtonListener3(); 
	private ButtonListener4 bl4 = new ButtonListener4(); 
	private ButtonListener5 bl5 = new ButtonListener5(); 
	private ButtonListener6 bl6 = new ButtonListener6(); 
	private ButtonListener7 bl7 = new ButtonListener7();
	
	public Display()
	{
		compare.addActionListener(bl1);
		reset.addActionListener(bl2);
		proc.addActionListener(bl3);
		help.addActionListener(bl4);
		final File f = new File(Display.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String path = ""+f;
		path = path.replace("DNA_Comparator.jar", "");
		browse1 = new JButton(new ImageIcon(((new ImageIcon(path + "images/open.png").getImage().getScaledInstance(20, 20,java.awt.Image.SCALE_SMOOTH)))));
		browse2 = new JButton(new ImageIcon(((new ImageIcon(path + "images/open.png").getImage().getScaledInstance(20, 20,java.awt.Image.SCALE_SMOOTH)))));
		reverse = new JButton(new ImageIcon(((new ImageIcon(path + "images/switchicon.png").getImage().getScaledInstance(20, 20,java.awt.Image.SCALE_SMOOTH)))));
		browse1.addActionListener(bl5);
		browse2.addActionListener(bl6);
		reverse.addActionListener(bl7);
		this.setIconImage(new ImageIcon(((new ImageIcon(path + "images/favicon.png").getImage().getScaledInstance(20, 20,java.awt.Image.SCALE_SMOOTH)))).getImage());
		cbox.setBackground(Color.WHITE);
		cbox.setSelectedIndex(2);
		cbox.setToolTipText("select the alignment type");
		lbl1.setText("Select Comparison Type: ");
		lbl2.setText("DNA Sequence 1: ");
		lbl2.setForeground(Color.WHITE);
		lbl3.setText("DNA Sequence 2: ");
		lbl3.setForeground(Color.WHITE);
		txt2.setToolTipText("first DNA string here");
		txt2.setFont(font);
		txt3.setToolTipText("second DNA string here");
		txt3.setFont(font);
		answer.setEditable(false);
		answer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		answer.setCaretPosition(txtar.getDocument().getLength());
		answer.setBackground(Color.WHITE);
		answer.setForeground(Color.RED);
		answer.setFont(font1);
		scrollpane2.setPreferredSize(new Dimension(400, 75));
		GroupLayout layoutmain = new GroupLayout(board);
		board.setLayout(layoutmain);
		board.setBackground(Color.BLUE);
		GroupLayout layout1 = new GroupLayout(panel1);
		panel1.setLayout(layout1);
		panel1.setBackground(Color.YELLOW);
		GroupLayout layout2 = new GroupLayout(panel2);
		panel2.setLayout(layout2);
		panel2.setBackground(Color.RED);
		GroupLayout layout3 = new GroupLayout(panel3);
		panel3.setLayout(layout3);
		panel3.setBackground(Color.RED);
		GroupLayout layout4 = new GroupLayout(panel4);
		panel4.setLayout(layout4);
		panel4.setBackground(Color.GREEN);
		
		layoutmain.setAutoCreateGaps(true);
		layoutmain.setAutoCreateContainerGaps(true);
		layout1.setAutoCreateGaps(true);
		layout1.setAutoCreateContainerGaps(true);
		layout2.setAutoCreateGaps(true);
		layout2.setAutoCreateContainerGaps(true);
		layout3.setAutoCreateGaps(true);
		layout3.setAutoCreateContainerGaps(true);
		layout4.setAutoCreateGaps(true);
		layout4.setAutoCreateContainerGaps(true);
		
		layoutmain.setHorizontalGroup(
				   layoutmain.createSequentialGroup()
				      .addGroup(layoutmain.createParallelGroup(GroupLayout.Alignment.CENTER)
				           .addComponent(panel1)
				           .addComponent(panel2)
				           .addComponent(panel3)
				           .addComponent(panel4))
				           );
		layoutmain.setVerticalGroup(
				   layoutmain.createSequentialGroup()
				      .addGroup(layoutmain.createParallelGroup(GroupLayout.Alignment.BASELINE)
				           .addComponent(panel1))
			          .addGroup(layoutmain.createParallelGroup(GroupLayout.Alignment.BASELINE)
				           .addComponent(panel2))
			          .addGroup(layoutmain.createParallelGroup(GroupLayout.Alignment.BASELINE)
				           .addComponent(panel3))
			          .addGroup(layoutmain.createParallelGroup(GroupLayout.Alignment.BASELINE)
				           .addComponent(panel4))
				           );
		
		layout1.setHorizontalGroup(
				layout1.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addGroup(layout1.createSequentialGroup()
						.addGroup(layout1.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(lbl1, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(30, 30, 30)
						.addGroup(layout1.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(cbox, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE)))
				);
		layout1.setVerticalGroup(
				layout1.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addGroup(layout1.createSequentialGroup()
						.addGroup(layout1.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(lbl1)
								.addComponent(cbox)))
				);
		
		layout2.setHorizontalGroup(
				layout2.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout2.createSequentialGroup()
						.addGroup(layout2.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(lbl2, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(30, 30, 30)
						.addGroup(layout2.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(txt2, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
						.addGap(30 , 30, 30)
						.addGroup(layout2.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(browse1, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)))
				);
		layout2.setVerticalGroup(
				layout2.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout2.createSequentialGroup()
						.addGroup(layout2.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(lbl2)
								.addComponent(txt2)
								.addComponent(browse1, GroupLayout.Alignment.CENTER)))
				);
		
		layout3.setHorizontalGroup(
				layout3.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout3.createSequentialGroup()
						.addGroup(layout3.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(lbl3, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(30, 30, 30)
						.addGroup(layout3.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(txt3, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
						.addGap(30 , 30, 30)
						.addGroup(layout3.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(browse2, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)))
				);
		layout3.setVerticalGroup(
				layout3.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout3.createSequentialGroup()
						.addGroup(layout3.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(lbl3)
								.addComponent(txt3)
								.addComponent(browse2, GroupLayout.Alignment.CENTER)))
				);
		
		layout4.setHorizontalGroup(
				layout4.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout4.createSequentialGroup()
						.addGroup(layout4.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(reset, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
						.addGap(50, 50, 50)
						.addGroup(layout4.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(compare, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout4.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(reverse))
						.addGroup(layout4.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(proc, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
						.addGap(50 , 50, 50)
						.addGroup(layout4.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(help, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)))
				.addGroup(layout4.createSequentialGroup()
						.addComponent(scrollpane2))
				);
		layout4.setVerticalGroup(
				layout4.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout4.createSequentialGroup()
						.addGroup(layout4.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(reset)
								.addComponent(compare)
								.addComponent(reverse)
								.addComponent(proc)
								.addComponent(help))
						.addGroup(layout4.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(scrollpane2)))
				);
		add(board);
	}
}