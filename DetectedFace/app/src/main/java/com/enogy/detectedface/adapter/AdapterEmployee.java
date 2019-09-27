package com.enogy.detectedface.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.util.List;

public class AdapterEmployee extends RecyclerView.Adapter<AdapterEmployee.EmployeeHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Employee> list;
    private OnEmployeeListener onEmployeeListener;

    public AdapterEmployee(Context context, List<Employee> list, OnEmployeeListener onEmployeeListener) {
        this.context = context;
        this.list = list;
        this.onEmployeeListener = onEmployeeListener;
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
        holder.txtName.setText(employee.getFullName());
        holder.txtAddress.setText(employee.getAddress());
        holder.txtEmail.setText(employee.getEmail());
        holder.txtPhone.setText(employee.getPhone());

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
            txtAddress = itemView.findViewById(R.id.txtAddress);
            cardViewEmployee = itemView.findViewById(R.id.cardViewEmployee);


        }
    }

    private Bitmap decode(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}
