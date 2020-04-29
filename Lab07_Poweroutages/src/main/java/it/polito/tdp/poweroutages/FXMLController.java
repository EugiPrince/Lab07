/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxNERC"
    private ChoiceBox<Nerc> boxNERC; // Value injected by FXMLLoader

    @FXML // fx:id="txtAnni"
    private TextField txtAnni; // Value injected by FXMLLoader

    @FXML // fx:id="txtOre"
    private TextField txtOre; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalysis"
    private Button btnAnalysis; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doWorstCaseAnalysis(ActionEvent event) {
    	int idNerc = this.boxNERC.getValue().getId();
    	int maxAnni = 0;
    	int maxOre = 0;
    	
    	try {
    		maxAnni = Integer.parseInt(this.txtAnni.getText());
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("Devi inserire un valore valido!\n");
    		return;
    	}
    	
    	try {
    		maxOre = Integer.parseInt(this.txtOre.getText());
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("Devi inserire un valore valido!\n");
    		return;
    	}
    	
    	List<PowerOutage> worstCase = this.model.worstCase(idNerc, maxOre, maxAnni);
    	
    	if(worstCase == null) {
    		this.txtResult.appendText("Non ho trovato soluzioni\n");
    		return;
    	}
    	
    	this.txtResult.appendText("Total Customers Affected: " + this.model.customersAffectedTot(worstCase) + "\n");
    	this.txtResult.appendText("Total Hours: " + (double)(this.model.tempoTot(worstCase)/60) + "\n");
    	for(PowerOutage po : worstCase)
    		this.txtResult.appendText(po.toString());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxNERC != null : "fx:id=\"boxNERC\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAnni != null : "fx:id=\"txtAnni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtOre != null : "fx:id=\"txtOre\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnalysis != null : "fx:id=\"btnAnalysis\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		
		List<Nerc> allNerc = model.getNercList();
		
		this.boxNERC.getItems().addAll(allNerc);	
	}
}
