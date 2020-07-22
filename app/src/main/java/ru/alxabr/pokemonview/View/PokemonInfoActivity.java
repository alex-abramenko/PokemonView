package ru.alxabr.pokemonview.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.alxabr.pokemonview.R;

public class PokemonInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_info);

        setTitle("Detail");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView textView_name = findViewById(R.id.textView_PI_name);
        TextView textView_type = findViewById(R.id.textView_type);
        ImageView imageView = findViewById(R.id.imageView2);
        TextView textView_height = findViewById(R.id.textView_height);
        TextView textView_weight = findViewById(R.id.textView_weight);
        TextView textView_attack = findViewById(R.id.textView_attack);
        TextView textView_defense = findViewById(R.id.textView_defense);
        TextView textView_hp = findViewById(R.id.textView_hp);

        String name = (String) getIntent().getSerializableExtra("name");
        ArrayList<String> type = (ArrayList<String>) getIntent().getSerializableExtra("type");
        String sprite = (String) getIntent().getSerializableExtra("sprite");
        long height = (long) getIntent().getSerializableExtra("height");
        long weight = (long) getIntent().getSerializableExtra("weight");
        long attack = (long) getIntent().getSerializableExtra("attack");
        long defense = (long) getIntent().getSerializableExtra("defense");
        long hp = (long) getIntent().getSerializableExtra("hp");

        textView_name.setText(name);

        String tmp = "";
        for (int i = 0; i < type.size() - 1; i++) {
            tmp += type.get(i) + ", ";
        } tmp += type.get(type.size()-1);
        textView_type.setText(tmp);

        String mDrawableName = "non_image"; // файл cat1.png в папке drawable
        int resID = getResources().getIdentifier(mDrawableName ,
                "drawable", getPackageName());
        Picasso.get().load(sprite).placeholder(resID).error(resID).into(imageView);

        textView_height.setText(String.format("%d", height));
        textView_weight.setText(String.format("%d", weight));
        textView_attack.setText(String.format("%d", attack));
        textView_defense.setText(String.format("%d", defense));
        textView_hp.setText(String.format("%d", hp));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
