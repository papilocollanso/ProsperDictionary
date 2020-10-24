package com.example.prosperdictionary.ui.dickwiki;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prosperdictionary.CustomAdapter;
import com.example.prosperdictionary.DatabaseHelper;
import com.example.prosperdictionary.DictObjectModel;
import com.example.prosperdictionary.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class DictionaryFragment extends Fragment {

  private ArrayList<DictObjectModel> data;
   ArrayList<String> meancombineList;
  ArrayList<String> wordcombineList;

  DatabaseHelper db ;


  private RecyclerView.Adapter adapter;
  LinkedHashMap<String,String> nameList;

    private DictionaryViewModel mDictionaryViewModel;
    private RecyclerView recyclerView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mDictionaryViewModel =
                ViewModelProviders.of(this).get(DictionaryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dictionary, container, false);
        setHasOptionsMenu(true);
        setRetainInstance(true);


         recyclerView=root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        db=new DatabaseHelper(getActivity());
        final LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data=new ArrayList<DictObjectModel>();
        final ProgressDialog rot =new ProgressDialog(getActivity(), AlertDialog.THEME_HOLO_DARK);
        rot.setMessage("Database Loading ...");

        rot.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchData();
                rot.dismiss();
            }
        },3000);









        mDictionaryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });


        return root;

    }



    private void fetchData( ) {
        db=new DatabaseHelper(getActivity());
        try{
            db.createDatabase();
            db.OpenDatabase();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        nameList=new LinkedHashMap<>();
        int ii;
        SQLiteDatabase sd = db.getReadableDatabase();
        Cursor cursor =sd.query("words",null,null,null,null,null,null, null);
        ii=cursor.getColumnIndex("word");
        wordcombineList=new ArrayList<String>();
        meancombineList=new ArrayList<String>();


        while(cursor.moveToNext()){
            nameList.put(cursor.getString(ii),cursor.getString(cursor.getColumnIndex("defn")));
        }
        Iterator entries= nameList.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry thisEntry = (Map.Entry) entries.next();
            wordcombineList.add(String.valueOf(thisEntry.getKey()));
            meancombineList.add(("-"+String.valueOf(thisEntry.getValue())));

        }
        for(int i=0; i<wordcombineList.size(); i++){
            data.add(new DictObjectModel(wordcombineList.get(i),meancombineList.get(i)));
        }
        adapter = new CustomAdapter(data);
        recyclerView.setAdapter(adapter);
    }






    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_dictionary_menu,menu);
      MenuItem searchItem=menu.findItem(R.id.action_search);
       SearchView searchView= (SearchView)searchItem.getActionView();
       searchView.setQueryRefinementEnabled(true);
searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
     newText = newText.toLowerCase();
        final ArrayList<DictObjectModel> filteredList = new ArrayList<DictObjectModel>();
     for(int i=0; i<wordcombineList.size(); i++){
         final String text =wordcombineList.get(i).toLowerCase();
         if(text.contains(newText)){
             filteredList.add(new DictObjectModel(wordcombineList.get(i),meancombineList.get(i)));

         }
     }
     adapter=new CustomAdapter(filteredList);
     recyclerView.setAdapter(adapter);
     return  true;
    }

});
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //keep the fragment and all its data across screen rotation
        setRetainInstance(true);
    }
}