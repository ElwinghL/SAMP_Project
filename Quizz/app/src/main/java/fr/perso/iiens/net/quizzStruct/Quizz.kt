package fr.perso.iiens.net.quizzStruct

class Quizz(var name: String, var questions: ArrayList<Question>) {
    var highscore : ArrayList<Score> = ArrayList()

    fun addScore(score:Score) {
        highscore.add(score)
    }
}