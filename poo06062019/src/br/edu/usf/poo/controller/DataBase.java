package br.edu.usf.poo.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.usf.poo.exceptions.NotConnectionEstablishedException;
import br.edu.usf.poo.models.Lixa;
import br.edu.usf.poo.models.Marca;
import br.edu.usf.poo.models.Roda;
import br.edu.usf.poo.models.Rolamento;
import br.edu.usf.poo.models.Shape;
import br.edu.usf.poo.models.Truck;
import br.edu.usf.poo.models.User;
import br.edu.usf.poo.utils.EncryptionUtils;

public class DataBase {
	private static DataBase instance;
	
	private static Connection con;
	private static Statement st;

	public static DataBase gi() {
		
		if (instance == null) {
			instance = new DataBase();
		}
		
		return instance;
	}
	
	private DataBase() {
		try {
			initConnection();
			
		} catch (Exception e) {
			
			AppController.gi().dbConnectionError(e);
		}
	}

	private void initConnection() throws ClassNotFoundException, SQLException {
		String driver = "org.postgresql.Driver";
		String user = "postgres";
		String pass = "Abcd1234";
		String address = "jdbc:postgresql://localhost/poo06062019";

		Class.forName(driver);

		con = DriverManager.getConnection(address, user, pass);

		st = con.createStatement();
	}

	private void confirmConnection() throws NotConnectionEstablishedException {
		if (!isConnected()) {
			throw new NotConnectionEstablishedException();
		}
	}
	
	public User validateLogin(String login, String password) {
		
		Integer userID = getUserIDByLogin(login);
		if (userID == null) {
			return null;
		}
		
		String encryptedPassword = EncryptionUtils.toSHA256Hash(userID + password);
		
		String sql = "SELECT userid, nome, login "
				+ "FROM users "
				+ "WHERE userID = " + userID + " "
				+ "AND password = '" + encryptedPassword + "'";
		
		ResultSet resultSet = executeQuery(sql);
		try {
			if (resultSet.next()) {
				User user = new User(
						resultSet.getInt("userid"),
						resultSet.getString("nome"),
						resultSet.getString("login"));
				
				return user;
			}
		} catch (Exception e) {}
		
		return null;
	}
	
	private Integer getUserIDByLogin(String login) {
		String sql = "SELECT userID "
				+ "FROM users "
				+ "WHERE login = '"+ login + "'";
		
		ResultSet resultSet = executeQuery(sql);
		try {
			if (resultSet.next()) {
				return resultSet.getInt("userID");
			}
			
		} catch (SQLException e) {}
		
		return null;
	}
	
	public List<Lixa> getLixas() {
		List<Lixa> lixas = new ArrayList<>();
		
		String sql = "SELECT codlixa, codmarca, desclixa, precolixa::numeric "
				+ "FROM lixa ";
		
		ResultSet resultSet = executeQuery(sql);
		try {
			while (resultSet.next()) {
				Lixa lixa = new Lixa();
				lixa.setCod(resultSet.getInt("codlixa"));
				lixa.setCodMarca(resultSet.getInt("codmarca"));
				lixa.setDesc(resultSet.getString("desclixa"));
				lixa.setPreco(resultSet.getFloat("precolixa"));
				
				lixas.add(lixa);
			}
		} catch (SQLException e) {
			processQuerryException(e);
		}
		return lixas;
	}
	
	public List<Marca> getMarcas() {
		List<Marca> marcas = new ArrayList<>();
		
		String sql = "SELECT codmarca, nomemarca "
				+ "FROM marca ";
		
		ResultSet resultSet = executeQuery(sql);
		try {
			while (resultSet.next()) {
				Marca marca = new Marca();
				marca.setCod(resultSet.getInt("codmarca"));
				marca.setNome(resultSet.getString("nomemarca"));
				
				marcas.add(marca);
			}
		} catch (SQLException e) {
			processQuerryException(e);
		}
		return marcas;
	}
	
	public List<Roda> getRodas() {
		List<Roda> rodas = new ArrayList<>();
		
		String sql = "SELECT codroda, codmarca, descroda, precoroda::numeric "
				+ "FROM roda ";
		
		ResultSet resultSet = executeQuery(sql);
		try {
			while (resultSet.next()) {
				Roda roda = new Roda();
				roda.setCod(resultSet.getInt("codroda"));
				roda.setCodMarca(resultSet.getInt("codmarca"));
				roda.setDesc(resultSet.getString("descroda"));
				roda.setPreco(resultSet.getFloat("precoroda"));
				
				rodas.add(roda);
			}
		} catch (SQLException e) {
			processQuerryException(e);
		}
		return rodas;
	}
	
	public List<Rolamento> getRolamentos() {
		List<Rolamento> rolamentos = new ArrayList<>();
		
		String sql = "SELECT codrolamento, codmarca, codrolamento, precorolamento::numeric "
				+ "FROM rolamento ";
		
		ResultSet resultSet = executeQuery(sql);
		try {
			while (resultSet.next()) {
				Rolamento roda = new Rolamento();
				roda.setCod(resultSet.getInt("codrolamento"));
				roda.setCodMarca(resultSet.getInt("codmarca"));
				roda.setDesc(resultSet.getString("codrolamento"));
				roda.setPreco(resultSet.getFloat("precorolamento"));
				
				rolamentos.add(roda);
			}
		} catch (SQLException e) {
			processQuerryException(e);
		}
		return rolamentos;
	}
	
	public List<Shape> getShapes() {
		List<Shape> shapes = new ArrayList<>();
		
		String sql = "SELECT codshape, codmarca, descshape, precoshape::numeric "
				+ "FROM shape ";
		
		ResultSet resultSet = executeQuery(sql);
		try {
			while (resultSet.next()) {
				Shape shape = new Shape();
				shape.setCod(resultSet.getInt("codshape"));
				shape.setCodMarca(resultSet.getInt("codmarca"));
				shape.setDesc(resultSet.getString("descshape"));
				shape.setPreco(resultSet.getFloat("precoshape"));
				
				shapes.add(shape);
			}
		} catch (SQLException e) {
			processQuerryException(e);
		}
		return shapes;
	}
	
	public List<Truck> getTrucks() {
		List<Truck> trucks = new ArrayList<>();
		
		String sql = "SELECT codtruck, codmarca, desctruck, precotruck::numeric "
				+ "FROM truck ";
		
		ResultSet resultSet = executeQuery(sql);
		try {
			while (resultSet.next()) {
				Truck truck = new Truck();
				truck.setCod(resultSet.getInt("codtruck"));
				truck.setCodMarca(resultSet.getInt("codmarca"));
				truck.setDesc(resultSet.getString("desctruck"));
				truck.setPreco(resultSet.getFloat("precotruck"));
				
				trucks.add(truck);
			}
		} catch (SQLException e) {
			processQuerryException(e);
		}
		return trucks;
	}
	
	private ResultSet executeQuery(String sql) {
		confirmConnection();
		
		try {
			ResultSet resultSet = st.executeQuery(sql);
			
			return resultSet;
			
		} catch (SQLException e) {
			processQuerryException(e);
		}
		return null;
	}
	
	private void processQuerryException(SQLException e) {
		// TODO
	}

	public static void close() throws SQLException {
		if (isConnected()) {
			st.close();
			con.close();
		}
	}

	private static boolean isConnected() {
		try {
			return !con.isClosed();
			
		} catch (Exception e) {
			return false;
		}
	}

}
