package ie.app.projectplanner.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import ie.app.projectplanner.adapters.ProjectListAdapter;
import ie.app.projectplanner.fragments.MapFragment;
import ie.app.projectplanner.fragments.ProjectFragment;
import ie.app.projectplanner.fragments.searchFragment;
import ie.projectplanner.R;

import static ie.app.projectplanner.activities.SplashScreen.auth;


public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected ProjectFragment projectFragment;

    public static String userName;
    public static String userEmail;
    public static Uri userPhoto;
    public ProjectListAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        if(auth.getCurrentUser() !=null) {
            userName = auth.getCurrentUser().getDisplayName();
            userEmail = auth.getCurrentUser().getEmail();
            userPhoto = auth.getCurrentUser().getPhotoUrl();
            if (userPhoto != null) {
                ImageView userimage = (ImageView) header.findViewById(R.id.UserImage);
                Picasso.with(this)
                        .load(userPhoto)
                        .resize(150, 150)
                        .centerCrop()
                        .into(userimage);

            }

            TextView username = (TextView) header.findViewById(R.id.AndroidID);
            username.setText(userName);


            TextView useremail = (TextView) header.findViewById(R.id.userEmail);
            useremail.setText(userEmail);
        }
        else{
            Toast.makeText(this,"Please Try Login Again", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, SplashScreen.class));

        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ProjectFragment fragment = ProjectFragment.newInstance();
        ft.replace(R.id.allProjectsfragment, fragment);
        ft.commit();


    }


    @Override
    protected void onResume() {
        super.onResume();


        if (!adapter.projectList.isEmpty()) {

            projectFragment = ProjectFragment.newInstance();
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.allProjectsfragment, projectFragment)
                    .commit();

        }


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment;
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        if (id == R.id.nav_home) {
            fragment = ProjectFragment.newInstance();
            ft.replace(R.id.allProjectsfragment, fragment);
            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_add) {
            startActivity(new Intent(this, addProject.class));


        } else if (id == R.id.nav_search) {
            startActivity(new Intent(this, search.class));


        } else if (id == R.id.nav_map) {

            startActivity(new Intent(this, MapFragment.class));

        } else if (id == R.id.nav_signOut) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(Home.this, SplashScreen.class));
                            finish();
                        }
                    });
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
