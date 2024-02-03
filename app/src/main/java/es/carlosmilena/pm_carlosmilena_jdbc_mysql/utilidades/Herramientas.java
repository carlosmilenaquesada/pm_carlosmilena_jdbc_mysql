package es.carlosmilena.pm_carlosmilena_jdbc_mysql.utilidades;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import es.carlosmilena.pm_carlosmilena_jdbc_mysql.controladores.StringArrayController;

public class Herramientas{
	public static void rellenarSpinnerConInfoBd(Context contexto, ArrayAdapter<String> adapter,
												String nombreTablaDelContenidoDB,
												String nombreColumnaContenidoDB){
		//NUEVO
		ArrayList<String> elementos =
				StringArrayController.obtenerStringArrayController(nombreTablaDelContenidoDB,
						nombreColumnaContenidoDB);
		if(elementos == null){
			Toast.makeText(contexto, "Los valores de " + nombreTablaDelContenidoDB +
									 " no se han podido cargar", Toast.LENGTH_SHORT).show();
			return;
		}
		adapter.addAll(elementos);
		adapter.notifyDataSetChanged();

		//VIEJO
		/*StringRequest request = new StringRequest(Request.Method.POST,
				ConfiguracionDB.DIRECCION_URL_RAIZ +
				nombreArchivoPhp, new Response.Listener<String>(){
			@Override
			public void onResponse(String response){
				adapter.clear();
				ArrayList<String> listaValores = new ArrayList<>();
				try{
					JSONObject jsonObject = new JSONObject(response);
					String exito = jsonObject.getString("exito");
					JSONArray jsonArray = jsonObject.getJSONArray(nombreTablaDelContenidoDB);
					if(exito.equals("1")){
						for(int i = 0; i < jsonArray.length(); i++){
							JSONObject object = jsonArray.getJSONObject(i);
							String contenidoColumna = object.getString(nombreColumnaContenidoDB);
							listaValores.add(contenidoColumna);
						}
						adapter.addAll(listaValores);
						adapter.notifyDataSetChanged();
					}
				}catch(JSONException ex){
					throw new RuntimeException(ex);
				}
			}
		}, new Response.ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError error){
				Toast.makeText(contexto, error.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}){
		};
		RequestQueue requestQueue = Volley.newRequestQueue(contexto);
		requestQueue.add(request);*/
	}
}
