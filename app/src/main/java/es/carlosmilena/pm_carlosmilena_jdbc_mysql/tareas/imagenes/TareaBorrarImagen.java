package es.carlosmilena.pm_carlosmilena_jdbc_mysql.tareas.imagenes;

import java.util.concurrent.Callable;

import es.carlosmilena.pm_carlosmilena_jdbc_mysql.modeloDB.sentenciassql.ImagenDB;

public class TareaBorrarImagen implements Callable<Boolean>{
	private String idImagen = null;

	public TareaBorrarImagen(String idImagen){
		this.idImagen = idImagen;
	}

	@Override
	public Boolean call() throws Exception{
		try{
			boolean borradoOK = ImagenDB.borrarImagen(idImagen);
			return borradoOK;
		}catch(Exception e){
			return false;
		}
	}
}

