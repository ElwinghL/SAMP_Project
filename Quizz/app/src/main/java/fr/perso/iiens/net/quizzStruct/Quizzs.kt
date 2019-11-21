package fr.perso.iiens.net.quizzStruct

import java.io.Serializable

class Quizzs(var quizz: ArrayList<Quizz>) : Serializable {
    fun isEmpty(): Boolean {
        return quizz.isEmpty();
    }

    fun exist(quizzName: String): Boolean {
        return quizz.find { it.name == quizzName } != null
    }
    fun getQuizzByName(quizzName: String): Quizz? {
        return quizz.find { it.name == quizzName }
    }

    fun removeQuizzByName(quizzName: String) {
        quizz.remove(getQuizzByName(quizzName))
    }

    fun concatQuizzs(otherQuizzs : Quizzs) {
        for (aQuizz in otherQuizzs.quizz) {
            if (!exist(aQuizz.name)) {
                quizz.add(aQuizz)
            }
        }
    }
}