package es.carlosmilena.pm_carlosmilena_jdbc_mysql.modeloDB.sentenciassql;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import es.carlosmilena.pm_carlosmilena_jdbc_mysql.modeloDB.conexion.ConfiguracionDB;

public class StringArrayDB{
	public static ArrayList<String> obtenerStringArray(String tabla, String columna){
		Connection conexion = ConfiguracionDB.conectarConBaseDeDatos();
		if(conexion == null){
			return null;
		}
		ArrayList<String> elementos = new ArrayList<String>();
		try{
			String ordenSQL = "SELECT * FROM " + tabla + " ORDER BY ? ASC;";
			PreparedStatement pst = conexion.prepareStatement(ordenSQL);
			pst.setString(1, columna);
			ResultSet resultado = pst.executeQuery();
			while(resultado.next()){
				elementos.add(resultado.getString(columna));
			}
			resultado.close();
			pst.close();
			conexion.close();
			return elementos;
		}catch(SQLException e){
			Log.i("sqla", "error sqla" + e.getMessage());
			return null;
		}
	}
}
