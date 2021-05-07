package gr.ihu.msc.cinema.classes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {
    public static JSONObject getJsonObject(String jsonString){
        JSONObject jObj = null;

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(jsonString);
        } catch (JSONException e) {
            jObj = null;
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return jObj;
    }

//fix
   /* public static JSONArray getJsonArray(String jsonString){
        JSONArray jArr = null;
        // try parse the string to a JSON Array
        try {
            jArr = new JSONArray(jsonString);
        } catch (JSONException e) {
            jArr = null;
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jArr;
    }*/
}
