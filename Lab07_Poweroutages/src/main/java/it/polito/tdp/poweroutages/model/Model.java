package it.polito.tdp.poweroutages.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	private List<PowerOutage> powerOutageList; //Lista su cui fare la ricorsione (partenza)
	private List<PowerOutage> worstCase;
	private List<PowerOutage> parziale;
	private int maxMinuti;
	private int maxAnni;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	private List<PowerOutage> getPOList(int idNerc) {
		return podao.getPowerOutage(idNerc);
	}
	
	private int getMinuti(LocalTime time) {
		return (time.getHour()*60)+time.getMinute();
	}
	
	public int tempoTot(List<PowerOutage> parziale) {
		int tempoTot = 0;
		for(PowerOutage po : parziale)
			tempoTot += this.getMinuti(po.getTime());
		
		return tempoTot;
	}
	
	public int customersAffectedTot(List<PowerOutage> parziale) {
		int customersAffectedTot = 0;
		for(PowerOutage po: parziale)
			customersAffectedTot += po.getCustomersAffected();
		
		return customersAffectedTot;
	}
	
	public List<PowerOutage> worstCase(int idNerc, int ore, int anni) {
		
		this.powerOutageList = this.getPOList(idNerc); //Lista di partenza
		this.parziale = new ArrayList<>(); //Lista su cui si fa la ricorsione per tutti i passaggi intermedi
		this.worstCase = new ArrayList<>(); //inizializzo a vuota la lista che poi sara' quella da restituire
		
		this.maxMinuti = ore*60;
		this.maxAnni = anni;
		
		this.cerca(parziale, 0);
		
		return worstCase;
	}
	
	/**
	 * Algoritmo ricorsivo (aiutato)
	 * @param parziale e' la lista su cui viene fatta la ricorsione
	 * @param livello indica di quale PowerOutage nella lista ci stiamo occupando
	 */
	private void cerca(List<PowerOutage> parziale, int livello) {
		//Casi terminali
		
		if(this.tempoTot(parziale) > maxMinuti) //Ho superato il vincolo di ore (minuti)
			return;
		
		if(this.anniTot(parziale) > maxAnni) //Ho superato il vincolo di anni
			return;
		
		if(livello==this.powerOutageList.size()) //Bene, hai finito di ispezionare tutti gli elementi della lista
			return;
		
		//E' la ottimizzazione questa.. a ogni livello della ricorsione guardo se le persone affected sono di piu'
		//se si' allora vuol dire che potrebbe essere una soluzione ottima e aggiorno il worstCase
		if(this.customersAffectedTot(parziale)>this.customersAffectedTot(worstCase)) {
			this.worstCase = new ArrayList<>(parziale);
		}
		
		//Come in voti nobel ho i 2 casi:
		//Caso in cui inserisco l'elemento nella lista
		parziale.add(this.powerOutageList.get(livello));
		cerca(parziale, livello+1);
		parziale.remove(this.powerOutageList.get(livello)); //faccio backtracking
		
		//Caso in cui non lo metto
		cerca(parziale, livello+1); //faccio direttamente ricorsione, cosi' passo al livello successivo senza aver inserito nulla
		
	}

	private int anniTot(List<PowerOutage> parziale) {
		int annoMin = 0;
		int annoMax = 0;
		
		for(PowerOutage po : parziale) {
			int annoTemp = po.getYear();
			if(annoMin==0)
				annoMin = annoTemp;
			if(annoTemp < annoMin)
				annoMin = annoTemp;
			if(annoTemp > annoMax)
				annoMax = annoTemp;
		}
		
		return annoMax-annoMin+1;
	}
}
