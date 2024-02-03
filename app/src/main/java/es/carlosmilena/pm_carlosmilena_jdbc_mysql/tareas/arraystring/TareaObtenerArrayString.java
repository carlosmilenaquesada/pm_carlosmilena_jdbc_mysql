package es.carlosmilena.pm_carlosmilena_jdbc_mysql.tareas.arraystring;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import es.carlosmilena.pm_carlosmilena_jdbc_mysql.modeloDB.sentenciassql.StringArrayDB;

public class TareaObtenerArrayString implements Callable<ArrayList<String>>{
	private String tabla;
	private String columna;

	public TareaObtenerArrayString(String tabla, String columna){
		this.tabla = tabla;
		this.columna = columna;
	}

	@Override
	public ArrayList<String> call() throws Exception{
		ArrayList<String> elementos = StringArrayDB.obtenerStringArray(tabla, columna);
		return elementos;
	}
}