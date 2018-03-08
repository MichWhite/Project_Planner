package ie.app.projectplanner.adapters;

import android.content.Context;
import android.view.ActionMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import ie.app.projectplanner.database.FirebaseHelper;
import ie.projectplanner.R;
import ie.app.projectplanner.models.Project;

public class ProjectListAdapter extends ArrayAdapter<Project> {
  private Context context;
  private OnClickListener deleteListener;
  public static List<Project> projectList;
  private static final String project_path = "https://projectplanner-134ee.firebaseio.com/";
  public Firebase projectRef;

  public DatabaseReference db;
  private FirebaseHelper helper;



  public ProjectListAdapter(Context context, OnClickListener deleteListener) {
    super(context, R.layout.row_project);

    this.context = context;
    this.deleteListener = deleteListener;
    projectList = new ArrayList<Project>();
    Firebase.setAndroidContext(context);
    projectRef = new Firebase(project_path);
	db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projectplanner-134ee.firebaseio.com/");
    helper = new FirebaseHelper(db);
//    projectRef.addChildEventListener(new projectChildEventListener());
    db.addChildEventListener(new ProjectChildEventListener());
  }


  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ProjectItem item = null;
    if (this!= null) {
      // Code goes here.
    item = new ProjectItem(context, parent, deleteListener,
            projectList.get(position));
    }
    return item.view;
  }

  @Override
  public int getCount() {
    return projectList.size();
  }

  public List<Project> getProjectList() {
    return this.projectList;
  }

  @Override
  public Project getItem(int position) {
    return projectList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public int getPosition(Project c) {
    return projectList.indexOf(c);
  }



  private class ProjectChildEventListener implements  com.google.firebase.database.ChildEventListener {


    @Override
    public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
      Project project = dataSnapshot.getValue(Project.class);
      project.setKey(dataSnapshot.getKey()); // set Key for update and delete
      projectList.add(0,project);
      notifyDataSetChanged();
    }

    @Override
    public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
//      String key =dataSnapshot.getKey();
//      Project tempProject = dataSnapshot.getValue(Project.class);
//
//      for(Project pro: projectList){
//        if(pro.getKey().equals(key)){
//          pro.setValues(tempProject);
//        }
//      }
      notifyDataSetChanged();
    }

    @Override
    public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
  }
}


