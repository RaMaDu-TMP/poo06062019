package br.edu.usf.poo.client;

import java.util.List;

import br.edu.usf.poo.controller.DataBase;
import br.edu.usf.poo.models.Lixa;

public class LixaClient {
	private static LixaClient instance;
	
	public static LixaClient gi() {

		if (instance == null) {
			instance = new LixaClient();
		}
		
		return instance;
	}
	
	private LixaClient() {
		super();
	}
	
	public List<Lixa> getAll() {
		return DataBase.gi().getLixas();
	}
	
}
