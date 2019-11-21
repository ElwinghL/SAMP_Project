package fr.perso.iiens.net.quizzStruct

import java.io.Serializable

class Quizz(var name: String, var questions: ArrayList<Question>) : Serializable {
    var highscore : ArrayList<Score> = ArrayList()

    fun addScore(score:Score) {
        highscore.add(score)
    }
}