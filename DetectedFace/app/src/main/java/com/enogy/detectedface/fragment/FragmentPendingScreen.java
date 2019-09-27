package com.enogy.detectedface.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enogy.detectedface.R;
import com.enogy.detectedface.adapter.AdapterEmployee;
import com.enogy.detectedface.adapter.AdapterImage;
import com.enogy.detectedface.listener.OnImageListener;

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class FragmentPendingScreen extends Fragment implements OnImageListener {
    private ImageView imageView;
    private RecyclerView recyclerView;
    private AdapterEmployee adapterEmployee;

    private Handler handler;
    private AdapterImage adapterImage;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_screen, container, false);
        imageView = view.findViewById(R.id.imageView);
        recyclerView = view.findViewById(R.id.recyclerViewImage);


        setupRecyclerView();


        handler = new Handler();

//        requestServer();


        return view;
    }

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
    public void onImageClicked(int image) {
       imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), image));
    }
}
