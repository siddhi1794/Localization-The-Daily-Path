package com.example.siddhipatil.location3;

/**
 * Created by siddhipatil on 11/18/17.
 */



        import android.app.Fragment;
        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.graphics.Color;
        import android.location.Location;
        import android.support.v4.app.FragmentActivity;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutCompat;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.MapFragment;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.*;
        import com.google.android.gms.maps.model.*;

        import java.util.ArrayList;

        import static com.example.siddhipatil.location3.DatabaseHelper.MyHelper.Latitude;
        import static com.example.siddhipatil.location3.DatabaseHelper.MyHelper.Longitude;
        import static com.example.siddhipatil.location3.DatabaseHelper.MyHelper.TABLE_NAME1;
        import static com.example.siddhipatil.location3.DatabaseHelper.MyHelper.Time;
        import static com.example.siddhipatil.location3.R.id.info;
        import static com.example.siddhipatil.location3.R.id.map;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int REQ_CODE= 123;
    ArrayList<Double> list;
    DatabaseHelper dbHelper;
    Cursor cursor;
    Polygon polygon;
    String name = "";

    PolygonOptions polygonOptions= new PolygonOptions();

    Button add_loc;
    Marker clickAdd;


   // float distanceTo(Location dest);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        add_loc=(Button)findViewById(R.id.idMarkLoc);

        //creating a map fragment
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(final GoogleMap map) {
        Intent intent= this.getIntent();
        double lat= intent.getDoubleExtra("latitude", 0.00);
        double lon= intent.getDoubleExtra("longitude", 0.00);
        String loc= intent.getStringExtra("LocationName");
        Toast.makeText(this, "Welcome to Maps:)", Toast.LENGTH_LONG).show();


        LatLng location= new LatLng(lat,lon);

        map.getUiSettings().setCompassEnabled(true);
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));

        dbHelper= new DatabaseHelper(this);
        cursor= dbHelper.getListContents();
        if(cursor.getCount()==0)
        {
            Toast.makeText(this, "No Entries in Database!", Toast.LENGTH_LONG).show();
        }
        else
        {
            cursor.moveToFirst();
            do {

                LatLng point= new LatLng(cursor.getDouble(2),cursor.getDouble(3));
                map.addMarker(new MarkerOptions().position(point).title(cursor.getString(0)));
                polygonOptions.add(point);
//
//                Location locationA= new Location("LocationA");
//                locationA.setLatitude(40.524);
//                locationA.setLongitude(-74.4355);
//
//                Location locationB= new Location("LocationB");
//                locationB.setLatitude(40.7412);
//                locationB.setLongitude(-74.1753);
//
//                float distance= locationA.distanceTo(locationB);
//                //Toast.makeText(this, "distance", Toast.LENGTH_LONG).show();
//                System.out.println(distance);




//                Polyline line = map.addPolyline(new PolylineOptions()
//                        .add(new LatLng(40.7412, -74.1753))   //Douglass Campus
//                        .add(new LatLng(40.502, -74.4504))    //College Campus
//                        .add(new LatLng(40.5212, -74.4623))   //Busch Campus
//                        .add(new LatLng(40.524, -74.4355))    //Livingston Campus
//                        .add(new LatLng(40.4793, -74.433))    //Cook Campus
//                        .width(5)
//                        .color(Color.RED));



            }while(cursor.moveToNext());
            polygon= map.addPolygon((polygonOptions).strokeColor(Color.RED));


        }

        map.addMarker(new MarkerOptions().title(loc).snippet(" ").position(location));
        add_loc.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent markerIntent= new Intent(MapActivity.this, MarkerNameActivity.class);
                startActivityForResult(markerIntent, REQ_CODE);

            }
        });


        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {



               // map.clear();
                clickAdd= map.addMarker(new MarkerOptions().title(name).snippet("").position(latLng).draggable(true));
                map.animateCamera(CameraUpdateFactory.newLatLng(latLng));

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
      MapActivity.super.onActivityResult(requestCode,resultCode, intent);
        if(requestCode== REQ_CODE)
        {
            name= intent.getStringExtra("name");
            Toast.makeText(MapActivity.this,"Got Marker Name!", Toast.LENGTH_LONG).show();
        }
    }
}
