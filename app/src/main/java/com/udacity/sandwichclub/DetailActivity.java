package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    public TextView akaTV, akaTVStatic, placeOfOriginTV,placeOfOriginTVStatic, descriptionTV, ingredientsTV, mainNameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ImageView foodIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
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
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(foodIv);

        setTitle(sandwich.getMainName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        onBackPressed();
        return true;
    }
    private void closeOnError() {
        finish();
        //Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        int i = 0;
        declareUIVariables();
        mainNameTV.setText(sandwich.getMainName());

        if (sandwich.getAlsoKnownAs().size() != 0){
            akaTV.setVisibility(View.VISIBLE);
            akaTVStatic.setVisibility(View.VISIBLE);

            while (i != sandwich.getAlsoKnownAs().size()){
                if (i == sandwich.getAlsoKnownAs().size() - 2){
                    akaTV.append(sandwich.getAlsoKnownAs().get(i) + " and ");
                }

                else if (i == sandwich.getAlsoKnownAs().size() - 1){
                    akaTV.append(sandwich.getAlsoKnownAs().get(i));
                }

                else {
                    akaTV.append(sandwich.getAlsoKnownAs().get(i) + ", ");
                }
                i++;
            }
        }

        if (sandwich.getPlaceOfOrigin().length() != 0){
            placeOfOriginTV.setVisibility(View.VISIBLE);
            placeOfOriginTVStatic.setVisibility(View.VISIBLE);
            placeOfOriginTV.setText(sandwich.getPlaceOfOrigin());
        }

        descriptionTV.setText(sandwich.getDescription());
        for (int j = 0; j < sandwich.getIngredients().size(); j++){
            String ingredientsContent = sandwich.getIngredients().get(j);
            ingredientsContent = ingredientsContent.substring(0,1).toUpperCase() + ingredientsContent.substring(1);
            ingredientsTV.append(j+1 + ". " + ingredientsContent + "\n");
        }
    }

    private void declareUIVariables(){
        akaTV = (TextView)findViewById(R.id.tv_AKA);
        akaTVStatic = (TextView)findViewById(R.id.tv_staticAKA);
        akaTV.setText("");
        akaTV.setVisibility(View.GONE);
        akaTVStatic.setVisibility(View.GONE);

        placeOfOriginTV = (TextView)findViewById(R.id.tv_placeOfOrigin);
        placeOfOriginTVStatic = (TextView)findViewById(R.id.tv_staticPlaceOfOrigin);
        placeOfOriginTV.setVisibility(View.GONE);
        placeOfOriginTVStatic.setVisibility(View.GONE);

        descriptionTV = (TextView)findViewById(R.id.tv_description);
        ingredientsTV = (TextView)findViewById(R.id.tv_ingredients);
        ingredientsTV.setText("");
        mainNameTV = (TextView)findViewById(R.id.tv_FoodNamePic);
    }
}
