package es.carlosmilena.pm_carlosmilena_jdbc_mysql.tareas.juegos;

import java.util.concurrent.Callable;

import es.carlosmilena.pm_carlosmilena_jdbc_mysql.clases.Juego;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.modeloDB.sentenciassql.JuegoDB;


public class TareaInsertarJuego implements Callable<Boolean> {
    private Juego juego = null;

    public TareaInsertarJuego(Juego juego) {
        this.juego = juego;
    }

    @Override
    public Boolean call() throws Exception {
        try{
            boolean insercionOK = JuegoDB.guardarJuego(juego);
            return insercionOK;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
