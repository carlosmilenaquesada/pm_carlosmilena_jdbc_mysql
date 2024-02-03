package es.carlosmilena.pm_carlosmilena_jdbc_mysql.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Juego implements Parcelable{
	private String identificador;
	private String plataforma;
	private String nombreJuego;
	private String genero;
	private double precioJuego;

	public Juego(String identificador, String plataforma, String nombreJuego, String genero,
				 double precioJuego){
		this.identificador = identificador;
		this.plataforma = plataforma;
		this.nombreJuego = nombreJuego;
		this.genero = genero;
		this.precioJuego = precioJuego;
	}

	public String getIdentificador(){
		return identificador;
	}

	public String getPlataforma(){
		return plataforma;
	}

	public String getNombreJuego(){
		return nombreJuego;
	}

	public String getGenero(){
		return genero;
	}

	public double getPrecioJuego(){
		return precioJuego;
	}

	public void setIdentificador(String identificador){
		this.identificador = identificador;
	}

	public void setPlataforma(String plataforma){
		this.plataforma = plataforma;
	}

	public void setNombreJuego(String nombreJuego){
		this.nombreJuego = nombreJuego;
	}

	public void setGenero(String genero){
		this.genero = genero;
	}

	public void setPrecioJuego(double precioJuego){
		this.precioJuego = precioJuego;
	}

	@Override
	public boolean equals(Object o){
		if(this == o){
			return true;
		}
		if(o == null || getClass() != o.getClass()){
			return false;
		}
		Juego juego = (Juego) o;
		return identificador.equals(juego.identificador);
	}

	@Override
	public int hashCode(){
		return Objects.hash(identificador);
	}

	@NonNull
	@Override
	public String toString(){
		return "Juego{" + "identificador='" + identificador + '\'' + ", plataforma='" + plataforma +
			   '\'' + ", nombreJuego='" + nombreJuego + '\'' + ", genero='" + genero + '\'' +
			   ", precioJuego=" + precioJuego + '}';
	}

	@Override
	public int describeContents(){
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags){
		dest.writeString(this.identificador);
		dest.writeString(this.plataforma);
		dest.writeString(this.nombreJuego);
		dest.writeString(this.genero);
		dest.writeDouble(this.precioJuego);//puede que de fallo
	}

	public void readFromParcel(Parcel source){
		this.identificador = source.readString();
		this.plataforma = source.readString();
		this.nombreJuego = source.readString();
		this.genero = source.readString();
		this.precioJuego = source.readDouble();
	}

	protected Juego(Parcel in){
		this.identificador = in.readString();
		this.plataforma = in.readString();
		this.nombreJuego = in.readString();
		this.genero = in.readString();
		this.precioJuego = in.readDouble();
	}

	public static final Creator<Juego> CREATOR = new Creator<Juego>(){
		@Override
		public Juego createFromParcel(Parcel source){
			return new Juego(source);
		}

		@Override
		public Juego[] newArray(int size){
			return new Juego[size];
		}
	};
}
