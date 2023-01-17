package com.example.ejercicio_listadelacompra_login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.ejercicio_listadelacompra_login.adapters.ProductosAdapter;
import com.example.ejercicio_listadelacompra_login.modelos.Producto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;



import com.example.ejercicio_listadelacompra_login.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private ProductosAdapter adapter;
    private RecyclerView.LayoutManager lm;
    private List<Producto> productos;


    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.toolbar);

        database = FirebaseDatabase.getInstance("https://fir-lucia-default-rtdb.europe-west1.firebasedatabase.app/");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        reference = database.getReference(uid).child("lista");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productos.clear();
                if(snapshot.exists()){
                    GenericTypeIndicator<ArrayList<Producto>> gti = new GenericTypeIndicator<ArrayList<Producto>>() {
                    };
                    productos.addAll(snapshot.getValue(gti));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        productos = new ArrayList<>();
        adapter= new ProductosAdapter(productos, R.layout.producto_view_holder, this,  reference);
        lm = new LinearLayoutManager(this);

        binding.contentMain.contenedor.setAdapter(adapter);
        binding.contentMain.contenedor.setLayoutManager(lm);



        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              createProducto().show();
            }
        });
    }

    private AlertDialog createProducto(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nuevo producto");
        builder.setCancelable(false);

        View productoView = getLayoutInflater().inflate(R.layout.producto_alert, null);
        EditText txtNombre = productoView.findViewById(R.id.txtNombreAlert);
        EditText txtCantidad = productoView.findViewById(R.id.txtCantidadAlert);
        EditText txtPrecio = productoView.findViewById(R.id.txtPrecioAlert);
        builder.setView(productoView);

        builder.setNegativeButton("Cancelar", null);
        builder.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String nombre = txtNombre.getText().toString();
                String cantidad = txtCantidad.getText().toString();
                String precio = txtPrecio.getText().toString();

                if(!nombre.isEmpty() && !cantidad.isEmpty() && !precio.isEmpty()){
                    productos.add(new Producto(nombre, Integer.parseInt(cantidad), Float.parseFloat(precio)) );
                   // adapter.notifyItemInserted(productos.size()-1);
                    reference.setValue(productos); //esto dispara el evento y se lo va a traer
                }
            }
        });
        return builder.create();
    }


    // especifica que menú se va a carga en la actividad:
    //La variable menu es el hueco donde aparece el menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.loout_menu, menu);
        return true;

        //cada actividad puede tener un menú diferente
    }



    //esta opción me permite discriminar las diferentes acciones en base al elemento del menú seleccionado:
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.btn_logout) {
            FirebaseAuth.getInstance().signOut();

            startActivity(new Intent(this, LogInActivity.class));
            finish();


            if (item.getItemId() == R.id.abrirInfoUsuario) {
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(this, MainActivityNombreYtelefono.class));
                finish();

            }
        }
        return true;
    }
}