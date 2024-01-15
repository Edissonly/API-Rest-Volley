package com.ista.apirestvolley;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ista.apirestvolley.model.Cliente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView,listView2,listView3;
    ArrayAdapter arrayAdapter,arrayAdapter2,arrayAdapter3;
    ArrayList<String> datos= new ArrayList<>();
    ArrayList<String> datos2= new ArrayList<>();
    ArrayList<String> datos3= new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.list1);
        listView2=findViewById(R.id.list2);
        listView3=findViewById(R.id.list3);
        arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,datos);
        arrayAdapter2=new ArrayAdapter(this, android.R.layout.simple_list_item_1,datos2);
        arrayAdapter3=new ArrayAdapter(this, android.R.layout.simple_list_item_1,datos3);
        listView.setAdapter(arrayAdapter);
        listView2.setAdapter(arrayAdapter2);
        listView3.setAdapter(arrayAdapter3);
        getDatos();
    }

    private void getDatos(){
        String url="http://192.168.40.228:8080/api/clientes";//endpoint.
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                pasarJson(response);
                Log.d("Response", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Tu mensaje aquí", Toast.LENGTH_SHORT).show();Toast.makeText(MainActivity.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
            }
        }
        );
        Volley.newRequestQueue(this).add(jsonArrayRequest);//hacemos la peticion al API
    }

    private void pasarJson( JSONArray array){

        for(int i=0;i<array.length();i++){
            JSONObject json=null;
            Cliente publicacion=new Cliente();
            try {
                json=array.getJSONObject(i);
                publicacion.setId(json.getInt("id"));//como viene del API
                publicacion.setNombre(json.getString("nombre"));
                publicacion.setApellido(json.getString("apellido"));
                publicacion.setEmail(json.getString("email"));
                datos.add(publicacion.getNombre() );
                datos2.add(publicacion.getApellido() );
                datos3.add(publicacion.getEmail() );

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        arrayAdapter.notifyDataSetChanged();
        arrayAdapter2.notifyDataSetChanged();
        arrayAdapter3.notifyDataSetChanged();
    }
}