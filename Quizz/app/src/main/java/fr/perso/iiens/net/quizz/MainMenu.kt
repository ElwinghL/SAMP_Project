package fr.perso.iiens.net.quizz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("TestInit","Init0")

        DownloadTask(this).execute()


    }

    public fun printQuizzs(quizzs : Quizzs) {
        Log.d("Test","Quizzs :")
        for (quizz in quizzs.getQuizzs) {
            Log.d("Test","Quizz : " + quizz.getName)
            for (question in quizz.getQuestions) {
                Log.d("Test","\tQuestion : " + question.getQuestion)
                for (i in 0..question.getAnwers.size) {
                    val bonus = if (i==question.getTruth)" correcte :" else ": "
                    Log.d("Test","\t\t Réponse $bonus" + question.getAnwers[i])
                }
            }
        }
    }
}