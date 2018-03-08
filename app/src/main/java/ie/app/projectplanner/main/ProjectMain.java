package ie.app.projectplanner.main;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import ie.app.projectplanner.models.Project;
import ie.projectplanner.R;

import static android.app.Activity.RESULT_OK;


public class ProjectMain extends Application
{
    public List<Project> projectList = new ArrayList<Project>();


    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.v("projectMain", "ProjectMain App Started");



    }

}