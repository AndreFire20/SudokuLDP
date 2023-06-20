/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuFX3;

/**
 *
 * @author Utilizador
 */
public class Jogador {
    private long tempo;
    
    public Jogador() {
        this.tempo = 0;
    }
    
    public long getTempo() {
        return tempo;
    }

    public void setTempo(long tempo) {
        this.tempo = tempo;
    }

    public String getTempoFormatado() {
        long segundos = tempo / 1000;
        long minutos = segundos / 60;
        segundos = segundos % 60;
        long horas = minutos / 60;
        minutos = minutos % 60;
        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }
}