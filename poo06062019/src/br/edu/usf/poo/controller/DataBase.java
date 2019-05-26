package br.edu.usf.poo.controller;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.edu.usf.poo.exceptions.NotConnectionEstablishedException;
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
	
	public boolean validLogin(String login, String password) {
		String encryptedPassword;
		try {
			encryptedPassword = EncryptionUtils.toSHA256Hash(password);
			
		} catch (NoSuchAlgorithmException e1) {
			return false;
		}
		
		String sql = "SELECT * "
				+ "FROM users "
				+ "WHERE login = '" + login + "'"
				+ "AND password = '" + encryptedPassword + "'";
		
		ResultSet resultSet = executeQuery(sql);
		try {
			return resultSet.next();
			
		} catch (Exception e) {
			return false;
		} 
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
