package ideal.com.utk.ideal;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ideal.com.utk.ideal.JSON.JSON_parser;
import ideal.com.utk.ideal.JSON.LeaveDataJSONParser;
import ideal.com.utk.ideal.JSON.RecommenderListJSONParser;
import ideal.com.utk.ideal.custom_datatypes.NameValuePair;
import ideal.com.utk.ideal.custom_datatypes.User_details;

public class UserAccountActivity extends AppCompatActivity {

    LinearLayout passcontainer;
    LinearLayout recommcontainer;
    private ProgressDialog pdia;
    private String URL_ADDRESS;
    private Spinner recom_spinner;

    private List<String> nameList;
    private List<String> usernameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        URL_ADDRESS = getResources().getString(R.string.server_url);

        final EditText oldpass = (EditText)findViewById(R.id.usersett_oldpassword);
        final EditText newpass = (EditText)findViewById(R.id.usersett_newpassword);
        final Button savepass = (Button)findViewById(R.id.usersett_pass_but);

        recom_spinner = (Spinner)findViewById(R.id.recom_list_spinner);
        final Button change_recom = (Button)findViewById(R.id.change_recomm_but);

        RetrieveInfo ri = new RetrieveInfo();
        ri.execute();


        savepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String opass = oldpass.getText().toString();
                String npass = newpass.getText().toString();
                if(opass.isEmpty())oldpass.setError("Can't be empty");
                else if(npass.isEmpty())newpass.setError("Can't be empty");
                else if(opass.equals(npass)){
                    newpass.setError("Can't be same as old Password");
                }
                else{
                    SubmitInfo s = new SubmitInfo();
                    s.execute(new NameValuePair("Password",opass), new NameValuePair("changeTo",npass));
                }


            }
        });


        change_recom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitInfo si = new SubmitInfo();
                si.execute(new NameValuePair("changeTo",usernameList.get(recom_spinner.getSelectedItemPosition())));

            }
        });


    }
    @Override
    public void onStop() {
        super.onStop();

        if(pdia!= null)
            pdia.dismiss();
    }

    public void populateSpinner() {

        User_details user = new User_details(this);
        String curr_r = user.getRecommender();


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, nameList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recom_spinner.setAdapter(dataAdapter);
        for(int i=0;i<usernameList.size();i++){
            if(curr_r.equals(usernameList.get(i))){
                recom_spinner.setSelection(i);
            }
        }

    }

    class SubmitInfo extends AsyncTask<NameValuePair, Void, JSONObject> {


        //three methods get called, first preExecute, then do in background, and once do
        //in back ground is completed, the onPost execute method will be called.

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdia = new ProgressDialog(UserAccountActivity.this);
            pdia.setMessage("Retrieving Info from server ...");
            pdia.show();


        }

        @Override
        protected JSONObject doInBackground(NameValuePair... params) {

            User_details saved_user = new User_details(UserAccountActivity.this);


            NameValuePair user = new NameValuePair();
            user.name = "Username";
            user.value = saved_user.getUsername();
            NameValuePair token = new NameValuePair();
            token.name = "Token";
            token.value = saved_user.getToken();
            NameValuePair type = new NameValuePair();
            type.name = "Type";
            type.value = "" + saved_user.getUserType();

            JSON_parser json_parser = new JSON_parser();
            JSONObject jObj;
            if(params.length ==1){
                 jObj= json_parser.getJSONFromUrl("POST" , URL_ADDRESS+"index.php/user/changeRecommender", user, token ,type,params[0]);
            }
            else{
                jObj = json_parser.getJSONFromUrl("POST" , URL_ADDRESS+"index.php/user/changePassword", user, token ,type,params[0],params[1]);
            }



            return jObj;
        }

        protected void onPostExecute(JSONObject jObj) {

            pdia.dismiss();
            if (jObj != null) {
                //for some reason just calling datasetchanged doesnt work the listview is not updated but the adapter has new data;
            } else {

                Toast.makeText(UserAccountActivity.this, "Failed to get Info. Internal error",
                        Toast.LENGTH_SHORT).show();

            }

        }
    }
    class RetrieveInfo extends AsyncTask<Void, Void, JSONObject> {


        //three methods get called, first preExecute, then do in background, and once do
        //in back ground is completed, the onPost execute method will be called.

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdia = new ProgressDialog(UserAccountActivity.this);
            pdia.setMessage("Retrieving Info from server ...");
            pdia.show();


        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            User_details saved_user = new User_details(UserAccountActivity.this);

            NameValuePair user = new NameValuePair();
            user.name = "Username";
            user.value = saved_user.getUsername();

            NameValuePair token = new NameValuePair();
            token.name = "Token";
            token.value = saved_user.getToken();
            NameValuePair type = new NameValuePair();
            type.name = "Type";
            type.value = "" + saved_user.getUserType();

            JSON_parser json_parser = new JSON_parser();
            JSONObject jObj = json_parser.getJSONFromUrl("GET" , URL_ADDRESS+ "index.php/apps/getRecommenders", user, type, token);

            return jObj;
        }

        protected void onPostExecute(JSONObject jObj) {

            pdia.dismiss();
            if (jObj != null) {
                nameList= new ArrayList<>();
                usernameList = new ArrayList<>();
                List<String> list;
                RecommenderListJSONParser rl = new RecommenderListJSONParser(jObj);
                list=rl.parse();
                for(int i=0;i<list.size();i=i+2){
                    nameList.add(list.get(i));
                    usernameList.add(list.get(i+1));
                }
                populateSpinner();
                //for some reason just calling datasetchanged doesnt work the listview is not updated but the adapter has new data;
            } else {

                Toast.makeText(UserAccountActivity.this, "Failed to get Info. Internal error",
                        Toast.LENGTH_SHORT).show();

            }

        }
    }
}
