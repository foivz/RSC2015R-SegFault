package com.example.tinoba.liveball;

import android.graphics.Bitmap;

/**
 * Created by tinoba on 21.11.2015..
 */
public class Instagram {
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getSlika() {
        return slika;
    }

    public void setSlika(Bitmap slika) {
        this.slika = slika;
    }

    private String text;
    private Bitmap slika;

    public Instagram(String text, Bitmap slika){
        this.text = text;
        this.slika = slika;
    }
}
