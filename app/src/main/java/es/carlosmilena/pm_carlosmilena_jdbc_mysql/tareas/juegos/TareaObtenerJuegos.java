package es.carlosmilena.pm_carlosmilena_jdbc_mysql.tareas.juegos;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import es.carlosmilena.pm_carlosmilena_jdbc_mysql.clases.Juego;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.modeloDB.sentenciassql.JuegoDB;

public class TareaObtenerJuegos implements Callable<ArrayList<Juego>>{
	@Override
	public ArrayList<Juego> call() throws Exception{
		ArrayList<Juego> juegos = JuegoDB.obtenerJuegos();
		return juegos;
	}
}
