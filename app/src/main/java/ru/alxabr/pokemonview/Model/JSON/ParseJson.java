package ru.alxabr.pokemonview.Model.JSON;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * This class is designed to work with the JSON-format.
 */
public class ParseJson {
    private String json;
    private StringReader reader;
    private JSONParser jsonParser;

    /**
     * Constructor.
     * @param json The string to parse.
     */
    public ParseJson(String json) {
        this.json = json;
        initialize();
    }

    private void initialize() {
        reader = new StringReader(json);
        jsonParser = new JSONParser();
    }

    /**
     * Finds the value of the attack stat in the JSON-String.
     * @return Attack value.
     */
    public long getStatAttack() {
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONArray array_stats = (JSONArray) jsonObject.get("stats");

            long base_stat = 0;
            for (int i = 0; i < array_stats.size(); i++) {
                jsonObject = (JSONObject) array_stats.get(i);

                base_stat = (long) jsonObject.get("base_stat");
                jsonObject = (JSONObject) jsonObject.get("stat");

                if(jsonObject.get("name").equals("attack"))
                    break;
                else
                    base_stat = 0;
            }

            initialize();

            return base_stat;

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        initialize();

        return 0;
    }

    /**
     * Finds the value of the defense stat in the JSON-String.
     * @return Defense value.
     */
    public long getStatDefense() {
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONArray array_stats = (JSONArray) jsonObject.get("stats");

            long base_stat = 0;
            for (int i = 0; i < array_stats.size(); i++) {
                jsonObject = (JSONObject) array_stats.get(i);

                base_stat = (long) jsonObject.get("base_stat");
                jsonObject = (JSONObject) jsonObject.get("stat");

                if(jsonObject.get("name").equals("defense"))
                    break;
                else
                    base_stat = 0;
            }

            initialize();

            return base_stat;

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        initialize();

        return 0;
    }

    /**
     * Finds the value of the HP stat in the JSON-String.
     * @return HP value.
     */
    public long getStatHP() {
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONArray array_stats = (JSONArray) jsonObject.get("stats");

            long base_stat = 0;
            for (int i = 0; i < array_stats.size(); i++) {
                jsonObject = (JSONObject) array_stats.get(i);

                base_stat = (long) jsonObject.get("base_stat");
                jsonObject = (JSONObject) jsonObject.get("stat");

                if(jsonObject.get("name").equals("hp"))
                    break;
                else
                    base_stat = 0;
            }

            initialize();

            return base_stat;

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        initialize();

        return 0;
    }

    /**
     * Finds the value of types in the JSON-String.
     * @return ArrayList of values of types.
     */
    public ArrayList<String> getTypes() {
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONArray array_types = (JSONArray) jsonObject.get("types");

            ArrayList<String> types = new ArrayList<>();

            for (int i = 0; i < array_types.size(); i++) {
                jsonObject = (JSONObject) array_types.get(i);
                jsonObject = (JSONObject) jsonObject.get("type");
                types.add((String) jsonObject.get("name"));
            }

            initialize();

            return types;

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        initialize();

        return null;
    }

    /**
     * Finds the value of the weight in the JSON-String.
     * @return Weight value.
     */
    public long getWeight() {
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            initialize();
            return (long) jsonObject.get("weight");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        initialize();
        return 0;
    }

    /**
     * Finds the value of the height in the JSON-String.
     * @return Height value.
     */
    public long getHeight() {
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            initialize();
            return (long) jsonObject.get("height");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        initialize();
        return 0;
    }

    /**
     * Finds the value of the name in the JSON-String.
     * @return Name value.
     */
    public String getName() {
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            initialize();
            return (String) jsonObject.get("name");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        initialize();
        return null;
    }

    /**
     * Finds URL pictures.
     * @return Pictures URL.
     */
    public String getSprite() {
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            jsonObject = (JSONObject) jsonObject.get("sprites");

            return (String) jsonObject.get("front_default");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        initialize();
        return null;
    }
}
