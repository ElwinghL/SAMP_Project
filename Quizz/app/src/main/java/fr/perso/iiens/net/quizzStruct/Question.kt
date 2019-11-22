package fr.perso.iiens.net.quizzStruct

class Question(var question: String, var answers: ArrayList<String>, var truth: Int) {}

fun defaultQuestion(): Question {
    val defaultAnswers = ArrayList<String>()
    defaultAnswers.add("Good Answer")
    defaultAnswers.add("Wrong Answer")
    return Question("Your new Question", defaultAnswers, 0)
}