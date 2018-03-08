package ie.app.projectplanner.activities;

/**
 * Created by michealin on 12/3/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import ie.app.projectplanner.database.FirebaseHelper;
import ie.app.projectplanner.models.User;
import ie.projectplanner.R;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ui.ResultCodes;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Arrays;



public class SplashScreen extends Activity {

    private static final int RC_SIGN_IN = 1;


    private static int SPLASH_TIME_OUT = 3000;
    private GoogleApiClient mGoogleApiClient;
    public GoogleSignInAccount acct;

    public DatabaseReference db;
    private FirebaseHelper helper;
public Button loginButton;

    public static FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);


        db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projectplanner-134ee.firebaseio.com/");
        helper = new FirebaseHelper(db);


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                auth = FirebaseAuth.getInstance();

                startActivityForResult(AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()))
                        .setIsSmartLockEnabled(false)
                        .build(), 1);


            }
        }, SPLASH_TIME_OUT);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){

            if(resultCode == RESULT_OK){
                startActivity(new Intent(SplashScreen.this, Home.class));
                finish();            }
            if(resultCode == RESULT_CANCELED){
                displayMessage(getString(R.string.signin_failed));
            }
            return;
        }
        displayMessage(getString(R.string.unknown_response));
    }


    private void displayMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
