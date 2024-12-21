package com.brain.dayoftheword

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var wordOfDayTextView: TextView
    private lateinit var favoriteButton: Button
    private lateinit var dictionaryManager: DictionaryManager
    private lateinit var favoritesListTextView: TextView
    private lateinit var dateTimeTextView: TextView


    private val words = listOf(
        "serendipity", "ephemeral", "ubiquitous", "mellifluous", "quixotic",
        "luminescent", "sonorous", "ethereal", "cacophony", "photometric"
    )
    private var currentWord: String? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wordOfDayTextView = findViewById(R.id.wordOfDayTextView)
        favoriteButton = findViewById(R.id.favoriteButton)
        favoritesListTextView = findViewById(R.id.favoritesListTextView)
        dictionaryManager = DictionaryManager(this)
        dateTimeTextView = findViewById(R.id.dateTimeTextView)


        displayWordOfTheDay()
        updateFavoritesDisplay()
        updateDateTime()
        favoriteButton.setOnClickListener {
            currentWord?.let { word ->
                if (dictionaryManager.isWordFavorite(word)) {
                    dictionaryManager.removeWordFromFavorites(word)
                    favoriteButton.text = "Add to Favorites"
                } else {
                    dictionaryManager.addWordToFavorites(word)
                    favoriteButton.text = "Remove from Favorites"
                }
                updateFavoritesDisplay()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayWordOfTheDay() {
        val calendar = Calendar.getInstance()
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        val wordIndex = dayOfYear % words.size
        currentWord = words[wordIndex]

        wordOfDayTextView.text = currentWord

        if(currentWord?.let { dictionaryManager.isWordFavorite(it) } == true){
            favoriteButton.text = "Remove from Favorites"
        }else{
            favoriteButton.text = "Add to Favorites"
        }

    }
    private fun updateFavoritesDisplay() {
        val favorites = dictionaryManager.getFavoriteWords()
        favoritesListTextView.text = if (favorites.isNotEmpty()) {
            favorites.joinToString(", ")
        } else {
            "No favorites added yet"
        }
    }
    private fun updateDateTime(){
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd\nHH:mm:ss", Locale.getDefault())
        val formattedDateTime = dateFormat.format(calendar.time)
        dateTimeTextView.text = formattedDateTime
    }
}