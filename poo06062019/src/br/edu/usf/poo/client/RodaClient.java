package br.edu.usf.poo.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.usf.poo.controller.DataBase;
import br.edu.usf.poo.models.Roda;

public class RodaClient {
	private static RodaClient instance;
	
	private Map<Integer, Roda> cache;
	private List<Roda> rodas;
	
	public static RodaClient gi() {

		if (instance == null) {
			instance = new RodaClient();
		}
		
		return instance;
	}
	
	private RodaClient() {
		super();
		
		rodas = DataBase.gi().getRodas();
		loadCache();
	}
	
	public List<Roda> getAll() {
		return rodas;
	}
	
	private void loadCache() {
		cache = new HashMap<>();
		
		for (Roda roda : rodas) {
			cache.put(roda.getCod(), roda);
		}
	}

	public Roda getByID(int codRoda) {
		return cache.get(codRoda);
	}
	
}
