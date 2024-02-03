package es.carlosmilena.pm_carlosmilena_jdbc_mysql.tareas.usuarios;

import java.util.concurrent.Callable;

import es.carlosmilena.pm_carlosmilena_jdbc_mysql.clases.Usuario;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.modeloDB.sentenciassql.UsuarioDB;

public class TareaObtenerUsuario implements Callable<Usuario>{
	private String email;
	private String password;

	public TareaObtenerUsuario(String email, String password){
		this.email = email;
		this.password = password;
	}

	@Override
	public Usuario call() throws Exception{
		Usuario usuario = UsuarioDB.obtenerUsuario(email, password);
		return usuario;
	}
}


