package ideal.com.utk.ideal;

import android.app.DatePickerDialog;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ideal.com.utk.ideal.JSON.FormSubmitJSON;
import ideal.com.utk.ideal.JSON.JSON_parser;
import ideal.com.utk.ideal.custom_datatypes.NameValuePair;
import ideal.com.utk.ideal.custom_datatypes.User_details;

public class ApplicationFormOCLActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private String URL_ADDRESS;

    private DatePickerDialog datePickerDialog;
    private EditText startDate , endDate , grounds , address;
    private CheckBox leaveconcession;
    private Spinner leaveType;
    private final Calendar currentDate = Calendar.getInstance();
    private Calendar entered_s_date = Calendar.getInstance(),entered_e_date = Calendar.getInstance();

    private ProgressDialog pdia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_form_ocl);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);//to make the keyboard appear only when any edittext is focused

        URL_ADDRESS = getResources().getString(R.string.server_url)+"leaveSlimAPI/public/index.php/apps/apply";

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        leaveType = (Spinner) findViewById(R.id.leavetype);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.application_form_ocl_leavetype, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        leaveType.setAdapter(adapter);
        leaveType.setOnItemSelectedListener(this);

        startDate = (EditText) findViewById(R.id.startdate);
        endDate = (EditText) findViewById(R.id.enddate);
        grounds = (EditText) findViewById(R.id.grounds);
        address = (EditText) findViewById(R.id.address);
        leaveconcession = (CheckBox)findViewById(R.id.leaveconcession);

        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);



    }

    @Override
    public void onStop() {
        super.onStop();

        if(pdia!= null)
            pdia.dismiss();
    }

    @Override
    public void onClick(View v) {

        final SimpleDateFormat dateFormat = new SimpleDateFormat(getResources().getString(R.string.shown_date_format), Locale.US);
        switch(v.getId()){

            case R.id.startdate:
                datePickerDialog = new DatePickerDialog(this, new OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                        entered_s_date.set(year, monthOfYear, dayOfMonth);
                        startDate.setText(dateFormat.format(entered_s_date.getTime()));
                    }
                }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;

            case R.id.enddate:
                datePickerDialog = new DatePickerDialog(this, new OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        entered_e_date.set(year, monthOfYear, dayOfMonth);
                        endDate.setText(dateFormat.format(entered_e_date.getTime()));
                    }
                }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;
            case R.id.fab:
                submit_form();

                break;

        }

    }

    private void submit_form(){
        int checked = 0;

        TextInputLayout sd = (TextInputLayout)findViewById(R.id.layout_startdate);
        TextInputLayout ed = (TextInputLayout)findViewById(R.id.layout_enddate);
        String start_date = startDate.getText().toString();
        String end_date = endDate.getText().toString();
        if(start_date.isEmpty())sd.setError("Please enter Start date");
        else if(entered_s_date.before(currentDate))sd.setError("Invalid Start date");
        else {
            checked++;
            sd.setError(null);
        }

        if(end_date.isEmpty())ed.setError("Please enter End date");
        else if(entered_e_date.before(currentDate)) ed.setError("Invalid End date");
        else if(!start_date.isEmpty() && entered_e_date.before(entered_s_date)) ed.setError("End date should come after start date");
        else {
            checked++;
            ed.setError(null);
        }

        if(checked==2){
            Submit submit = new Submit();
            String tlc = "false";
            if(leaveconcession.isChecked())tlc="true";
            SimpleDateFormat dateFormat = new SimpleDateFormat(getResources().getString(R.string.server_date_format), Locale.US);
            submit.execute(leaveType.getSelectedItem().toString(),dateFormat.format(entered_s_date.getTime()),dateFormat.format(entered_e_date.getTime()),grounds.getText().toString(),address.getText().toString(),tlc);
            Log.d("dfg",leaveType.getSelectedItem().toString());
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    class Submit extends AsyncTask<String,Void,JSONObject> {

        //three methods get called, first preExecute, then do in background, and once do
        //in back ground is completed, the onPost execute method will be called.//TODO:incorporate on progreessdialog cancelled method in all asynctask

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdia = new ProgressDialog(ApplicationFormOCLActivity.this);
            pdia.setMessage("Submitting ...");
            pdia.show();

        }

        @Override
        protected JSONObject doInBackground(String... data) {

            User_details user = new User_details(ApplicationFormOCLActivity.this);


            NameValuePair username = new NameValuePair();
            username.name = "Username";
            username.value = ""+user.getUsername();
            NameValuePair token = new NameValuePair();
            token.name = "Token";
            token.value = "" + user.getToken();

            NameValuePair usertype = new NameValuePair();
            usertype.name = "Type";
            usertype.value = "" + user.getType();

            NameValuePair leavetype = new NameValuePair();
            leavetype.name="leaveType";
            leavetype.value = "1";
            NameValuePair leavenature = new NameValuePair();
            leavenature.name="nature";
            leavenature.value= data[0];
            NameValuePair start = new NameValuePair();
            start.name="leaveStart";
            start.value= data[1];//"2016-5-12";
            NameValuePair end = new NameValuePair();
            end.name="leaveEnd";
            end.value= data[2];//"2016-5-13"
            NameValuePair grounds = new NameValuePair();
            grounds.name="groundsForLeave";
            grounds.value= data[3];
            NameValuePair address = new NameValuePair();
            address.name="addressWhileOnLeave";
            address.value= data[4];
            NameValuePair tlc = new NameValuePair();
            tlc.name="TLC";
            tlc.value= data[0];

            JSON_parser json_parser = new JSON_parser();
            JSONObject jObj = json_parser.getJSONFromUrl("POST",URL_ADDRESS ,leavenature,username,token,usertype,leavetype,start,end,grounds,address,tlc);


            return jObj;

        }

        protected void onPostExecute(JSONObject jObj) {

            pdia.dismiss();

            if(jObj != null) {
                FormSubmitJSON form = new FormSubmitJSON(jObj);
                if(!form.isSuccess())Toast.makeText(ApplicationFormOCLActivity.this, "Failed to Submit. Server returned status error.", Toast.LENGTH_SHORT).show();
                else if(!form.code())Toast.makeText(ApplicationFormOCLActivity.this, "Failed to Submit."+ form.getMessage(), Toast.LENGTH_SHORT).show();
                else Toast.makeText(ApplicationFormOCLActivity.this, "OCL Application Successful", Toast.LENGTH_SHORT).show();
            }
            else{

                Toast.makeText(ApplicationFormOCLActivity.this, "Failed to Submit. Network unreachable or Internal server error.",
                        Toast.LENGTH_SHORT).show();

            }
        }


    }
}
