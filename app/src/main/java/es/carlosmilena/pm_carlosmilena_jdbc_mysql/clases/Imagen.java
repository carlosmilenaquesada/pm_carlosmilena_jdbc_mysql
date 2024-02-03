package es.carlosmilena.pm_carlosmilena_jdbc_mysql.clases;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Imagen implements Parcelable{
	private String idImagen;
	private String imagenEnBlob;

	public Imagen(){
	}

	public Imagen(String idImagen, String imagenEnBlob){
		this.idImagen = idImagen;
		this.imagenEnBlob = imagenEnBlob;
	}

	public String getIdImagen(){
		return idImagen;
	}

	public void setIdImagen(String idImagen){
		this.idImagen = idImagen;
	}

	public String getImagenEnBlob(){
		return imagenEnBlob;
	}

	public void setImagenEnBlob(String imagenEnBlob){
		this.imagenEnBlob = imagenEnBlob;
	}

	@Override
	public boolean equals(Object o){
		if(this == o){
			return true;
		}
		if(o == null || getClass() != o.getClass()){
			return false;
		}
		Imagen imagen1 = (Imagen) o;
		return idImagen.equals(imagen1.idImagen) && Objects.equals(imagenEnBlob, imagen1.imagenEnBlob);
	}

	@Override
	public int hashCode(){
		return Objects.hash(idImagen, imagenEnBlob);
	}

	@Override
	public String toString(){
		return "Imagen{" + "idImagen='" + idImagen + '\'' + ", imagenEnBlob='" + imagenEnBlob + '\'' + '}';
	}

	@Override
	public int describeContents(){
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags){
		dest.writeString(this.idImagen);
		dest.writeString(this.imagenEnBlob);
	}

	public void readFromParcel(Parcel source){
		this.idImagen = source.readString();
		this.imagenEnBlob = source.readString();
	}

	protected Imagen(Parcel in){
		this.idImagen = in.readString();
		this.imagenEnBlob = in.readString();
	}

	public static final Creator<Imagen> CREATOR = new Creator<Imagen>(){
		@Override
		public Imagen createFromParcel(Parcel source){
			return new Imagen(source);
		}

		@Override
		public Imagen[] newArray(int size){
			return new Imagen[size];
		}
	};
}
