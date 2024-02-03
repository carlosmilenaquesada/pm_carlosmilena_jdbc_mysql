package es.carlosmilena.pm_carlosmilena_jdbc_mysql.modeloDB.sentenciassql;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import es.carlosmilena.pm_carlosmilena_jdbc_mysql.clases.Juego;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.modeloDB.conexion.ConfiguracionDB;

public class JuegoDB{
	public static ArrayList<Juego> obtenerJuegos(){
		Connection conexion = ConfiguracionDB.conectarConBaseDeDatos();
		if(conexion == null){
			return null;
		}
		ArrayList<Juego> juegos = new ArrayList<>();
		try{
			Statement sentencia = conexion.createStatement();
			String ordenSQL = "SELECT * FROM carlosmilena_juegos ORDER BY CONVERT(identificador, " +
							  "SIGNED);";
			ResultSet resultado = sentencia.executeQuery(ordenSQL);
			while(resultado.next()){
				String identificador = resultado.getString("identificador");
				String plataforma = resultado.getString("plataforma");
				String nombreJuego = resultado.getString("nombrejuego");
				String genero = resultado.getString("genero");
				double precioJuego = resultado.getDouble("preciojuego");
				Juego juego = new Juego(identificador, plataforma, nombreJuego, genero,
						precioJuego);
				juegos.add(juego);
			}
			resultado.close();
			sentencia.close();
			conexion.close();
			return juegos;
		}catch(SQLException e){
			Log.i("sql", "error sql");
			return null;
		}
	}

	public static boolean guardarJuego(Juego juego){
		Connection conexion = ConfiguracionDB.conectarConBaseDeDatos();
		if(conexion == null){
			return false;
		}
		try{
			String ordensql = "INSERT INTO carlosmilena_juegos (identificador,plataforma," +
							  "nombrejuego,genero,preciojuego) values (?,?,?,?,?);";
			PreparedStatement pst = conexion.prepareStatement(ordensql);
			pst.setString(1, juego.getIdentificador());
			pst.setString(2, juego.getPlataforma());
			pst.setString(3, juego.getNombreJuego());
			pst.setString(4, juego.getGenero());
			pst.setDouble(5, juego.getPrecioJuego());
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

	public static boolean borrarJuego(String idJuego){
		Connection conexion = ConfiguracionDB.conectarConBaseDeDatos();
		if(conexion == null){
			return false;
		}
		try{
			String ordensql = "DELETE FROM `carlosmilena_juegos` WHERE (`identificador` = ?);";
			PreparedStatement pst = conexion.prepareStatement(ordensql);
			pst.setString(1, idJuego);
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

	public static boolean actualizarJuego(Juego juegoNew, String idJuegoOld){
		Connection conexion = ConfiguracionDB.conectarConBaseDeDatos();
		if(conexion == null){
			return false;
		}
		try{
			String ordensql = "UPDATE carlosmilena_juegos SET identificador = ?,plataforma = ?, " +
							  "nombrejuego = ?, genero = ?, preciojuego = ? WHERE identificador " +
							  "=" +
							  " ?";
			PreparedStatement pst = conexion.prepareStatement(ordensql);
			pst.setString(1, juegoNew.getIdentificador());
			pst.setString(2, juegoNew.getPlataforma());
			pst.setString(3, juegoNew.getNombreJuego());
			pst.setString(4, juegoNew.getGenero());
			pst.setDouble(5, juegoNew.getPrecioJuego());
			pst.setString(6, idJuegoOld);
			int filasAfectadas = pst.executeUpdate();
			pst.close();
			conexion.close();
			if(filasAfectadas > 0){
				return true;
			}else{
				return false;
			}
		}catch(SQLException e){
			return false;
		}
	}
}
