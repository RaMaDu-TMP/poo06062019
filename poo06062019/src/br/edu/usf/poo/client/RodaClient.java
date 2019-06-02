package br.edu.usf.poo.client;

import java.util.List;

import br.edu.usf.poo.controller.DataBase;
import br.edu.usf.poo.models.Roda;

public class RodaClient {
	private static RodaClient instance;
	
	public static RodaClient gi() {

		if (instance == null) {
			instance = new RodaClient();
		}
		
		return instance;
	}
	
	private RodaClient() {
		super();
	}
	
	public List<Roda> getAll() {
		return DataBase.gi().getRodas();
	}
	
}
