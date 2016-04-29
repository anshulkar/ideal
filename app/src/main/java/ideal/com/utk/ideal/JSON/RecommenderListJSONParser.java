package ideal.com.utk.ideal.JSON;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Utkarsh on 29-04-2016 with the help of SWAG.
 */
public class RecommenderListJSONParser {
    JSONObject jObj = new JSONObject();

    public RecommenderListJSONParser(JSONObject j){
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

    public List<String> parse(){

        List<String> list = new ArrayList<>();

        try{
            JSONObject record;
            for(int i =0; i<jObj.getJSONArray("records").length() ; i++){
                record = jObj.getJSONArray("records").getJSONObject(i);
                list.add(record.getString("Fname")+" "+record.getString("Lname"));
                list.add(record.getString("Username"));

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
