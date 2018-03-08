package ie.app.projectplanner.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ie.app.projectplanner.fragments.ProjectFragment;
import ie.app.projectplanner.fragments.searchFragment;
import ie.projectplanner.R;

/**
 * Created by michealin on 12/10/2016.
 */


public class search extends AppCompatActivity {
    ProjectFragment ProjectFragment;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        ProjectFragment = new ProjectFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ProjectFragment = searchFragment.newInstance(); //get a new Fragment instance
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout, ProjectFragment)
                .commit(); // add/replace in the current activity
    }
}
