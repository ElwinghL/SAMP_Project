package fr.perso.iiens.net.quizz.Menus

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import com.beust.klaxon.Klaxon
import fr.perso.iiens.net.quizz.DownloadTask
import fr.perso.iiens.net.quizz.R
import fr.perso.iiens.net.quizz.saveQuizz
import fr.perso.iiens.net.quizzStruct.Quizzs
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainMenu : AppCompatActivity() {

    var currentQuizzs: Quizzs = Quizzs(ArrayList())
    private lateinit var context: Context
    private lateinit var mMediaPlayer: MediaPlayer
    private var shouldExecuteOnResume: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        shouldExecuteOnResume = false
        super.onCreate(savedInstanceState)
        //Suppression de la barre de titre
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        context = this.applicationContext
        setContentView(R.layout.activity_main)
        //Création des boutons de jeu
        btn_Play.setOnClickListener {
            if (currentQuizzs.quizz.size > 1) {
                startActivity(Intent(this, PlayMenu::class.java))
            } else {
                Toast.makeText(this,R.string.error_play_qty,Toast.LENGTH_LONG).show()
            }
        }
        btn_Edit.setOnClickListener {
            startActivity(Intent(this, EditMenu::class.java))
        }
        btn_Download.setOnClickListener {
            DownloadTask(
                this,
                "https://dept-info.univ-fcomte.fr/joomla/images/CR0700/Quizzs.xml"
            ).execute()
        }
        loadQuizz()
        mMediaPlayer = MediaPlayer.create(this, R.raw.jingle)
        mMediaPlayer.isLooping = true
        mMediaPlayer.start()
    }

    override fun onResume() {
        if(shouldExecuteOnResume) {
            loadQuizz()
            mMediaPlayer = MediaPlayer.create(this, R.raw.jingle)
            mMediaPlayer.isLooping = true
            mMediaPlayer.start()
        } else {
            shouldExecuteOnResume = true
        }
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMediaPlayer.stop()
    }

    fun printQuizzs(quizzs: Quizzs, tag: String = "Test") {
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

    fun loadQuizz() {
        if (File(context.filesDir, "state.json").exists()) {
            currentQuizzs = Klaxon().parse<Quizzs>(File(context.filesDir, "state.json"))!!
            printQuizzs(currentQuizzs, "TestAbs")
        } else {
            DownloadTask(
                this,
                "https://dept-info.univ-fcomte.fr/joomla/images/CR0700/Quizzs.xml"
            ).execute()
        }
    }
}