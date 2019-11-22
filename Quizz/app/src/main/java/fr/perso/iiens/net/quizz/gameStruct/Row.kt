package fr.perso.iiens.net.quizz.gameStruct

import fr.perso.iiens.net.quizzStruct.Question

class Row(var game:Game, var question: Question) {
    fun trueAnswer() {
        ++game.score
        game.nextRow()
    }
    fun wrongAnswer() {
        --game.score
        game.nextRow()
    }
    fun noAnswer() {
        game.nextRow()
    }
}