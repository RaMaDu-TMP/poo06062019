package br.edu.usf.poo.client;

import java.util.List;

import br.edu.usf.poo.controller.DataBase;
import br.edu.usf.poo.models.Rolamento;

public class RolamentoClient {
	private static RolamentoClient instance;
	
	public static RolamentoClient gi() {

		if (instance == null) {
			instance = new RolamentoClient();
		}
		
		return instance;
	}
	
	private RolamentoClient() {
		super();
	}
	
	public List<Rolamento> getAll() {
		return DataBase.gi().getRolamentos();
	}
	
}
