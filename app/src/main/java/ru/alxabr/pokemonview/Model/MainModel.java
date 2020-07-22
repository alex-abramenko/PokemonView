package ru.alxabr.pokemonview.Model;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;

import ru.alxabr.pokemonview.ContractMVP;
import ru.alxabr.pokemonview.Model.JDBC.DatabaseHelper;
import ru.alxabr.pokemonview.Model.JSON.ParseJson;
import ru.alxabr.pokemonview.Model.Network.GetDataAPI;
import ru.alxabr.pokemonview.Model.Wrapper.Pokemon;

/**
 * This class implements the Contract.Model interface.
 * Designed to work with data.
 */
public class MainModel implements ContractMVP.Model {
    private final int RND_MIN = 1;
    private final int RND_MAX = 600;
    private final int PKMN_COUNT = 30; //The number of downloadable Pokemon at one time.

    private static int last_pokemon_id = -1; //The ID of the last downloaded Pokemon.

    private String uri = "https://pokeapi.co/api/v2/pokemon/"; //URL to connect to API.

    /**
     * Receives a string with a response from the API,
     * parses it and converts it into a ArrayList of Pokemon objects.
     * @return ArrayList of converted Pokemon objects from API.
     * @throws IOException To catch errors when connecting in GetDataAPI class.
     */
    @Override
    public ArrayList<Pokemon> getPokemon() throws IOException {
        int id = last_pokemon_id;

        /*
         * If the last ID of the loaded Pokemon class object
         * is not specified (last_pokemon_id = -1),
         * the initial ID is randomly specified.
         */
        if(last_pokemon_id == -1)
            id = (int) (Math.random() * ((RND_MAX - RND_MIN) + 1)) + RND_MIN;

        ArrayList<Pokemon> pokemonList = new ArrayList<>();
        for (int i = id; i < id + PKMN_COUNT; i++) {
            String url = uri + i;
            String json = new GetDataAPI().getHTML(url);

            ParseJson parseJson = new ParseJson(json);

            Pokemon pokemon = new Pokemon(parseJson.getName(), parseJson.getHeight(), parseJson.getWeight(),
                    parseJson.getTypes(), parseJson.getStatAttack(), parseJson.getStatDefense(),
                    parseJson.getStatDefense(), parseJson.getSprite());

            pokemonList.add(pokemon);
        }

        last_pokemon_id = id + PKMN_COUNT;

        return pokemonList;
    }

    /**
     * Caches a ArrayList of Pokemon class objects.
     * @param pokemonList ArrayList of Pokemon objects to cache.
     * @param context Context from Activity for DatabaseHelper.
     */
    @Override
    public void cache(ArrayList<Pokemon> pokemonList, Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.addPokemon(pokemonList);
    }

    /**
     * Loads the cache.
     * @param context Context from Activity for DatabaseHelper.
     * @return ArrayList of Pokemon class objects from the cache.
     */
    @Override
    public ArrayList<Pokemon> getCache(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        return databaseHelper.getPokemon();
    }

    /**
     * Clears Cache.
     * @param context Context from Activity for DatabaseHelper.
     */
    @Override
    public void clearCache(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.clearPokemon_Table();
    }

    /**
     * Refresh the tagged ID of the loaded object.
     */
    @Override
    public void refreshLast_pokemon_id() {
        last_pokemon_id = -1;
    }
}
