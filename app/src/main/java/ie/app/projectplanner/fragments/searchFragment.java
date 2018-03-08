package ie.app.projectplanner.fragments;

/**
 * Created by michealin on 11/28/2016.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import ie.app.projectplanner.adapters.ProjectFilter;
import ie.app.projectplanner.adapters.ProjectListAdapter;
import ie.projectplanner.R;



public class searchFragment extends ProjectFragment
		implements AdapterView.OnItemSelectedListener, TextWatcher {

	public searchFragment() {
	}

	public static searchFragment newInstance() {
		searchFragment fragment = new searchFragment();
		return fragment;
	}
	@Override
	public void onAttach(Context c) {
		super.onAttach(c);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		Spinner spinner = ((Spinner) getActivity().findViewById(R.id.searchProjectTypeSpinner));


		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getActivity(), R.array.search_array, android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(this);



		EditText nameText = (EditText)getActivity().findViewById(R.id.searchProjectNameEditText);
		nameText.addTextChangedListener(this);
	}
	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		String selected = parent.getItemAtPosition(position).toString();

		if (selected != null) {
			if (selected.equals("All Types")) {
				projectFilter.setFilter("all");
			} else if (selected.equals("New Building")) {
				projectFilter.setFilter("new");
			} else if (selected.equals("Building Repairs")) {
				projectFilter.setFilter("repairs");
			} else if (selected.equals("Extension")) {
				projectFilter.setFilter("extension");
			} else if (selected.equals("Demolish")) {
				projectFilter.setFilter("demolish");
			}
			projectFilter.filter("");
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		projectFilter.filter(s);
	}

	@Override
	public void afterTextChanged(Editable s) {

	}
}