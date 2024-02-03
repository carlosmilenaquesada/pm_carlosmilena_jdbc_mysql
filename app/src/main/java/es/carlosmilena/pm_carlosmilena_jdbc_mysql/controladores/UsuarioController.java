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
			usuario = (Usuario) t.get(5000, TimeUnit.MILLISECONDS);
		}catch(ExecutionException e){
			e.printStackTrace();
			Log.i("sql", "Error al obtener usuario: " + e.getMessage());
		}catch(InterruptedException | TimeoutException e){
			e.printStackTrace();
			Log.i("sql", "Error al obtener usuario: " + e.getMessage());
		}finally{
			es.shutdown();
		}
		return usuario;
	}

	public static boolean insertarUsuario(Usuario usuario){
		FutureTask t = new FutureTask(new TareaInsertarUsuario(usuario));
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.submit(t);
		boolean insercionOK = false;
		try{
			insercionOK = (boolean) t.get(5000, TimeUnit.MILLISECONDS);
		}catch(ExecutionException e){
			e.printStackTrace();
			Log.i("sql", "Error al insertar el usuario: " + e.getMessage());
		}catch(InterruptedException | TimeoutException e1){
			e1.printStackTrace();
			Log.i("sql", "Error al insertar el usuario: " + e1.getMessage());
		}finally{
			es.shutdown();
		}
		return insercionOK;
	}
}
