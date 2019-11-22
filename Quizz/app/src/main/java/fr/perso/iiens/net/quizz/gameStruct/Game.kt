package fr.perso.iiens.net.quizz.gameStruct

import android.util.Log
import android.view.View
import fr.perso.iiens.net.quizz.Menus.PlayGame
import fr.perso.iiens.net.quizzStruct.Quizz
import kotlinx.android.synthetic.main.activity_play_game.*
import java.lang.StringBuilder

class Game(val playGame: PlayGame, var quizz: Quizz) {
    var rows = ArrayList<Row>()
    var score = 0
    lateinit var currentRow: Row

    fun startGame() {
        for (aQuestion in quizz.questions) {
            rows.add(Row(this, aQuestion))
        }
        currentRow = rows.first()
        displayRow()

        playGame.linear_start.visibility = View.GONE
        playGame.linear_game.visibility = View.VISIBLE
    }

    fun nextRow() {
        val index = rows.indexOf(currentRow)
        if (index in (0 until rows.size - 1)) {
            currentRow = rows[index + 1]
            displayRow()
        } else {
            endGame()
        }
    }

    fun endGame() {
        playGame.linear_game.visibility = View.GONE
        playGame.linear_score.visibility = View.VISIBLE

        val scoreStr = StringBuilder()
        scoreStr.append(score).append("/").append(quizz.questions.size)
        playGame.game_score.text = scoreStr.toString()
    }

    fun displayRow() {
        playGame.displayQuestion(currentRow.question)
    }
}