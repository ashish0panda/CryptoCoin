package com.example.cryptocoin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CurrencyRVAdapterFav extends RecyclerView.Adapter<CurrencyRVAdapterFav.CurrencyViewholder> {
        private static DecimalFormat df2 = new DecimalFormat("#.##");
        private ArrayList<CurrencyModal> currencyModals;
        private Context context;


    public CurrencyRVAdapterFav(ArrayList<CurrencyModal> currencyModals, Context context) {
            this.currencyModals = currencyModals;
            this.context = context;
        }

        // below is the method to filter our list.
        public void filterList(ArrayList<CurrencyModal> filterllist) {
            // adding filtered list to our
            // array list and notifying data set changed
            currencyModals = filterllist;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public CurrencyRVAdapterFav.CurrencyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // this method is use to inflate the layout file
            // which we have created for our recycler view.
            // on below line we are inflating our layout file.
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_rv_item_fav, parent, false);
            return new CurrencyRVAdapterFav.CurrencyViewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CurrencyRVAdapterFav.CurrencyViewholder holder, int position) {
            // on below line we are setting data to our item of
            // recycler view and all its views.
            CurrencyModal modal = currencyModals.get(position);
            holder.nameTV.setText(modal.getName());
            holder.rateTV.setText("$ " + df2.format(modal.getPrice()));
            holder.symbolTV.setText(modal.getSymbol());
        }

        @Override
        public int getItemCount() {
            // on below line we are returning
            // the size of our array list.
            return currencyModals.size();
        }

        // on below line we are creating our view holder class
        // which will be used to initialize each view of our layout file.
        public class CurrencyViewholder extends RecyclerView.ViewHolder {
            private TextView symbolTV, rateTV, nameTV;

            public CurrencyViewholder(@NonNull View itemView) {
                super(itemView);
                // on below line we are initializing all
                // our text views along with  its ids.
                symbolTV = itemView.findViewById(R.id.idTVSymbol);
                rateTV = itemView.findViewById(R.id.idTVRate);
                nameTV = itemView.findViewById(R.id.idTVName);
    }
    }
}

