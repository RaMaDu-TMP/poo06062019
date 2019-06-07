package br.edu.usf.poo.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

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

	@Nullable
	public Rolamento getByID(Integer codRolamento) {
		if (codRolamento == null) {
			return null;
		}
		
		return cache.get(codRolamento);
	}
	
}
