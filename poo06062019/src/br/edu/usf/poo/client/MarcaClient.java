package br.edu.usf.poo.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.edu.usf.poo.controller.DataBase;
import br.edu.usf.poo.models.Marca;

public class MarcaClient {
	private static MarcaClient instance;
	
	private Map<Integer, Marca> mapMarcas = new HashMap<>();
	
	public static MarcaClient gi() {

		if (instance == null) {
			instance = new MarcaClient();
		}
		
		return instance;
	}
	
	private MarcaClient() {
		super();
	}
	
	public List<Marca> getAll() {
		List<Marca> marcas = DataBase.gi().getMarcas();
		updateCache(marcas);
		return marcas;
	}
	
	private void updateCache(List<Marca> marcas) {
		mapMarcas.clear();
		mapMarcas = marcas.stream().collect(Collectors.toMap(m -> m.getCod(), m -> m));
	}
	
	public Marca getMarcaByID(int id) {
		return mapMarcas.get(id);
	}
	
}
