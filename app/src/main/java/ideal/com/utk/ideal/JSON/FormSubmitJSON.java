package ideal.com.utk.ideal.JSON;

import org.json.JSONObject;

/**
 * Created by Utkarsh on 07-04-2016 with the help of SWAG.
 */
public class FormSubmitJSON {

    private String status,message;
    private boolean code;
    public FormSubmitJSON(JSONObject jObj){
        try{
            status = jObj.getString("status");
            code = jObj.getBoolean("code");
            message = jObj.getString("message");
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public boolean isSuccess(){
        if(status.equals("success"))return true;
        else return false;
    }

    public boolean code(){
        return code;
    }

    public String getMessage(){
        return message;
    }
}
