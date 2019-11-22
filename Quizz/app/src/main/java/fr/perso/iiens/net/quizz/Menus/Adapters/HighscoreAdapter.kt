package fr.perso.iiens.net.quizz.Menus.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.perso.iiens.net.quizz.Menus.HighscoreMenu
import fr.perso.iiens.net.quizz.R
import fr.perso.iiens.net.quizzStruct.Score
import java.lang.StringBuilder

class HighscoreAdapter (var highscoreMenu: HighscoreMenu, var highscore: ArrayList<Score>): RecyclerView.Adapter<HighscoreAdapter.ViewHolder>(){
    class ViewHolder (v: View) : RecyclerView.ViewHolder(v) {
        var pseudo : TextView = v.findViewById(R.id.highscore_pseudo) as TextView
        var score : TextView = v.findViewById(R.id.highscore_score) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(highscoreMenu)
        val v = inflater.inflate(R.layout.item_highscore,parent,false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pseudo.text = highscore[position].pseudo
        val s = StringBuilder()
        s.append(highscore[position].score).append("/").append(highscore[position].on)
        holder.score.text = s.toString()

    }

    override fun getItemCount(): Int {
        return highscore.size
    }
}
