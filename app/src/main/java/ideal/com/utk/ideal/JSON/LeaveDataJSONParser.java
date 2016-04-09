package ideal.com.utk.ideal.JSON;

import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Utkarsh on 04-04-2016 with the help of SWAG.
 */
public class LeaveDataJSONParser {

    JSONObject jObj = new JSONObject();

    public LeaveDataJSONParser(JSONObject j){
        jObj=j;
    }

    public boolean isSuccess(){
        Boolean succ= false;

        try{
            if("success" == jObj.getString("status"))succ= true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return succ;
    }

    public boolean recordsPresent(){
        Boolean succ= false;

        try{
            succ = jObj.getBoolean("code");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return succ;
    }

    public List<LeaveDataJSONSchema> parse(){

        List<LeaveDataJSONSchema> leave_data_list = new ArrayList<>();

        try{
            JSONObject record;
            for(int i =0; i<jObj.getJSONArray("records").length() ; i++){
                record = jObj.getJSONArray("records").getJSONObject(i);
                LeaveDataJSONSchema leave_data = new LeaveDataJSONSchema(
                        record.getInt("id"),
                        "OCL",//record.getString("type"), TODO:add a leave type in api
                        record.getString("nature"),
                        record.getString("leaveStart"),
                        record.getString("leaveEnd"),
                        record.getString("groundsForLeave"),
                        record.getString("recommender"),
                        record.getString("approver"),
                        record.getString("recommenderComment"),
                        record.getString("approverComment"),
                        record.getString("status")
                    );
                leave_data_list.add(leave_data);

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return leave_data_list;
    }
}
