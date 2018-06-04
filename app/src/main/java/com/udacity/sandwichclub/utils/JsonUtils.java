package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        /*
          I have well received your feedback on using optString() and similar functions. I will
          keep that in mind and use them wherever required. In this project I have managed the
          visibility manually. If some value is not present, the section will not be visible.

          Thanks! for the great review.
         */

        try {
            JSONObject sandwichJson = new JSONObject(json);
            Sandwich sandwich = new Sandwich();

            JSONObject sandwichName = sandwichJson.getJSONObject("name");
            sandwich.setMainName(sandwichName.getString("mainName"));
            sandwich.setAlsoKnownAs(prepareStringList(sandwichName.getJSONArray("alsoKnownAs")));

            sandwich.setPlaceOfOrigin(sandwichJson.getString("placeOfOrigin"));
            sandwich.setDescription(sandwichJson.getString("description"));
            sandwich.setImage(sandwichJson.getString("image"));

            sandwich.setIngredients(prepareStringList(sandwichJson.getJSONArray("ingredients")));

            return sandwich;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static ArrayList<String> prepareStringList(JSONArray array) throws JSONException {
        ArrayList<String> strings = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            strings.add(array.getString(i));
        }

        return strings;
    }
}
