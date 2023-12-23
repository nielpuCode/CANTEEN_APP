package com.example.canteen_app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.firestore.CollectionReference;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Menu_Buyer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Menu_Buyer extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Menu_Buyer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Menu_Buyer.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Menu_Buyer newInstance(String param1, String param2) {
        Fragment_Menu_Buyer fragment = new Fragment_Menu_Buyer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__menu__buyer, container, false);

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data");
        progressDialog.show();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        ArrayList<Buyer_Menu_List> buyerArrayList = new ArrayList<>();
        Adapter_Menu_2 adapterMenu = new Adapter_Menu_2(getContext(), buyerArrayList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterMenu);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        EventChangeListener(db, buyerArrayList, adapterMenu, progressDialog);

        return view;
    }

    private void EventChangeListener(FirebaseFirestore db, ArrayList<Buyer_Menu_List> buyerArrayList,
                                     Adapter_Menu_2 adapterMenu, ProgressDialog progressDialog) {

        db.collection("Canteen2").document("Seller").collection("Menu")
                .orderBy("dataFoodName", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();

                            Log.e("FireStore error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                // Access the DocumentSnapshot associated with the DocumentChange
                                DocumentSnapshot documentSnapshot = dc.getDocument();

                                // Create a new Buyer_Menu_List object and set its fields from Firestore document
                                Buyer_Menu_List buyerMenu = new Buyer_Menu_List();
                                buyerMenu.setDataFoodName(documentSnapshot.getString("dataFoodName"));
                                buyerMenu.setDataFoodPrice(documentSnapshot.getString("dataFoodPrice"));
                                buyerMenu.setDataImage(documentSnapshot.getString("dataImage"));
                                buyerMenu.setDataVariant_1(documentSnapshot.getString("dataVariant_1"));
                                buyerMenu.setDataVariant_2(documentSnapshot.getString("dataVariant_2"));

                                // Add the Buyer_Menu_List object to the list
                                buyerArrayList.add(buyerMenu);
                            }
                        }

                        adapterMenu.notifyDataSetChanged();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                });

        adapterMenu.setOnOrderClickListener(new Adapter_Menu_2.OnOrderClickListener() {
            @Override
            public void onOrderClick(int position) {
                Buyer_Menu_List clickedMenu = buyerArrayList.get(position);

                // Create a new document in the Orders collection
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference newOrderRef = db.collection("Order").document("Buyer").collection("Menu").document();

                // Create a data object with the order information
                Map<String, Object> orderData = new HashMap<>();
                orderData.put("foodName", clickedMenu.getDataFoodName());
                orderData.put("foodPrice", clickedMenu.getDataFoodPrice());
                orderData.put("variant1", clickedMenu.getDataVariant_1());
                orderData.put("variant2", clickedMenu.getDataVariant_2());
                orderData.put("quantity", clickedMenu.getDataQuantity());
                orderData.put("totalPrice", clickedMenu.getDataTotalPrice());
                orderData.put("userId", clickedMenu.getDataUserId());

                // Add the data to the new document
                newOrderRef.set(orderData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Order stored successfully
                                Toast.makeText(getContext(), "Order placed successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error storing the order
                                Toast.makeText(getContext(), "Failed to place order", Toast.LENGTH_SHORT).show();
                                Log.e("Firestore", "Error storing order", e);
                            }
                        });
            }
        });

    }


}