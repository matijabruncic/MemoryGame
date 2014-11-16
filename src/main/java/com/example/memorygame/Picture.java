package com.example.memorygame;

import org.springframework.core.io.ClassPathResource;

import javax.swing.ImageIcon;
import java.io.IOException;

public class Picture {
	
	private int id;
	private ImageIcon icon;

    public Picture(int id) throws IOException {
        this.id = id;
        this.icon = new ImageIcon(new ClassPathResource(id + ".jpg").getFile().getAbsolutePath());
    }

    public ImageIcon getIcon() {
		return icon;
	}
	public int getId() {
		return id;
	}
}
