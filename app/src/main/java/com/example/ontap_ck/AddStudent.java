package com.example.ontap_ck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddStudent extends AppCompatActivity {
    String name , age;
    private Button bt_sql , bt_api , bt_addAll;
    private EditText et_name , et_age;
    private DBHelper dbHelper = new DBHelper(this);
    StudentAdapter studentAdapter;
    List<Student> list = new ArrayList<>();
    String url = "https://60c5e90219aa1e001769e661.mockapi.io/student/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);

        bt_addAll = findViewById(R.id.bt_addAll);
        bt_addAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = list.size();
                Student student = new Student();
                student.setId(i+1);
                student.setName(et_name.getText().toString());
                student.setAge(et_age.getText().toString());
                if (dbHelper.insert(student) > 0)
                    Toast.makeText(getApplicationContext(),"Da luu thanh cong",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"Luu khong thanh cong",Toast.LENGTH_SHORT).show();

                //Save(POST) tren api
                save(url);
            }
        });

        bt_sql = findViewById(R.id.bt_sqlite);
//        bt_sql.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int i = list.size();
//                Student student = new Student();
//                student.setId(i+1);
//                student.setName(et_name.getText().toString());
//                student.setAge(et_age.getText().toString());
//                if (dbHelper.insert(student) > 0)
//                    Toast.makeText(getApplicationContext(),"Da luu thanh cong",Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(getApplicationContext(),"Luu khong thanh cong",Toast.LENGTH_SHORT).show();
//            }
//        });
        bt_sql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String nameadd=eName.getText().toString().trim();
//                String classadd=eClass.getText().toString().trim();
//                String phoneadd=ePhone.getText().toString().trim();
//                String cityadd=eCity.getText().toString().trim();
                    int i=list.size();
                    name= String.valueOf(et_name.getText());
                    age= String.valueOf(et_age.getText());

                if (dbHelper.insert(new Student(i+1,name,age)) > 0)
                    Toast.makeText(getApplicationContext(),"Da luu thanh cong",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"Luu khong thanh cong",Toast.LENGTH_SHORT).show();


            }
        });
        bt_api = findViewById(R.id.bt_api);
        bt_api.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save(POST) tren api
                save(url);
            }
        });

    }
    public void save(String url){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AddStudent.this, "Da luu thanh cong", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddStudent.this, "Luu khong thanh cong", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("name",et_name.getText().toString().trim());
                params.put("age",et_age.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}