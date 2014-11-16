package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import results.Score;

public class Popup {
	private static long time;
	
	public Popup() {
		time = (Frame.stop - Frame.start - Frame.ukupnoPauzirano)/1000;
		System.out.println("Stop : \t\t" + Frame.stop);
		System.out.println("Start : \t" + Frame.start);
		System.out.println("Pauzirano : \t" + Frame.ukupnoPauzirano);
		System.out.println("Vrijeme : \t" + time);
		final JFrame okvir = new JFrame("Kraj igre");
		Container c = okvir.getContentPane();
		c.setLayout(new FlowLayout());
		JPanel whole = new JPanel(new BorderLayout(20, 20));
		JPanel up= new JPanel(new GridLayout(2, 1));
		JLabel text = new JLabel("Igru ste zavrsili za " + time + " sekundi");
		text.setHorizontalAlignment(JLabel.CENTER);
		JLabel text2 = new JLabel("Upisite svoje ime:");
		text2.setHorizontalAlignment(JLabel.CENTER);
		up.add(text);
		up.add(text2);
		
		JPanel down = new JPanel(new FlowLayout());
		final JTextField ime = new JTextField();
		ime.setColumns(20);
		down.add(ime);
		
		
		ActionListener imeHandler = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text;
				File dat = new File(System.getProperty("user.home") + "/.memorygame/results.txt");
				File path = new File(System.getProperty("user.home") + "/.memorygame/");
				ArrayList<Score> popis = new ArrayList<Score>();
				
				
				try {
					BufferedReader read;
					read = new BufferedReader(new FileReader(dat));
					while ((text = read.readLine()) != null){
						Score tempScore = new Score();
						String name;
						String points;
						name = new String(text.substring(text.indexOf(' ')+1, text.indexOf('\t')));
						points = new String(text.substring(text.lastIndexOf("\t")+1, text.length()));
						tempScore.setScore(Integer.parseInt(points));
						tempScore.setIme(name);
						popis.add(tempScore);
					}
					read.close();
				} catch (FileNotFoundException e3) {
					e3.printStackTrace();
				} catch (NumberFormatException e2) {
					e2.printStackTrace();
				} catch (IOException e4) {
					e4.printStackTrace();
				}
				
				Score tempScore = new Score();
				if(popis.size()!=0){
					if((int) time < popis.get(0).getScore()){
						//JOptionPane.showMessageDialog(null, "Napravili ste najbolje vrijeme do sad!!");
						JOptionPane.showMessageDialog(null, "Napravili ste najbolje vrijeme do sad!!", "Najbolji rezultat", JOptionPane.WARNING_MESSAGE, new ImageIcon(Frame.class.getResource("star.gif")));
					}
				}
				tempScore.setScore((int)time);
				tempScore.setIme(ime.getText());
				popis.add(tempScore);
				Collections.sort(popis);
				
				try {
					if(!path.exists()){
						path.mkdir();
					}
					if(!dat.exists()){
						dat.createNewFile();
					}
					PrintWriter out = new PrintWriter(dat);
					int br=1;
					for(Score trenutni: popis){
						out.println("#" + br + " " + trenutni.getIme() + "\t\t" + trenutni.getScore());
						br++;
					}
					out.close();
				} catch (FileNotFoundException e1) {
					System.err.println("Nije moguce otvoriti fajl");
					e1.printStackTrace();
				} catch (IOException e2) {
					System.err.println("Nije moguce kreirati fajl");
					e2.printStackTrace();
				}
				final JFrame confirm = new JFrame();
				JLabel label = new JLabel("Uspjesno ste dodani na rang listu");
				label.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));
				JButton ok = new JButton("OK");
				JPanel donjiPanel = new JPanel(new GridBagLayout());
				GridBagConstraints constraints = new GridBagConstraints();
			    KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false);
				
				ActionListener okHandler = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						confirm.dispose();
						okvir.dispose();
					}
				};
				ok.addActionListener(okHandler);
			    ok.registerKeyboardAction(okHandler, keystroke, JComponent.WHEN_FOCUSED);
				
				constraints.gridwidth = 2;
				constraints.gridx =3;
				donjiPanel.add(ok, constraints);
				
				confirm.getContentPane().setLayout(new BorderLayout());
				confirm.getContentPane().add(label, BorderLayout.NORTH);
				confirm.getContentPane().add(donjiPanel, BorderLayout.SOUTH);
				
				confirm.pack();
				confirm.setLocation((int) (Frame.glavniOkvir.getX() + (Frame.glavniOkvir.getWidth()/2) - confirm.getWidth()/2), 
						(int) (Frame.glavniOkvir.getY() + Frame.glavniOkvir.getHeight()/2) - confirm.getHeight()/2);
				confirm.setResizable(false);
				confirm.setVisible(true);
				
			}
		};
		ime.addActionListener(imeHandler);
		
		whole.add(down);
		whole.add(up, BorderLayout.NORTH);
		c.add(whole);
		okvir.pack();
		okvir.setLocation((int) (Frame.glavniOkvir.getX() + (Frame.glavniOkvir.getWidth()/2) - okvir.getWidth()/2), 
				(int) (Frame.glavniOkvir.getY() + Frame.glavniOkvir.getHeight()/2) - okvir.getHeight()/2);
		okvir.setResizable(false);
		okvir.setVisible(true);
	}
}
