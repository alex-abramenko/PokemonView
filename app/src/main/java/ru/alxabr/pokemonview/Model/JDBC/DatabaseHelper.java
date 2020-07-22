package ru.alxabr.pokemonview.Model.JDBC;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.regex.Pattern;

import ru.alxabr.pokemonview.Model.Wrapper.Pokemon;

/**
 * This class is designed to work with the local SQLie database.
 * This class extends the class SQLiteOpenHelper and overrides
 * methods onCreate(), onUpgrade().
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Cache";
    private static final String POKEMON_TABLE = "Pokemon";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + POKEMON_TABLE + "(" +
                "'name' STRING UNIQUE ON CONFLICT IGNORE, " +
                "'height' LONG, " +
                "'weight' LONG, " +
                "'type' STRING, " +
                "'attack' LONG, " +
                "'defense' LONG, " +
                "'hp' LONG, " +
                "'sprite' STRING);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + POKEMON_TABLE);
        onCreate(db);
    }

    /**
     * Adds a list of objects to the database table.
     * @param pokemonList ArrayList of Pokemon class objects to be add.
     */
    public void addPokemon(ArrayList<Pokemon> pokemonList) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i = 0; i < pokemonList.size(); i++) {
            ContentValues values = new ContentValues();
            Pokemon pokemon = pokemonList.get(i);

            values.put("name", pokemon.getName());
            values.put("height", pokemon.getHeight());
            values.put("weight", pokemon.getWeight());

            //Convert the array of strings into a single string delimited by "@".
            String str = "";
            for (int j = 0; j < pokemon.getTypes().size(); j++) {
                str = str + pokemon.getTypes().get(j) + "@";
            }

            values.put("type", str);
            values.put("attack", pokemon.getAttack());
            values.put("defense", pokemon.getDefense());
            values.put("hp", pokemon.getHp());
            values.put("sprite", pokemon.getSprite());

            db.insert(POKEMON_TABLE, null, values);
        }

        db.close();
    }

    /**
     * Getting data about objects of the Pokemon class from the database table.
     * @return ArrayList of Pokemon class objects obtained from the table.
     */
    public ArrayList<Pokemon> getPokemon() {
        ArrayList<Pokemon> pokemonList = new ArrayList();
        String selectQuery = "SELECT * FROM " + POKEMON_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                long height = Long.parseLong(cursor.getString(1));
                long weight = Long.parseLong(cursor.getString(2));

                //Parse the resulting string through the "@" separator.
                String[] str = Pattern.compile("@").split(cursor.getString(3));
                ArrayList<String> types = new ArrayList<>();
                for (int i = 0; i < str.length; i++) {
                    types.add(str[i]);
                }

                long attack = Long.parseLong(cursor.getString(4));
                long defense = Long.parseLong(cursor.getString(5));
                long hp = Long.parseLong(cursor.getString(6));
                String sprite = cursor.getString(6);

                Pokemon pokemon = new Pokemon(name, height, weight, types, attack, defense, hp, sprite);
                pokemonList.add(pokemon);
            } while (cursor.moveToNext());
        }

        return pokemonList;
    }

    /**
     * Clears the database table completely.
     */
    public void clearPokemon_Table() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(POKEMON_TABLE, null, null);
        db.execSQL(String.format("update sqlite_sequence set seq=0 WHERE Name='%s'", POKEMON_TABLE));
        db.close();
    }
}
