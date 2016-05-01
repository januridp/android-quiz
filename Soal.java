package com.example.kuisfisika;

public class Soal {
    private String soal;
    private String pil_a;
    private String pil_b;
    private String pil_c;
    private int jwban;
    private int gambar;

    public Soal() {
        super();
    }

    public String getSoal() {
        return soal;
    }

    public void setSoal(String soal) {
        this.soal = soal;
    }

    public String getPil_a() {
        return pil_a;
    }

    public void setPil_a(String pil_a) {
        this.pil_a = pil_a;
    }

    public String getPil_b() {
        return pil_b;
    }

    public void setPil_b(String pil_b) {
        this.pil_b = pil_b;
    }

    public String getPil_c() {
        return pil_c;
    }

    public void setPil_c(String pil_c) {
        this.pil_c = pil_c;
    }

    public int getJwban() {
        return jwban;
    }

    public void setJwban(int jwban) {
        this.jwban = jwban;
    }

    public int getGambar() {
        return gambar;
    }

    public void setGambar(int gambar) {
        this.gambar = gambar;
    }

}