package com.example.bilgikutusu;

public class SoruModel {

    private String soru,secenek1,secenek2,secenek3,secenek4,dogrucvp;
    private int setNo;

    public SoruModel(){

        //firebase



    }

    public SoruModel(String soru, String secenek1, String secenek2, String secenek3, String secenek4, String dogrucvp, int setNo) {
        this.soru = soru;
        this.secenek1 = secenek1;
        this.secenek2 = secenek2;
        this.secenek3 = secenek3;
        this.secenek4 = secenek4;
        this.dogrucvp = dogrucvp;
        this.setNo = setNo;
    }


    public String getSoru() {
        return soru;
    }

    public void setSoru(String soru) {
        this.soru = soru;
    }

    public String getSecenek1() {
        return secenek1;
    }

    public void setSecenek1(String secenek1) {
        this.secenek1 = secenek1;
    }

    public String getSecenek2() {
        return secenek2;
    }

    public void setSecenek2(String secenek2) {
        this.secenek2 = secenek2;
    }

    public String getSecenek3() {
        return secenek3;
    }

    public void setSecenek3(String secenek3) {
        this.secenek3 = secenek3;
    }

    public String getSecenek4() {
        return secenek4;
    }

    public void setSecenek4(String secenek4) {
        this.secenek4 = secenek4;
    }

    public String getDogrucvp() {
        return dogrucvp;
    }

    public void setDogrucvp(String dogrucvp) {
        this.dogrucvp = dogrucvp;
    }

    public int getSetNo() {
        return setNo;
    }

    public void setSetNo(int setNo) {
        this.setNo = setNo;
    }
}
