package com.example.prosperdictionary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.prosperdictionary.ui.favourite.FavouriteFragment;
import com.example.prosperdictionary.ui.favourite.FavouriteFragmentArgs;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;



public class WordActivity extends AppCompatActivity {
private TextView name;
private TextView explain;

private  DictObjectModel dick;
private static  TextToSpeech tts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
         dick = getIntent().getParcelableExtra("DictObjectModel");
         name=(TextView) findViewById(R.id.name);
         explain=(TextView) findViewById(R.id.explain);

        assert dick != null;
        name.setText(dick.word);

       explain.setText(dick.defn);




        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Definition");

        BottomNavigationView navView = findViewById(R.id.nav_view);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_wiki, R.id.navigation_voice)
                .build();

        Menu menu = navView.getMenu();
        final MenuItem wiki=menu.findItem(R.id.navigation_wiki);
        wiki.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                String value=name.getText().toString();
               Intent check=new Intent(WordActivity.this, WikiActivity.class);
               check.putExtra("key",value);

               startActivity(check);
               return true;
            }
        });


        final MenuItem voice = menu.findItem(R.id.navigation_voice);
        voice.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                final String word=name.getText().toString();
                final String meaning=explain.getText().toString();

                  tts= new TextToSpeech(WordActivity.this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status !=TextToSpeech.ERROR) {
                            tts.setLanguage(Locale.US);

                        }
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            tts.speak(word, TextToSpeech.QUEUE_ADD, null);


                            tts.speak(meaning, TextToSpeech.QUEUE_ADD, null);
                            //queue_flush drops the current and plays new entry


                        }

                    }

                });

                return  true;
            }

            public void onPause(){
                if(tts!=null){
                    tts.stop();
                    tts.shutdown();
                }
                WordActivity.super.onPause();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.word,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.love:
               addNewFavourite();
                return  true;

            case R.id.share:
                Intent share=new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                String head=name.getText().toString();
                String body= explain.getText().toString();
                share.putExtra(Intent.EXTRA_TEXT, head + "  " + body);
                startActivity(Intent.createChooser(share, " Share Word && Meaning  ... "));
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void addNewFavourite() {


        if (dick.word.equals("")) {
            Toast.makeText(WordActivity.this, "Favourite failed to add", Toast.LENGTH_SHORT).show();

        } else {


           String head1=name.getText().toString();
             Bundle bundle = new Bundle();
             bundle.putString("title", head1);


           Toast.makeText(WordActivity.this, "Favourite successfully  added", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}