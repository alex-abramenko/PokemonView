package ru.alxabr.pokemonview.Model.Wrapper;

import java.util.ArrayList;

/**
 * This class is designed to wrap
 * objects that characterize Pokemon.
 */
public class Pokemon {
    private String name;
    private long height;
    private long weight;
    private ArrayList<String> types;
    private long attack;
    private long defense;
    private long hp;
    private String sprite;
    public boolean max_attack = false;
    public boolean max_defense = false;
    public boolean max_hp = false;

    public Pokemon(String name, long height, long weight, ArrayList<String> types,
                   long attack, long defense, long hp, String sprite) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.types = types;
        this.attack = attack;
        this.defense = defense;
        this.hp = hp;
        this.sprite = sprite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<String> types) {
        this.types = types;
    }

    public long getAttack() {
        return attack;
    }

    public void setAttack(long attack) {
        this.attack = attack;
    }

    public long getDefense() {
        return defense;
    }

    public void setDefense(long defense) {
        this.defense = defense;
    }

    public long getHp() {
        return hp;
    }

    public void setHp(long hp) {
        this.hp = hp;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }
}
