package com.example.memorygame;

import javax.swing.ImageIcon;
import java.io.IOException;

public class Picture {
	
	private int id;
	private ImageIcon icon;

    public Picture(int id, ImageIcon icon) throws IOException {
        this.id = id;
        this.icon = icon;
    }

    public ImageIcon getIcon() {
		return icon;
	}
	public int getId() {
		return id;
	}
}
