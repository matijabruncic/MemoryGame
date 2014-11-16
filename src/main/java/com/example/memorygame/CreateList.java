package com.example.memorygame;

import com.example.memorygame.gui.PictureLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateList {
	
	public List<Picture> createList() throws IOException {
		return PictureLoader.loadAllPictures();
	}

}
