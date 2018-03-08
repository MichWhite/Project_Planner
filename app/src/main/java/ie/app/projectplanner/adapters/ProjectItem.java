package ie.app.projectplanner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ie.app.projectplanner.models.Project;
import ie.projectplanner.R;


public class ProjectItem {
	View view;

	public ProjectItem(Context context, ViewGroup parent,
					   OnClickListener deleteListener, Project project)
	{
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.row_project, parent, false);
		view.setId(project.ProjectId);

		updateControls(project);


		Button btnDelete = (Button) view.findViewById(R.id.deleteButton);
		btnDelete.setTag(project);
		btnDelete.setOnClickListener(deleteListener);
	}

	private void updateControls(Project project) {
		TextView ProjectName = (TextView) view.findViewById(R.id.row_projectName);
		TextView NoOfSteps = (TextView) view.findViewById(R.id.row_NoOfSteps);
		TextView MembersNames = (TextView) view.findViewById(R.id.row_MemberNames);
		TextView NoOfMembers = (TextView) view.findViewById(R.id.row_NoOfMember);
		TextView ProjectIdView = (TextView) view.findViewById(R.id.projectId);
		TextView ProjectAddress = (TextView) view.findViewById(R.id.row_address);

		TextView finishDate = (TextView) view.findViewById(R.id.row_endDate);
		TextView startDate = (TextView) view.findViewById(R.id.row_startDate);

		ProjectName.setText(project.ProjectName);
		NoOfSteps.setText(project.typeOfConstruction);
		String[] Names = project.MemberNames.split(",");

		NoOfMembers.setText(Integer.toString(Names.length));
		ProjectIdView.setText(Integer.toString(project.ProjectId));
		startDate.setText(project.startDay+"/"+project.startMonth+"/"+project.startYear);
		finishDate.setText(project.finishDay+"/"+project.finishMonth+"/"+project.finishYear);
		ProjectAddress.setText(project.ProjectAddress);

		String MemNames = project.MemberNames.replace(',', '\n');


		MembersNames.setText(MemNames);


	}

}