package com.example.memorygame.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.example.memorygame.CreateList;
import com.example.memorygame.MemoryGame;
import com.example.memorygame.Picture;
import org.springframework.core.io.ClassPathResource;


public class Frame {

    public static int ROWS = 6;
    public static int COLS = 9;
    private JToggleButton[] button = new JToggleButton[ROWS * COLS];
    final List<Picture> slike = new ArrayList<Picture>();
    private static int brojac = 0;
    ImageIcon pattern;
    private static boolean play = true;
    static long ukupnoPauzirano = 0;
    private long playTime, pauseTime;
    static long start = 0;
    static long stop = 0;
    public static JFrame glavniOkvir;
    public static long vrijeme_spavanja = 1000;


    public Frame() throws IOException {
        pattern = new ImageIcon(new ClassPathResource("mem.png").getFile().getAbsolutePath());
        glavniOkvir = new JFrame("Memory Game");
        glavniOkvir.setUndecorated(true);
        final Container c = glavniOkvir.getContentPane();
        JPanel panelZaGumbice = new JPanel();
        panelZaGumbice.setLayout(new GridLayout(ROWS, COLS, 10, 10));
        panelZaGumbice.setBackground(Color.DARK_GRAY);
        panelZaGumbice.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        CreateList op = new CreateList();
        final JPanel box[] = new JPanel[ROWS * COLS];

        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("Igra");
        JMenuItem nova = new JMenuItem("Nova igra", new ImageIcon(new ClassPathResource("new.png").getFile().getAbsolutePath()));
        JMenuItem kraj = new JMenuItem("Kraj", new ImageIcon(new ClassPathResource("close.png").getFile().getAbsolutePath()));
        JMenu controls = new JMenu("Kontrole");
        final JMenuItem playPause = new JMenuItem("Pauziraj", new ImageIcon(new ClassPathResource("pause.png").getFile().getAbsolutePath()));
        JMenu postavke = new JMenu("Postavke");
        JMenuItem podesavanja = new JMenuItem("Podesavanja", new ImageIcon(new ClassPathResource("pref.png").getFile().getAbsolutePath()));
        JMenu vidi = new JMenu("Vidi");
        JMenuItem rezultati = new JMenuItem("Rezultati", new ImageIcon(new ClassPathResource("results.png").getFile().getAbsolutePath()));

        vidi.add(rezultati);
        postavke.add(podesavanja);
        file.add(nova);
        file.add(kraj);
        controls.add(playPause);
        menu.add(file);
        menu.add(controls);
        menu.add(vidi);
        menu.add(postavke);

        List<Picture> list = op.createList();
        slike.addAll(list);
        slike.addAll(list);
        for (int i = 0; i < 5; i++) {
            Collections.shuffle(slike);
        }

        int br = 1;
        for (int i = 1; i <= ROWS; i++) {
            for (int j = 1; j <= COLS; j++) {
                box[br - 1] = new JPanel();
                button[br - 1] = new JToggleButton(pattern);
                button[br - 1].setSelectedIcon(slike.get(br - 1).getIcon());
                box[br - 1].setLayout(new BorderLayout());
                box[br - 1].add(button[br - 1], BorderLayout.CENTER);
                panelZaGumbice.add(box[br - 1]);
                br++;

            }
        }

        start = System.currentTimeMillis();

        Handler handler = new Handler();
        for (int i = 0; i < ROWS * COLS; i++) {
            button[i].addActionListener(handler);
        }

        ActionListener novaHandler = new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Collections.shuffle(slike);
                int br = 1;
                for (int i = 1; i <= ROWS; i++) {
                    for (int j = 1; j <= COLS; j++) {
                        button[br - 1].setIcon(pattern);
                        button[br - 1].setSelectedIcon(slike.get(br - 1).getIcon());
                        button[br - 1].setEnabled(true);
                        button[br - 1].setSelected(false);
                        br++;
                    }
                }
                start = System.currentTimeMillis();
                ukupnoPauzirano = 0;
                stop = 0;
            }
        };
        nova.addActionListener(novaHandler);

        ActionListener krajHandler = new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        };
        kraj.addActionListener(krajHandler);

        ActionListener rezultatiHandler = new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFrame rezFrame = new JFrame("Rezultati");
                JPanel whole = new JPanel(new BorderLayout());
                JTable tabela = new JTable(10, 2);
                tabela.getColumnModel().getColumn(0).setHeaderValue("Ime");
                tabela.getColumnModel().getColumn(1).setHeaderValue("Vrijeme");
                tabela.getTableHeader().setBackground(Color.DARK_GRAY);
                tabela.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.BOLD | Font.ITALIC, 12));
                tabela.getTableHeader().setForeground(Color.WHITE);
                tabela.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
                tabela.setRowHeight(25);


                String text;
                File dat = new File(System.getProperty("user.home") + "/.memorygame/results.txt");
                BufferedReader read = null;

                if (dat.exists()) {
                    try {
                        read = new BufferedReader(new FileReader(dat));
                        int br = 1;
                        while ((text = read.readLine()) != null && br <= 10) {
                            String name, points;
                            name = new String(text.substring(text.indexOf(' ') + 1, text.indexOf('\t')));
                            points = new String(text.substring(text.lastIndexOf("\t") + 1, text.length()));
                            tabela.setValueAt(name, br - 1, 0);
                            tabela.setValueAt(points, br - 1, 1);
                            br++;
                            System.out.println("Brojac je : \t" + br);
                        }
                    } catch (FileNotFoundException e3) {
                        e3.printStackTrace();
                    } catch (NumberFormatException e2) {
                        e2.printStackTrace();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    } finally {
                        try {
                            read.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                whole.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.DARK_GRAY));

                whole.add(tabela, BorderLayout.SOUTH);
                whole.add(tabela.getTableHeader(), BorderLayout.NORTH);
                rezFrame.getContentPane().add(whole);
                rezFrame.pack();
                rezFrame.setLocation((int) (Frame.glavniOkvir.getX() + (Frame.glavniOkvir.getWidth() / 2) - rezFrame.getWidth() / 2),
                        (int) (Frame.glavniOkvir.getY() + Frame.glavniOkvir.getHeight() / 2) - rezFrame.getHeight() / 2);
                rezFrame.setVisible(true);
            }
        };
        rezultati.addActionListener(rezultatiHandler);

        ActionListener playPauseHandler = new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (play) {
                    pauseTime = System.currentTimeMillis();
                    play = false;
                    playPause.setText("Pokreni");
                    try {
                        playPause.setIcon(new ImageIcon(new ClassPathResource("play.png").getFile().getAbsolutePath()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    for (int i = 1; i <= ROWS * COLS; i++) {
                        button[i - 1].setVisible(false);
                    }
                } else {
                    playTime = System.currentTimeMillis();
                    play = true;
                    ukupnoPauzirano += playTime - pauseTime;
                    for (int i = 1; i <= ROWS * COLS; i++) {
                        button[i - 1].setVisible(true);
                    }
                    playPause.setText("Pauziraj");
                    try {
                        playPause.setIcon(new ImageIcon(new ClassPathResource("pause.png").getFile().getAbsolutePath()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        };
        playPause.addActionListener(playPauseHandler);

        ActionListener podesavanjaHandler = new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFrame properties = new JFrame("Postavke");
                JPanel cijeli = new JPanel(new GridBagLayout());
                cijeli.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                GridBagConstraints constr = new GridBagConstraints();
                JLabel vrijemeCekanja = new JLabel("Vrijeme prikazivanja 2 razlicite sličice");
                JLabel empty = new JLabel("       ");
                final JSlider slide;

                int pocVrijednost = 0;
                try {
                    pocVrijednost = (int) (vrijeme_spavanja / 1000);
                } catch (IllegalArgumentException e) {
                    pocVrijednost = 1;
                }
                slide = new JSlider(0, 7, pocVrijednost);


                slide.setBorder(BorderFactory.createTitledBorder("vrijeme u sekundama"));
                slide.setMajorTickSpacing(1);
                slide.setPaintTicks(true);
                slide.setPaintLabels(true);


                constr.gridwidth = 3;
                constr.gridx = 0;
                constr.gridy = 0;
                cijeli.add(vrijemeCekanja, constr);

                constr.gridwidth = 1;
                constr.gridx = 3;
                constr.gridy = 0;
                cijeli.add(empty, constr);

                constr.gridwidth = 2;
                constr.gridx = 4;
                constr.gridy = 0;
                cijeli.add(slide, constr);

                ChangeListener sliderHandler = new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        vrijeme_spavanja = slide.getValue() * 1000;
                    }
                };
                slide.addChangeListener(sliderHandler);

                properties.getContentPane().add(cijeli);
                properties.pack();
                properties.setLocation((int) (glavniOkvir.getX() + (glavniOkvir.getWidth() / 2) - properties.getWidth() / 2),
                        (int) (glavniOkvir.getY() + glavniOkvir.getHeight() / 2) - properties.getHeight() / 2);
                properties.setVisible(true);
            }
        };
        podesavanja.addActionListener(podesavanjaHandler);

        c.setLayout(new BorderLayout());
        c.add(panelZaGumbice, BorderLayout.CENTER);
        glavniOkvir.setJMenuBar(menu);
        glavniOkvir.setVisible(true);
        glavniOkvir.setExtendedState(JFrame.MAXIMIZED_BOTH);
        glavniOkvir.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


    private class Handler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (brojac < 2)
                brojac++;
            if (brojac == 2) {
                for (int i = 0; i < ROWS * COLS; i++) {
                    if (button[i].isSelected()) {
                        for (int j = i + 1; j < ROWS * COLS; j++) {
                            if (button[j].isSelected()) {
                            /*
							 * ako slike imaju isti ID onda ih disableam
							 */
                                if (slike.get(j).getId() == slike.get(i).getId()) {
                                    button[i].setEnabled(false);
                                    button[j].setEnabled(false);
                                    button[i].setSelected(false);
                                    button[j].setSelected(false);
                                }
							/*
							 * ako nemaju onda brojac vracam na 0 i deselektiram ih.
							 */
                                else {
                                    brojac = 0;
                                    try {
                                        Thread.sleep(vrijeme_spavanja);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    button[i].setSelected(false);
                                    button[j].setSelected(false);
                                }
                            }
                        }
                    }
                }
            }
            int zavrsenih = 0;
            for (int i = 0; i < ROWS * COLS; i++) {
                if (!button[i].isEnabled()) {
                    zavrsenih++;
                }
            }
            if (zavrsenih == PictureLoader.getPicturesSize() * 2) {
                stop = System.currentTimeMillis();
                new Popup();
            }
        }
    }
}