package br.edu.usf.poo.client;

import java.util.List;

import br.edu.usf.poo.controller.DataBase;
import br.edu.usf.poo.models.Truck;

public class TruckClient {
	private static TruckClient instance;
	
	public static TruckClient gi() {

		if (instance == null) {
			instance = new TruckClient();
		}
		
		return instance;
	}
	
	private TruckClient() {
		super();
	}
	
	public List<Truck> getAll() {
		return DataBase.gi().getTrucks();
	}
	
}
