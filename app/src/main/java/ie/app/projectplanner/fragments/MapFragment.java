package ie.app.projectplanner.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import ie.app.projectplanner.adapters.ProjectListAdapter;
import ie.app.projectplanner.database.FirebaseHelper;
import ie.app.projectplanner.models.Project;
import ie.projectplanner.R;



public class MapFragment extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for(Project p : ProjectListAdapter.projectList) {
            if((p.ProjectLong!=0.0)&& (p.ProjectLat!=0.0)){
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(p.ProjectLat, p.ProjectLong))
                        .title(p.ProjectName)
                        .snippet("Start Date:" +p.startDay + "/"+p.startMonth + "/"+p.startYear)
                        .snippet(p.ProjectAddress));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(p.ProjectLat, p.ProjectLong)));
            }
            }



    }



}