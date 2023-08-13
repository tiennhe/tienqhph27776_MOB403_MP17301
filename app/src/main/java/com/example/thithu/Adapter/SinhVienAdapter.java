package com.example.thithu.Adapter;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thithu.API.APIService;
import com.example.thithu.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thithu.Model.SinhVienModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SinhVienAdapter extends RecyclerView.Adapter<SinhVienAdapter.ViewHolder>  {

    private Context context;
    private ArrayList<SinhVienModel> list = new ArrayList<>();
    private ArrayList<SinhVienModel> listold ;

    public SinhVienAdapter(ArrayList<SinhVienModel> list) {
        this.list = list;
        this.listold = list;
    }

    public SinhVienAdapter(Context context, ArrayList<SinhVienModel> list) {
        this.context = context;
        this.list = list;
    }

    public SinhVienAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lauout_item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SinhVienModel model = list.get(position);
        if (model == null) {
            return;
        }

        SinhVienModel objects = list.get(position);
        Picasso.get().load(objects.getImg()).into(holder.imganh);

        holder.txtname.setText("Tên: " + model.getName());
        holder.txttuoi.setText("Tuổi: " + model.getTuoi());
        holder.txtstatus.setText("Status: " + model.Status());
        holder.txtdiemtb.setText("Điểm TB: " + model.getDiemtb());
        String id = list.get(position).getId();

        String name = list.get(position).getName();
        int tuoi = list.get(position).getTuoi();
        double diemTb = list.get(position).getTuoi();
        int status = list.get(position).getStatus();
        String link = list.get(position).getImg();
        holder.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editStudent(position, id, name, tuoi, diemTb, status, link);
            }
        });

        holder.imdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteStudent(id, position);
            }
        });


    }

    private void deleteStudent(String iddelete, int posion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xóa sản phẩm");
        builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?");
        builder.setPositiveButton("Xóa", (dialog, which) -> {


            Call<Void> call = APIService.MY_API.deleteUser(iddelete);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // Xử lý khi đối tượng đã được xóa thành công
                        Log.d("respone", "onResponse: " + response);
                        list.remove(posion);
                        notifyDataSetChanged();
                    } else {
                        // Xử lý khi có lỗi xảy ra
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });


        });
        builder.setNegativeButton("Hủy", null);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void editStudent(int position, String id, String name, int tuoi, double diem, int status, String anh) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.layout_add_product, null);
        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();
        dialog.show();
        TextView txttitle = dialog.findViewById(R.id.txttitle);
        EditText edtname = dialog.findViewById(R.id.edtaddname);
        EditText edtage = dialog.findViewById(R.id.edtaddage);
        EditText edtdiem = dialog.findViewById(R.id.edtadddiem);
        EditText edtstatus = dialog.findViewById(R.id.edtaddstatus);
        EditText edtlinkanh = dialog.findViewById(R.id.edtaddstatus);
        Button btnedit = dialog.findViewById(R.id.btnadd);
        Button btnhuy = dialog.findViewById(R.id.btnhuy);
        btnedit.setText("Edit");
        txttitle.setText("Edit Student");

        edtname.setText(name);
        edtage.setText(String.valueOf(tuoi));
        edtdiem.setText(String.valueOf(diem));
        edtstatus.setText(String.valueOf(status));
        edtlinkanh.setText(anh);





        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameedi = edtname.getText().toString().trim();
                int ageedit = Integer.parseInt(edtage.getText().toString().trim());
                double diemedit = Double.parseDouble(edtdiem.getText().toString().trim());
                int statusedit = Integer.parseInt(edtstatus.getText().toString().trim());
                String linkedit = edtlinkanh.getText().toString().trim();
                OnEditStudent(position, id, nameedi, ageedit, diemedit, statusedit, linkedit);
                dialog.dismiss();
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    private void OnEditStudent(int position, String id, String name, int age, double diem, int status, String link) {

        SinhVienModel model = new SinhVienModel();
        model.setName(name);
        model.setTuoi(age);
        model.setDiemtb(diem);
        model.setStatus(status);
        model.setImg(link);
        Call<SinhVienModel> call = APIService.MY_API.update(id, model);
        call.enqueue(new Callback<SinhVienModel>() {
            @Override
            public void onResponse(Call<SinhVienModel> call, Response<SinhVienModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "thành công", Toast.LENGTH_SHORT).show();
                    list.set(position, model);
                    notifyItemChanged(position);

                }
            }

            @Override
            public void onFailure(Call<SinhVienModel> call, Throwable t) {

            }
        });

    }

    @Override
    public int getItemCount() {
        if (list != null) {

            return list.size();
        }
        return 0;
    }

    public void setFilter(List<SinhVienModel> filteredList) {

        list = (ArrayList<SinhVienModel>) filteredList;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtname, txttuoi, txtdiemtb, txtstatus, txtid;

        ImageView imganh, imdelete, imgedit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtname = itemView.findViewById(R.id.txtten);

            txtdiemtb = itemView.findViewById(R.id.txtdiemTB);
            txtstatus = itemView.findViewById(R.id.txtstatus);
            txttuoi = itemView.findViewById(R.id.age);


            imganh = itemView.findViewById(R.id.imgitem);
            imdelete = itemView.findViewById(R.id.imgdelete);
            imgedit = itemView.findViewById(R.id.imgedit);
        }
    }
    public void sortData() {
        // Sắp xếp dataList theo thuộc tính mong muốn
        Collections.sort(list, new Comparator<SinhVienModel>() {
            @Override
            public int compare(SinhVienModel item1, SinhVienModel item2) {
                // Xác định cách sắp xếp dựa trên thuộc tính của item1 và item2
                // Trả về -1, 0, hoặc 1 tương ứng với item1 < item2, item1 == item2, item1 > item2
                // Ví dụ: Sắp xếp theo tên trong DataItem


                return item1.getName().compareTo(item2.getName());
            }

        });
        notifyDataSetChanged();
      // Cập nhật giao diện
    }

}
