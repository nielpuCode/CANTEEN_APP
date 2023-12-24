package com.example.canteen_app;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adapter_Menu_Buyer extends RecyclerView.Adapter<Adapter_Menu_Buyer.MenuViewHolder> {

    Context context;
    ArrayList<Menu_List> userArrayList;
//    private Fragment_Menu_Buyer fragment;

//    public Adapter_Menu_Buyer(Context context, ArrayList<Menu_List> userArrayList, Fragment_Menu_Buyer fragment) {
    public Adapter_Menu_Buyer(Context context, ArrayList<Menu_List> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
//        this.fragment = fragment;
    }

    @NonNull
    @Override
    public Adapter_Menu_Buyer.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.menu_list_buyer, parent, false);

        return new Adapter_Menu_Buyer.MenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Menu_Buyer.MenuViewHolder holder, int position) {

        Menu_List menu = userArrayList.get(position);

        // Assuming dataImage is a URL or image resource identifier
        // Load image into ImageView using Glide
        Glide.with(context)
                .load(menu.dataImage) // Provide the URL or resource identifier here
                .placeholder(R.drawable.the_logo) // Placeholder image while loading
                .error(R.drawable.upload_image) // Image to display if loading fails
                .into(holder.foodImage);

        holder.foodName.setText(menu.dataFoodName);
        holder.foodPrice.setText(menu.dataFoodPrice);
        holder.variant1.setText(menu.dataVariant_1);
        holder.variant2.setText(menu.dataVariant_2);
//        holder.increaseButton.setOnClickListener(v -> fragment.increaseQuantity(position));
//        holder.decreaseButton.setOnClickListener(v -> fragment.decreaseQuantity(position));

        int[] quantity = {0}; // Initialize quantity using an array to make it effectively final
        holder.quantityTextView.setText(String.valueOf(quantity)); // Set initial quantity

        holder.incrementButton.setOnClickListener(view -> {
            quantity[0]++;
            holder.quantityTextView.setText(String.valueOf(quantity[0])); // Update quantity display
            menu.setQuantity(quantity[0]); // Update the quantity in the Menu_List object
            // Update your Menu_List object with the new quantity here if needed
            // e.g., userArrayList.get(position).setQuantity(quantity[0]);

        });


        holder.decrementButton.setOnClickListener(view -> {
            if (quantity[0] > 0) {
                quantity[0]--;
                holder.quantityTextView.setText(String.valueOf(quantity[0])); // Update quantity display
                menu.setQuantity(quantity[0]); // Update the quantity in the Menu_List object
                // Update your Menu_List object with the new quantity here if needed
                // e.g., userArrayList.get(position).setQuantity(quantity[0]);
            }
        });

        // Assuming you have a reference to your Firestore database
        FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();

        holder.orderButton.setOnClickListener(v -> {
            Menu_List selectedMenu = userArrayList.get(position);

            // Save selectedMenu data to a new collection in Firestore
            Map<String, Object> newData = new HashMap<>();
            newData.put("dataImage", selectedMenu.getDataImage());
            newData.put("dataFoodName", selectedMenu.getDataFoodName());
            newData.put("dataFoodPrice", selectedMenu.getDataFoodPrice());
            newData.put("quantity", menu.getQuantity()); // Add the quantity to the map for Firestore
            // Add other fields as needed

            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = auth.getCurrentUser();
//
            if (currentUser!=null) {
                String userEmail = currentUser.getEmail();

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setCancelable(false);
                builder.setView(R.layout.progress_layout);
                AlertDialog dialog = builder.create();
                dialog.show();

                firestoreDB.collection("Order Canteen").document(userEmail).collection("Menu")
                        .add(newData).addOnSuccessListener(documentReference -> {

                            String generatedDocumentId = documentReference.getId(); // Get the auto-generated document ID


                            // Data saved successfully, now handle radio button variant selection
                            int selectedVariantId = holder.radioGroup.getCheckedRadioButtonId();
                            RadioButton selectedVariant = holder.itemView.findViewById(selectedVariantId);



                            if (selectedVariant != null) {
                                String variant = selectedVariant.getText().toString();

                                // Save the selected variant to the same document in Firestore
                                firestoreDB.collection("Order Canteen").document(userEmail).collection("Menu")
                                        .document(generatedDocumentId).update("selectedVariant", variant).addOnSuccessListener(aVoid1 -> {
                                            Toast.makeText(v.getContext(), "Order Saved", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            // Variant data saved successfully
                                            // You can perform further actions or show a success message here
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(v.getContext(), "Failed to add Variant", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            // Handle variant saving failure
                                        });
                            } else {
                                Toast.makeText(v.getContext(), "Please choose one variant", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(v.getContext(), "Failed to add Menu", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder{

        ImageView foodImage;
        TextView foodName, foodPrice, variant1, variant2, quantityTextView;
        Button orderButton, incrementButton, decrementButton;
        RadioGroup radioGroup;
        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.displayFoodName);
            foodPrice = itemView.findViewById(R.id.displayFoodPrice);
            foodImage = itemView.findViewById(R.id.displayImage);
            variant1 = itemView.findViewById(R.id.displayVariant1);
            variant2 = itemView.findViewById(R.id.displayVariant2);
            orderButton = itemView.findViewById(R.id.orderButton);
            radioGroup = itemView.findViewById(R.id.selectedVariant);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            incrementButton = itemView.findViewById(R.id.incrementButton);
            decrementButton = itemView.findViewById(R.id.decrementButton);
        }
    }
}
