package es.carlosmilena.pm_carlosmilena_jdbc_mysql.activities.vistas;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.R;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.clases.Juego;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.controladores.JuegoController;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.recyclerview.CatalogoJuegosAdapter;

public class MostrarCatalogoActivity extends AppCompatActivity{
	public ArrayList<Juego> juegos;
	public CatalogoJuegosAdapter adapter;
	private EditText etBuscarJuego;

	@Override
	protected void onStart(){
		super.onStart();
		SharedPreferences sharedpreferences =
				getSharedPreferences(AutenticacionActivity.SHARED_PREFS, Context.MODE_PRIVATE);
		String email = sharedpreferences.getString(AutenticacionActivity.EMAIL_KEY, null);
		String password = sharedpreferences.getString(AutenticacionActivity.PASSWORD_KEY, null);
		if(email == null || password == null){
			Toast.makeText(getApplicationContext(), "No tienes permiso para esta sección",
					Toast.LENGTH_SHORT).show();
			startActivity(new Intent(getApplicationContext(), AutenticacionActivity.class));
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mostrar_catalogo);
		RecyclerView rvListaJuegos = (RecyclerView) findViewById(R.id.rvListaJuegos);
		etBuscarJuego = (EditText) findViewById(R.id.etBuscarJuego);
		juegos = new ArrayList<Juego>();
		adapter = new CatalogoJuegosAdapter(this, juegos);
		rvListaJuegos.setAdapter(adapter);
		mostrarJuegos();
		int orientation = getResources().getConfiguration().orientation;
		if(orientation == Configuration.ORIENTATION_LANDSCAPE){
			// In landscape
			rvListaJuegos.setLayoutManager(new GridLayoutManager(this, 2));
		}else{
			// In portrait
			rvListaJuegos.setLayoutManager(new LinearLayoutManager(this));
		}
	}

	public void mostrarJuegos(){
		//NUEVO
		juegos = JuegoController.obtenerJuegosController();
		if(juegos == null){
			Toast.makeText(MostrarCatalogoActivity.this, "Error al obtener los juegos",
					Toast.LENGTH_SHORT).show();
			return;
		}
		adapter.setJuegos(juegos);
		adapter.notifyDataSetChanged();
		//VIEJO
		/*StringRequest request = new StringRequest(Request.Method.POST,
				ConfiguracionDB.DIRECCION_URL_RAIZ +
				"/mostrar.php", new Response.Listener<String>(){
			@Override
			public void onResponse(String response){
				juegos.clear();
				try{
					JSONObject jsonObject = new JSONObject(response);
					String exito = jsonObject.getString("exito");
					JSONArray jsonArray = jsonObject.getJSONArray("tablajuego");
					if(exito.equals("1")){
						for(int i = 0; i < jsonArray.length(); i++){
							JSONObject object = jsonArray.getJSONObject(i);
							String identificador = object.getString("identificador");
							String plataforma = object.getString("plataforma");
							String nombrejuego = object.getString("nombrejuego");
							String genero = object.getString("genero");
							double preciojuego = Double.valueOf(object.getString("preciojuego"));
							Juego juego = new Juego(identificador, plataforma, nombrejuego, genero
									, preciojuego);
							juegos.add(juego);
						}
						adapter.setJuegos(juegos);
						adapter.notifyDataSetChanged();
					}
				}catch(JSONException ex){
					throw new RuntimeException(ex);
				}
			}
		}, new Response.ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError error){
				Toast.makeText(MostrarCatalogoActivity.this, error.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
		}){
		};
		RequestQueue requestQueue = Volley.newRequestQueue(MostrarCatalogoActivity.this);
		requestQueue.add(request);*/
	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	public void buscarJuego(View view){
		String texto = String.valueOf(etBuscarJuego.getText());
		//NUEVO
		juegos = JuegoController.obtenerJuegosController();
		if(juegos == null){
			Toast.makeText(MostrarCatalogoActivity.this, "Error al obtener los juegos",
					Toast.LENGTH_SHORT).show();
			return;
		}
		juegos.removeIf(juego -> !juego.getNombreJuego().contains(texto));
		adapter.setJuegos(juegos);
		adapter.notifyDataSetChanged();
		//VIEJO
		/*StringRequest request = new StringRequest(Request.Method.POST,
				ConfiguracionDB.DIRECCION_URL_RAIZ +
				"/mostrar.php", new Response.Listener<String>(){
			@Override
			public void onResponse(String response){
				juegos.clear();
				try{
					JSONObject jsonObject = new JSONObject(response);
					String exito = jsonObject.getString("exito");
					JSONArray jsonArray = jsonObject.getJSONArray("tablajuego");
					if(exito.equals("1")){
						for(int i = 0; i < jsonArray.length(); i++){
							JSONObject object = jsonArray.getJSONObject(i);
							String nombrejuego = object.getString("nombrejuego");
							if(nombrejuego.contains(texto)){
								String identificador = object.getString("identificador");
								String plataforma = object.getString("plataforma");
								String genero = object.getString("genero");
								double preciojuego = Double.parseDouble(object.getString(
										"preciojuego"));
								Juego juego = new Juego(identificador, plataforma, nombrejuego,
										genero, preciojuego);
								juegos.add(juego);
							}
						}
						adapter.setJuegos(juegos);
						adapter.notifyDataSetChanged();
					}
				}catch(JSONException ex){
					throw new RuntimeException(ex);
				}
			}
		}, new Response.ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError error){
				Toast.makeText(MostrarCatalogoActivity.this, error.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
		}){
		};
		RequestQueue requestQueue = Volley.newRequestQueue(MostrarCatalogoActivity.this);
		requestQueue.add(request);*/
	}

	public void volver(View view){
		startActivity(new Intent(MostrarCatalogoActivity.this, MenuGeneralActivity.class));
	}
}
