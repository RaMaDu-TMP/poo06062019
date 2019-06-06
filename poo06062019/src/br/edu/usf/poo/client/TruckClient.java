package br.edu.usf.poo.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.usf.poo.controller.DataBase;
import br.edu.usf.poo.models.Truck;

public class TruckClient {
	private static TruckClient instance;
	
	private Map<Integer, Truck> cache;
	private final List<Truck> trucks;
	
	public static TruckClient gi() {

		if (instance == null) {
			instance = new TruckClient();
		}
		
		return instance;
	}
	
	private TruckClient() {
		super();
		
		trucks = DataBase.gi().getTrucks();
		loadCache();
	}
	
	private void loadCache() {
		cache = new HashMap<>();
		
		for (Truck truck : trucks) {
			cache.put(truck.getCod(), truck);
		}
	}

	public List<Truck> getAll() {
		return trucks;
	}

	public Truck getByID(int codTruck) {
		return cache.get(codTruck);
	}
	
}
