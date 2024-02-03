package es.carlosmilena.pm_carlosmilena_jdbc_mysql.activities.vistas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import es.carlosmilena.pm_carlosmilena_jdbc_mysql.R;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.clases.Imagen;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.clases.Juego;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.controladores.ImagenController;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.controladores.JuegoController;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.utilidades.Herramientas;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.utilidades.ImagenesBlobBitmap;

public class AgregarNuevoJuegoActivity extends AppCompatActivity{
	public static final int NUEVA_IMAGEN = 1;
	Uri imagen_seleccionada = null;
	private Spinner spNuevoPlataforma;
	private EditText edtIdJuego;
	private EditText edtNuevoNombreJuego;
	private Spinner spNuevoGenero;
	private EditText edtNuevoPrecioJuego;
	private ImageView ivNuevoImagen;
	ArrayAdapter<String> adapterPlataforma;
	ArrayAdapter<String> adapterGenero;

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
		setContentView(R.layout.activity_agregar_nuevo_juego);
		edtIdJuego = (EditText) findViewById(R.id.edtIdJuego);
		edtNuevoNombreJuego = (EditText) findViewById(R.id.edtNuevoNombreJuego);
		edtNuevoPrecioJuego = (EditText) findViewById(R.id.edtNuevoPrecioJuego);
		ivNuevoImagen = (ImageView) findViewById(R.id.ivNuevoImagen);
		/*configuro el adapter del spinner de consolas con información de la base de datos*/
		spNuevoPlataforma = (Spinner) findViewById(R.id.spNuevoPlataforma);
		adapterPlataforma = new ArrayAdapter<>(this, R.layout.spinner_cerrado);
		adapterPlataforma.setDropDownViewResource(R.layout.spinner_desplegado);
		Herramientas.rellenarSpinnerConInfoBd(this, adapterPlataforma, "carlosmilena_consolas", "nombreconsola"/*
				, "/obtener-plataformas.php"*/);
		spNuevoPlataforma.setAdapter(adapterPlataforma);
		/*configuro el adapter del spinner de género con información de la base de datos*/
		spNuevoGenero = (Spinner) findViewById(R.id.spNuevoGenero);
		adapterGenero = new ArrayAdapter<>(this, R.layout.spinner_cerrado);
		adapterGenero.setDropDownViewResource(R.layout.spinner_desplegado);
		Herramientas.rellenarSpinnerConInfoBd(this, adapterGenero, "carlosmilena_generos", "nombregenero"/*,
				"/obtener-generos.php"*/);
		spNuevoGenero.setAdapter(adapterGenero);
	}

	public void insertarJuegoConImagen(View view){
		String identificador = String.valueOf(edtIdJuego.getText());
		String nombreJuego = String.valueOf(edtNuevoNombreJuego.getText());
		String precioVenta = String.valueOf(edtNuevoPrecioJuego.getText());
		boolean camposIntroducidos = true;
		if(identificador.isEmpty()){
			camposIntroducidos = false;
			edtIdJuego.setError("Identificador no puede estar vacío");
		}
		if(nombreJuego.isEmpty()){
			camposIntroducidos = false;
			edtNuevoNombreJuego.setError("Nombre del juego no puede estar vacío");
		}
		if(precioVenta.isEmpty()){
			camposIntroducidos = false;
			edtNuevoPrecioJuego.setError("Precio de venta no puede estar vacío");
		}
		if(!camposIntroducidos){
			Toast.makeText(AgregarNuevoJuegoActivity.this, "Campos requeridos vacíos",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if(spNuevoPlataforma.getSelectedItemPosition() == -1 ||
		   (spNuevoGenero.getSelectedItemPosition() == -1)){
			Toast.makeText(AgregarNuevoJuegoActivity.this,
					"No pudo cargarse la lista de Géneros y" +
					" Plataformas. Cambios no guardados.", Toast.LENGTH_LONG).show();
			return;
		}
		String plataforma = spNuevoPlataforma.getSelectedItem().toString();
		String genero = spNuevoGenero.getSelectedItem().toString();
		double precioVentaDouble = Double.parseDouble(precioVenta);
		Juego juegoNuevo = new Juego(identificador, plataforma, nombreJuego, genero,
				precioVentaDouble);
		//NUEVO
		boolean esInsertado = JuegoController.insertarJuego(juegoNuevo);
		if(esInsertado){
			Toast.makeText(AgregarNuevoJuegoActivity.this, "Juego insertado", Toast.LENGTH_SHORT).show();
			if(imagen_seleccionada != null){
				insertarImagen(juegoNuevo.getIdentificador(), ivNuevoImagen);
			}
			startActivity(new Intent(getApplicationContext(), MostrarCatalogoActivity.class));
			finish();
		}else{
			Toast.makeText(AgregarNuevoJuegoActivity.this, "Error al insertar el juego",
					Toast.LENGTH_SHORT).show();
		}
		//VIEJO
		/*StringRequest request = new StringRequest(Request.Method.POST,
				ConfiguracionDB.DIRECCION_URL_RAIZ +
				"/insertar.php", new Response.Listener<String>(){
			@Override
			public void onResponse(String response){
				if(response.equalsIgnoreCase("datos insertados")){
					Toast.makeText(AgregarNuevoJuegoActivity.this, "Juego insertado",
							Toast.LENGTH_SHORT).show();
					if(imagen_seleccionada != null){
						insertarImagen(juegoNuevo.getIdentificador(), ivNuevoImagen);
					}
					startActivity(new Intent(getApplicationContext(),
							MostrarCatalogoActivity.class));
					finish();
				}else{
					Toast.makeText(AgregarNuevoJuegoActivity.this,
							"Error: " + response, Toast.LENGTH_LONG).show();
				}
			}
		}, new Response.ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError error){
				Toast.makeText(AgregarNuevoJuegoActivity.this, error.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
		}){
			@Override
			protected Map<String, String> getParams(){
				Map<String, String> params = new HashMap<>();
				params.put("identificador", juegoNuevo.getIdentificador());
				params.put("plataforma", juegoNuevo.getPlataforma());
				params.put("nombrejuego", juegoNuevo.getNombreJuego());
				params.put("genero", juegoNuevo.getGenero());
				params.put("preciojuego", String.valueOf(juegoNuevo.getPrecioJuego()));
				return params;
			}
		};
		RequestQueue requestQueue = Volley.newRequestQueue(AgregarNuevoJuegoActivity.this);
		requestQueue.add(request);*/
	}

	public void cambiarImagenCreacion(View view){
		Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
		getIntent.setType("image/*");
		Intent pickIntent = new Intent(Intent.ACTION_PICK,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		pickIntent.setType("image/*");
		Intent chooserIntent = Intent.createChooser(getIntent, "Seleccione una imagen");
		chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
		startActivityForResult(chooserIntent, NUEVA_IMAGEN);
	}

	private void insertarImagen(String idimagen, ImageView ivNuevoImagen){
		//NUEVO
		ivNuevoImagen.buildDrawingCache();
		Bitmap foto_bm = ivNuevoImagen.getDrawingCache();
		byte[] fotobytes = ImagenesBlobBitmap.bitmap_to_bytes_png(foto_bm);
		String fotostring = ImagenesBlobBitmap.byte_to_string(fotobytes);
		Imagen imagen = new Imagen(idimagen, fotostring);
		boolean esInsertada = ImagenController.insertarImagen(imagen);
		if(!esInsertada){
			Toast.makeText(AgregarNuevoJuegoActivity.this, "Error al insertar la imagen",
					Toast.LENGTH_SHORT).show();
		}
		//VIEJO
		/*StringRequest request = new StringRequest(Request.Method.POST,
				ConfiguracionDB.DIRECCION_URL_RAIZ +
				"/insertar-foto.php", new Response.Listener<String>(){
			@Override
			public void onResponse(String response){
				if(response.equalsIgnoreCase("foto insertada")){
					startActivity(new Intent(getApplicationContext(),
							MostrarCatalogoActivity.class));
					finish();
				}else{
					Toast.makeText(AgregarNuevoJuegoActivity.this, "Error al subir la foto",
							Toast.LENGTH_SHORT).show();
				}
			}
		}, new Response.ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError error){
				Toast.makeText(AgregarNuevoJuegoActivity.this, error.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
		}){
			@Override
			protected Map<String, String> getParams(){
				Map<String, String> params = new HashMap<>();
				params.put("idimagen", idimagen);
				ivNuevoImagen.buildDrawingCache();
				Bitmap foto_bm = ivNuevoImagen.getDrawingCache();
				byte[] fotobytes = ImagenesBlobBitmap.bitmap_to_bytes_png(foto_bm);
				String fotostring = ImagenesBlobBitmap.byte_to_string(fotobytes);
				params.put("imagen", fotostring);
				return params;
			}
		};
		RequestQueue requestQueue = Volley.newRequestQueue(AgregarNuevoJuegoActivity.this);
		requestQueue.add(request);*/
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == NUEVA_IMAGEN && resultCode == Activity.RESULT_OK){
			imagen_seleccionada = data.getData();
			Bitmap bitmap;
			try{
				bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
						imagen_seleccionada);
				ivNuevoImagen.setImageBitmap(bitmap);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	public void volverAtras(View view){
		startActivity(new Intent(AgregarNuevoJuegoActivity.this, MenuGeneralActivity.class));
	}
}