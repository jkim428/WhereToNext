package edu.orangecoastcollege.cs273.wheretonext;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.List;

public class CollegeListActivity extends AppCompatActivity {

    private DBHelper db;
    private List<College> collegesList;
    private CollegeListAdapter collegesListAdapter;
    private ListView collegesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_list);

        //this.deleteDatabase(DBHelper.DATABASE_NAME);
        db = new DBHelper(this);

        // TODO: Comment this section out once the colleges below have been added to the database,
        // TODO: so they are not added multiple times (prevent duplicate entries)
        db.addCollege(new College("UC Berkeley", 42082, 14068, 4.7, "ucb.png"));
        db.addCollege(new College("UC Irvine", 31551, 15026.47, 4.3, "uci.png"));
        db.addCollege(new College("UC Los Angeles", 43301, 25308, 4.5, "ucla.png"));
        db.addCollege(new College("UC San Diego", 33735, 20212, 4.4, "ucsd.png"));
        db.addCollege(new College("CSU Fullerton", 38948, 6437, 4.5, "csuf.png"));
        db.addCollege(new College("CSU Long Beach", 37430, 6452, 4.4, "csulb.png"));

        // TODO:  Fill the collegesList with all Colleges from the database
        // TODO:  Connect the list adapter with the list
        // TODO:  Set the list view to use the list adapter
        collegesList = db.getAllColleges();
        collegesListAdapter = new CollegeListAdapter(this, R.layout.college_list_item, collegesList);
        collegesListView = (ListView) findViewById(R.id.collegeListView);
        collegesListView.setAdapter(collegesListAdapter);
    }

    public void viewCollegeDetails(View view) {

        // TODO: Implement the view college details using an Intent
        Intent detailsIntent = new Intent(this, CollegeDetailsActivity.class);

        College selectedCollege = (College) view.getTag();

        detailsIntent.putExtra("Name", selectedCollege.getName());
        detailsIntent.putExtra("Population", selectedCollege.getPopulation());
        detailsIntent.putExtra("Tuition", selectedCollege.getTuition());
        detailsIntent.putExtra("Rating", selectedCollege.getRating());
        detailsIntent.putExtra("ImageName", selectedCollege.getImageName());

        startActivity(detailsIntent);
    }

    public void addCollege(View view) {

        // TODO: Implement the code for when the user clicks on the addCollegeButton
        EditText mCollegeNameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText mCollegePopulationEditText = (EditText) findViewById(R.id.populationEditText);
        EditText mCollegeTuitionEditText = (EditText) findViewById(R.id.tuitionEditText);
        RatingBar mCollegeRatingBar = (RatingBar) findViewById(R.id.collegeRatingBar);

        if (mCollegeNameEditText.getText() == null
                || mCollegePopulationEditText.getText() == null
                || mCollegeTuitionEditText.getText() == null)
            Toast.makeText(this, "All information about the college must be provided", Toast.LENGTH_LONG).show();
        else
        {
            String name = mCollegeNameEditText.getText().toString();
            int population = Integer.parseInt(mCollegePopulationEditText.getText().toString());
            double tuition = Double.parseDouble(mCollegeTuitionEditText.getText().toString());
            double rating = (double) mCollegeRatingBar.getRating();

            College newCollege = new College(name, population, tuition, rating);
            db.addCollege(newCollege);
            collegesList.add(newCollege);
            collegesListAdapter.notifyDataSetChanged();
        }

    }

}

