/**
 * Sample Skeleton for 'Ufo.fxml' Controller Class
 */

package it.polito.tdp.ufo;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.ufo.model.AnnoCount;
import it.polito.tdp.ufo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class UfoController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<AnnoCount> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxStato"
    private ComboBox<String> boxStato; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void handleAnalizza(ActionEvent event) {
    	
    	txtResult.clear();
    	String stato = this.boxStato.getValue();
    	txtResult.appendText("Lista di nodi predecessori:\n");
    	for(String predecessori : model.getPredecessori(stato)) {
    		txtResult.appendText(predecessori+"\n");
    	}
    	
    	txtResult.appendText("Lista di nodi successori:\n");
    	for(String successori : model.getSuccessori(stato)) {
    		txtResult.appendText(successori+"\n");
    	}
    	
    	txtResult.appendText("Lista di nodi raggiungibili:\n");
    	for(String raggiungibili : model.getRaggiungibili(stato)) {
    		txtResult.appendText(raggiungibili+"\n");
    	}
    	
    }

    @FXML
    void handleAvvistamenti(ActionEvent event) {
    	
    	AnnoCount anno = boxAnno.getValue();
    	if(anno == null) {
    		txtResult.appendText("Devi selezionare un anno");
    		return;
    	}
    	this.model.creaGrafo(anno.getYear());
    	txtResult.appendText("Grafo Creato!");
    	txtResult.appendText("\n# vertici: " + this.model.getNvertici());
    	txtResult.appendText("\n# archi: " + this.model.getNarchi());
    	
    	this.boxStato.getItems().addAll(this.model.getStati());
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	
    		String stato = boxStato.getValue();
    		List<String> percorso = this.model.getPercorsoMax(stato);
    		txtResult.clear();
    		txtResult.appendText("PERCORSO MASSIMO: ");
    		for(String s : percorso) {
    			txtResult.appendText(s+" - ");
    		}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Ufo.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxAnno.getItems().addAll(this.model.getAnni());
    }
}
