package com.enogy.detectedface.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.enogy.detectedface.R;
import com.enogy.detectedface.listener.OnEmployeeListener;
import com.enogy.detectedface.model.remote.Employee;

import java.util.ArrayList;
import java.util.List;

public class AdapterEmployee extends RecyclerView.Adapter<AdapterEmployee.EmployeeHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Employee> list;
    private OnEmployeeListener onEmployeeListener;

    public AdapterEmployee(Context context, OnEmployeeListener onEmployeeListener) {
        this.context = context;
        this.list = new ArrayList<>();
        this.onEmployeeListener = onEmployeeListener;
    }

    public void setList(List<Employee> list1){
        this.list = list1;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EmployeeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        return new EmployeeHolder(layoutInflater.inflate(R.layout.item_recycler_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeHolder holder, int position) {
        final Employee employee = list.get(position);
        if (!TextUtils.isEmpty(employee.getFullName())) {
            holder.txtName.setText(employee.getFullName());
        }else{
            holder.txtName.setText("Unknown");
        }
        if (!TextUtils.isEmpty(employee.getAddress())) {
            holder.txtAddress.setText(employee.getAddress());
        }else{
            holder.txtAddress.setText("Unknown");
        }
        if (!TextUtils.isEmpty(employee.getEmail())) {
            holder.txtEmail.setText(employee.getEmail());
        }else{
            holder.txtEmail.setText("Unknown");
        }
        if (!TextUtils.isEmpty(employee.getPhone())) {
            holder.txtPhone.setText(employee.getPhone());
        }else
            holder.txtPhone.setText("Unknown");

        holder.cardViewEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEmployeeListener.onEmployeeClicked(employee);
                Log.e("TAG", "employee " + employee);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EmployeeHolder extends RecyclerView.ViewHolder {
        private ImageView imgEmployee;
        private TextView txtName;
        private TextView txtPhone;
        private TextView txtEmail;
        private TextView txtAddress;
        private CardView cardViewEmployee;

        public EmployeeHolder(@NonNull View itemView) {
            super(itemView);
            imgEmployee = itemView.findViewById(R.id.imgViewImageEmployee);
            txtName = itemView.findViewById(R.id.txtNameEmployee);
            txtPhone = itemView.findViewById(R.id.txtPhoneEmployee);
            txtEmail = itemView.findViewById(R.id.txtEmailEmployee);
            txtAddress = itemView.findViewById(R.id.txtAddressEmployee);
            cardViewEmployee = itemView.findViewById(R.id.cardViewEmployee);


        }
    }

    private Bitmap decode(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}
