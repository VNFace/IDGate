package com.enogy.detectedface.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enogy.detectedface.R;
import com.enogy.detectedface.adapter.AdapterEmployee;
import com.enogy.detectedface.listener.OnEmployeeListener;
import com.enogy.detectedface.model.remote.DataEmployee;
import com.enogy.detectedface.model.remote.Employee;
import com.enogy.detectedface.model.retrofit.RetrofitCreated;
import com.enogy.detectedface.uis.activities.InformationEmployeeActivity;
import com.enogy.detectedface.utils.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentGuestManager extends Fragment implements OnEmployeeListener {
    private RecyclerView recyclerView;
    private AdapterEmployee adapterEmployee;
    private SharedPreferences sharedPreferences;
    private Call<DataEmployee> call;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e("TAG", "attach guest");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guest_manager, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewEmployee);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        setupRecyclerView();
        sharedPreferences = getActivity().getSharedPreferences(Config.EMPLOYEE, Context.MODE_PRIVATE);
        call = RetrofitCreated.createApi().getDataEmployee();
        call.enqueue(new Callback<DataEmployee>() {
            @Override
            public void onResponse(Call<DataEmployee> call, Response<DataEmployee> response) {
                adapterEmployee.setList(response.body().getList());
            }

            @Override
            public void onFailure(Call<DataEmployee> call, Throwable t) {

            }
        });

        return view;
    }

    private void setupRecyclerView() {
        adapterEmployee = new AdapterEmployee(getActivity(), this);
        recyclerView.setAdapter(adapterEmployee);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onEmployeeClicked(Employee employee) {
        String idEmployee = employee.getIdEmp();
        sharedPreferences.edit().putString(Config.ID_EMPLOYEE, idEmployee).commit();
        startActivity(new Intent(getActivity(), InformationEmployeeActivity.class));
        Log.e("TAG", " name " + employee.getFullName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "destroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("TAG", "destroy view");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("TAG", "detach");
    }
}
