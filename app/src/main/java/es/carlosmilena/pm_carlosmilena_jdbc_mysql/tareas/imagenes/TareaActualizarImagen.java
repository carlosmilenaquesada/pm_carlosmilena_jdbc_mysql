package es.carlosmilena.pm_carlosmilena_jdbc_mysql.tareas.imagenes;

import java.util.concurrent.Callable;

import es.carlosmilena.pm_carlosmilena_jdbc_mysql.clases.Imagen;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.modeloDB.sentenciassql.ImagenDB;

public class TareaActualizarImagen implements Callable<Boolean>{
	private Imagen imagenNew = null;
	private String idImagenOld = null;

	public TareaActualizarImagen(Imagen imagenNew, String idImagenOld) {
		this.imagenNew = imagenNew;
		this.idImagenOld = idImagenOld;
	}

	@Override
	public Boolean call() throws Exception {
		try{
			boolean actualizadoOK = ImagenDB.actualizarImagen(imagenNew, idImagenOld);
			return actualizadoOK;
		}
		catch (Exception e)
		{
			return false;
		}
	}
}
