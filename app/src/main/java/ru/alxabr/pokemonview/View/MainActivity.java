package ru.alxabr.pokemonview.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.alxabr.pokemonview.ContractMVP;
import ru.alxabr.pokemonview.Presenter.MainPresenter;
import ru.alxabr.pokemonview.R;
import ru.alxabr.pokemonview.Model.Wrapper.Pokemon;
import ru.alxabr.pokemonview.View.Adapter.ListAdapter;

/**
 * Implements the view of the main screen.
 */
public class MainActivity extends AppCompatActivity implements ContractMVP.View {
    ContractMVP.Presenter presenter;
    private Context thisContext;

    private LinearLayout layout_load;
    private LinearLayout layout_base;
    private CheckBox checkBox_attack;
    private CheckBox checkBox_defense;
    private CheckBox checkBox_hp;
    private Button button_refresh;
    private RecyclerView recyclerViewPokemons;
    private ProgressBar progressBar;
    private TextView textView_errorConnection;
    private Parcelable mLayoutManagerState;
    private CustomGridLayoutManager layoutManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout_load = findViewById(R.id.layout_load);
        layout_base = findViewById(R.id.layout_base);
        checkBox_attack = findViewById(R.id.checkBox_attack);
        checkBox_defense = findViewById(R.id.checkBox_defense);
        checkBox_hp = findViewById(R.id.checkBox_hp);
        button_refresh = findViewById(R.id.button_refresh);
        recyclerViewPokemons = findViewById(R.id.recyclerViewPokemons);
        progressBar = findViewById(R.id.progressBar);
        textView_errorConnection = findViewById(R.id.textView_errorConnection);

        layoutManager = new CustomGridLayoutManager(thisContext);

        thisContext = this;
        presenter = new MainPresenter(this, thisContext);

        button_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewPokemons.getAdapter().notifyDataSetChanged();
                presenter.refreshPokemonList();
            }
        });

        recyclerViewPokemons.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    mLayoutManagerState = recyclerViewPokemons.getLayoutManager().onSaveInstanceState();
                    presenter.updatePokemonList();
                }
            }
        });

        View.OnClickListener checkBoxListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayoutManagerState = null;
                presenter.showMarkedMaxItemPokemonList();
            }
        };

        checkBox_attack.setOnClickListener(checkBoxListener);
        checkBox_defense.setOnClickListener(checkBoxListener);
        checkBox_hp.setOnClickListener(checkBoxListener);

        presenter.showPokemonList();
    }

    /**
     * Outputs the list to RecyclerView.
     * @param pokemonList ArrayList of Pokemon class objects.
     */
    @Override
    public void showPokemonList(final ArrayList<Pokemon> pokemonList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListAdapter adapter = new ListAdapter(thisContext, pokemonList);
                recyclerViewPokemons.setAdapter(adapter);
                //recyclerViewPokemons.setLayoutManager(new LinearLayoutManager(thisContext));
                recyclerViewPokemons.setLayoutManager(layoutManager);

                if (mLayoutManagerState != null) {
                    recyclerViewPokemons.getLayoutManager().onRestoreInstanceState(mLayoutManagerState);
                }
            }
        });
    }


    @Override
    public void resetStatePokemonList() {
        mLayoutManagerState = null;
    }

    /**
     * Makes CheckBoxes, Button and RecycleView inactive.
     */
    @Override
    public void disableUI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                checkBox_attack.setEnabled(false);
                checkBox_defense.setEnabled(false);
                checkBox_hp.setEnabled(false);
                button_refresh.setEnabled(false);
                button_refresh.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                layoutManager.setScrollEnabled(false);
            }
        });
    }

    /**
     * Makes CheckBoxes, Button and RecycleView active.
     */
    @Override
    public void enableUI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                checkBox_attack.setEnabled(true);
                checkBox_defense.setEnabled(true);
                checkBox_hp.setEnabled(true);
                button_refresh.setEnabled(true);
                button_refresh.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                layoutManager.setScrollEnabled(true);
            }
        });
    }

    /**
     * Shows custom views and hides loading.
     */
    @Override
    public void hideBigLoad() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                layout_base.setVisibility(android.view.View.VISIBLE);
                layout_load.setVisibility(android.view.View.GONE);
            }
        });
    }

    /**
     * Hides custom views and shows loading.
     */
    @Override
    public void showBigLoad() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                layout_base.setVisibility(android.view.View.GONE);
                layout_load.setVisibility(android.view.View.VISIBLE);
            }
        });
    }

    /**
     * Shows a Toast with an internet connection error message.
     */
    @Override
    public void showError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(thisContext,
                        "Pokemon failed to load, cache is shown (Check your internet connection).",
                        Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    @Override
    public void showUpdateMessage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(thisContext,
                        "Pokуmon are loaded. Scroll...",
                        Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    @Override
    public void showErrorEmptyCache(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                checkBox_attack.setVisibility(View.GONE);
                checkBox_defense.setVisibility(View.GONE);
                checkBox_hp.setVisibility(View.GONE);
                recyclerViewPokemons.setVisibility(View.GONE);
                textView_errorConnection.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideErrorEmptyCache(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                checkBox_attack.setVisibility(View.VISIBLE);
                checkBox_defense.setVisibility(View.VISIBLE);
                checkBox_hp.setVisibility(View.VISIBLE);
                recyclerViewPokemons.setVisibility(View.VISIBLE);
                textView_errorConnection.setVisibility(View.GONE);
            }
        });
    }

    /**
     * Finds out the state of the CheckBoxes.
     * @return Result isChecked() checkBoxes (boolean[0] - checkBox sort by Attack,
     * boolean[1] - checkBox sort by Defense, boolean[3] - checkBox sort by HP).
     */
    @Override
    public boolean[] checkedCheckBoxes() {
        boolean[] array = {checkBox_attack.isChecked(),
                checkBox_defense.isChecked(),
                checkBox_hp.isChecked()};
        return array;
    }

    public class CustomGridLayoutManager extends LinearLayoutManager {
        private boolean isScrollEnabled = true;

        public CustomGridLayoutManager(Context context) {
            super(context);
        }

        public void setScrollEnabled(boolean flag) {
            this.isScrollEnabled = flag;
        }

        @Override
        public boolean canScrollVertically() {
            //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
            return isScrollEnabled && super.canScrollVertically();
        }
    }
}
