package com.example.testingapps.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.testingapps.R;
import com.example.testingapps.data.DataContact;
import com.example.testingapps.model.ResponseJson;
import com.example.testingapps.model.ResponseJsonArray;
import com.example.testingapps.network.ApiClient;
import com.example.testingapps.network.ApiServices;
import com.example.testingapps.network.FunctionInterface;
import com.example.testingapps.view.EditDataActivity;
import com.example.testingapps.view.ViewContactActivity;
import com.example.testingapps.view.fragment.HomeFragment;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataContactAdapter extends RecyclerView.Adapter<DataContactAdapter.ContactViewHolder> {
    Context context;
    private ArrayList<DataContact> list = new ArrayList<>();
    FunctionInterface functionInterface;

    public DataContactAdapter(Context context, ArrayList<DataContact> list) {
        this.context = context;
        this.list = list;
    }

    public void addItems(ArrayList<DataContact> dataContacts) {
        list.addAll(dataContacts);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_people, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    protected class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNama, tvId;
        private ImageButton btnDelete;
        private Button btnEdit;
        private ImageView ivProfil;


        public ContactViewHolder(@NonNull View itemView) {

            super(itemView);

            tvNama = itemView.findViewById(R.id.tvNamaUser);
            tvId = itemView.findViewById(R.id.tvIdUser);
            ivProfil = itemView.findViewById(R.id.iv_datafoto);
            btnDelete = itemView.findViewById(R.id.ib_deletedata);
            btnEdit = itemView.findViewById(R.id.btn_edit);

//            tvNama = itemView.findViewById(R.id.namachat);



        }

        protected void onBind(DataContact data) {

            tvId.setText("ID : "+data.getIdUser());
            tvNama.setText(data.getFirstName()+" "+data.getLastName());
            Log.d("Data Umur", "onBind: "+data.getAge());

            RequestOptions options = new RequestOptions().fitCenter()
                    .placeholder(R.drawable.peterparker)
                    .error(R.drawable.peterparker);


            Log.d("Data Foto", "onBind: "+Glide.with(context).load(data.getPhoto()));
            if (data.getPhoto().toString().equals("N/A")){
                Glide.with(context).load("https://sm.ign.com/t/ign_ap/feature/h/how-to-wat/how-to-watch-the-spider-man-movies-in-order_avjf.1280.jpg").fitCenter().centerCrop().into(ivProfil);
            } else {
                Glide.with(context).load(data.getPhoto().toString()).apply(options).fitCenter().centerCrop().into(ivProfil);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ViewContactActivity.class);
                    intent.putExtra("idUser", data.getIdUser());
                    intent.putExtra("firstName", data.getFirstName());
                    intent.putExtra("lastName", data.getLastName());
                    intent.putExtra("ageUser", String.valueOf(data.getAge()));
                    if (data.getPhoto().toString().equals("N/A")){
                        intent.putExtra("photoUser", "https://sm.ign.com/t/ign_ap/feature/h/how-to-wat/how-to-watch-the-spider-man-movies-in-order_avjf.1280.jpg");
                    } else {
                        intent.putExtra("photoUser", data.getPhoto().toString());
                    }
                    context.startActivity(intent);
                }
            });
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, EditDataActivity.class);
                    intent.putExtra("idUser", data.getIdUser());
                    intent.putExtra("firstName", data.getFirstName());
                    intent.putExtra("lastName", data.getLastName());
                    intent.putExtra("ageUser", String.valueOf(data.getAge()));
                    if (data.getPhoto().toString().equals("N/A")){
                        intent.putExtra("photoUser", "https://sm.ign.com/t/ign_ap/feature/h/how-to-wat/how-to-watch-the-spider-man-movies-in-order_avjf.1280.jpg");
                    } else {
                        intent.putExtra("photoUser", data.getPhoto().toString());
                    }
                    context.startActivity(intent);
                    ((Activity)context).finish();                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                        Log.d("DATA ADAPTER DELETE ", "onClick: ID User = "+data.getIdUser());

                        ApiServices apiServices = ApiClient.getInstance().create(ApiServices.class);
                        Call<ResponseJson> call = apiServices.deleteData(data.getIdUser().toString());
                        call.enqueue(new Callback<ResponseJson>() {
                            @Override
                            public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {

                                Log.d("Data Deleted", "onResponse: "+response.code());
                                if(response.code()== 400){
                                    Toast.makeText(context, "Failed, Data can't deleted", Toast.LENGTH_SHORT).show();
                                } else if (response.code()== 202){
                                    Toast.makeText(context, "Success, Data deleted", Toast.LENGTH_SHORT).show();
                                    functionInterface.onClicked(data);
                                } else {
                                    Toast.makeText(context, "Failed, Data isn't deleted", Toast.LENGTH_SHORT).show();
                                }


                            }

                            @Override
                            public void onFailure(Call<ResponseJson> call, Throwable t) {
                                Toast.makeText(context, "Error, Data isn't deleted" + t, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Please checking your connection = " + e, Toast.LENGTH_SHORT).show();
                    }


                }
            });

        }
    }

}
