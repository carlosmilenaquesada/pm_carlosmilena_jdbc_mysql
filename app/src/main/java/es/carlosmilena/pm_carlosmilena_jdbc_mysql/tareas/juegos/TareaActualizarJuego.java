package es.carlosmilena.pm_carlosmilena_jdbc_mysql.tareas.juegos;

import java.util.concurrent.Callable;

import es.carlosmilena.pm_carlosmilena_jdbc_mysql.clases.Juego;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.modeloDB.sentenciassql.JuegoDB;

public class TareaActualizarJuego implements Callable<Boolean>{
	private Juego juegoNew = null;
	private String idJuegoOld = null;

	public TareaActualizarJuego(Juego juegoNew, String idJuegoOld){
		this.juegoNew = juegoNew;
		this.idJuegoOld = idJuegoOld;
	}

	@Override
	public Boolean call() throws Exception{
		try{
			boolean actualizadoOK = JuegoDB.actualizarJuego(juegoNew, idJuegoOld);
			return actualizadoOK;
		}catch(Exception e){
			return false;
		}
	}
}
