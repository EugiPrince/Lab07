package it.polito.tdp.poweroutages.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		System.out.println(model.getNercList());
		
		//List<PowerOutage> prova = model.getPOList(8);
		
		//System.out.println(model.tempoTot(prova));

		List<PowerOutage> ricorsione = model.worstCase(3, 200, 4);
		System.out.println("Tot persone: "+model.customersAffectedTot(ricorsione));
		System.out.println("Tot ore: "+(double)(model.tempoTot(ricorsione)/60));
		for(PowerOutage po : ricorsione)
			System.out.println(po);
	}

}
