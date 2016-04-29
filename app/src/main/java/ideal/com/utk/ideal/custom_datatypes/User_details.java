package ideal.com.utk.ideal.custom_datatypes;

import android.content.SharedPreferences;
import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Utkarsh on 24-02-2016 with the help of SWAG.
 */
public class User_details {

    private SharedPreferences prefs;
    public User_details(Context context){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }



    public String getUsername(){
        return prefs.getString("username",null);
    }
    public void setUsername(String s){
        prefs.edit().putString("username",s).apply();
    }

    public String getToken(){
        return prefs.getString("token",null);
    }
    public char getUserType(){
        return prefs.getString("token",null).charAt(0);
    }
    public void setToken(String s){
        prefs.edit().putString("token",s).apply();
    }
    public String getPassword(){
        return prefs.getString("password",null);
    }
    public void setPassword(String s){
        prefs.edit().putString("password",s).apply();
    }
    public String getOCL_balance(){
        return prefs.getString("ocl_balance",null);
    }
    public void setOCL_balance(String s){
        prefs.edit().putString("ocl_balance",s).apply();
    }
    public String getCL_balance(){
        return prefs.getString("cl_balance",null);
    }
    public void setCL_balance(String s){
        prefs.edit().putString("cl_balance",s).apply();
    }
    public String getFname(){
        return prefs.getString("fname",null);
    }
    public void setFname(String s){
        prefs.edit().putString("fname",s).apply();
    }
    public String getLname(){
        return prefs.getString("lname",null);
    }
    public void setLname(String s){
        prefs.edit().putString("lname",s).apply();
    }
    public String getRecommender(){
        return prefs.getString("recommender",null);
    }
    public void setRecommender(String s){
        prefs.edit().putString("recommender",s).apply();
    }
}
