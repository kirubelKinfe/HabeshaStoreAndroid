package com.example.commerceapp.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.commerceapp.Activities.DetailsActivity;
import com.example.commerceapp.Models.Row;
import com.example.commerceapp.R;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements RecyclerViewInterface{
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Row> list;

    int[] imgs = {
            R.drawable.eirphone_1,
            R.drawable.eirphone_2,
            R.drawable.eirphone_3,
            R.drawable.eirphone_4,
            R.drawable.eirphone_5,
            R.drawable.eirphone_6,
    };

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        list = new ArrayList<>();
        adapter = new RecyclerViewAdapter(getContext(),list, this);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);



        InsertData();

        return rootView;


    }

    @SuppressLint("NotifyDataSetChanged")
    private void InsertData() {
        Row item = new Row(imgs[0], "Beats solo3",345,"Input Type: 3.5mm stereo jack\n" +
                "Other Features: Bluetooth, Foldable, Noise\n" +
                "Isolation,  Wireless\n" +
                "Form Factor: On-Ear\n" +
                "Connections: Bluetooth, Wireless\n" +
                "Speaker Configurations: Stereo");
        list.add(item);
        item = new Row(imgs[1], "Beats solo pro",300,"Input Type: 3.7mm stereo jack\n" +
                "Other Features: Bluetooth,  Noise\n" +
                "Isolation, Stereo, Stereo Bluetooth, Wireless\n" +
                "Form Factor: On-Ear\n" +
                "Connections: Bluetooth, Wireless\n" +
                "Speaker Configurations: Stereo");
        list.add(item);
        item = new Row(imgs[2], "Beats solo",234,"Input Type: 4.5mm stereo jack\n" +
                "Other Features:  Noise\n" +
                "Isolation,  Wireless\n" +
                "Form Factor: On-Ear\n" +
                "Connections: Bluetooth, Wireless\n" +
                "Speaker Configurations: Stereo");
        list.add(item);
        item = new Row(imgs[3], "Beats solo EP",321,"Input Type: 2.5mm stereo jack\n" +
                "Other Features: Bluetooth, Noise\n" +
                "Isolation, Wireless\n" +
                "Form Factor: On-Ear\n" +
                "Connections: Wireless\n" +
                "Speaker Configurations: Stereo");
        list.add(item);
        item = new Row(imgs[4], "Beats solo2",432,"Input Type: 5.5mm stereo jack\n" +
                "Other Features:  Noise\n" +
                "Isolation, Stereo\n" +
                "Form Factor: On-Ear\n" +
                "Connections: Bluetooth, Wireless\n" +
                "Speaker Configurations: Stereo");
        list.add(item);
        item = new Row(imgs[5], "Beats solo pro",234,"Input Type: 7.5mm stereo jack\n" +
                "Other Features: Bluetooth,  Noise\n" +
                "Isolation, Stereo,  Wireless\n" +
                "Form Factor: On-Ear\n" +
                "Connections: Bluetooth, Wireless\n" +
                "Speaker Configurations: Stereo");
        list.add(item);

        adapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putExtra("img", list.get(position).getImg());
        intent.putExtra("title", list.get(position).getTitle());
        intent.putExtra("price", list.get(position).getPrice());
        intent.putExtra("description", list.get(position).getDescription());
        startActivity(intent);
    }
}