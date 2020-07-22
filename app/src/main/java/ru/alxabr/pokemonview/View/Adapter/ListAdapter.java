package ru.alxabr.pokemonview.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.alxabr.pokemonview.Model.Wrapper.Pokemon;
import ru.alxabr.pokemonview.R;
import ru.alxabr.pokemonview.View.PokemonInfoActivity;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Pokemon> pokemonList;
    private Context mainContext;

    public ListAdapter(Context context, List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
        this.inflater = LayoutInflater.from(context);
        this.mainContext = context;
    }
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);

        if(pokemon.max_attack)
            holder.textView_attack_max.setVisibility(View.VISIBLE);
        else
            holder.textView_attack_max.setVisibility(View.GONE);

        if(pokemon.max_defense)
            holder.textView_defense_max.setVisibility(View.VISIBLE);
        else
            holder.textView_defense_max.setVisibility(View.GONE);

        if(pokemon.max_hp)
            holder.textView_hp_max.setVisibility(View.VISIBLE);
        else
            holder.textView_hp_max.setVisibility(View.GONE);

        String mDrawableName = "non_image";
        int resID = mainContext.getResources().getIdentifier(mDrawableName ,
                "drawable", mainContext.getPackageName());

        Picasso.get().load(pokemon.getSprite()).placeholder(resID).error(resID).into(holder.imageView);
        holder.textView_name.setText(pokemon.getName());
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView textView_name;
        final TextView textView_attack_max;
        final TextView textView_defense_max;
        final TextView textView_hp_max;

        ViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.imageView);
            textView_name = view.findViewById(R.id.textView_name);
            textView_attack_max = view.findViewById(R.id.textView_max_attack);
            textView_defense_max = view.findViewById(R.id.textView_max_defense);
            textView_hp_max = view.findViewById(R.id.textView_max_hp);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mainContext, PokemonInfoActivity.class);
                    int pos = getAdapterPosition();
                    intent.putExtra("name", pokemonList.get(pos).getName());
                    intent.putExtra("type", pokemonList.get(pos).getTypes());
                    intent.putExtra("sprite", pokemonList.get(pos).getSprite());
                    intent.putExtra("height", pokemonList.get(pos).getHeight());
                    intent.putExtra("weight", pokemonList.get(pos).getWeight());
                    intent.putExtra("attack", pokemonList.get(pos).getAttack());
                    intent.putExtra("defense", pokemonList.get(pos).getDefense());
                    intent.putExtra("hp", pokemonList.get(pos).getHp());
                    mainContext.startActivity(intent);
                }
            });
        }
    }
}
