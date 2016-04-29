package ideal.com.utk.ideal.JSON;

import org.json.JSONObject;

import ideal.com.utk.ideal.custom_datatypes.User_details;

/**
 * Created by Utkarsh on 18-02-2016 with the help of SWAG.
 */
public class LoginJSONSchema {
    JSONObject jObj = new JSONObject();
    private User_details user_details;
    public LoginJSONSchema(JSONObject j, User_details u){
        jObj= j;
        user_details=u;
    }

    public boolean LoginSuccess(){
        Boolean log_succ= false;

        try{
            log_succ = jObj.getBoolean("code");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return log_succ;
    }

    public String returnToken(){
        try {
            return jObj.getString("Token");
        }
        catch(Exception e){
            
        }
        return null;
    }
    public void LoginGetDetails(){
        JSONObject details ;

        try {
            user_details.setToken(jObj.getString("Token"));

            details = jObj.getJSONArray("details").getJSONObject(0);

            user_details.setUsername(details.getString("Username"));
            user_details.setFname(details.getString("Fname"));
            user_details.setLname(details.getString("Lname"));
            user_details.setOCL_balance(details.getString("OCL_Balance"));
            user_details.setCL_balance(details.getString("CL_Balance"));
            user_details.setRecommender(details.getString("recommender"));

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
