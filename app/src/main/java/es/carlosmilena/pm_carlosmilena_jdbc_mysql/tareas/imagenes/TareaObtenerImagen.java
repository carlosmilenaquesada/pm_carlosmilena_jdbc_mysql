package es.carlosmilena.pm_carlosmilena_jdbc_mysql.tareas.imagenes;

import java.util.concurrent.Callable;

import es.carlosmilena.pm_carlosmilena_jdbc_mysql.clases.Imagen;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.modeloDB.sentenciassql.ImagenDB;

public class TareaObtenerImagen implements Callable<Imagen>{
	private String idImagen;

	public TareaObtenerImagen(String idImagen){
		this.idImagen = idImagen;
	}

	@Override
	public Imagen call() throws Exception{
		Imagen imagen = ImagenDB.obtenerImagen(idImagen);
		return imagen;
	}
}
