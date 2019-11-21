package fr.perso.iiens.net.quizz

import android.content.Context
import com.google.gson.Gson
import fr.perso.iiens.net.quizz.Menus.MainMenu
import fr.perso.iiens.net.quizzStruct.Quizzs
import java.io.File

fun saveQuizz(quizzs: Quizzs,context : Context) {
    val gson = Gson()
    val json = gson.toJson(quizzs)
    File(context.filesDir,"state.json").writeText(json)
}