package com.example.ejercicio_listadelacompra_login.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejercicio_listadelacompra_login.R;
import com.example.ejercicio_listadelacompra_login.modelos.Producto;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductoVH> {

    private List<Producto> objects;
    private int resourse;
    private Context context;
    private DatabaseReference reference;

    public ProductosAdapter(List<Producto> objects, int resourse, Context context, DatabaseReference reference) {
        this.objects = objects;
        this.resourse = resourse;
        this.context = context;
        this.reference = reference;
    }

    @NonNull
    @Override
    public ProductoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productoView = LayoutInflater.from(context).inflate(resourse, null);
        productoView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ProductoVH(productoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoVH holder, int position) {

        Producto p = objects.get(position);
        holder.lblNombre.setText(p.getNombre());
        holder.lblcantidad.setText(p.getCantidadTexto());
        holder.lblPrecio.setText(p.getPrecioMoneda());


        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                objects.remove(p);
               // notifyItemRemoved(holder.getAdapterPosition());
                reference.setValue(objects);
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }


    public class ProductoVH extends RecyclerView.ViewHolder{

        ImageButton btnEliminar;
        TextView lblNombre;
        TextView lblcantidad;
        TextView lblPrecio;


        public ProductoVH(@NonNull View itemView){
            super(itemView);
            btnEliminar = itemView.findViewById(R.id.btnDeleteCard);
            lblNombre = itemView.findViewById(R.id.lblNombreCard);
            lblcantidad =  itemView.findViewById(R.id.lblcantidadCard);
            lblPrecio= itemView.findViewById(R.id.lblPrecioCard);
        }

    }
}
