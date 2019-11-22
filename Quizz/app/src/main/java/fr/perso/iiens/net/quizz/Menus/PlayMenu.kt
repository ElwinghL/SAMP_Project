package fr.perso.iiens.net.quizz.Menus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.beust.klaxon.Klaxon
import fr.perso.iiens.net.quizz.Menus.Adapters.PlayMenuAdapter
import fr.perso.iiens.net.quizz.R
import fr.perso.iiens.net.quizz.saveQuizz
import fr.perso.iiens.net.quizzStruct.Quizzs
import kotlinx.android.synthetic.main.activity_play_menu.*
import java.io.File

class PlayMenu : AppCompatActivity() {

    lateinit var quizzs: Quizzs

    private fun updateList() {
        play_ViewQuizzs.adapter!!.notifyItemInserted(play_ViewQuizzs.adapter!!.itemCount)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_menu)
        quizzs = Klaxon().parse<Quizzs>(File(this.applicationContext.filesDir, "state.json"))!!

        play_ViewQuizzs.layoutManager = LinearLayoutManager(this)
        play_ViewQuizzs.adapter = PlayMenuAdapter(this,quizzs)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        quizzs = Klaxon().parse<Quizzs>(File(this.applicationContext.filesDir, "state.json"))!!
        updateList()
    }

    override fun onBackPressed() {
        saveQuizz(quizzs,this)
        this.finish()
    }
}
