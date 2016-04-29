package ideal.com.utk.ideal;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ideal.com.utk.ideal.JSON.JSON_parser;
import ideal.com.utk.ideal.JSON.LeaveDataJSONParser;
import ideal.com.utk.ideal.JSON.LeaveDataJSONSchema;
import ideal.com.utk.ideal.custom_datatypes.NameValuePair;
import ideal.com.utk.ideal.custom_datatypes.User_details;

public class LeaveProcessingFragment extends Fragment {

    private String URL_ADDRESS;
    private ProgressDialog pdia;

    public LeaveProcessingFragment() {
        // Required empty public constructor
    }
    private List<LeaveDataJSONSchema> leaveDataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LeaveDataAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        URL_ADDRESS  = getResources().getString(R.string.server_url)+"index.php/apps/getMyApps/processing";

        View view = inflater.inflate(R.layout.fragment_leave_approved_rejected_requests_processing, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mAdapter = new LeaveDataAdapter(leaveDataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        setRetainInstance(true);

        RetrieveInfo ri = new RetrieveInfo();
        ri.execute();



        return view;
    }

    @Override
    public void onStop() {
        super.onStop();

        if(pdia!= null)
            pdia.dismiss();
    }


    class RetrieveInfo extends AsyncTask<Void, Void, JSONObject> {


        //three methods get called, first preExecute, then do in background, and once do
        //in back ground is completed, the onPost execute method will be called.

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdia = new ProgressDialog(getContext());
            pdia.setMessage("Retrieving Info from server ...");
            pdia.show();


        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            User_details saved_user = new User_details(getActivity());

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
             JSONObject jObj = json_parser.getJSONFromUrl("GET" , URL_ADDRESS, user, token, type);

            /**JSONObject jObj= null;
            try{
                jObj = new JSONObject("{\"records\":[{\"id\":1,\"username\":\"Leah\",\"type\":\"Rose\",\"nature\":\"Willie\",\"startdate\":\"Justin\",\"enddate\":\"Rita\",\"grounds\":\"Patrick\",\"recommender\":\"Dorothy\",\"approver\":\"Wesley\",\"recommendercomment\":\"Eddie\",\"approvercomment\":\"Pauline\",\"status\":\"null\"},{\"id\":1,\"username\":\"Leah\",\"type\":\"Rose\",\"nature\":\"Willie\",\"startdate\":\"Justin\",\"enddate\":\"Rita\",\"grounds\":\"Patrick\",\"recommender\":\"Dorothy\",\"approver\":\"Wesley\",\"recommendercomment\":\"Eddie\",\"approvercomment\":\"Pauline\",\"status\":\"null\"},{\"id\":1,\"username\":\"Leah\",\"type\":\"Rose\",\"nature\":\"Willie\",\"startdate\":\"Justin\",\"enddate\":\"Rita\",\"grounds\":\"Patrick\",\"recommender\":\"Dorothy\",\"approver\":\"Wesley\",\"recommendercomment\":\"Eddie\",\"approvercomment\":\"Pauline\",\"status\":\"null\"},{\"id\":2,\"username\":\"Sherri\",\"type\":\"Audrey\",\"nature\":\"Christina\",\"startdate\":\"Maxine\",\"enddate\":\"Paul\",\"grounds\":\"Bruce\",\"recommender\":\"Joanne\",\"approver\":\"Julie\",\"recommendercomment\":\"Scott\",\"approvercomment\":\"Rhonda\",\"status\":\"null\"},{\"id\":3,\"username\":\"Bill\",\"type\":\"Dorothy\",\"nature\":\"Kathy\",\"startdate\":\"Lloyd\",\"enddate\":\"Connie\",\"grounds\":\"Brett\",\"recommender\":\"Clyde\",\"approver\":\"Ruth\",\"recommendercomment\":\"Joel\",\"approvercomment\":\"Carrie\",\"status\":\"null\"},{\"id\":3,\"username\":\"Bill\",\"type\":\"Dorothy\",\"nature\":\"Kathy\",\"startdate\":\"Lloyd\",\"enddate\":\"Connie\",\"grounds\":\"Brett\",\"recommender\":\"Clyde\",\"approver\":\"Ruth\",\"recommendercomment\":\"Joel\",\"approvercomment\":\"Carrie\",\"status\":\"null\"},{\"id\":3,\"username\":\"Bill\",\"type\":\"Dorothy\",\"nature\":\"Kathy\",\"startdate\":\"Lloyd\",\"enddate\":\"Connie\",\"grounds\":\"Brett\",\"recommender\":\"Clyde\",\"approver\":\"Ruth\",\"recommendercomment\":\"Joel\",\"approvercomment\":\"Carrie\",\"status\":\"null\"},{\"id\":4,\"username\":\"Bonnie\",\"type\":\"Peter\",\"nature\":\"Ann\",\"startdate\":\"Glenda\",\"enddate\":\"Pat\",\"grounds\":\"Geraldine\",\"recommender\":\"Jan\",\"approver\":\"Sherri\",\"recommendercomment\":\"Greg\",\"approvercomment\":\"Gayle\",\"status\":\"null\"},{\"id\":4,\"username\":\"Bonnie\",\"type\":\"Peter\",\"nature\":\"Ann\",\"startdate\":\"Glenda\",\"enddate\":\"Pat\",\"grounds\":\"Geraldine\",\"recommender\":\"Jan\",\"approver\":\"Sherri\",\"recommendercomment\":\"Greg\",\"approvercomment\":\"Gayle\",\"status\":\"null\"},{\"id\":5,\"username\":\"Ellen\",\"type\":\"Terry\",\"nature\":\"Helen\",\"startdate\":\"Betsy\",\"enddate\":\"Doris\",\"grounds\":\"Audrey\",\"recommender\":\"Marvin\",\"approver\":\"Craig\",\"recommendercomment\":\"Shawn\",\"approvercomment\":\"Dawn\",\"status\":\"null\"},{\"id\":6,\"username\":\"Max\",\"type\":\"Joanna\",\"nature\":\"Malcolm\",\"startdate\":\"Steve\",\"enddate\":\"Jack\",\"grounds\":\"Cheryl\",\"recommender\":\"Michelle\",\"approver\":\"Miriam\",\"recommendercomment\":\"Brian\",\"approvercomment\":\"Beth\",\"status\":\"null\"},{\"id\":6,\"username\":\"Max\",\"type\":\"Joanna\",\"nature\":\"Malcolm\",\"startdate\":\"Steve\",\"enddate\":\"Jack\",\"grounds\":\"Cheryl\",\"recommender\":\"Michelle\",\"approver\":\"Miriam\",\"recommendercomment\":\"Brian\",\"approvercomment\":\"Beth\",\"status\":\"null\"},{\"id\":7,\"username\":\"Caroline\",\"type\":\"Christina\",\"nature\":\"Joseph\",\"startdate\":\"Eleanor\",\"enddate\":\"Greg\",\"grounds\":\"Thomas\",\"recommender\":\"Martin\",\"approver\":\"Nathan\",\"recommendercomment\":\"Alice\",\"approvercomment\":\"Benjamin\",\"status\":\"null\"},{\"id\":8,\"username\":\"Vincent\",\"type\":\"Joanna\",\"nature\":\"Alice\",\"startdate\":\"Dennis\",\"enddate\":\"Claire\",\"grounds\":\"Sara\",\"recommender\":\"Betsy\",\"approver\":\"Brett\",\"recommendercomment\":\"Gretchen\",\"approvercomment\":\"Karen\",\"status\":\"null\"},{\"id\":8,\"username\":\"Vincent\",\"type\":\"Joanna\",\"nature\":\"Alice\",\"startdate\":\"Dennis\",\"enddate\":\"Claire\",\"grounds\":\"Sara\",\"recommender\":\"Betsy\",\"approver\":\"Brett\",\"recommendercomment\":\"Gretchen\",\"approvercomment\":\"Karen\",\"status\":\"null\"}]}");
            }
            catch (Exception e){
                e.printStackTrace();
            }**/
            return jObj;
        }

        protected void onPostExecute(JSONObject jObj) {

            pdia.dismiss();
            if (jObj != null) {
                leaveDataList.clear();
                leaveDataList.addAll(new LeaveDataJSONParser(jObj).parse());
                mAdapter.notifyDataSetChanged();//for some reason just calling datasetchanged doesnt work the listview is not updated but the adapter has new data;
            } else {

                Toast.makeText(getActivity(), "Failed to get Info. Internal error",
                        Toast.LENGTH_SHORT).show();

            }

        }
    }
}


