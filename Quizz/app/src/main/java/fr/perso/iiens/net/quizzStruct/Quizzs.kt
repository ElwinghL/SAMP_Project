package fr.perso.iiens.net.quizzStruct

class Quizzs(var quizz: ArrayList<Quizz>) {
    fun getQuizzByName(quizzName: String): Quizz {
        return quizz.filter { it.name == quizzName }[0]
    }
    fun removeQuizzByName(quizzName:String) {
        quizz.remove(getQuizzByName(quizzName))
    }
}