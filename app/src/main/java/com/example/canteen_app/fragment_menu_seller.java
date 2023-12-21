package com.example.canteen_app;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_menu_seller#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_menu_seller extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_menu_seller() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_menu_seller.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_menu_seller newInstance(String param1, String param2) {
        fragment_menu_seller fragment = new fragment_menu_seller();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_seller, container, false);

        ImageView uploadImage=view.findViewById(R.id.uploadImg);
        Button saveButton = view.findViewById(R.id.saveButton);
        EditText uploadFoodName=view.findViewById(R.id.foodNameEditText),
                uploadFoodPrice=view.findViewById(R.id.foodPriceEditText),
                uploadVariant1 = view.findViewById(R.id.variant1EditText),
                uploadVariant2 = view.findViewById(R.id.variant2EditText);
        String imageURL;
        Uri uri;


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_seller, container, false);
    }
}