package main;

import gui.Frame;

import javax.swing.ImageIcon;

public class Picture {
	
	public static int br = 1;
	private int id=br;
	private ImageIcon icon = new ImageIcon(Frame.class.getResource(br + ".jpg"));
	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}
	public ImageIcon getIcon() {
		return icon;
	}
	public int getBr(){
		return br;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
}
