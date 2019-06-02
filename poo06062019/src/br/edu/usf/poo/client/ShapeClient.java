package br.edu.usf.poo.client;

import java.util.List;

import br.edu.usf.poo.controller.DataBase;
import br.edu.usf.poo.models.Shape;

public class ShapeClient {
	private static ShapeClient instance;
	
	public static ShapeClient gi() {

		if (instance == null) {
			instance = new ShapeClient();
		}
		
		return instance;
	}
	
	private ShapeClient() {
		super();
	}
	
	public List<Shape> getAll() {
		return DataBase.gi().getShapes();
	}
	
}
