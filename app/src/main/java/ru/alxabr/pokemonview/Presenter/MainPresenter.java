package ru.alxabr.pokemonview.Presenter;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;

import ru.alxabr.pokemonview.ContractMVP;
import ru.alxabr.pokemonview.Model.MainModel;
import ru.alxabr.pokemonview.Model.Wrapper.Pokemon;

/**
 * Binds the View and Model. Implements the Contract.Presenter interface.
 * Implements business logic to find the maximum value in a list.
 */
public class MainPresenter implements ContractMVP.Presenter {
    private ContractMVP.View view;
    private ContractMVP.Model model;
    private Context viewContext;
    private ArrayList<Pokemon> currentPokemonList;

    public MainPresenter(ContractMVP.View view, Context viewContext) {
        this.view = view;
        this.viewContext = viewContext;
        this.model = new MainModel();
        this.currentPokemonList = new ArrayList<>();
    }

    @Override
    public void showPokemonList() {
        view.hideErrorEmptyCache();
        view.showBigLoad();
        new TaskForLoad().execute();
    }

    @Override
    public void updatePokemonList() {
        view.hideErrorEmptyCache();
        view.disableUI();
        new TaskForLoad().execute();
    }

    @Override
    public void refreshPokemonList() {
        view.hideErrorEmptyCache();
        view.showBigLoad();
        model.refreshLast_pokemon_id();
        currentPokemonList.clear();
        view.resetStatePokemonList();
        new TaskForLoad().execute();
    }

    @Override
    public void showMarkedMaxItemPokemonList() {
        view.hideErrorEmptyCache();
        view.disableUI();
        markMaxItemPokemonList();
        view.resetStatePokemonList();
        view.showPokemonList(currentPokemonList);
        view.enableUI();
    }

    /**
     * Mark list objects with maximum parameters Attack, Defense, HP.
     */
    private void markMaxItemPokemonList() {
        if (currentPokemonList.size() == 0)
            return;

        for (int i = 0; i < 3; i++) {
            currentPokemonList.get(i).max_attack = false;
            currentPokemonList.get(i).max_defense = false;
            currentPokemonList.get(i).max_hp = false;
        }

        boolean[] array = view.checkedCheckBoxes();

        if (array[0]) {
            int index_max = searchMaxByAttack(currentPokemonList);
            Pokemon tmp = currentPokemonList.get(index_max);
            tmp.max_attack = true;
            currentPokemonList.add(0, tmp);
            currentPokemonList.remove(index_max + 1);
        }

        if (array[1]) {
            int index_max = searchMaxByDefense(currentPokemonList);
            Pokemon tmp = currentPokemonList.get(index_max);
            tmp.max_defense = true;
            currentPokemonList.add(0, tmp);
            currentPokemonList.remove(index_max + 1);
        }

        if (array[2]) {
            int index_max = searchMaxByHP(currentPokemonList);
            Pokemon tmp = currentPokemonList.get(index_max);
            tmp.max_hp = true;
            currentPokemonList.add(0, tmp);
            currentPokemonList.remove(index_max + 1);
        }
    }

    /**
     * Finds the position of the object in the list with the maximum attack.
     * @param pokemonList The list of objects of the Pokemon class in which to search.
     * @return The position of the object.
     */
    private int searchMaxByAttack(ArrayList<Pokemon> pokemonList) {
        int index_max = 0;
        for (int i = 1; i < pokemonList.size(); i++) {
            if (pokemonList.get(index_max).getAttack() < pokemonList.get(i).getAttack()) {
                index_max = i;
            }
        }
        return index_max;
    }

    /**
     * Finds the position of the object in the list with the maximum defense.
     * @param pokemonList The list of objects of the Pokemon class in which to search.
     * @return The position of the object.
     */
    private int searchMaxByDefense(ArrayList<Pokemon> pokemonList) {
        int index_max = 0;
        for (int i = 1; i < pokemonList.size(); i++) {
            if (pokemonList.get(index_max).getDefense() < pokemonList.get(i).getDefense()) {
                index_max = i;
            }
        }
        return index_max;
    }

    /**
     * Finds the position of the object in the list with the maximum HP.
     * @param pokemonList The list of objects of the Pokemon class in which to search.
     * @return The position of the object.
     */
    private int searchMaxByHP(ArrayList<Pokemon> pokemonList) {
        int index_max = 0;
        for (int i = 1; i < pokemonList.size(); i++) {
            if (pokemonList.get(index_max).getHp() < pokemonList.get(i).getHp()) {
                index_max = i;
            }
        }
        return index_max;
    }

    class TaskForLoad extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                ArrayList<Pokemon> pokemonList = model.getPokemon();
                model.cache(pokemonList, viewContext);
                currentPokemonList.addAll(pokemonList);
                return false;
            }
            catch (IOException e) {
                e.printStackTrace();
                ArrayList<Pokemon> pokemonList = model.getCache(viewContext);
                currentPokemonList.addAll(pokemonList);
                return true;
            }
        }

        @Override
        protected void onPostExecute(Boolean listIsCache) {
            super.onPostExecute(listIsCache);

            if (listIsCache)
                view.showError();

            if(currentPokemonList.size() == 0)
                view.showErrorEmptyCache();
            else {
                markMaxItemPokemonList();
                view.showUpdateMessage();
            }

            view.showPokemonList(currentPokemonList);
            view.hideBigLoad();
            view.enableUI();
        }
    }
}
