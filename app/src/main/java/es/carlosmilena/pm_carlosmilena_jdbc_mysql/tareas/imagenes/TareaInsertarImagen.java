package es.carlosmilena.pm_carlosmilena_jdbc_mysql.tareas.imagenes;

import java.util.concurrent.Callable;

import es.carlosmilena.pm_carlosmilena_jdbc_mysql.clases.Imagen;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.modeloDB.sentenciassql.ImagenDB;

public class TareaInsertarImagen implements Callable<Boolean>{
	private Imagen imagen = null;

	public TareaInsertarImagen(Imagen imagen){
		this.imagen = imagen;
	}

	@Override
	public Boolean call() throws Exception{
		try{
			boolean insercionOK = ImagenDB.guardarImagen(imagen);
			return insercionOK;
		}catch(Exception e){
			return false;
		}
	}
}
