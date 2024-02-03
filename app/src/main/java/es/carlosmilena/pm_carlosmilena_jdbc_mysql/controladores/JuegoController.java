package es.carlosmilena.pm_carlosmilena_jdbc_mysql.controladores;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import es.carlosmilena.pm_carlosmilena_jdbc_mysql.clases.Juego;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.tareas.juegos.TareaActualizarJuego;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.tareas.juegos.TareaBorrarJuego;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.tareas.juegos.TareaInsertarJuego;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.tareas.juegos.TareaObtenerJuegos;

public class JuegoController{
	public static ArrayList<Juego> obtenerJuegosController(){
		ArrayList<Juego> juegos = null;
		FutureTask t = new FutureTask(new TareaObtenerJuegos());
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.submit(t);
		try{
			juegos = (ArrayList<Juego>) t.get(5000, TimeUnit.MILLISECONDS);
		}catch(ExecutionException e){
			e.printStackTrace();
			Log.i("sql", "Error al obtener juegos: " + e.getMessage());
		}catch(InterruptedException | TimeoutException e){
			e.printStackTrace();
			Log.i("sql", "Error al obtener juegos: " + e.getMessage());
		}finally{
			es.shutdown();
		}
		return juegos;
	}

	public static boolean insertarJuego(Juego juego){
		FutureTask t = new FutureTask(new TareaInsertarJuego(juego));
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.submit(t);
		boolean insercionOK = false;
		try{
			insercionOK = (boolean) t.get(5000, TimeUnit.MILLISECONDS);
		}catch(ExecutionException e){
			e.printStackTrace();
			Log.i("sql", "Error al insertar el juego: " + e.getMessage());
		}catch(InterruptedException | TimeoutException e1){
			e1.printStackTrace();
			Log.i("sql", "Error al insertar el juego: " + e1.getMessage());
		}finally{
			es.shutdown();
		}
		return insercionOK;
	}

	public static boolean borrarJuego(String idJuego){
		FutureTask t = new FutureTask(new TareaBorrarJuego(idJuego));
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.submit(t);
		boolean borradoOK = false;
		try{
			borradoOK = (boolean) t.get(5000, TimeUnit.MILLISECONDS);
		}catch(ExecutionException e){
			e.printStackTrace();
			Log.i("sql", "Error al borrar el juego: " + e.getMessage());
		}catch(InterruptedException | TimeoutException e1){
			e1.printStackTrace();
			Log.i("sql", "Error al borrar el juego: " + e1.getMessage());
		}finally{
			es.shutdown();
		}
		return borradoOK;
	}

	public static boolean actualizarJuego(Juego juegoNew, String idJuegoOld){
		FutureTask t = new FutureTask(new TareaActualizarJuego(juegoNew, idJuegoOld));
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.submit(t);
		boolean actualizadoOK = false;
		try{
			actualizadoOK = (boolean) t.get(5000, TimeUnit.MILLISECONDS);
		}catch(ExecutionException e){
			e.printStackTrace();
			Log.i("sql", "Error al actualizar el juego: " + e.getMessage());
		}catch(InterruptedException | TimeoutException e1){
			e1.printStackTrace();
			Log.i("sql", "Error al actualizar el juego: " + e1.getMessage());
		}finally{
			es.shutdown();
		}
		return actualizadoOK;
	}
}
