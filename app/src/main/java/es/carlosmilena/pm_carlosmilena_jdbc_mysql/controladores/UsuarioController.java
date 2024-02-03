package es.carlosmilena.pm_carlosmilena_jdbc_mysql.controladores;

import android.util.Log;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


import es.carlosmilena.pm_carlosmilena_jdbc_mysql.clases.Usuario;

import es.carlosmilena.pm_carlosmilena_jdbc_mysql.tareas.usuarios.TareaInsertarUsuario;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.tareas.usuarios.TareaObtenerUsuario;

public class UsuarioController{
	public static Usuario obtenerUsuarioController(String email, String password){
		Usuario usuario = null;
		FutureTask t = new FutureTask(new TareaObtenerUsuario(email, password));
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.submit(t);
		try{
			// Especifica un tiempo de espera razonable (por ejemplo, 5 segundos)
			usuario = (Usuario) t.get(5000, TimeUnit.MILLISECONDS);

		}catch(ExecutionException e){
			// Maneja la excepción de ejecución
			e.printStackTrace();
			// Registra el error de alguna manera
			Log.i("sql", "Error al obtener usuario: " + e.getMessage());
		}catch(InterruptedException | TimeoutException e){
			// Maneja la excepción de interrupción o tiempo de espera agotado
			e.printStackTrace();
			// Registra el error de alguna manera
			Log.i("sql", "Error al obtener usuario: " + e.getMessage());
		}finally{
			// Intenta detener el servicio de ejecución
			es.shutdown();
		}
		return usuario;
	}
	//------------------------------------------------------------------------------------------

	public static boolean insertarUsuario(Usuario usuario){
		FutureTask t = new FutureTask(new TareaInsertarUsuario(usuario));
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
			Log.i("sql", "Error al insertar el usuario: " + e.getMessage());
		}catch(InterruptedException | TimeoutException e1){
			// Maneja la excepción de interrupción o tiempo de espera agotado
			e1.printStackTrace();
			// Registra el error de alguna manera
			Log.i("sql", "Error al insertar el usuario: " + e1.getMessage());
		}finally{
			// Intenta detener el servicio de ejecución
			es.shutdown();
		}
		return insercionOK;
	}
}
