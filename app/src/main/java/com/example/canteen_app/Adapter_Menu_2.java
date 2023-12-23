package com.example.canteen_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter_Menu_2 extends RecyclerView.Adapter<Adapter_Menu_2.MenuViewHolder> {

    Context context;
    ArrayList<Buyer_Menu_List> buyerArrayList;

    public Adapter_Menu_2(Context context, ArrayList<Buyer_Menu_List> buyerArrayList) {
        this.context = context;
        this.buyerArrayList = buyerArrayList;
    }

    @NonNull
    @Override
    public Adapter_Menu_2.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.activity_buyer_menu_list, parent, false);

        return new MenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Menu_2.MenuViewHolder holder, int position) {

        Buyer_Menu_List menu = buyerArrayList.get(position);

        // Assuming dataImage is a URL or image resource identifier
        // Load image into ImageView using Glide
        Glide.with(context)
                .load(menu.dataImage) // Provide the URL or resource identifier here
                .placeholder(R.raw.the_logo) // Placeholder image while loading
                .error(R.raw.upload_image) // Image to display if loading fails
                .into(holder.foodImage);

        holder.foodName.setText(menu.dataFoodName);
        holder.foodPrice.setText(menu.dataFoodPrice);
        holder.variant1.setText(menu.dataVariant_1);
        holder.variant2.setText(menu.dataVariant_2);
    }

    @Override
    public int getItemCount() {
        return buyerArrayList.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        ImageView foodImage;
        TextView foodName, foodPrice, variant1, variant2;
        Button orderButton;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.displayFoodName);
            foodPrice = itemView.findViewById(R.id.displayFoodPrice);
            foodImage = itemView.findViewById(R.id.displayImage);
            variant1 = itemView.findViewById(R.id.displayVariant1);
            variant2 = itemView.findViewById(R.id.displayVariant2);
            orderButton = itemView.findViewById(R.id.orderingButton);

            orderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onOrderClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onOrderClickListener.onOrderClick(position);
                        }
                    }
                }
            });
        }
    }

    public interface OnOrderClickListener {
        void onOrderClick(int position);
    }

    private OnOrderClickListener onOrderClickListener;

    public void setOnOrderClickListener(OnOrderClickListener listener) {
        this.onOrderClickListener = listener;
    }


}
