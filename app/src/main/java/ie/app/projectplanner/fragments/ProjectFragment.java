package ie.app.projectplanner.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import ie.app.projectplanner.adapters.ProjectFilter;
import ie.app.projectplanner.database.FirebaseHelper;
import ie.projectplanner.R;
import ie.app.projectplanner.activities.Edit;
import ie.app.projectplanner.adapters.ProjectListAdapter;
import ie.app.projectplanner.models.Project;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class ProjectFragment  extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener
{
    public   ProjectListAdapter 	listAdapter;
    protected         ListView 			listView;
    protected ProjectFilter projectFilter;

    public DatabaseReference db;
    private FirebaseHelper helper;
    public ProjectFragment() {

    }

    public static ProjectFragment newInstance() {
        ProjectFragment fragment = new ProjectFragment();
        return fragment;
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projectplanner-134ee.firebaseio.com/");
        helper = new FirebaseHelper(db);
        listView = (ListView) v.findViewById(R.id.projectList);

        listAdapter = new ProjectListAdapter(getActivity(), this);
        listView.setAdapter (listAdapter);
        projectFilter = new ProjectFilter(ProjectListAdapter.projectList,"all",listAdapter);



        listView.setOnItemClickListener(this);
        listView.setEmptyView(getActivity().findViewById(R.id.empty_list_view));
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onStart()
    {
        super.onStart();
    }

     @Override
    public void onClick(View view)
    {

        if (view.getTag() instanceof Project)
        {

            onProjectDelete ((Project) view.getTag());
        }
    }

    public void onProjectDelete(final Project project)
    {
        String stringName = project.ProjectName;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to Delete this \'Project\' " + stringName + "?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {

                db.child(project.getKey()).removeValue();
                listAdapter.projectList.remove(project);
                listAdapter.notifyDataSetChanged();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent i = new Intent(getActivity(), Edit.class);
        i.putExtra("projectId", view.getId());
        getActivity().startActivity(i);

    }

}

