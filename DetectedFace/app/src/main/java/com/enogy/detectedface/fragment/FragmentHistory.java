package com.enogy.detectedface.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.enogy.detectedface.model.remote.DataEmployeeHistory;
import com.enogy.detectedface.model.remote.Employee;
import com.enogy.detectedface.model.retrofit.RetrofitCreated;
import com.enogy.detectedface.utils.Config;

import retrofit2.Call;

public class FragmentHistory extends Fragment implements OnEmployeeListener {
    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;
    private AdapterEmployee adapterEmployee;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewHistory);
        setupRecyclerView();
        sharedPreferences = getActivity().getSharedPreferences(Config.NAME, Context.MODE_PRIVATE);
        String baseApi = sharedPreferences.getString(Config.BASE_API, "");



        return view;
    }


    private void setupRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        adapterEmployee = new AdapterEmployee(getActivity(), this);
        recyclerView.setAdapter(adapterEmployee);
    }

    @Override
    public void onEmployeeClicked(Employee employee) {

    }
}
