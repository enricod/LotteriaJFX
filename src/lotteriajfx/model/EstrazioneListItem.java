/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lotteriajfx.model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author enricodonelli
 */
public class EstrazioneListItem {

    private final SimpleStringProperty contatore = new SimpleStringProperty("");
    private final SimpleStringProperty biglietto = new SimpleStringProperty("");
    private final SimpleStringProperty premio = new SimpleStringProperty("");

    public EstrazioneListItem() {
        this("", "", "");
    }

    public EstrazioneListItem(String firstName, String lastName, String email) {
        setContatore(firstName);
        setBiglietto(lastName);
        setPremio(email);
    }

    public String getContatore() {
        return contatore.get();
    }

    public void setContatore(String fName) {
        contatore.set(fName);
    }

    public String getBiglietto() {
        return biglietto.get();
    }

    public void setBiglietto(String fName) {
        biglietto.set(fName);
    }

    public String getPremio() {
        return premio.get();
    }

    public void setPremio(String fName) {
        premio.set(fName);
    }
}
