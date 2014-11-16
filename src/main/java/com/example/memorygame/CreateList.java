package com.example.memorygame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateList {
	
	public List<Picture> createList() throws IOException {
		List<Picture> list = new ArrayList<Picture>();
		for(int br=1; br<=Main.BROJ_SLIKA; br++){
			Picture picture = new Picture(br);
			list.add(picture);
        }
		return list;
	}

}
