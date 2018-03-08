package ie.app.projectplanner.activities;

/**
 * Created by michealin on 11/29/2016.
 */

import ie.app.projectplanner.adapters.ProjectListAdapter;
import ie.app.projectplanner.database.FirebaseHelper;
import ie.projectplanner.R;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Location;

import java.util.Date;

import ie.app.projectplanner.fragments.MapFragment;
import ie.app.projectplanner.models.Project;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Toolbar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.R.attr.data;
import static android.R.attr.fragment;


public class addProject extends FragmentActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    private Button MapButton;
    public EditText MemberNamesText;
    public TextView Address;
    public Spinner typeOfConstruction;
    public DatePicker datePicker;
    public DatePicker finishDate;
    public EditText ProjectTitle;
    public String ProjectName, MemberNames,typeOfConstructionInProject;
    public int ProjectId, startDay, startMonth, startYear, finishDay, finishMonth, finishYear;
    private Place place =null;
    int PLACE_PICKER_REQUEST = 1;
    public DatabaseReference db;
    private FirebaseHelper helper;
    String projectAddress;
    double projectlatitude;
    double projectlongitude;
    public ProjectListAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        MapButton = (Button) findViewById(R.id.Maps_Button);
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .enableAutoManage(this, this)
                .build();
        db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projectplanner-134ee.firebaseio.com/");
		helper = new FirebaseHelper(db);



        Button saveButton = (Button) findViewById(R.id.saveButton);
        typeOfConstruction = (Spinner) findViewById(R.id.type_of_construction);
        MemberNamesText = (EditText) findViewById(R.id.MemberNames);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        finishDate = (DatePicker) findViewById(R.id.FinishDate);
        ProjectTitle = (EditText) findViewById(R.id.ConstructionTitle);
        Address = (TextView) findViewById(R.id.Address);


        datePicker.setMinDate(new Date().getTime() - 10000);
        finishDate.setMinDate(new Date().getTime() - 10000);

        Spinner spinner = (Spinner) findViewById(R.id.type_of_construction);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        saveButton.setOnClickListener(this);


        MapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    try {
                        startActivityForResult(builder.build(addProject.this), PLACE_PICKER_REQUEST);
                    } catch (GooglePlayServicesRepairableException e1) {
                        e1.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e1) {
                        e1.printStackTrace();
                    }


                    if (ActivityCompat.checkSelfPermission(addProject.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi
                            .getCurrentPlace(mGoogleApiClient, null);

                    result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {

                        @Override
                        public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                            for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                                Log.i("ADD PROJECT", String.format("Place '%s' has likelihood: %g",
                                        placeLikelihood.getPlace().getName(),
                                        placeLikelihood.getLikelihood()));

                            }
                            likelyPlaces.release();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(addProject.this, "EXCEPTION fOUND", Toast.LENGTH_SHORT).show();
                }


            }

        });


    }
    public void onClick(View v) {
        Boolean saved = false;
        if (place == null && place == null) {
            projectAddress = "n/a";
            projectlatitude = 0;
            projectlongitude = 0;
        } else {
            projectAddress = place.getAddress().toString();
            projectlatitude = place.getLatLng().latitude;
            projectlongitude = place.getLatLng().longitude;
        }


        ProjectName = ProjectTitle.getText().toString();

        ProjectId = adapter.projectList.size() + 1;

        startDay = datePicker.getDayOfMonth();
        startMonth = datePicker.getMonth()+1;
        startYear = datePicker.getYear();
        finishDay = finishDate.getDayOfMonth();
        finishMonth = finishDate.getMonth()+1;
        finishYear = finishDate.getYear();
    for(int i=0; i< adapter.projectList.size(); i++){
        if(adapter.projectList.get(i).ProjectName.equals(ProjectName)){
            ProjectTitle.setError("Please Title already exists");
        }
    }

        if (ProjectTitle.getText().toString().equals("") ) {
            ProjectTitle.setError("Please Enter a Project Title");
            ProjectTitle.isFocused();

        } else if ((ProjectName.length() > 0) && (MemberNamesText.length() > 0)) {

            Project p = new Project(ProjectId, ProjectTitle.getText().toString(), typeOfConstruction.getSelectedItem().toString(),
                    MemberNamesText.getText().toString(), startDay, startMonth, startYear,
                    finishDay, finishMonth, finishYear, projectAddress,
                    projectlatitude, projectlongitude);

            db.push().setValue(p);


            Toast.makeText(this, "Project Saved", Toast.LENGTH_LONG).show();

            startActivity(new Intent(this, Home.class));

        } else {
            Toast.makeText(this, "Details are missing please check again", Toast.LENGTH_LONG).show();

        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                place = PlacePicker.getPlace(data, this);
                String toastMsg = ("Location Added");
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                Address.setVisibility(View.VISIBLE);
                Address.setText(place.getAddress());
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    
}

