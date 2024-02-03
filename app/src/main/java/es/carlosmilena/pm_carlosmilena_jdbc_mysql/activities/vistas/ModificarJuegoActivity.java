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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import es.carlosmilena.pm_carlosmilena_jdbc_mysql.R;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.clases.Imagen;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.clases.Juego;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.controladores.ImagenController;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.controladores.JuegoController;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.recyclerview.JuegoViewHolder;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.utilidades.Herramientas;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.utilidades.ImagenesBlobBitmap;

public class ModificarJuegoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
	public static final int NUEVA_IMAGEN = 1;
	Uri imagenSeleccionada = null;
	private EditText edtModificarIdentificador;
	private Spinner spModificarJuegoPlataforma;
	private Spinner spModificarGenero;
	private EditText edtModificarNombreJuego;
	private EditText edtModificarPrecioVenta;
	private ImageView ivModificarImagen;
	ArrayAdapter<String> adapterPlataforma;
	ArrayAdapter<String> adapterGenero;
	private Juego juego;
	boolean spinnersIniciados = false;
	String valorSpinnerGeneroInicial;
	String valorSpinnerPlataformaInicial;

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
		setContentView(R.layout.activity_modificar_juego);
		ivModificarImagen = (ImageView) findViewById(R.id.ivModificarJuegoImagen);
		Intent intent = getIntent();
		if(intent != null){
			juego = (Juego) intent.getParcelableExtra(JuegoViewHolder.EXTRA_MODIFICAR_JUEGO);
			System.out.println("mi juego" + juego);
			byte[] fotobinaria =
					(byte[]) intent.getByteArrayExtra(JuegoViewHolder.EXTRA_MODIFICAR_IMAGEN);
			Bitmap fotobitmap = ImagenesBlobBitmap.bytes_to_bitmap(fotobinaria, 500, 500);
			ivModificarImagen.setImageBitmap(fotobitmap);
		}
		edtModificarIdentificador = (EditText) findViewById(R.id.edtModificarIdentificador);
		//configuro el adapter del spinner de consolas con información de la base de datos
		spModificarJuegoPlataforma = (Spinner) findViewById(R.id.spModificarJuegoPlataforma);
		adapterPlataforma = new ArrayAdapter<>(this, R.layout.spinner_cerrado);
		adapterPlataforma.setDropDownViewResource(R.layout.spinner_desplegado);
		Herramientas.rellenarSpinnerConInfoBd(this, adapterPlataforma, "carlosmilena_consolas",
				"nombreconsola");
		spModificarJuegoPlataforma.setAdapter(adapterPlataforma);
		spModificarJuegoPlataforma.setOnItemSelectedListener(this);
		edtModificarNombreJuego = (EditText) findViewById(R.id.edtModificarJuegoNombreJuego);
		edtModificarPrecioVenta = (EditText) findViewById(R.id.edtModificarJuegoPrecioVenta);
		ivModificarImagen = (ImageView) findViewById(R.id.ivModificarJuegoImagen);
		edtModificarIdentificador.setText(juego.getIdentificador());
		edtModificarNombreJuego.setText(juego.getNombreJuego());
		//configuro el adapter del spinner de género con información de la base de datos
		spModificarGenero = (Spinner) findViewById(R.id.spModificarGenero);
		adapterGenero = new ArrayAdapter<>(this, R.layout.spinner_cerrado);
		adapterGenero.setDropDownViewResource(R.layout.spinner_desplegado);
		Herramientas.rellenarSpinnerConInfoBd(this, adapterGenero, "carlosmilena_generos",
				"nombregenero");
		spModificarGenero.setAdapter(adapterGenero);
		spModificarGenero.setOnItemSelectedListener(this);
		edtModificarPrecioVenta.setText(String.valueOf(juego.getPrecioJuego()));
		if(savedInstanceState != null){
			valorSpinnerPlataformaInicial = savedInstanceState.getString("valorSpinnerPlataforma");
			valorSpinnerGeneroInicial = savedInstanceState.getString("valorSpinnerGenero");
		}else{
			valorSpinnerPlataformaInicial = juego.getPlataforma();
			valorSpinnerGeneroInicial = juego.getGenero();
		}
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState){
		super.onSaveInstanceState(outState);
		outState.putString("valorSpinnerPlataforma",
				spModificarJuegoPlataforma.getSelectedItem().toString());
		outState.putString("valorSpinnerGenero", spModificarGenero.getSelectedItem().toString());
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
		if(!spinnersIniciados){
			spinnersIniciados = true;
			spModificarJuegoPlataforma.setSelection(adapterPlataforma.getPosition(valorSpinnerPlataformaInicial));
			spModificarGenero.setSelection(adapterGenero.getPosition(valorSpinnerGeneroInicial));
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent){
	}

	public void borrarJuego(View view){
		//NUEVO
		boolean esBorrado = JuegoController.borrarJuego(juego.getIdentificador());
		if(esBorrado){
			Toast.makeText(ModificarJuegoActivity.this, "Juego eliminado correctamente",
					Toast.LENGTH_LONG).show();
			borrarImagen(juego.getIdentificador());
			startActivity(new Intent(getApplicationContext(), MostrarCatalogoActivity.class));
			finish();
		}else{
			Toast.makeText(ModificarJuegoActivity.this, "Error al borrar el juego",
					Toast.LENGTH_LONG).show();
		}
		//VIEJO
		/*StringRequest request = new StringRequest(Request.Method.POST,
				ConfiguracionDB.DIRECCION_URL_RAIZ +
				"/eliminar.php", new Response.Listener<String>(){
			@Override
			public void onResponse(String response){
				if(response.equalsIgnoreCase("datos eliminados")){
					Toast.makeText(ModificarJuegoActivity.this, "Juego eliminado correctamente",
							Toast.LENGTH_LONG).show();
					borrarImagen(juego.getIdentificador());
					startActivity(new Intent(getApplicationContext(),
							MostrarCatalogoActivity.class));
					finish();
				}else{
					Toast.makeText(ModificarJuegoActivity.this,
							"Error: " + response, Toast.LENGTH_LONG).show();
				}
			}
		}, new Response.ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError error){
				Toast.makeText(ModificarJuegoActivity.this, error.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
		}){
			@Override
			protected Map<String, String> getParams(){
				Map<String, String> params = new HashMap<>();
				params.put("identificador", juego.getIdentificador());
				return params;
			}
		};
		RequestQueue requestQueue = Volley.newRequestQueue(ModificarJuegoActivity.this);
		requestQueue.add(request);*/
	}

	private void borrarImagen(String idimagen){
		//NUEVO
		boolean esBorrada = ImagenController.borrarImagen(idimagen);
		if(!esBorrada){
			Toast.makeText(ModificarJuegoActivity.this, "Error al borrar la imagen",
					Toast.LENGTH_SHORT).show();
		}
		//VIEJO
		/*
		StringRequest request = new StringRequest(Request.Method.POST,
				ConfiguracionDB.DIRECCION_URL_RAIZ +
				"/eliminar-foto.php", new Response.Listener<String>(){
			@Override
			public void onResponse(String response){
				if(!response.equalsIgnoreCase("datos eliminados")){
					Toast.makeText(ModificarJuegoActivity.this, "Error al borrar la imagen",
							Toast.LENGTH_SHORT).show();
				}
			}
		}, new Response.ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError error){
				Toast.makeText(ModificarJuegoActivity.this, String.valueOf(error.getMessage()),
						Toast.LENGTH_SHORT).show();
			}
		}){
			@Override
			protected Map<String, String> getParams(){
				Map<String, String> params = new HashMap<>();
				params.put("idimagen", idimagen);
				return params;
			}
		};
		RequestQueue requestQueue = Volley.newRequestQueue(ModificarJuegoActivity.this);
		requestQueue.add(request);*/
	}

	private void insertarImagen(String idimagen, ImageView nuevaImagen){
		//NUEVO
		nuevaImagen.buildDrawingCache();
		Bitmap foto_bm = nuevaImagen.getDrawingCache();
		byte[] fotobytes = ImagenesBlobBitmap.bitmap_to_bytes_png(foto_bm);
		String fotostring = ImagenesBlobBitmap.byte_to_string(fotobytes);
		Imagen imagen = new Imagen(idimagen, fotostring);
		boolean esInsertada = ImagenController.insertarImagen(imagen);
		if(!esInsertada){
			Toast.makeText(ModificarJuegoActivity.this, "Error al insertar la imagen",
					Toast.LENGTH_SHORT).show();
		}
		//VIEJO
		/*StringRequest request = new StringRequest(Request.Method.POST,
				ConfiguracionDB.DIRECCION_URL_RAIZ +
				"/insertar-foto.php", new Response.Listener<String>(){
			@Override
			public void onResponse(String response){
				if(!response.equalsIgnoreCase("foto insertada")){
					Toast.makeText(ModificarJuegoActivity.this, "Error al guardar la imagen",
							Toast.LENGTH_SHORT).show();
				}
			}
		}, new Response.ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError error){
				Toast.makeText(ModificarJuegoActivity.this, error.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
		}){
			@Override
			protected Map<String, String> getParams(){
				Map<String, String> params = new HashMap<>();
				params.put("idimagen", idimagen);
				nuevaImagen.buildDrawingCache();
				Bitmap foto_bm = nuevaImagen.getDrawingCache();
				byte[] fotobytes = ImagenesBlobBitmap.bitmap_to_bytes_png(foto_bm);
				String fotostring = ImagenesBlobBitmap.byte_to_string(fotobytes);
				params.put("imagen", fotostring);
				return params;
			}
		};
		RequestQueue requestQueue = Volley.newRequestQueue(ModificarJuegoActivity.this);
		requestQueue.add(request);*/
	}

	public void editarJuego(View view){
		String identificador = String.valueOf(edtModificarIdentificador.getText());
		String nombreJuego = String.valueOf(edtModificarNombreJuego.getText());
		String precioVenta = String.valueOf(edtModificarPrecioVenta.getText());
		boolean camposIntroducidos = true;
		if(identificador.isEmpty()){
			camposIntroducidos = false;
			edtModificarIdentificador.setError("Identificador no puede estar vacío");
		}
		if(nombreJuego.isEmpty()){
			camposIntroducidos = false;
			edtModificarNombreJuego.setError("Nombre del juego no puede estar vacío");
		}
		if(precioVenta.isEmpty()){
			camposIntroducidos = false;
			edtModificarPrecioVenta.setError("Precio de venta no puede estar vacío");
		}
		if(!camposIntroducidos){
			Toast.makeText(ModificarJuegoActivity.this, "Campos requeridos vacíos",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if(spModificarJuegoPlataforma.getSelectedItemPosition() == -1 ||
		   (spModificarGenero.getSelectedItemPosition() == -1)){
			Toast.makeText(ModificarJuegoActivity.this, "No pudo cargarse la lista de Géneros y " +
														"Plataformas. Cambios no guardados.",
					Toast.LENGTH_LONG).show();
			return;
		}
		String plataforma = spModificarJuegoPlataforma.getSelectedItem().toString();
		String genero = spModificarGenero.getSelectedItem().toString();
		double precioVentaDouble = Double.valueOf(precioVenta);
		Juego juegoModificado = new Juego(identificador, plataforma, nombreJuego, genero,
				precioVentaDouble);
		//NUEVO
		boolean esModificado = JuegoController.actualizarJuego(juegoModificado,
				juego.getIdentificador());
		if(esModificado){
			if(imagenSeleccionada != null ||
			   !juego.getIdentificador().equals(juegoModificado.getIdentificador())){
				//cuando se modifica un juego, la imagen se modifica también, pero solo cuando ésta
				//haya cambiado o la id del juego haya cambiado (ya que la id del juego y de la
				// imagen
				// es la misma)
				borrarImagen(juego.getIdentificador());
				insertarImagen(juegoModificado.getIdentificador(), ivModificarImagen);
			}
			Toast.makeText(ModificarJuegoActivity.this, "Juego modificado correctamente",
					Toast.LENGTH_LONG).show();
			startActivity(new Intent(getApplicationContext(), MostrarCatalogoActivity.class));
			finish();
		}else{
			Toast.makeText(ModificarJuegoActivity.this, "Error al modificar el juego",
					Toast.LENGTH_LONG).show();
		}
		//VIEJO
		/*
		StringRequest request = new StringRequest(Request.Method.POST,
				ConfiguracionDB.DIRECCION_URL_RAIZ +
				"/actualizar.php", new Response.Listener<String>(){
			@Override
			public void onResponse(String response){
				if(response.equalsIgnoreCase("datos actualizados")){
					if(imagenSeleccionada != null ||
					   !juego.getIdentificador().equals(juegoModificado.getIdentificador())){
						borrarImagen(juego.getIdentificador());
						insertarImagen(juegoModificado.getIdentificador(), ivModificarImagen);
					}
					Toast.makeText(ModificarJuegoActivity.this, "Juego modificado correctamente",
							Toast.LENGTH_LONG).show();
					startActivity(new Intent(getApplicationContext(),
							MostrarCatalogoActivity.class));
					finish();
				}else{
					Toast.makeText(ModificarJuegoActivity.this,
							"Error: " + response, Toast.LENGTH_LONG).show();
				}
			}
		}, new Response.ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError error){
				Toast.makeText(ModificarJuegoActivity.this, error.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
		}){
			@Override
			protected Map<String, String> getParams(){
				Map<String, String> params = new HashMap<>();
				params.put("identificador_old", juego.getIdentificador());
				params.put("identificador_new", juegoModificado.getIdentificador());
				params.put("plataforma", juegoModificado.getPlataforma());
				params.put("nombrejuego", juegoModificado.getNombreJuego());
				params.put("genero", juegoModificado.getGenero());
				params.put("preciojuego", String.valueOf(juegoModificado.getPrecioJuego()));
				return params;
			}
		};
		RequestQueue requestQueue = Volley.newRequestQueue(ModificarJuegoActivity.this);
		requestQueue.add(request);*/
		//startActivity(new Intent(ModificarJuegoActivity.this, MostrarCatalogoActivity.class));
	}

	public void cambiarImagenModificacion(View view){
		Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
		getIntent.setType("image/*");
		Intent pickIntent = new Intent(Intent.ACTION_PICK,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		pickIntent.setType("image/*");
		Intent chooserIntent = Intent.createChooser(getIntent, "Selecciona una imagen");
		chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
		startActivityForResult(chooserIntent, NUEVA_IMAGEN);
	}

	public void cancelarCambiosModificacion(View view){
		startActivity(new Intent(ModificarJuegoActivity.this, MostrarCatalogoActivity.class));
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == NUEVA_IMAGEN && resultCode == Activity.RESULT_OK){
			imagenSeleccionada = data.getData();
			Bitmap bitmap;
			try{
				bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
						imagenSeleccionada);
				ivModificarImagen.setImageBitmap(bitmap);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}