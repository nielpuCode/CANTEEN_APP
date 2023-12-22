package com.example.canteen_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter_Menu extends RecyclerView.Adapter<Adapter_Menu.MenuViewHolder> {

    Context context;
    ArrayList<Menu_List> userArrayList;

    public Adapter_Menu(Context context, ArrayList<Menu_List> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public Adapter_Menu.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.menu_list, parent, false);

        return new MenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Menu.MenuViewHolder holder, int position) {

        Menu_List menu = userArrayList.get(position);

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
        return userArrayList.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder{

        ImageView foodImage;
        TextView foodName, foodPrice, variant1, variant2;
        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.displayFoodName);
            foodPrice = itemView.findViewById(R.id.displayFoodPrice);
            foodImage = itemView.findViewById(R.id.displayImage);
            variant1 = itemView.findViewById(R.id.displayVariant1);
            variant2 = itemView.findViewById(R.id.displayVariant2);
        }
    }
}
