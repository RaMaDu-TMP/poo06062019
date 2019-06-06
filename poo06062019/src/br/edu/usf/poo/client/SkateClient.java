package br.edu.usf.poo.client;

import java.util.List;

import br.edu.usf.poo.controller.DataBase;
import br.edu.usf.poo.models.Lixa;
import br.edu.usf.poo.models.Roda;
import br.edu.usf.poo.models.Rolamento;
import br.edu.usf.poo.models.Shape;
import br.edu.usf.poo.models.Skate;
import br.edu.usf.poo.models.Truck;

public class SkateClient {
	private static SkateClient instance;
	
	public static SkateClient gi() {

		if (instance == null) {
			instance = new SkateClient();
		}
		
		return instance;
	}
	
	private SkateClient() {
		super();
	}
	
	public List<Skate> getAll() {
		return DataBase.gi().getSkates();
	}
	
	public Object[][] getAllAsMatrix() {
		List<Skate> skates = getAll();
		
		Object[][] matrix = new Object[skates.size()][6];
		
		for (int i = 0; i < skates.size(); i++) {
			Skate skate = skates.get(i);
			
			Lixa lixa = LixaClient.gi().getByID(skate.getCodLixa());
			Roda roda = RodaClient.gi().getByID(skate.getCodRoda());
			Rolamento rolamento = RolamentoClient.gi().getByID(skate.getCodRolamento());
			Shape shape = ShapeClient.gi().getByID(skate.getCodShape());
			Truck truck = TruckClient.gi().getByID(skate.getCodTruck());
			
			matrix[i][0] = lixa;
			matrix[i][1] = roda;
			matrix[i][2] = rolamento;
			matrix[i][3] = shape;
			matrix[i][4] = truck;
		}
		
		return matrix;
	}
	
}
