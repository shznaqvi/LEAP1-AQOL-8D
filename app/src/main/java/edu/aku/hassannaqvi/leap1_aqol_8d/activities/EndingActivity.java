package edu.aku.hassannaqvi.leap1_aqol_8d.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.aku.hassannaqvi.leap1_aqol_8d.R;
import edu.aku.hassannaqvi.leap1_aqol_8d.core.DatabaseHelper;

public class EndingActivity extends Activity {

    private static final String TAG = EndingActivity.class.getSimpleName();

    @BindView(R.id.activity_section_ending)
    ScrollView activitySectionEnding;
    @BindView(R.id.iStatus)
    RadioGroup iStatus;
    @BindView(R.id.status01)
    RadioButton status01;
    @BindView(R.id.status02)
    RadioButton status02;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);
        ButterKnife.bind(this);

        Boolean check = getIntent().getExtras().getBoolean("check");

        if (check) {
            status01.setEnabled(true);
            status02.setEnabled(false);

        } else {
            //fldGrpmn0823Reason.setVisibility(View.GONE);
            status01.setEnabled(false);
            status02.setEnabled(true);


        }

    }

    @OnClick(R.id.btn_End)
    void onBtnEndClick() {

        Toast.makeText(this, "Processing Closing Section", Toast.LENGTH_SHORT).show();
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                finish();
                Toast.makeText(this, "Closing Form!", Toast.LENGTH_SHORT).show();
                Intent endSec = new Intent(this, MainActivity.class);
                //AppMain.mnb1 = "TEST";
                startActivity(endSec);
            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
            //}
        }
    }

    private void SaveDraft() throws JSONException {
        Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        JSONObject se = new JSONObject();
        se.put("iStatus", status01.isChecked() ? "1" : status02.isChecked() ? "2" : "0");


        Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();
    }

    private boolean UpdateDB() {
        DatabaseHelper db = new DatabaseHelper(this);

      /*  long updcount = db.addForm(AppMain.fc);

        AppMain.fc.setID(String.valueOf(updcount));

        if (updcount != 0) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();

            AppMain.fc.setUID(
                    (AppMain.fc.getDeviceID() + AppMain.fc.getID()));
            db.updateFormID();

        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
        }*/
        return true;

    }


    private boolean formValidation() {
        Toast.makeText(this, "Validating This Section ", Toast.LENGTH_SHORT).show();

        if (iStatus.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "ERROR(not selected): " + getResources().getResourceTypeName(R.string.endForm), Toast.LENGTH_LONG).show();
            status02.setError("This data is Required!");
            Log.i(TAG, "istatus: This data is Required!");
            return false;
        } else {
            status02.setError(null);
        }


        return true;
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "You Can't go back", Toast.LENGTH_LONG).show();
    }


}
