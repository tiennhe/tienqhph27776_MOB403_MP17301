package com.example.thithu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thithu.API.APIService;
import com.example.thithu.Adapter.SinhVienAdapter;
import com.example.thithu.Model.SinhVienModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button btnAddnewitem ;

    SinhVienAdapter adapter  ;
ArrayList<SinhVienModel> list  =new ArrayList<>() ;
RecyclerView recyclerView ;
private SearchView searchView ;
private EditText edtSearch  ;
Button btnsapxep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAddnewitem   =findViewById(R.id.btnnewproduct) ;
        edtSearch = findViewById(R.id.editsearch);

    btnsapxep = findViewById(R.id.btnsapxep) ;
    btnsapxep.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            adapter.sortData();
        }
    });
        recyclerView = findViewById(R.id.rclhienthi) ;
        LinearLayoutManager manager =new LinearLayoutManager(this , RecyclerView.VERTICAL , false) ;
        ShowData();
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {



            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Toast.makeText(MainActivity.this, charSequence.toString().trim(), Toast.LENGTH_SHORT).show();
                filterData(charSequence.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        btnAddnewitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendilog();
            }
        });
    }

    private void filterData(String query) {

        List<SinhVienModel> filteredList = new ArrayList<>();

        for (SinhVienModel item : list) {
            // Kiểm tra nếu tên item chứa query
            if (item.getName().toLowerCase().trim().contains(query.toLowerCase().trim())) {
                filteredList.add(item);
            }
        }

        adapter.setFilter(filteredList);
        adapter.notifyDataSetChanged();
    }

    private void opendilog() {

        Dialog dialog = new Dialog(this) ;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_add_product);

        Window window = dialog.getWindow() ;
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT ,WindowManager.LayoutParams.WRAP_CONTENT);
        EditText edtname = dialog.findViewById(R.id.edtaddname);
        EditText edtage = dialog.findViewById(R.id.edtaddage);
        EditText edtdiem = dialog.findViewById(R.id.edtadddiem);
        EditText edtstatus = dialog.findViewById(R.id.edtaddstatus);
        EditText edtlinkanh = dialog.findViewById(R.id.edtaddstatus);
        Button btnadd = dialog.findViewById(R.id.btnadd) ;
        Button btnhuy = dialog.findViewById(R.id.btnhuy) ;


        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtname.getText().toString().trim() ;
                int age = Integer.parseInt(edtage.getText().toString().trim());

                double diem = Double.parseDouble(edtdiem.getText().toString().trim());
                int status = Integer.parseInt(edtstatus.getText().toString().trim());
                String link = edtlinkanh.getText().toString().trim();
                addNewStudent(name , age , diem , status , link);
                dialog.dismiss();
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void addNewStudent(String name , int tuoi , double diemTb , int Status , String linkanh) {

        SinhVienModel model = new SinhVienModel(name , tuoi , diemTb , Status  , linkanh);

        Call<SinhVienModel> call = APIService.MY_API.createUser(model);


        call.enqueue(new Callback<SinhVienModel>() {
            @Override
            public void onResponse(Call<SinhVienModel> call, Response<SinhVienModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "thành công", Toast.LENGTH_SHORT).show();
                    ShowData();

                }
            }

            @Override
            public void onFailure(Call<SinhVienModel> call, Throwable t) {

            }
        });

    }

    public void ShowData(){
        Call<List<SinhVienModel>> call = APIService.MY_API.getData();

        call.enqueue(new Callback<List<SinhVienModel>>() {
            @Override
            public void onResponse(Call<List<SinhVienModel>> call, Response<List<SinhVienModel>> response) {
                if(response.isSuccessful()){
                    list = (ArrayList<SinhVienModel>) response.body();
                    Log.i("list", "onResponse: "+list);
                     adapter = new SinhVienAdapter(MainActivity.this , list);
                    recyclerView.setAdapter(adapter);


                }
                else {
                    Toast.makeText(MainActivity.this, "Thất Bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SinhVienModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                Log.d("lỗi", "onFailure: "+t);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ShowData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ShowData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShowData();
    }
}