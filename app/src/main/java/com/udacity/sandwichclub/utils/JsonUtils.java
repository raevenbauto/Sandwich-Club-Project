package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        int j = 0;
        List<String> akaList = new ArrayList<String>();
        List<String> ingredientsList = new ArrayList<String>();
        Sandwich sandwich = null;

        try {
            JSONObject objectJSON = new JSONObject(json);
            JSONObject nameObject = objectJSON.getJSONObject("name");
            // Food Name
            String mainName = nameObject.getString("mainName");
            JSONArray akaNameArray = nameObject.getJSONArray("alsoKnownAs");

            if (akaNameArray.length() != 0){
                j = 0;
                while (j != akaNameArray.length()){
                    //Insert AKA name here.
                    akaList.add(akaNameArray.getString(j));
                    j++;
                }
            }

            // Place of Origin
            String originName = objectJSON.getString("placeOfOrigin");

            // Description
            String description = objectJSON.getString("description");

            //ImageLink
            String imageLink = objectJSON.getString("image");

            JSONArray ingredientsArray = objectJSON.getJSONArray("ingredients");
            if (ingredientsArray.length() != 0){
                j = 0;
                while (j != ingredientsArray.length()){
                    //Insert Array of Ingredients
                    ingredientsList.add(ingredientsArray.getString(j));
                    j++;
                }
            }

            sandwich = new Sandwich(mainName, akaList, originName,
                    description, imageLink, ingredientsList);
        }

        catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
