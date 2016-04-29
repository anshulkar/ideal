package ideal.com.utk.ideal;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import ideal.com.utk.ideal.JSON.FormSubmitJSON;
import ideal.com.utk.ideal.JSON.JSON_parser;
import ideal.com.utk.ideal.custom_datatypes.NameValuePair;
import ideal.com.utk.ideal.custom_datatypes.User_details;

/**
 * Created by Utkarsh on 09-04-2016 with the help of SWAG.
 */
public class LeaveDialog extends DialogFragment {


    private String URL_ADDRESS;
    private ProgressDialog pdia;
    // Empty constructor required for DialogFragment
    public LeaveDialog() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leavedialog, container);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.d_toolbar);

        Bundle bundle = this.getArguments();

        if(bundle.getString("type")=="OCL"){
            URL_ADDRESS = getResources().getString(R.string.server_url)+"index.php/apps/giveOCL";
        }
        else URL_ADDRESS = getResources().getString(R.string.server_url)+"index.php/apps/giveCL";
        if(!bundle.getBoolean("showrej"))URL_ADDRESS = URL_ADDRESS +  "Recommendation";
        else URL_ADDRESS =URL_ADDRESS +  "Approval";
        toolbar.setTitle(bundle.getString("title"));

        final EditText comments = (EditText)view.findViewById(R.id.l_diag_comments);
        final Button sub = (Button)view.findViewById(R.id.l_diag_submit_but);
        Button rej = (Button)view.findViewById(R.id.l_diag_reject_but);

        if(!bundle.getBoolean("showrej"))rej.setVisibility(view.GONE);

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comm = comments.getText().toString();
                Submit s= new Submit();
                s.execute(URL_ADDRESS,comm,"0");
            }
        });


        return view;
    }


    class Submit extends AsyncTask<String,Void,JSONObject> {

        //three methods get called, first preExecute, then do in background, and once do
        //in back ground is completed, the onPost execute method will be called.//TODO:incorporate on progreessdialog cancelled method in all asynctask

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdia = new ProgressDialog(getActivity());
            pdia.setMessage("Submitting ...");
            pdia.show();

        }

        @Override
        protected JSONObject doInBackground(String... data) {

            User_details user = new User_details(getActivity());


            NameValuePair username = new NameValuePair();
            username.name = "Username";
            username.value = ""+user.getUsername();
            NameValuePair token = new NameValuePair();
            token.name = "Token";
            token.value = "" + user.getToken();

            NameValuePair usertype = new NameValuePair();
            usertype.name = "Type";
            usertype.value = "" + user.getUserType();

            NameValuePair comments =new NameValuePair();
            comments.name="rComments";



            JSON_parser json_parser = new JSON_parser();
            JSONObject jObj = json_parser.getJSONFromUrl("POST",data[0] ,username,token,usertype);


            return jObj;

        }

        protected void onPostExecute(JSONObject jObj) {

            pdia.dismiss();

            if(jObj != null) {
                FormSubmitJSON form = new FormSubmitJSON(jObj);
                if(!form.isSuccess()) Toast.makeText(getActivity(), "Failed to Submit. Server returned status error.", Toast.LENGTH_SHORT).show();
                else if(!form.code())Toast.makeText(getActivity(), "Failed to Submit."+ form.getMessage(), Toast.LENGTH_SHORT).show();
                else Toast.makeText(getActivity(), "OCL Application Successful", Toast.LENGTH_SHORT).show();
            }
            else{

                Toast.makeText(getActivity(), "Failed to Submit. Network unreachable or Internal server error.",
                        Toast.LENGTH_SHORT).show();

            }
        }


    }
}

