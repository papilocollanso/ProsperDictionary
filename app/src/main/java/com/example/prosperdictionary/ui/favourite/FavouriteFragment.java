package com.example.prosperdictionary.ui.favourite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prosperdictionary.R;
import com.example.prosperdictionary.WikiActivity;
import com.example.prosperdictionary.WordActivity;
import com.example.prosperdictionary.favAdapter;

import java.util.ArrayList;


public class FavouriteFragment extends Fragment {

    private FavouriteViewModel mFavouriteViewModel;

private RecyclerView mRecyclerView;
private LinearLayoutManager mLinearLayoutManager;
private String word;
private ArrayList<String> data = new ArrayList<String>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mFavouriteViewModel =
                ViewModelProviders.of(this).get(FavouriteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favourite, container, false);


        assert getArguments() != null;
         word=FavouriteFragmentArgs.fromBundle(getArguments()).getTitle();

    data.add(word);


        mRecyclerView=root.findViewById(R.id.favRecy);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        favAdapter adapter = new favAdapter(getActivity(),data);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        mLinearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        mFavouriteViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;


    }



}