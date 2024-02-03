package es.carlosmilena.pm_carlosmilena_jdbc_mysql.tareas.usuarios;

import java.util.concurrent.Callable;

import es.carlosmilena.pm_carlosmilena_jdbc_mysql.clases.Usuario;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.modeloDB.sentenciassql.UsuarioDB;

public class TareaInsertarUsuario implements Callable<Boolean>{
	private Usuario usuario = null;

	public TareaInsertarUsuario(Usuario usuario){
		this.usuario = usuario;
	}

	@Override
	public Boolean call() throws Exception{
		try{
			boolean insercionOK = UsuarioDB.crearUsuario(usuario);
			return insercionOK;
		}catch(Exception e){
			return false;
		}
	}
}
