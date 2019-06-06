package br.edu.usf.poo.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.usf.poo.controller.DataBase;
import br.edu.usf.poo.models.Rolamento;

public class RolamentoClient {
	private static RolamentoClient instance;
	
	private Map<Integer, Rolamento> cache;
	private List<Rolamento> rolamentos;
	
	public static RolamentoClient gi() {

		if (instance == null) {
			instance = new RolamentoClient();
		}
		
		return instance;
	}
	
	private RolamentoClient() {
		super();
		
		rolamentos = DataBase.gi().getRolamentos();
		loadCache();
	}
	
	public List<Rolamento> getAll() {
		return rolamentos;
	}
	
	private void loadCache() {
		cache = new HashMap<>();
		
		for (Rolamento rolamento : rolamentos) {
			cache.put(rolamento.getCod(), rolamento);
		}
	}

	public Rolamento getByID(int codRolamento) {
		return cache.get(codRolamento);
	}
	
}
