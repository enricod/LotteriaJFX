/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lotteriajfx;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.text.Font;
import lotteriajfx.model.Estrazione;
import lotteriajfx.model.EstrazioneListItem;

/**
 *
 * @author enricodonelli
 */
public class FXMLDocumentController implements Initializable {

    private Estrazione estrazione;

    @FXML
    private Label numeroEstrattoLabel;

    @FXML
    private Label premioEstrattoLabel;

    @FXML
    private Label msgLabel;

    @FXML
    private Label numeroEstrazioni;

    @FXML
    private Label numeroPremi;

    @FXML
    private TableView<EstrazioneListItem> tableView;
    @FXML
    Button estraiBtn;
    
    @FXML 
    TextArea logsTextArea;

    @FXML
    private void resetAction(ActionEvent event) {
       
    }

    @FXML
    private void salvaDatiAction(ActionEvent event) {
        ObservableList<EstrazioneListItem> data = tableView.getItems();
        try {
            //create a temporary file
            String originalFileName = "lotteria-risultato";
            int counter = 0;
           File logFile ;
            do {
                counter++;
                String fileName = originalFileName + "-" + counter + ".txt";
                logFile = new File(System.getProperty("user.home") + "/" + fileName);
            } while (logFile.exists());
           

            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
            writer.write("Cont.\tBigl.\tPremio\n");
            data.forEach(i -> {
                try {
                    StringBuffer line = new StringBuffer();
                    line.append(i.getContatore());
                    line.append("\t");
                    line.append(i.getBiglietto());
                    line.append("\t");
                    line.append(i.getPremio());
                    line.append("\n");
                    writer.write(line.toString());
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            //Close writer
            writer.close();
            
            logsTextArea.setText(logsTextArea.getText() + "\n" + "Dati salvati in " + logFile.getAbsolutePath() );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void estraiNumeroAction(ActionEvent event) {
        estrazione.avviaEstrazione();
        
        numeroEstrattoLabel.setText(estrazione.getUltimoEstrattoAsString());
        premioEstrattoLabel.setText(estrazione.getElencoPremi().get(estrazione.getContNumeriEstratti() - 1));
        List<Integer> numeriEstratti = estrazione.getNumeriEstratti();
        List<EstrazioneListItem> numeriEstrattiString = new ArrayList<>();
        int counter = 1;
        for (Integer i : numeriEstratti) {
            EstrazioneListItem item = new EstrazioneListItem("" + counter, "" + i, estrazione.getElencoPremi().get(counter - 1));
            numeriEstrattiString.add(item);
            counter++;
        }
        ObservableList<EstrazioneListItem> data = tableView.getItems();
        data.setAll(numeriEstrattiString);

        numeroEstrazioni.setText("" + estrazione.getContNumeriEstratti());
        estraiBtn.setDisable(estrazione.isFinished());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            estrazione = new Estrazione();
            logsTextArea.setText(logsTextArea.getText() + "\n" + "Dati estrazione caricati");
            numeroPremi.setText(estrazione.numeroPremi() + "");
            numeroEstrattoLabel.setFont(Font.font("Cambria", 128));
            // logsTextArea.setBackground(Background.);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(AlertType.ERROR, "Errore: " + ex.getMessage(), ButtonType.YES);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                Platform.exit();
                System.exit(0);
            }
        }

    }

}
