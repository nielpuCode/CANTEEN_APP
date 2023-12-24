package com.example.canteen_app;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

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

//    private ArrayList<Menu_List> userArrayList; // Declaring at the class level
//    private Adapter_Menu_Buyer adapterMenuBuyer; // Declaring at the class level

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment__menu__buyer, container, false);

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data");
        progressDialog.show();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewBuyer);
        ArrayList<Menu_List> userArrayList = new ArrayList<>();
//        Adapter_Menu_Buyer adapterMenuBuyer = new Adapter_Menu_Buyer(getContext(), userArrayList, this); // Pass 'this' as the third parameter
        Adapter_Menu_Buyer adapterMenuBuyer = new Adapter_Menu_Buyer(getContext(), userArrayList); // Pass 'this' as the third parameter

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterMenuBuyer);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        EventChangeListener(db, userArrayList, adapterMenuBuyer, progressDialog);

        return view;
    }

//    public void increaseQuantity(int position) {
//        Menu_List menu = userArrayList.get(position);
//        menu.setQuantity(menu.getQuantity() + 1);
//        adapterMenuBuyer.notifyItemChanged(position);
//    }
//
//    public void decreaseQuantity(int position) {
//        Menu_List menu = userArrayList.get(position);
//        if (menu.getQuantity() > 1) {
//            menu.setQuantity(menu.getQuantity() - 1);
//            adapterMenuBuyer.notifyItemChanged(position);
//        }
//    }

    private void EventChangeListener(FirebaseFirestore db, ArrayList<Menu_List> userArrayList,
                                     Adapter_Menu_Buyer adapterMenuBuyer, ProgressDialog progressDialog) {


        db.collection("Canteen2").document("Seller").collection("Menu")
                .orderBy("dataFoodName", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
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

                                userArrayList.add(dc.getDocument().toObject(Menu_List.class));

                            }

                            adapterMenuBuyer.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }

                    }
                });

    }


}