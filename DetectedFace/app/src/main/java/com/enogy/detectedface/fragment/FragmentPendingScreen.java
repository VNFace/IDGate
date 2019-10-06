package com.enogy.detectedface.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enogy.detectedface.R;
import com.enogy.detectedface.adapter.AdapterEmployee;
import com.enogy.detectedface.adapter.AdapterImage;
import com.enogy.detectedface.listener.OnImageListener;

import com.enogy.detectedface.model.remote.DataEmployeeHistory;
import com.enogy.detectedface.model.remote.EmployeeHistory;
import com.enogy.detectedface.model.retrofit.RetrofitCreated;
import com.enogy.detectedface.utils.Config;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPendingScreen extends Fragment implements OnImageListener {
    private ImageView imageView;
    private RecyclerView recyclerView;
    private AdapterEmployee adapterEmployee;
    private TextView txtFullName;
    private TextView txtAddress;
    private TextView txtTime;
    private Handler handler;
    private AdapterImage adapterImage;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_screen, container, false);
        imageView = view.findViewById(R.id.imageView);
        recyclerView = view.findViewById(R.id.recyclerViewImage);
        txtFullName = view.findViewById(R.id.txtFullName);
        txtAddress = view.findViewById(R.id.txtAddress);
        txtTime = view.findViewById(R.id.txtTime);

        setupRecyclerView();


        handler = new Handler();

//        requestSever();


        return view;
    }

    private void requestSever(){
        handler.postDelayed(runnable, 2000);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Call<DataEmployeeHistory> call = RetrofitCreated.createApi(Config.BASE_API).getListEmployeeHistory();
            call.enqueue(new Callback<DataEmployeeHistory>() {
                @Override
                public void onResponse(Call<DataEmployeeHistory> call, Response<DataEmployeeHistory> response) {
                    EmployeeHistory e = response.body().getList().get(0);
                    txtFullName.setText(e.getFullName());
                    txtTime.setText(e.geteTime());
                    imageView.setImageBitmap(decodeBase64(e.getImage()));

                }

                @Override
                public void onFailure(Call<DataEmployeeHistory> call, Throwable t) {

                }
            });
            requestSever();
        }
    };

//    private Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            Call<JsonElement> call = RetrofitCreated.createApi().getAllEmployee();
//            call.enqueue(new Callback<JsonElement>() {
//                @Override
//                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//                    JsonElement jsonObject = response.body().getAsJsonObject().get("FullName");
//                    JsonElement jsonElement = response.body().getAsJsonObject().get("ID_Emp_His");
//                    JsonElement jsonElement1 = response.body().getAsJsonObject().get("etime");
//                    JsonElement jsonElement2 = response.body().getAsJsonObject().get("id_emp");
//                    JsonElement jsonElement3 = response.body().getAsJsonObject().get("image");
//                    getListFullName(jsonObject);
//                    getListIdTempHis(jsonElement);
//                    getListETime(jsonElement1);
//                    getListIdTemp(jsonElement2);
//                    getListImage(jsonElement3);
//                    for (int i = 0; i < 3; i++) {
//                        employees.add(new Employee(list.get(i).getName(), idEmpHisList.get(i).getHis(),
//                                etimeList.get(i).getEdTime(),
//                                idEmpList.get(i).getId(), imageList.get(i).getImage()));
//
//                    }
//                    adapterEmployee = new AdapterEmployee(getActivity(), employees);
//                    recyclerView.setAdapter(adapterEmployee);
//                    Log.e("TAG", "Request");
//                }
//
//
//                @Override
//                public void onFailure(Call<JsonElement> call, Throwable t) {
//                    Log.e("TAG", "error " + t.toString());
//                }
//            });
//            Call<JsonElement> call = RetrofitCreated.createApi().testApi();
//            call.enqueue(new Callback<JsonElement>() {
//                @Override
//                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//                    Log.e("TAG", " respones " + response.body().toString());
//                }
//
//                @Override
//                public void onFailure(Call<JsonElement> call, Throwable t) {
//                    Log.e("TAG", " respones error " + t.toString());
//
//                }
//            });
//            requestServer();

//        }
//    };



    private void setupRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        adapterImage = new AdapterImage(getActivity(), this);
        recyclerView.setAdapter(adapterImage);
        adapterImage.notifyDataSetChanged();
    }
//    private void requestServer(){
//        handler.postDelayed(runnable, 5000);
//    }



    @Override
    public void onDetach() {
        super.onDetach();
        removeCallback();
        Log.e("TAG", "dettack");
    }

    private void removeCallback(){
        if (handler != null) {
//            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public void onImageClicked(String image) {
       imageView.setImageBitmap(decodeBase64(image));
    }

    private Bitmap decodeBase64(String s){
        byte[] decodedString = android.util.Base64.decode(s, android.util.Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;

    }
}
