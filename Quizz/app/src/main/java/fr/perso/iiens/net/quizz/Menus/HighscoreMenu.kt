package fr.perso.iiens.net.quizz.Menus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.beust.klaxon.Klaxon
import fr.perso.iiens.net.quizz.Menus.Adapters.HighscoreAdapter
import fr.perso.iiens.net.quizz.R
import fr.perso.iiens.net.quizzStruct.Quizz
import fr.perso.iiens.net.quizzStruct.Quizzs
import kotlinx.android.synthetic.main.activity_highscore_menu.*
import java.io.File

class HighscoreMenu : AppCompatActivity() {

    lateinit var quizzs: Quizzs
    lateinit var quizz: Quizz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highscore_menu)
        quizzs = Klaxon().parse<Quizzs>(File(this.applicationContext.filesDir, "state.json"))!!
        quizz = quizzs.quizz[intent.getSerializableExtra("KEY_QUIZZ") as Int]

        val aList = ArrayList(quizz.highscore.sortedWith(compareBy({ it.score }, { it.time })))
        aList.reverse()
        view_game_highscore.layoutManager = LinearLayoutManager(this)
        view_game_highscore.adapter = HighscoreAdapter(
            this,aList
        )
    }
}
