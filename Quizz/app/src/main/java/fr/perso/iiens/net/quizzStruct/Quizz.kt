package fr.perso.iiens.net.quizzStruct

class Quizz(var name: String, var questions: ArrayList<Question>) {
    var highscore: ArrayList<Score> = ArrayList()

    fun addScore(score: Score) {
        highscore.add(score)
    }
}

fun defaultQuizz(): Quizz {
    val questions = ArrayList<Question>()
    questions.add(defaultQuestion())

    return Quizz("New Quizz", questions)
}