package ru.alxabr.pokemonview;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;

import ru.alxabr.pokemonview.Model.Wrapper.Pokemon;

public interface ContractMVP {
    interface Model {
        ArrayList<Pokemon> getPokemon() throws IOException;
        void refreshLast_pokemon_id();
        void cache(ArrayList<Pokemon> pokemonList, Context context);
        ArrayList<Pokemon> getCache(Context context);
        void clearCache(Context context);
    }

    interface Presenter {
        void showPokemonList();
        void updatePokemonList();
        void refreshPokemonList();
        void showMarkedMaxItemPokemonList();
    }

    interface View {
        void disableUI();
        void enableUI();
        void showBigLoad();
        void hideBigLoad();
        void showPokemonList(ArrayList<Pokemon> pokemonList);
        void resetStatePokemonList();
        void showError();
        void showUpdateMessage();
        void showErrorEmptyCache();
        void hideErrorEmptyCache();
        boolean[] checkedCheckBoxes();
    }
}
