package br.edu.usf.poo.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import br.edu.usf.poo.controller.DataBase;
import br.edu.usf.poo.models.Shape;

public class ShapeClient {
	private static ShapeClient instance;
	
	private Map<Integer, Shape> cache;
	private List<Shape> shapes;
	
	public static ShapeClient gi() {

		if (instance == null) {
			instance = new ShapeClient();
		}
		
		return instance;
	}
	
	private ShapeClient() {
		super();
		
		shapes = DataBase.gi().getShapes();
		loadCahce();
	}
	
	private void loadCahce() {
		cache = new HashMap<>();
		
		for (Shape shape : shapes) {
			cache.put(shape.getCod(), shape);
		}
	}
	
	public List<Shape> getAll() {
		return shapes;
	}

	@Nullable
	public Shape getByID(Integer codShape) {
		if (codShape == null) {
			return null;
		}
		
		return cache.get(codShape);
	}
	
}
