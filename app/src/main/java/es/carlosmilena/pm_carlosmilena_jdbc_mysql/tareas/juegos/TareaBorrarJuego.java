package es.carlosmilena.pm_carlosmilena_jdbc_mysql.tareas.juegos;

import java.util.concurrent.Callable;

import es.carlosmilena.pm_carlosmilena_jdbc_mysql.modeloDB.sentenciassql.JuegoDB;

public class TareaBorrarJuego implements Callable<Boolean>{
	private String idJuego = null;

	public TareaBorrarJuego(String idJuego){
		this.idJuego = idJuego;
	}

	@Override
	public Boolean call() throws Exception{
		try{
			boolean borradoOK = JuegoDB.borrarJuego(idJuego);
			return borradoOK;
		}catch(Exception e){
			return false;
		}
	}
}
