package br.edu.usf.poo.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.usf.poo.controller.DataBase;
import br.edu.usf.poo.models.Lixa;

public class LixaClient {
	private static LixaClient instance;
	
	private Map<Integer, Lixa> cache;
	private List<Lixa> lixas;
	
	public static LixaClient gi() {

		if (instance == null) {
			instance = new LixaClient();
		}
		
		return instance;
	}
	
	private LixaClient() {
		super();
		
		lixas = DataBase.gi().getLixas();
		loadCache();
	}
	
	public List<Lixa> getAll() {
		return lixas;
	}
	
	private void loadCache() {
		cache = new HashMap<>();
		
		for (Lixa lixa : lixas) {
			cache.put(lixa.getCod(), lixa);
		}
	}

	public Lixa getByID(int codLixa) {
		return cache.get(codLixa);
	}
	
}
