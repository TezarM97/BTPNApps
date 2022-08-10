package com.example.testingapps.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.testingapps.R;
import com.example.testingapps.adapter.DataContactAdapter;
import com.example.testingapps.data.DataContact;
import com.example.testingapps.model.ResponseJsonArray;
import com.example.testingapps.network.ApiClient;
import com.example.testingapps.network.ApiServices;
import com.example.testingapps.network.FunctionInterface;
import com.example.testingapps.view.AddDataActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements FunctionInterface {
    FloatingActionButton fabAdd;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    EditText etSearchData;
    DataContactAdapter dataContactAdapter;
    ArrayList<DataContact> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        etSearchData = view.findViewById(R.id.search_view);
        etSearchData.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable !=null){
                    Toast.makeText(getContext(), "Sorry, that feature it's temporary not available", Toast.LENGTH_SHORT).show();
                }

            }
        });
        fabAdd = view.findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddDataActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        recyclerView = view.findViewById(R.id.rv_dataorang);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        dataContactAdapter = new DataContactAdapter(getContext(), list);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        getData();


        return view;
    }

    private void getData() {
        swipeRefreshLayout.setRefreshing(true);
        try {

            ApiServices apiServices = ApiClient.getInstance().create(ApiServices.class);
            Call<ResponseJsonArray> call = apiServices.getDataAll();
            call.enqueue(new Callback<ResponseJsonArray>() {
                @Override
                public void onResponse(Call<ResponseJsonArray> call, Response<ResponseJsonArray> response) {
                    Log.d("Data Dari Response", "onResponse = " + !response.body().getMessage().toString().isEmpty());
                    if (!response.body().getMessage().toString().isEmpty()) {
                        Log.d("Data Dari API", "onResponse = " + response.body().getData());
                        Toast.makeText(getContext(), "Success Get Data ", Toast.LENGTH_SHORT).show();
                        JsonArray jsonElements = response.body().getData().getAsJsonArray();
                        ArrayList<DataContact> getDataAllContact = new ArrayList<DataContact>();
                        list.clear();
                        for (int i = 0; i < jsonElements.size(); i++) {
                            DataContact dataContact = new Gson().fromJson(jsonElements.get(i), DataContact.class);
                            getDataAllContact.add(dataContact);
                        }

                        dataContactAdapter.addItems(getDataAllContact);
                        dataContactAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataContactAdapter);


                        swipeRefreshLayout.setRefreshing(false);


                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getContext(), "Error, Please check your connection", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseJsonArray> call, Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(), "Please checking your connection = " + t, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getContext(), "Please checking your connection = " + e, Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onClicked(DataContact dataContact) {
        getData();
    }
}