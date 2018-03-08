package ie.app.projectplanner.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import ie.app.projectplanner.adapters.ProjectListAdapter;
import ie.app.projectplanner.database.FirebaseHelper;
import ie.app.projectplanner.models.Project;
import ie.projectplanner.R;



public class Edit extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
	private Context context;
	private Project aProject;
	private Spinner constructionType;
	private String projectName,ProjectMembers;
	private	DatePicker datePicker, finishDate;
	private Button editButton;
	private Place place;
	int PLACE_PICKER_REQUEST = 1;
	private GoogleApiClient mGoogleApiClient;
	private Button MapButton;
	public TextView Address;
 private ProjectListAdapter adapter;
	public DatabaseReference db;
	private FirebaseHelper helper;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.edit);

		db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projectplanner-134ee.firebaseio.com/");
		helper = new FirebaseHelper(db);


		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if(extras == null) {
				aProject= null;
			} else {
				aProject= getProjectObject(extras.getInt("projectId"));
			}
		} else {
			aProject= (Project) savedInstanceState.getSerializable("projectId");
		}



		((EditText)findViewById(R.id.ConstructionTitle)).setText(aProject.ProjectName);
		((EditText)findViewById(R.id.MemberNames)).setText(aProject.MemberNames);

		Address = (TextView) findViewById(R.id.Address);


		constructionType = (Spinner) findViewById(R.id.type_of_construction);
		datePicker = (DatePicker) findViewById(R.id.datePicker);
		finishDate = (DatePicker) findViewById(R.id.FinishDate);
		MapButton = (Button) findViewById(R.id.Maps_Button);
		mGoogleApiClient = new GoogleApiClient
				.Builder(this)
				.addApi(Places.GEO_DATA_API)
				.addApi(Places.PLACE_DETECTION_API)
				.enableAutoManage(this, this)
				.build();


		datePicker.setMinDate(new Date().getTime());
		finishDate.setMinDate(new Date().getTime());

		Spinner spinner = (Spinner) findViewById(R.id.type_of_construction);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.types_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		Address.setText(aProject.ProjectAddress);

		datePicker.updateDate(aProject.startYear, aProject.startMonth-1, aProject.startDay);
		finishDate.updateDate(aProject.finishYear, aProject.finishMonth-1, aProject.finishDay);
		int spinnerPosition = adapter.getPosition(aProject.typeOfConstruction);
		constructionType.setSelection(spinnerPosition);


		MapButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				try {
					PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

					startActivityForResult(builder.build(Edit.this), PLACE_PICKER_REQUEST);
					if (ActivityCompat.checkSelfPermission(Edit.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

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
					Toast.makeText(Edit.this, "EXCEPTION fOUND", Toast.LENGTH_SHORT).show();
				}


			}

		});
	}

	private Project getProjectObject(int id) {

		for (Project c : adapter.projectList)
			if (c.ProjectId == id)
				return c;

		return null;
	}

	private int getProjectIndex(Project obj) {

		for (Project c : adapter.projectList)
			if (c.ProjectId == obj.ProjectId)
				return adapter.projectList.indexOf(c);

		return -1;
	}

	public void editButtonClicked(View v) {

		projectName = ((EditText) findViewById(R.id.ConstructionTitle)).getText().toString();
		ProjectMembers = ((EditText) findViewById(R.id.MemberNames)).getText().toString();

		String projectAddress;
		double projectlatitude;
		double projectlongitude;
		if ((projectName.length() > 0) && (ProjectMembers.length() > 0)) {

			if(place==null) {
				projectAddress = aProject.ProjectAddress;
				projectlatitude = aProject.ProjectLat;
				projectlongitude = aProject.ProjectLong;
			}
			else {
				projectAddress = place.getAddress().toString();
				projectlatitude = place.getLatLng().latitude;
				projectlongitude = place.getLatLng().longitude;
			}






			db.child(aProject.getKey()).setValue(new Project(aProject.ProjectId, projectName.toString(),
					constructionType.getSelectedItem().toString(),
					ProjectMembers, datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear(),
					finishDate.getDayOfMonth(), finishDate.getMonth(), finishDate.getYear(), projectAddress,
					projectlatitude, projectlongitude));
			startActivity(new Intent(this, Home.class));

		} else
			Toast.makeText(this,"Details are missing please check again", Toast.LENGTH_LONG).show();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PLACE_PICKER_REQUEST) {
			if (resultCode == RESULT_OK) {
				place = PlacePicker.getPlace(data, this);
				String toastMsg = ("Location Added");
				Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();

				Address.setText(place.getAddress());
			}
		}
	}

	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

	}
}
