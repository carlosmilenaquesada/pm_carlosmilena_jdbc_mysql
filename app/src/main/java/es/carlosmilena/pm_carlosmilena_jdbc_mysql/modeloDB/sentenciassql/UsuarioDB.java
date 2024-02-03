package es.carlosmilena.pm_carlosmilena_jdbc_mysql.modeloDB.sentenciassql;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.carlosmilena.pm_carlosmilena_jdbc_mysql.clases.Usuario;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.modeloDB.conexion.ConfiguracionDB;

public class UsuarioDB{
	public static Usuario obtenerUsuario(String emailUsuario, String passwordUsuario){
		Connection conexion = ConfiguracionDB.conectarConBaseDeDatos();
		if(conexion == null){
			return null;
		}
		Usuario usuario = null;
		try{
			String ordenSQL ="SELECT * FROM carlosmilena_usuarios WHERE email = ? AND password = ?";
			PreparedStatement pst = conexion.prepareStatement(ordenSQL);
			pst.setString(1, emailUsuario);
			pst.setString(2, passwordUsuario);
			ResultSet resultado = pst.executeQuery();
			if(resultado.next()){
				String email = resultado.getString("email");
				String password = resultado.getString("password");
				usuario = new Usuario(email, password);
				System.out.println("hay resultado");
			}
			resultado.close();
			pst.close();
			conexion.close();
			return usuario;
		}catch(SQLException e){
			Log.i("sql", "error sql" + e.getMessage());
			return usuario;
		}
	}

	public static boolean crearUsuario(Usuario usuario){
		Connection conexion = ConfiguracionDB.conectarConBaseDeDatos();
		if(conexion == null){
			return false;
		}
		try{
			String ordensql = "INSERT INTO carlosmilena_usuarios (email,password) values (?,?);";
			PreparedStatement pst = conexion.prepareStatement(ordensql);
			pst.setString(1, usuario.getEmail());
			pst.setString(2, usuario.getPassword());
			int filasafectadas = pst.executeUpdate();
			pst.close();
			conexion.close();
			if(filasafectadas > 0){
				return true;
			}else{
				return false;
			}
		}catch(SQLException e){
			return false;
		}
	}
}
