package ideal.com.utk.ideal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import ideal.com.utk.ideal.JSON.JSON_parser;
import ideal.com.utk.ideal.JSON.LoginJSONSchema;
import ideal.com.utk.ideal.custom_datatypes.NameValuePair;
import ideal.com.utk.ideal.custom_datatypes.User_details;

public class LogInActivity extends AppCompatActivity {
    private  String URL_ADDRESS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        URL_ADDRESS= getResources().getString(R.string.server_url)+"leaveSlimAPI/public/index.php/login";

        final EditText password = (EditText)findViewById(R.id.password);
        final EditText username = (EditText)findViewById(R.id.username);
        Button login_but = (Button)findViewById(R.id.log_in_button);

        final User_details saved_user = new User_details(LogInActivity.this);

        String saved_username = saved_user.getUsername();
        final String saved_password = saved_user.getPassword();

        final CheckBox remember_login = (CheckBox)findViewById(R.id.remember_login_checkbox);

        if(saved_username !=null && saved_password != null){
            AttemptLogin at = new AttemptLogin();
            at.execute(saved_username,saved_password);
        }

        login_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttemptLogin at = new AttemptLogin();

                String entered_username= username.getText().toString();
                String entered_password = password.getText().toString();
                at.execute(entered_username,entered_password);

                if(remember_login.isChecked()){
                    saved_user.setUsername(entered_username);
                    saved_user.setPassword(entered_password);
                }
                /*
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("JSON","{\"status\":\"success\",\"code\":true,\"message\":\"Record found\",\"details\":[{\"username\":\"UTK\",\"name\":\"Utkarsh\",\"password\":\"123\",\"CL_Balance\":\"0\",\"OCL_Balance\":\"0\"}]}");
                finishActivity(0);
                startActivity(i);*/

            }
        });

    }

    class AttemptLogin extends AsyncTask<String,Void,JSONObject> {

        //three methods get called, first preExecute, then do in background, and once do
        //in back ground is completed, the onPost execute method will be called.//TODO:incorporate on progreessdialog cancelled method in all asynctask

        private ProgressDialog pdia;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdia = new ProgressDialog(LogInActivity.this);
            pdia.setMessage("Logging In ...");
            pdia.show();


        }

        @Override
        protected JSONObject doInBackground(String... uname_pass) {

            NameValuePair user = new NameValuePair();
            user.name="Username";
            user.value= uname_pass[0];
            NameValuePair pass = new NameValuePair();
            pass.name="Password";
            pass.value = uname_pass[1];

            JSON_parser json_parser = new JSON_parser();
            JSONObject jObj = json_parser.getJSONFromUrl("POST", URL_ADDRESS , user,pass);

            User_details saved_user = new User_details(LogInActivity.this);
            LoginJSONSchema login = new LoginJSONSchema(jObj , saved_user);
            if(!login.LoginSuccess()){
                Log.d("login","loginSuccess false");
                jObj = null;
            }
            else{
                saved_user.setPassword(pass.value);
                login.LoginGetDetails();
            }
            return jObj;

        }

        protected void onPostExecute(JSONObject jObj) {

            pdia.dismiss();

            if(jObj != null) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finishActivity(0);
                startActivity(i);
            }
            else{

                Toast.makeText(LogInActivity.this, "Failed to login. Invalid username or password",
                        Toast.LENGTH_SHORT).show();

            }
        }


    }
}
