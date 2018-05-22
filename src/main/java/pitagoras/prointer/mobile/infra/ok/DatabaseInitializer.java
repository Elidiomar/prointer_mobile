package pitagoras.prointer.mobile.infra.ok;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import pitagoras.prointer.mobile.AppLoader;

import static pitagoras.prointer.mobile.infra.ok.DatabaseHelper.DATABASE_NAME;

public class DatabaseInitializer extends SQLiteOpenHelper{

    public static String DB_PATH =  "/data/data/" + new AppLoader().getApplicationContext().getFilesDir().getPath() + "/databases/";


    private SQLiteDatabase database;
    private final Context context;

    public DatabaseInitializer(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    public void createDatabase() throws IOException{

        boolean dbExist = checkDatabase();

        if(!dbExist){
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }

    }
    private boolean checkDatabase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException ignored){

        }

        if(checkDB != null){
            checkDB.close();
        }

        return checkDB != null;
    }
    private void copyDatabase() throws IOException{

        InputStream myInput = context.getAssets().open(DATABASE_NAME);

        String outFileName = DB_PATH + DATABASE_NAME;

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    @Override
    public synchronized void close() {
        if(database != null)
            database.close();

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
