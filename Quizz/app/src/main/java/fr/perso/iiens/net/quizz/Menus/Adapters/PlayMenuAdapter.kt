package fr.perso.iiens.net.quizz.Menus.Adapters

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import fr.perso.iiens.net.quizz.Menus.HighscoreMenu
import fr.perso.iiens.net.quizz.Menus.PlayGame
import fr.perso.iiens.net.quizz.Menus.PlayMenu
import fr.perso.iiens.net.quizz.R
import fr.perso.iiens.net.quizzStruct.Quizzs

class PlayMenuAdapter (var playMenu: PlayMenu, var quizzs: Quizzs) : RecyclerView.Adapter<PlayMenuAdapter.ViewHolder>(){
    class ViewHolder (v: View) : RecyclerView.ViewHolder(v) {
        var text : TextView = v.findViewById(R.id.play_QuizzName) as TextView
        var btn_play : Button = v.findViewById(R.id.btn_play_quizz) as Button
        var btn_highscore : Button = v.findViewById(R.id.btn_play_highscore) as Button
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(playMenu)
        val v = inflater.inflate(R.layout.item_play_menu,parent,false)

        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#C0C0C0"))
        }
        holder.text.text = quizzs.quizz[position].name.trim()

        holder.btn_play.setOnClickListener {
            val intent = Intent(playMenu,PlayGame::class.java)
            intent.putExtra("KEY_QUIZZ",position)
            startActivity(playMenu,intent,null)
        }

        holder.btn_highscore.setOnClickListener {
            if (quizzs.quizz[position].highscore.size > 0 ) {
                val intent = Intent(playMenu,HighscoreMenu::class.java)
                intent.putExtra("KEY_QUIZZ",position)
                startActivity(playMenu,intent,null)
            } else {
                Toast.makeText(
                    playMenu,
                    "You must have at least played this quizz once",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return quizzs.quizz.size
    }
}