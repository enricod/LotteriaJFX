/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lotteriajfx.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author enricodonelli
 */
public class Estrazione {

    private List<Integer> numeriEstraibiliOrig = new ArrayList<>();
    private List<Integer> numeriEstraibili = new ArrayList<>();
    private List<Integer> numeriEstratti = new ArrayList<>();
   
    private List<String> elencoPremi = new ArrayList<>();

    // private boolean mescolati = false;
    public Estrazione() throws IOException {
            leggiFileDati();
            leggiFilePremi();
    }

    private void leggiFileDati() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.home") + "/lotteria-numeri.txt"))) {
            String line = br.readLine();
            while (line != null) {
                numeriEstraibili.add(Integer.parseInt(line));
                line = br.readLine();
            }
        }
    }

    private void leggiFilePremi() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.home") + "/lotteria-premi.txt"))) {
            String line = br.readLine();
            while (line != null ) {
                if (line.isEmpty()) {
                    break;
                }
                elencoPremi.add(line);
                line = br.readLine();
            }
        }
    }

    public String getUltimoPremio() {
        if (numeriEstratti.isEmpty()) {
            return "";
        }

        return elencoPremi.get(numeriEstratti.size() - 1);
    }

//	public boolean isMescolati() {
//		return mescolati;
//	}
    public List<Integer> getNumeriEstraibili() {
        return numeriEstraibili;
    }

    public boolean isStarted() {
        return !numeriEstratti.isEmpty();
    }

    public boolean isFinished() {
        if (getContNumeriEstratti()== elencoPremi.size()) {
            return true;
        }
        return numeriEstraibili.size() > 0 && (getContNumeriEstratti() == getNumeriDaEstrarre() );
    }

    public void setNumeriEstraibili(List<Integer> numeriEstraibili) {
        this.numeriEstraibili = numeriEstraibili;
        numeriEstraibiliOrig.addAll(numeriEstraibili);
    }

    public List<Integer> getNumeriEstratti() {
        return numeriEstratti;
    }

    public void addNumeriEstratti(Integer numeriEstratti) {
        this.numeriEstratti.add(numeriEstratti);
    }

    public List<String> getElencoPremi() {
        return elencoPremi;
    }

    public int numeroPremi() {
        return elencoPremi.size();
    }
    public int getContNumeriEstratti() {
        return numeriEstratti.size();
    }

    public int getNumeriDaEstrarre() {
        return elencoPremi.size();
    }

    public void setElencoPremi(List<String> premi) {
        this.elencoPremi.addAll(premi);
    }

    public void avviaEstrazione() {
        // if (mescolati) {
        doEstrai();
//		}else {
//			doMescola();
//		}
//		mescolati = !mescolati;

    }

    private void doMescola() {
        Collections.shuffle(numeriEstraibili, new Random(System.nanoTime()));
    }

    private void doEstrai() {
        doMescola();
        Random random = new Random(System.nanoTime());
        int numero = random.nextInt(numeriEstraibili.size());

        Integer estratto = numeriEstraibili.get(numero);
        numeriEstratti.add(estratto);
        numeriEstraibili.remove(numero);
    }

    public String getUltimoEstrattoAsString() {
        if (numeriEstratti.isEmpty()) { //|| mescolati) {
            return "_____";
        } else {
            return "" + numeriEstratti.get(numeriEstratti.size() - 1);
        }
    }

    public void reset() {
        numeriEstraibili.clear();
        numeriEstraibili.addAll(numeriEstraibiliOrig);
        numeriEstratti.clear();
    }
}
