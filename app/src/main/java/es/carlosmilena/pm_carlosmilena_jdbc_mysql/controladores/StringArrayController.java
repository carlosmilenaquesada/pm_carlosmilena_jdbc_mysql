package es.carlosmilena.pm_carlosmilena_jdbc_mysql.controladores;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import es.carlosmilena.pm_carlosmilena_jdbc_mysql.tareas.arraystring.TareaObtenerArrayString;

public class StringArrayController{
	public static ArrayList<String> obtenerStringArrayController(String tabla, String columna){
		ArrayList<String> elementos = null;
		FutureTask t = new FutureTask(new TareaObtenerArrayString(tabla, columna));
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.submit(t);
		try{
			elementos = (ArrayList<String>) t.get(5000, TimeUnit.MILLISECONDS);
		}catch(ExecutionException e){
			e.printStackTrace();
			Log.i("sql", "Error al obtener " + tabla + ": " + e.getMessage());
		}catch(InterruptedException | TimeoutException e){
			e.printStackTrace();
			Log.i("sql", "Error al obtener " + tabla + ": " + e.getMessage());
		}finally{
			es.shutdown();
		}
		return elementos;
	}
}
