package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

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
