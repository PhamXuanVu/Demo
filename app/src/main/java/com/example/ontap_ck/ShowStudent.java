package com.example.ontap_ck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowStudent extends AppCompatActivity {
    private Button bt_sql , bt_api , bt_back;
    private RecyclerView recyclerView;
    private DBHelper dbHelper = new DBHelper(this);
    private RecyclerView.LayoutManager layoutManager;
    private StudentAdapter adapter;

    ArrayList<Student> list = new ArrayList<>();
    String url = "https://60c5e90219aa1e001769e661.mockapi.io/student/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student);

        recyclerView = findViewById(R.id.recyclerView);

        bt_sql = findViewById(R.id.bt_showSql);
        bt_sql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Student> students = new ArrayList<>();
                students = dbHelper.getAll();
                layoutManager = new LinearLayoutManager(ShowStudent.this);
                adapter = new StudentAdapter(students,ShowStudent.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
            }
        });

        bt_back = findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowStudent.this,MainActivity.class);
                startActivity(intent);
            }
        });

        bt_api = findViewById(R.id.bt_showApi);
        bt_api.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAll(url);
            }
        });

    }

    private void getAll(String url){
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject object = (JSONObject) response.get(i);
                                        Student student = new Student();
                                        student.setId(Integer.parseInt(object.getString("id")));
                                        student.setName(object.getString("name"));
                                        student.setAge(object.getString("age"));
                                        list.add(student);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                layoutManager = new LinearLayoutManager(ShowStudent.this);
                                adapter = new StudentAdapter(list, ShowStudent.this);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(layoutManager);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShowStudent.this,"Loi",Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}