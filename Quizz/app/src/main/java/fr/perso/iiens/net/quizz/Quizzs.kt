package fr.perso.iiens.net.quizz

class Quizzs(private val quizz: ArrayList<Quizz>) {
    val getQuizzs = quizz
}

class Quizz(private val name: String, private val questions: ArrayList<Question>) {
    val getName = name

    val getQuestions = questions
}

class Question(private val question: String, private val answers: ArrayList<String>,private val truth: Int) {
    val nbAnswers = answers.size

    val getQuestion = question

    val getAnwers = answers

    val getCorrectAnswer = answers[truth]

    val getTruth = truth

    fun isThisCorrect(index : Int) = index == truth
}