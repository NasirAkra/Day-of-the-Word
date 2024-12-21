package com.brain.dayoftheword

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DictionaryManager(context: Context) {

    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences("dictionary_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        const val FAVORITES_KEY = "favorite_words"
    }


    fun addWordToFavorites(word: String) : Boolean {
        val currentFavorites = getFavoriteWords().toMutableSet()
        if (currentFavorites.add(word)){
            saveFavoriteWords(currentFavorites)
            return true
        }
        return false
    }

    fun removeWordFromFavorites(word: String) : Boolean {
        val currentFavorites = getFavoriteWords().toMutableSet()
        if(currentFavorites.remove(word)){
            saveFavoriteWords(currentFavorites)
            return true
        }
        return false
    }


    fun isWordFavorite(word: String): Boolean {
        return getFavoriteWords().contains(word)
    }


    fun getFavoriteWords(): Set<String> {
        val json = sharedPrefs.getString(FAVORITES_KEY, null)
        return json?.let {
            val type = object : TypeToken<Set<String>>(){}.type
            gson.fromJson(it, type)
        } ?: emptySet()
    }

    private fun saveFavoriteWords(words: Set<String>) {
        val json = gson.toJson(words)
        sharedPrefs.edit().putString(FAVORITES_KEY, json).apply()
    }
}