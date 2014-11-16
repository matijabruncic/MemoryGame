package main;

import java.util.ArrayList;
import java.util.List;

public class CreateList {
	
	public List<Picture> createList(){
		Picture.br=1;
		List<Picture> lista = new ArrayList<Picture>();
		for(int i=1; i<=Main.BROJ_SLIKA; i++){
			Picture slika = new Picture();
			Picture.br++;
			lista.add(slika);
		}
		return lista;
	}

}
