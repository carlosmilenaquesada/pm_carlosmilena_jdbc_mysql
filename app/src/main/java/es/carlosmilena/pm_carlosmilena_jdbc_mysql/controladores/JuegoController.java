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
	//------------------------------------------------------------------------------------------
	public static ArrayList<Juego> obtenerJuegosController(){
		ArrayList<Juego> juegos = null;
		FutureTask t = new FutureTask(new TareaObtenerJuegos());
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.submit(t);
		try{
			// Especifica un tiempo de espera razonable (por ejemplo, 5 segundos)
			juegos = (ArrayList<Juego>) t.get(5000, TimeUnit.MILLISECONDS);
		}catch(ExecutionException e){
			// Maneja la excepción de ejecución
			e.printStackTrace();
			// Registra el error de alguna manera
			Log.i("sql", "Error al obtener juegos: " + e.getMessage());
		}catch(InterruptedException | TimeoutException e){
			// Maneja la excepción de interrupción o tiempo de espera agotado
			e.printStackTrace();
			// Registra el error de alguna manera
			Log.i("sql", "Error al obtener juegos: " + e.getMessage());
		}finally{
			// Intenta detener el servicio de ejecución
			es.shutdown();
		}
		return juegos;
	}
	//------------------------------------------------------------------------------------------

	public static boolean insertarJuego(Juego juego){
		FutureTask t = new FutureTask(new TareaInsertarJuego(juego));
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.submit(t);
		boolean insercionOK = false;
		try{
			// Especifica un tiempo de espera razonable (por ejemplo, 5 segundos)
			insercionOK = (boolean) t.get(5000, TimeUnit.MILLISECONDS);
		}catch(ExecutionException e){
			// Maneja la excepción de ejecución
			e.printStackTrace();
			// Registra el error de alguna manera
			Log.i("sql", "Error al insertar el juego: " + e.getMessage());
		}catch(InterruptedException | TimeoutException e1){
			// Maneja la excepción de interrupción o tiempo de espera agotado
			e1.printStackTrace();
			// Registra el error de alguna manera
			Log.i("sql", "Error al insertar el juego: " + e1.getMessage());
		}finally{
			// Intenta detener el servicio de ejecución
			es.shutdown();
		}
		return insercionOK;
	}

	//------------------------------------------------------------------------------------------
	public static boolean borrarJuego(String idJuego){
		FutureTask t = new FutureTask(new TareaBorrarJuego(idJuego));
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.submit(t);
		boolean borradoOK = false;
		try{
			borradoOK = (boolean) t.get(5000, TimeUnit.MILLISECONDS);
		}catch(ExecutionException e){
			// Maneja la excepción de ejecución
			e.printStackTrace();
			// Registra el error de alguna manera
			Log.i("sql", "Error al borrar el juego: " + e.getMessage());
		}catch(InterruptedException | TimeoutException e1){
			// Maneja la excepción de interrupción o tiempo de espera agotado
			e1.printStackTrace();
			// Registra el error de alguna manera
			Log.i("sql", "Error al borrar el juego: " + e1.getMessage());
		}finally{
			// Intenta detener el servicio de ejecución
			es.shutdown();
		}
		return borradoOK;
	}

	//------------------------------------------------------------------------------------------
	public static boolean actualizarJuego(Juego juegoNew, String idJuegoOld){
		FutureTask t = new FutureTask(new TareaActualizarJuego(juegoNew, idJuegoOld));
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.submit(t);
		boolean actualizadoOK = false;
		try{
			actualizadoOK = (boolean) t.get(5000, TimeUnit.MILLISECONDS);
		}catch(ExecutionException e){
			// Maneja la excepción de ejecución
			e.printStackTrace();
			// Registra el error de alguna manera
			Log.i("sql", "Error al actualizar el juego: " + e.getMessage());
		}catch(InterruptedException | TimeoutException e1){
			// Maneja la excepción de interrupción o tiempo de espera agotado
			e1.printStackTrace();
			// Registra el error de alguna manera
			Log.i("sql", "Error al actualizar el juego: " + e1.getMessage());
		}finally{
			// Intenta detener el servicio de ejecución
			es.shutdown();
		}
		return actualizadoOK;
	}
}
