package ie.app.projectplanner.adapters;

import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import ie.app.projectplanner.models.Project;
import ie.projectplanner.R;

import static android.R.id.empty;


public class ProjectFilter extends Filter {
	private List<Project> 		originalProjectList;
	private String 				filterText;
	private ProjectListAdapter 	adapter;

	public ProjectFilter(List<Project> originalProjectList, String filterText,
						ProjectListAdapter adapter) {
		super();
		this.originalProjectList = originalProjectList;
		this.filterText = filterText;
		this.adapter = adapter;
	}

	public void setFilter(String filterText) {
		this.filterText = filterText;
	}

	@Override
	protected FilterResults performFiltering(CharSequence prefix) {
		FilterResults results = new FilterResults();

		if (originalProjectList == null) {
			originalProjectList = new ArrayList<Project>();
		}
		if (prefix == null || prefix.length() == 0) {
			List<Project> newProjects = new ArrayList<Project>();
			if (filterText.equals("all")) {
				results.values = originalProjectList;
				results.count = originalProjectList.size();
			}


			else {


				if (filterText.equals("new")) {
					for (Project c : originalProjectList)
						if (c.typeOfConstruction.equals("New Building"))
							newProjects.add(c);
				}

				else if (filterText.equals("repairs")) {
					for (Project c : originalProjectList)
						if (c.typeOfConstruction.equals("Building Repairs"))
							newProjects.add(c);
				}

				else if (filterText.equals("demolish")) {
					for (Project c : originalProjectList)
						if (c.typeOfConstruction.equals("Demolish"))
							newProjects.add(c);
				}

				if (filterText.equals("extension")) {
					for (Project c : originalProjectList)
						if (c.typeOfConstruction.equals("Extension"))
							newProjects.add(c);
				}
				results.values = newProjects;
				results.count = newProjects.size();
			}
		} else {
			String prefixString = prefix.toString().toLowerCase();
			final ArrayList<Project> newProjects = new ArrayList<Project>();

			for (Project c : originalProjectList) {
				final String itemName = c.ProjectName.toLowerCase();
				if (itemName.contains(prefixString)) {
					if (filterText.equals("all")) {
						newProjects.add(c);
					}

					else if (filterText.equals("new")) {
						newProjects.add(c);
					}

					else if (filterText.equals("repairs")) {
						newProjects.add(c);
					}

					else if (filterText.equals("demolish")) {
						newProjects.add(c);
					}

					else if (filterText.equals("extension")) {
						newProjects.add(c);
					}


				}}
			results.values = newProjects;
			results.count = newProjects.size();
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void publishResults(CharSequence prefix, FilterResults results) {

		ProjectListAdapter.projectList = (ArrayList<Project>) results.values;

		if (results.count >= 0)

		adapter.notifyDataSetChanged();
		else {
			adapter.notifyDataSetInvalidated();
			ProjectListAdapter.projectList = originalProjectList;
		}
	}
}
