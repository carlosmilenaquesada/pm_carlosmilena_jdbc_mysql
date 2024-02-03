package es.carlosmilena.pm_carlosmilena_jdbc_mysql.activities.vistas;

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

public class AutenticacionActivity extends AppCompatActivity{
	public static final String EMAIL_KEY =
			"es.carlosmilena.pm_carlosmilena_jdbc_mysql" + ".autenticacionactivity.email";
	public static final String PASSWORD_KEY = "es.carlosmilena.pm_carlosmilena_jdbc_mysql" +
											  ".autenticacionactivity.password";
	public static final String SHARED_PREFS = "es.carlosmilena.pm_carlosmilena_jdbc_mysql" +
											  ".autenticacionactivity.shared_prefs";
	EditText etCorreo;
	EditText etPassword;
	SharedPreferences sharedpreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_autenticacion);
		etCorreo = (EditText) findViewById(R.id.etCorreoAcceso);
		etPassword = (EditText) findViewById(R.id.etPasswordAcceso);
		sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
		if(sharedpreferences.contains(EMAIL_KEY) && sharedpreferences.contains(PASSWORD_KEY)){
			etCorreo.setText(sharedpreferences.getString(EMAIL_KEY, ""));
			etPassword.setText(sharedpreferences.getString(PASSWORD_KEY, ""));
		}
	}

	public void acceder(View view){
		String email = String.valueOf(etCorreo.getText()).trim();
		String password = String.valueOf(etPassword.getText()).trim();
		boolean camposIntroducidos = true;
		if(email.isEmpty()){
			camposIntroducidos = false;
			etCorreo.setError("Cuenta Email no puede estar vacío");
		}
		if(password.isEmpty()){
			camposIntroducidos = false;
			etPassword.setError("Contraseña no puede estar vacío");
		}
		if(!camposIntroducidos){
			Toast.makeText(AutenticacionActivity.this, "Campos requeridos vacíos",
					Toast.LENGTH_SHORT).show();
			return;
		}
		//NUEVO
		Usuario usuario = UsuarioController.obtenerUsuarioController(email, password);
		if(usuario == null){
			Toast.makeText(AutenticacionActivity.this, "Usuario o password incorrectos",
					Toast.LENGTH_LONG).show();
		}else{
			SharedPreferences.Editor editor = sharedpreferences.edit();
			editor.putString(EMAIL_KEY, email);
			editor.putString(PASSWORD_KEY, password);
			editor.apply();
			Toast.makeText(AutenticacionActivity.this, "Autenticación correcta",
					Toast.LENGTH_SHORT).show();
			startActivity(new Intent(getApplicationContext(), MenuGeneralActivity.class));
		}
		//VIEJO
		/*StringRequest request = new StringRequest(Request.Method.POST,
				ConfiguracionDB.DIRECCION_URL_RAIZ +
				"/loguear.php", new Response.Listener<String>(){
			@Override
			public void onResponse(String response){
				if(response.equalsIgnoreCase("autenticacion ok")){
					SharedPreferences.Editor editor = sharedpreferences.edit();
					editor.putString(EMAIL_KEY, email);
					editor.putString(PASSWORD_KEY, password);
					editor.apply();
					Toast.makeText(AutenticacionActivity.this, "Autenticación correcta",
							Toast.LENGTH_SHORT).show();
					startActivity(new Intent(getApplicationContext(), MenuGeneralActivity.class));
				}else{
					Toast.makeText(AutenticacionActivity.this,
							"Usuario no encontrado", Toast.LENGTH_LONG).show();
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
		RequestQueue requestQueue = Volley.newRequestQueue(AutenticacionActivity.this);
		requestQueue.add(request);*/
	}

	public void cerrarSesion(View view){
		SharedPreferences.Editor editor = sharedpreferences.edit();
		editor.clear();
		editor.apply();
		Toast.makeText(this, "Ha cerrado la sesión", Toast.LENGTH_LONG).show();
	}

	public void irACrearNuevaCuenta(View view){
		Intent intent = new Intent(AutenticacionActivity.this, CrearCuentaActivity.class);
		startActivity(intent);
	}
}