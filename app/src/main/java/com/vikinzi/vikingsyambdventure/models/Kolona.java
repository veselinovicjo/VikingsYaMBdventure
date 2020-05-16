package com.vikinzi.vikingsyambdventure.models;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class Kolona {

    /*protected TextView one;
    protected TextView two;
    protected TextView three;
    protected TextView four;
    protected TextView five;
    protected TextView six;

    protected TextView max;
    protected TextView min;

    protected TextView kenta;
    protected TextView triling;
    protected TextView full;
    protected TextView poker;
    protected TextView yamb;*/

    public TextView[] kolona = new TextView[13];

    //test konstruktori
    public Kolona(){

    }


    //writers(setters)
    public void setKolona(TextView[] niz){
        kolona = niz;
    }

    protected boolean writeOnes(TextView[] kocke){
        int value, count = 0;
        if(kolona[0].getText().toString().equals(""))
            return false;
        else {
            int sum = 0;
            for (int i = 0; i < 6; i++) {
                value = Integer.parseInt(kocke[i].getText().toString());
                if (kocke[i].isSelected() && value == 1) {
                    count++;
                    sum += value;
                }
            }
            if (count <= 5) {
                kolona[0].setText(sum);
                return true;
            }
            return false;
        }
    }
    protected boolean writeTwos(TextView[] kocke){
        int value, count = 0;
        if(kolona[1].getText().toString().equals(""))
            return false;
        else {
            int sum = 0;
            for(int i = 0; i < 6; i++){
                value = Integer.parseInt( kocke[i].getText().toString() );
                if( kocke[i].isSelected() && value == 1 ) {
                    count++;
                    sum += value;
                }
            }
            if (count <= 5) {
                kolona[1].setText(sum);
                return true;
            }
            return false;
        }
    }
    protected boolean writeThrees(TextView[] kocke){
        int value, count = 0;
        if(kolona[2].getText().toString().equals(""))
            return false;
        else {
            int sum = 0;
            for(int i = 0; i < 6; i++){
                value = Integer.parseInt( kocke[i].getText().toString() );
                if( kocke[i].isSelected() && value == 1 ) {
                    count++;
                    sum += value;
                }
            }
            if (count <= 5) {
                kolona[2].setText(sum);
                return true;
            }
            return false;
        }
    }
    protected boolean writeFours(TextView[] kocke){
        int value, count = 0;
        if(kolona[3].getText().toString().equals(""))
            return false;
        else {
            int sum = 0;
            for(int i = 0; i < 6; i++){
                value = Integer.parseInt( kocke[i].getText().toString() );
                if( kocke[i].isSelected() && value == 1 ) {
                    count++;
                    sum += value;
                }
            }
            if (count <= 5) {
                kolona[3].setText(sum);
                return true;
            }
            return false;
        }
    }
    protected boolean writeFives(TextView[] kocke){
        int value, count = 0;
        if(kolona[4].getText().toString().equals(""))
            return false;
        else {
            int sum = 0;
            for(int i = 0; i < 6; i++){
                value = Integer.parseInt( kocke[i].getText().toString() );
                if( kocke[i].isSelected() && value == 1 ) {
                    count++;
                    sum += value;
                }
            }
            if (count <= 5) {
                kolona[4].setText(sum);
                return true;
            }
            return false;
        }
    }
    protected boolean writeSixes(TextView[] kocke){
        int value, count = 0;
        if(kolona[5].getText().toString().equals(""))
            return false;
        else {
            int sum = 0;
            for(int i = 0; i < 6; i++){
                value = Integer.parseInt( kocke[i].getText().toString() );
                if( kocke[i].isSelected() && value == 1 ) {
                    count++;
                    sum += value;
                }
            }
            if (count <= 5) {
                kolona[5].setText(sum);
                return true;
            }
            return false;
        }
    }
    protected boolean writeMax(TextView[] kocke){
        int value, count = 0;
        if(kolona[6].getText().toString().equals(""))
            return false;
        else {
            int sum = 0;
            for(int i = 0; i < 6; i++){
                value = Integer.parseInt( kocke[i].getText().toString() );
                if( kocke[i].isSelected() ) {
                    count++;
                    sum += value;
                }
            }
            if (count == 5) {
                kolona[6].setText(sum);
                return true;
            }
            return false;
        }
    }
    protected boolean writeMin(TextView[] kocke){
        int value, count = 0;
        if(kolona[7].getText().toString().equals(""))
            return false;
        else {
            int sum = 0;
            for(int i = 0; i < 6; i++){
                value = Integer.parseInt( kocke[i].getText().toString() );
                if( kocke[i].isSelected() ) {
                    count++;
                    sum += value;
                }
            }
            if (count == 5) {
                kolona[7].setText(sum);
                return true;
            }
            return false;
        }
    }


    //geters
    public int getOnes(){
       return Integer.parseInt( kolona[0].getText().toString() );
    }
    public int getTwos(){
       return Integer.parseInt( kolona[1].getText().toString() );
    }
    public int getThrees(){
       return Integer.parseInt( kolona[2].getText().toString() );
    }
    public int getFours(){
       return Integer.parseInt( kolona[3].getText().toString() );
    }
    public int getFives(){
       return Integer.parseInt( kolona[4].getText().toString() );
    }
    public int getSixes(){
       return Integer.parseInt( kolona[5].getText().toString() );
    }
    public int getMax(){
        return Integer.parseInt( kolona[6].getText().toString() );
    }
    public int getMin(){
        return Integer.parseInt( kolona[7].getText().toString() );
    }


    //prototipi


    public boolean upisi(int i, ImageView[] kockice, int[] arrayDiceValue){
        int sum = 0;
        if(!kolona[i].getText().toString().equals(""))
            return false;
        //1-6 polja
        if(i == 0 || i == 1 || i == 2 || i == 3 || i ==4 || i == 5){
            for(int j = 0; j < 6; j++) {
                if (kockice[j].isSelected() && arrayDiceValue[j]==i+1){
                    sum+=arrayDiceValue[j];
                }
            }
            kolona[i].setText(sum + "");
            return true;
        }
        //max/min polja
        return false;
    }

}
