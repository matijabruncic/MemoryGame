package com.example.memorygame.results;

public class Score implements Comparable<Score> {

    private int score;
    private String ime;

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getIme() {
        return ime;
    }


    @Override
    public int compareTo(Score o) {
        if (this.getScore() < o.getScore()) {
            return -1;
        } else if (this.getScore() > o.getScore()) {
            return 1;
        } else {
            return 0;
        }
    }
}
