package es.carlosmilena.pm_carlosmilena_jdbc_mysql.modeloDB.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfiguracionDB{
	public static final String HOSTDB = "10.0.2.2";
	public static final String NOMBREDB = "tienda_carlosmilena";
	public static final String USUARIODB = "root";
	public static final String CLAVEDB = "";
	private static final String OPCIONESHORA = "?useUnicode=true&useJDBCCompliantTimezoneShift" +
											   "=true&useLegacyDatetimeCode=false&serverTimezone" +
											   "=UTC";
	// las opciones de hora tambien las puedes poner en mysql
	// SET GLOBAL time_zone = '+1:00';
	public static final String PUERTOMYSQL = "3306";
	public static final String URLMYSQL =
			"jdbc:mysql://" + HOSTDB + ":" + PUERTOMYSQL + "/" + NOMBREDB + OPCIONESHORA;

	//----------------------------------------------------------....
	public static Connection conectarConBaseDeDatos(){
		try{
			Connection conexion = DriverManager.getConnection(URLMYSQL, USUARIODB, CLAVEDB);
			return conexion;
		}catch(SQLException e){
			return null;
		}
	}
	//-----------------------------------------------------------
}
