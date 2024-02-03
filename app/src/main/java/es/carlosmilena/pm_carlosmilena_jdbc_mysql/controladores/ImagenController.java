package es.carlosmilena.pm_carlosmilena_jdbc_mysql.controladores;

import android.util.Log;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import es.carlosmilena.pm_carlosmilena_jdbc_mysql.clases.Imagen;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.tareas.imagenes.TareaActualizarImagen;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.tareas.imagenes.TareaBorrarImagen;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.tareas.imagenes.TareaInsertarImagen;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.tareas.imagenes.TareaObtenerImagen;

public class ImagenController{
	public static Imagen obtenerImagenController(String idImagen){
		Imagen imagen = null;
		FutureTask t = new FutureTask(new TareaObtenerImagen(idImagen));
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.submit(t);
		try{
			// Especifica un tiempo de espera razonable (por ejemplo, 5 segundos)
			imagen = (Imagen) t.get(5000, TimeUnit.MILLISECONDS);
		}catch(ExecutionException e){
			// Maneja la excepción de ejecución
			e.printStackTrace();
			// Registra el error de alguna manera
			Log.i("sql", "Error al obtener imagen: " + e.getMessage());
		}catch(InterruptedException | TimeoutException e){
			// Maneja la excepción de interrupción o tiempo de espera agotado
			e.printStackTrace();
			// Registra el error de alguna manera
			Log.i("sql", "Error al obtener imagen: " + e.getMessage());
		}finally{
			// Intenta detener el servicio de ejecución
			es.shutdown();
		}
		return imagen;
	}
	//------------------------------------------------------------------------------------------

	public static boolean insertarImagen(Imagen imagen){
		FutureTask t = new FutureTask(new TareaInsertarImagen(imagen));
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
			Log.i("sql", "Error al insertar la imagen: " + e.getMessage());
		}catch(InterruptedException | TimeoutException e1){
			// Maneja la excepción de interrupción o tiempo de espera agotado
			e1.printStackTrace();
			// Registra el error de alguna manera
			Log.i("sql", "Error al insertar la imagen: " + e1.getMessage());
		}finally{
			// Intenta detener el servicio de ejecución
			es.shutdown();
		}
		return insercionOK;
	}
	//------------------------------------------------------------------------------------------
	public static boolean borrarImagen(String idImagen)
	{
		FutureTask t = new FutureTask(new TareaBorrarImagen(idImagen));
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.submit(t);
		boolean borradoOK = false;
		try {
			borradoOK = (boolean) t.get(5000, TimeUnit.MILLISECONDS);
		} catch (ExecutionException e) {
			// Maneja la excepción de ejecución
			e.printStackTrace();
			// Registra el error de alguna manera
			Log.i("sql", "Error al borrar la imagen: " + e.getMessage());
		} catch (InterruptedException | TimeoutException e1) {
			// Maneja la excepción de interrupción o tiempo de espera agotado
			e1.printStackTrace();
			// Registra el error de alguna manera
			Log.i("sql", "Error al borrar la imagen: " + e1.getMessage());
		}  finally {
			// Intenta detener el servicio de ejecución
			es.shutdown();
		}
		return borradoOK;

	}
	//------------------------------------------------------------------------------------------
	public static boolean actualizarImagen(Imagen imagenNew, String idImagenOld) {
		FutureTask t = new FutureTask(new TareaActualizarImagen(imagenNew, idImagenOld));
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.submit(t);
		boolean actualizadoOK = false;
		try {
			actualizadoOK = (boolean) t.get(5000, TimeUnit.MILLISECONDS);
		} catch (ExecutionException e) {
			// Maneja la excepción de ejecución
			e.printStackTrace();
			// Registra el error de alguna manera
			Log.i("sql", "Error al actualizar la imagen: " + e.getMessage());
		} catch (InterruptedException | TimeoutException e1) {
			// Maneja la excepción de interrupción o tiempo de espera agotado
			e1.printStackTrace();
			// Registra el error de alguna manera
			Log.i("sql", "Error al actualizar ela imagen: " + e1.getMessage());
		}  finally {
			// Intenta detener el servicio de ejecución
			es.shutdown();
		}
		return actualizadoOK;
	}
}
