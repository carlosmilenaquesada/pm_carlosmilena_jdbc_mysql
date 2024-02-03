package es.carlosmilena.pm_carlosmilena_jdbc_mysql.recyclerview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import es.carlosmilena.pm_carlosmilena_jdbc_mysql.activities.vistas.ModificarJuegoActivity;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.clases.Juego;

import es.carlosmilena.pm_carlosmilena_jdbc_mysql.utilidades.ImagenesBlobBitmap;
import es.carlosmilena.pm_carlosmilena_jdbc_mysql.R;

public class JuegoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
	public static final String EXTRA_MODIFICAR_JUEGO =
			"com.example.pm_mysql.recyclerview.juegoviewholder.juego";
	public static final String EXTRA_MODIFICAR_IMAGEN =
			"com.example.pm_mysql.recyclerview.juegoviewholder.fotobytes";
	private final TextView tvItemIdentificador;
	private final TextView tvItemPlataforma;
	private final TextView tvItemNombreJuego;
	private final TextView tvItemGenero;
	private final TextView tvItemPrecioVenta;
	private final ImageView ivItemImagen;
	private final CatalogoJuegosAdapter lpa;

	public JuegoViewHolder(@NonNull View itemView, CatalogoJuegosAdapter lpa){
		super(itemView);
		tvItemIdentificador = (TextView) itemView.findViewById(R.id.tvItemIdentificador);
		tvItemPlataforma = (TextView) itemView.findViewById(R.id.tvItemPlataforma);
		tvItemNombreJuego = (TextView) itemView.findViewById(R.id.tvItemNombreJuego);
		tvItemGenero = (TextView) itemView.findViewById(R.id.tvItemGenero);
		tvItemPrecioVenta = (TextView) itemView.findViewById(R.id.tvItemPrecioVenta);
		ivItemImagen = (ImageView) itemView.findViewById(R.id.ivItemImagen);
		this.lpa = lpa;
		itemView.setOnClickListener(this);
	}

	public TextView getTvItemIdentificador(){
		return tvItemIdentificador;
	}

	public TextView getTvItemPlataforma(){
		return tvItemPlataforma;
	}

	public TextView getTvItemNombreJuego(){
		return tvItemNombreJuego;
	}

	public TextView getTvItemGenero(){
		return tvItemGenero;
	}

	public TextView getTvItemPrecioVenta(){
		return tvItemPrecioVenta;
	}

	public ImageView getIvItemImagen(){
		return ivItemImagen;
	}

	@Override
	public void onClick(View view){
		int posicion = getLayoutPosition();
		Juego juego = lpa.getJuegos().get(posicion);
		Intent intent = new Intent(lpa.getContexto(), ModificarJuegoActivity.class);
		intent.putExtra(EXTRA_MODIFICAR_JUEGO, juego);
		ivItemImagen.buildDrawingCache();
		Bitmap imagenBm = ivItemImagen.getDrawingCache();
		byte[] imagenBytes = ImagenesBlobBitmap.bitmap_to_bytes_png(imagenBm);
		intent.putExtra(EXTRA_MODIFICAR_IMAGEN, imagenBytes);
		lpa.getContexto().startActivity(intent);
	}
}
