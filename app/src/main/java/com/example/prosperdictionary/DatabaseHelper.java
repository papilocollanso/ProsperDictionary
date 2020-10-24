package com.example.prosperdictionary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    // the android default system path of your application database
    private static  String DB_PATH="";
    private static  String DB_NAME="dictionary_big.db";
    private  SQLiteDatabase mSQLiteDatabase;
    private Context myContext;
    // deals with context inorder to asses the asset locations and resources
    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME,null, 1);
        this.myContext=context;
        assert myContext != null;
        DB_PATH=myContext.getDatabasePath(DB_NAME).toString();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        this.getWritableDatabase();
        myContext.deleteDatabase(DB_NAME);

    }
    //copies your databases from your local assests-folder to the just created empty database
    //in the system folder, from where it can be accessded and handled.
    //this is done by transferring bytestream.
    private void copyDatabase() throws IOException{
        //open your db as the input stream
        InputStream myInput=myContext.getAssets().open(DB_NAME);
        //path to the just created empty db
        String outFileName=DB_PATH;
        //open the empty db as the output stream
        OutputStream myOutput =new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer=new byte[1024];
        int length;
        while((length=myInput.read(buffer))>0){
            myOutput.write(buffer,0,length);
        }
        //close the stream
        myOutput.flush();
    }
    //create a empty database on the system and rewrite it with your own database
    public void  createDatabase() throws IOException{
        boolean dbExist= checkDatabase();
        if(dbExist){
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database
            this.getWritableDatabase();

            try{

                copyDatabase();
            }
            catch (IOException e){
                throw new Error("Error copying database");
            }
        }
    }
    //check if the database already exist to avoid recopying the file each time you open the application
    //returns true if it exists , false if it doesnt.
    public boolean checkDatabase(){
        //this.getReadableDatabase;
        SQLiteDatabase checkDB=null;
        try{
            String myPath=DB_PATH;
            checkDB=SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);
        }
        catch (SQLiteException e){
            //database does not exist yet
        }
        if(checkDB!=null){
            checkDB.close();
        }
        return checkDB!=null?true:false;
    }
    public void OpenDatabase() throws IOException{
        String  myPath=DB_PATH + DB_NAME;
        mSQLiteDatabase=SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
    }
    @Override
    public  synchronized void close(){
        if(mSQLiteDatabase!=null){
            mSQLiteDatabase.close();
            super.close();
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }
}
