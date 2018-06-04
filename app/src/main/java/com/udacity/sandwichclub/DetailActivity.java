package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
            return;
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        /*
          I very wel understood you feedback regarding adding an error placeholder. I will make
          sure to add the same in future projects. I didn't use any place holder as I was unable
          to find any nice looking image which has suitable usage rights. To avoid any conflicts
          I left it as it is.
         */
        Picasso.get()
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {


        String alsoKnownAs = TextUtils.join(", ", sandwich.getAlsoKnownAs()); // Works like a charm.

        /* Also known as part will only be visible if the json's alsoKnownAs attribute is non empty.
         Similar is the scenario for all the other places where ternary operator is used to
         alter the visibility of view.*/
        findViewById(R.id.also_known_container)
                .setVisibility(TextUtils.isEmpty(alsoKnownAs) ? View.GONE : View.VISIBLE); // Altering View's visibility based on data
        ((TextView) findViewById(R.id.also_known_as_tv)).setText(alsoKnownAs);


        String ingredients = TextUtils.join(",", sandwich.getIngredients());
        findViewById(R.id.ingredients_container)
                .setVisibility(TextUtils.isEmpty(ingredients) ? View.GONE : View.VISIBLE); // Altering View's visibility based on data
        ((TextView) findViewById(R.id.ingredients_tv)).setText(ingredients);

        findViewById(R.id.origin_container)
                .setVisibility(TextUtils.isEmpty(sandwich.getPlaceOfOrigin()) ? View.GONE : View.VISIBLE); // Altering View's visibility based on data
        ((TextView) findViewById(R.id.origin_tv)).setText(sandwich.getPlaceOfOrigin());

        findViewById(R.id.description_container)
                .setVisibility(TextUtils.isEmpty(sandwich.getDescription()) ? View.GONE : View.VISIBLE); // Altering View's visibility based on data
        ((TextView) findViewById(R.id.description_tv)).setText(sandwich.getDescription());
    }
}
