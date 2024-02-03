package es.carlosmilena.pm_carlosmilena_jdbc_mysql.activities.vistas;

import static android.widget.Toast.LENGTH_SHORT;
import static es.carlosmilena.pm_carlosmilena_jdbc_mysql.activities.vistas.AutenticacionActivity.EMAIL_KEY;
import static es.carlosmilena.pm_carlosmilena_jdbc_mysql.activities.vistas.AutenticacionActivity.PASSWORD_KEY;
import static es.carlosmilena.pm_carlosmilena_jdbc_mysql.activities.vistas.AutenticacionActivity.SHARED_PREFS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.carlosmilena.pm_carlosmilena_jdbc_mysql.R;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.clases.Usuario;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.controladores.UsuarioController;

public class CrearCuentaActivity extends AppCompatActivity{
	EditText etNuevoCorreo;
	EditText etNuevoPassword;
	EditText etNuevoRepetirPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crear_cuenta);
		etNuevoCorreo = (EditText) findViewById(R.id.etNuevoCorreo);
		etNuevoPassword = (EditText) findViewById(R.id.etNuevoPassword);
		etNuevoRepetirPassword = (EditText) findViewById(R.id.etNuevoRepetirPassword);
	}

	public void registrarCuenta(View view){
		String email = String.valueOf(etNuevoCorreo.getText()).trim();
		String password = String.valueOf(etNuevoPassword.getText()).trim();
		String passwordConfirmar = String.valueOf(etNuevoRepetirPassword.getText()).trim();
		boolean camposIntroducidos = true;
		if(email.isEmpty()){
			camposIntroducidos = false;
			etNuevoCorreo.setError("Cuenta Email no puede estar vacío");
		}
		if(password.isEmpty()){
			camposIntroducidos = false;
			etNuevoPassword.setError("Contraseña no puede estar vacío");
		}
		if(passwordConfirmar.isEmpty()){
			camposIntroducidos = false;
			etNuevoRepetirPassword.setError("Confirmar contraseña no puede estar vacío");
		}
		if((!password.isEmpty() && !passwordConfirmar.isEmpty()) &&
		   !password.equals(passwordConfirmar)){
			camposIntroducidos = false;
			etNuevoRepetirPassword.setError("Confirmar contraseña es distinto a contraseña");
		}
		if(!camposIntroducidos){
			Toast.makeText(CrearCuentaActivity.this, "Error en la obtención de datos",
					LENGTH_SHORT).show();
			return;
		}
		Usuario usuario = new Usuario(email, password);
		boolean esInsertado = UsuarioController.insertarUsuario(usuario);
		if(esInsertado){
			Toast.makeText(getApplicationContext(), "Cuenta registrada correctamente",
					Toast.LENGTH_SHORT).show();
			SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFS,
					Context.MODE_PRIVATE).edit();
			editor.putString(EMAIL_KEY, email);
			editor.putString(PASSWORD_KEY, password);
			editor.apply();
			startActivity(new Intent(CrearCuentaActivity.this, AutenticacionActivity.class));
		}else{
			Toast.makeText(CrearCuentaActivity.this, "Error al registrar la cuenta",
					Toast.LENGTH_LONG).show();
		}

		/*
		StringRequest request = new StringRequest(Request.Method.POST,
				ConfiguracionDB.DIRECCION_URL_RAIZ +
				"/registrar.php", new Response.Listener<String>(){
			@Override
			public void onResponse(String response){
				if(response.equalsIgnoreCase("registro ok")){
					Toast.makeText(getApplicationContext(), "Cuenta registrada correctamente",
							Toast.LENGTH_SHORT).show();
					SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFS
							, Context.MODE_PRIVATE).edit();
					editor.putString(EMAIL_KEY, email);
					editor.putString(PASSWORD_KEY, password);
					editor.apply();
					startActivity(new Intent(CrearCuentaActivity.this, AutenticacionActivity
					.class));
				}else{
					Toast.makeText(CrearCuentaActivity.this,
							"Error: " + response, Toast.LENGTH_LONG).show();
				}
			}
		}, new Response.ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError error){
				Toast.makeText(getApplicationContext(), String.valueOf(error.getMessage()),
						Toast.LENGTH_SHORT).show();
			}
		}){
			@Override
			protected Map<String, String> getParams(){
				Map<String, String> params = new HashMap<>();
				params.put("email", email);
				params.put("password", password);
				return params;
			}
		};
		RequestQueue requestQueue = Volley.newRequestQueue(this);
		requestQueue.add(request);*/
	}

	public void salirDeCrearCuenta(View view){
		Intent intent = new Intent(CrearCuentaActivity.this, AutenticacionActivity.class);
		startActivity(intent);
	}
}