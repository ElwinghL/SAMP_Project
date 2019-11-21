package fr.perso.iiens.net.quizz.Menus

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.beust.klaxon.Klaxon
import fr.perso.iiens.net.quizz.DownloadTask
import fr.perso.iiens.net.quizz.R
import fr.perso.iiens.net.quizzStruct.Quizz
import fr.perso.iiens.net.quizzStruct.Quizzs
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainMenu : AppCompatActivity() {

    public var curentQuizzs: Quizzs = Quizzs(ArrayList())
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this.applicationContext
        setContentView(R.layout.activity_main)

        //Création des boutons de jeu
        btn_Play.setOnClickListener {
            startActivity(Intent(this, PlayMenu::class.java))
        }
        btn_Edit.setOnClickListener {
            startActivity(Intent(this, EditMenu::class.java))
        }

        if (File(context.filesDir, "state.json").exists()) {
            curentQuizzs = Klaxon().parse<Quizzs>(File(context.filesDir, "state.json"))!!
            printQuizzs(curentQuizzs,"TestAbs")
        } else {
            DownloadTask(
                this,
                "https://dept-info.univ-fcomte.fr/joomla/images/CR0700/Quizzs.xml"
            ).execute()

        }

    }

    public fun printQuizzs(quizzs: Quizzs, tag: String = "Test") {
        Log.d(tag, "Quizzs :")
        for (quizz in quizzs.quizz) {
            Log.d(tag, "Quizz : " + quizz.name)
            for (question in quizz.questions) {
                Log.d(tag, "\tQuestion : " + question.question)
                for (i in 0 until question.answers.size) {
                    val bonus = if (i == question.truth) " correcte :" else ": "
                    Log.d(tag, "\t\t Réponse $bonus" + question.answers[i])
                }
            }
        }
    }
}