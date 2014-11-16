package com.example.memorygame.gui;

import com.example.memorygame.Picture;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mats990 on 11/16/14.
 */
public class PictureLoader {

    private PictureLoader(){}
    private static final int PICTURES_SIZE = 27;

    public static int getPicturesSize() {
        return PICTURES_SIZE;
    }

    public static Picture loadPicture(int id) throws IOException {
        return new Picture(id, loadPicture(id + ".jpg"));
    }

    public static ImageIcon loadPicture(String filename) throws IOException {
        return new ImageIcon(ImageIO.read(new ClassPathResource(filename).getInputStream()));
    }

    public static List<Picture> loadAllPictures() throws IOException {
        List<Picture> list = new ArrayList<Picture>();
        for(int br=1; br<= PICTURES_SIZE; br++){
            Picture picture = PictureLoader.loadPicture(br);
            list.add(picture);
        }
        return list;
    }
}
