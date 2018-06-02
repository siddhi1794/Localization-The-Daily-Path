package com.example.siddhipatil.location3;

/**
 * Created by siddhipatil on 11/18/17.
 */


        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.widget.Toast;

        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.MarkerOptions;

        import java.util.ArrayList;

        import static android.R.attr.version;
        //import static com.example.siddhipatil.location3.DatabaseHelper.MyHelper.Address;
        //import static com.example.siddhipatil.location3.DatabaseHelper.MyHelper.Address;
        import static com.example.siddhipatil.location3.DatabaseHelper.MyHelper.Address;
        import static com.example.siddhipatil.location3.DatabaseHelper.MyHelper.CustomName;
        import static com.example.siddhipatil.location3.DatabaseHelper.MyHelper.Latitude;
        import static com.example.siddhipatil.location3.DatabaseHelper.MyHelper.Longitude;
        import static com.example.siddhipatil.location3.DatabaseHelper.MyHelper.TABLE_NAME1;
        import static com.example.siddhipatil.location3.DatabaseHelper.MyHelper.Time;
        import static com.example.siddhipatil.location3.R.id.map;


public class DatabaseHelper{

    MyHelper helper;

    //Constructor
    public DatabaseHelper(Context context) {
        helper = new MyHelper(context);
    }
    //method to insert data
    public long insertData(String dateTime, double lat, double lon, String addr, String locName){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //put the time, latitude and longitude
        values.put(Time, dateTime);
        values.put(Latitude, lat);
        values.put(Longitude, lon);
        values.put(Address, addr);
        values.put(CustomName, locName);

        //inserting rows
        long id = db.insert(TABLE_NAME1, null, values);
        return id;
    }

    public Cursor getListContents()
    {
        SQLiteDatabase db= helper.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM "+TABLE_NAME1, null);
        return cursor;
    }



    public ArrayList<String> getRows() {
        ArrayList<String> rows= new ArrayList<>();
        SQLiteDatabase db= helper.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT COUNT (*) FROM"+ TABLE_NAME1, null);
        cursor.moveToFirst();
        int icount= cursor.getInt(0);
        if(cursor!=null && icount>0)
        {
            String query ="SELECT * FROM "+TABLE_NAME1;
            cursor = db.rawQuery(query, null);
            cursor.moveToFirst();

            do{
                if(cursor.getDouble(cursor.getColumnIndex(Latitude))!=0)
                {
                    String datetime= "Time: "+cursor.getString(cursor.getColumnIndex(Time));
                    String xCoord= "\nLatitude: "+ cursor.getDouble(cursor.getColumnIndex(Latitude));
                    String yCoord= "\nLongitude: "+ cursor.getDouble(cursor.getColumnIndex(Longitude));
                    String addre= "\nAddress: "+ cursor.getString(cursor.getColumnIndex(Address));
                    String cusName="\nCustom Name: "+cursor.getString(cursor.getColumnIndex(CustomName));
                    rows.add(0,datetime+xCoord+yCoord+addre+cusName);

//                    LatLng point= new LatLng(cursor.getDouble(1), cursor.getDouble(2));
//                    map.addMarker(new MarkerOptions().title(" ").position(point));


                }
            }while(cursor.moveToNext());
        }
        else
        {
            System.out.println("Empty Database");
        }
        return rows;
    }


    static class MyHelper extends SQLiteOpenHelper{

        //Database name
        static String DATABASE_NAME = "LocationDatabase";
        //Table name
        public static final String TABLE_NAME1 = "locationInfo5";
        //Column names
        public static final String ID = "ID";
        public static final String Time = "DateTime";
        public static final String Latitude = "Latitude";
        public static final String Longitude = "Longitude";
        public static final String Address = "Address";
        public static final String CustomName="CustomName";


        public Context context;

        public MyHelper(Context context) {
            super(context, TABLE_NAME1, null, 11);
            this.context = context;
            Toast.makeText(context, "Constructor Called", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try{
                String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME1 +"(ID INTEGER PRIMARY KEY, DateTime TIME, Latitude DOUBLE, Longitude DOUBLE, Address VARCHAR(200), CustomName VARCHAR(100));";
                db.execSQL(CREATE_TABLE);
                //db.execSQL("DROP TABLE TABLE_NAME");
                //db.execSQL("ALTER TABLE"+TABLE_NAME+" ADD COLUMN"+ Address);
                Toast.makeText(context, "onCreate Called", Toast.LENGTH_SHORT).show();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try{
                db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME1 + ";");
                Toast.makeText(context, "onUpgrade Called", Toast.LENGTH_SHORT).show();
                onCreate(db);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}


