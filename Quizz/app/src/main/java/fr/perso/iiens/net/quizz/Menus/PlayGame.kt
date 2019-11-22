package fr.perso.iiens.net.quizz.Menus

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.beust.klaxon.Klaxon
import com.jakewharton.threetenabp.AndroidThreeTen
import fr.perso.iiens.net.quizz.Menus.Adapters.PlayGameAdapter
import fr.perso.iiens.net.quizz.R
import fr.perso.iiens.net.quizz.gameStruct.Game
import fr.perso.iiens.net.quizz.saveQuizz
import fr.perso.iiens.net.quizzStruct.Question
import fr.perso.iiens.net.quizzStruct.Quizz
import fr.perso.iiens.net.quizzStruct.Quizzs
import fr.perso.iiens.net.quizzStruct.Score
import kotlinx.android.synthetic.main.activity_play_game.*
import kotlinx.android.synthetic.main.activity_play_menu.*
import java.io.File
import java.sql.Time
import java.time.LocalDateTime
import java.time.ZonedDateTime

class PlayGame : AppCompatActivity() {
    lateinit var quizzs: Quizzs
    lateinit var quizz: Quizz
    lateinit var game: Game

    private fun updateList() {
        play_ViewAnswers.adapter?.notifyItemInserted(play_ViewAnswers.adapter!!.itemCount)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)
        setContentView(R.layout.activity_play_game)
        quizzs = Klaxon().parse<Quizzs>(File(this.applicationContext.filesDir, "state.json"))!!
        quizz = quizzs.quizz[intent.getSerializableExtra("KEY_QUIZZ") as Int]
        game = Game(this,quizz)

        linear_start.visibility = View.VISIBLE
        game_QuizzName.text = quizz.name

        btn_game_start.setOnClickListener {
            game.startGame()
            updateList()
        }

        btn_game_next.setOnClickListener {
            game.currentRow.noAnswer()
        }

        btn_game_highscore.setOnClickListener {
            if (quizz.highscore.size > 0) {
                startActivity(goToHighScore())
            } else {
                Toast.makeText(this,"You must have at least played this quizz once",Toast.LENGTH_LONG).show()
            }
        }

        btn_game_save.setOnClickListener {
            val score = Score(name_pseudo.text.toString(),game.score,quizz.questions.size)
            quizz.addScore(score)
            quizzs.replaceQuizz(quizz)
            saveQuizz(quizzs,this)
            startActivity(goToHighScore())
        }
    }

    fun displayQuestion(question: Question) {
        game_QuestionName.text = question.question
        play_ViewAnswers.layoutManager = LinearLayoutManager(this)
        play_ViewAnswers.adapter = PlayGameAdapter(this,question)
        updateList()
    }

    fun goToHighScore(): Intent {
        val intent = Intent(this,HighscoreMenu::class.java)
        intent.putExtra("KEY_QUIZZ",quizzs.quizz.indexOf(quizz))
        return intent
    }
}
