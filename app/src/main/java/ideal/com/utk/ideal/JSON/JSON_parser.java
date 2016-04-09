package ideal.com.utk.ideal.JSON;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.util.Log;

import ideal.com.utk.ideal.custom_datatypes.NameValuePair;

/**
 * Created by Utkarsh on 17-02-2016 with the help of SWAG.
 */

public class JSON_parser {

     InputStream is = null;
     JSONObject jObj = null;
     String json = "";

    public JSONObject getJSONFromUrl(String verb, String url_str, NameValuePair... params) {
        try {
            URL url= new URL(url_str);
            HttpURLConnection urlConnection;

                url = new URL(url_str);
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setConnectTimeout(15000);

                if(verb.equals("GET")){
                    urlConnection.setDoOutput(false);
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                }
                else if(verb.equals("POST")){
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    urlConnection.setUseCaches(false);
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                }


                for(int i =0;i<params.length;i++){
                        urlConnection.setRequestProperty(params[i].name,params[i].value);
                }
                urlConnection.connect();

            try {
                is = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }


                is.close();
                // Convert the string builder data to an actual string.
                json = sb.toString();
                Log.d("qwerty",json);
            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }finally{
                urlConnection.disconnect();
            }
        }catch (IOException e){
            e.printStackTrace();
        }




        // Try to parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
            Log.d("tag",json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // Return the JSON Object.
        return jObj;

    }

    /*public JSONObject getJSONFromUrlPost(String url_str , NameValuePair... params){
        try{
            URL url= new URL(url_str);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);

            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            for(int i=0 ; i<params.length ; i++){
                urlConnection.setRequestProperty(params[i].name, params[i].value);
                Log.d("ddd",params[i].value);
            }


            *//*Uri.Builder builder = new Uri.Builder();
            for(int i=0 ; i<params.length ; i++){
                builder.appendQueryParameter(params[i].name, params[i].value);
            }
            String query = builder.build().getEncodedQuery();

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();*//*

            urlConnection.connect();
            try {
                is = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);

                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();

                json = sb.toString();
            }
            catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        try {
            Log.d("JSON data",json);
            jObj = new JSONObject(json);

        }
        catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return jObj;
    }*/
}