package es.carlosmilena.pm_carlosmilena_jdbc_mysql.clases;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Usuario implements Parcelable{
	private String email;
	private String password;

	public Usuario(){

	}

	public Usuario(String email, String password){
		this.email = email;
		this.password = password;
	}

	public String getEmail(){
		return email;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getPassword(){
		return password;
	}

	public void setPassword(String password){
		this.password = password;
	}

	@Override
	public boolean equals(Object o){
		if(this == o){
			return true;
		}
		if(o == null || getClass() != o.getClass()){
			return false;
		}
		Usuario usuario = (Usuario) o;
		return email.equals(usuario.email) && password.equals(usuario.password);
	}

	@Override
	public int hashCode(){
		return Objects.hash(email, password);
	}

	@Override
	public String toString(){
		return "Usuario{" + "email='" + email + '\'' + ", password='" + password + '\'' + '}';
	}

	@Override
	public int describeContents(){
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags){
		dest.writeString(this.email);
		dest.writeString(this.password);
	}

	public void readFromParcel(Parcel source){
		this.email = source.readString();
		this.password = source.readString();
	}

	protected Usuario(Parcel in){
		this.email = in.readString();
		this.password = in.readString();
	}

	public static final Creator<Usuario> CREATOR = new Creator<Usuario>(){
		@Override
		public Usuario createFromParcel(Parcel source){
			return new Usuario(source);
		}

		@Override
		public Usuario[] newArray(int size){
			return new Usuario[size];
		}
	};
}
