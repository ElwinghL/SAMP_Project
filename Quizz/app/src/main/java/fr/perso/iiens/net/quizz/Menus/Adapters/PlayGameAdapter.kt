package fr.perso.iiens.net.quizz.Menus.Adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import fr.perso.iiens.net.quizz.Menus.PlayGame
import fr.perso.iiens.net.quizz.R
import fr.perso.iiens.net.quizzStruct.Question
import kotlinx.android.synthetic.main.item_play_game.view.*

class PlayGameAdapter(var playGame: PlayGame, var question: Question) :
    RecyclerView.Adapter<PlayGameAdapter.ViewHolder>() {
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var btn_answer: Button = v.findViewById(R.id.btn_answer) as Button
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayGameAdapter.ViewHolder {
        val inflater = LayoutInflater.from(playGame)
        val v = inflater.inflate(R.layout.item_play_game, parent, false)

        return PlayGameAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: PlayGameAdapter.ViewHolder, position: Int) {
        holder.btn_answer.text = question.answers[position]

        holder.btn_answer.setOnClickListener {
            if (position == question.truth) {
                playGame.game.currentRow.trueAnswer()
            } else {
                playGame.game.currentRow.wrongAnswer()
            }
        }
    }

    override fun getItemCount(): Int {
        return question.answers.size
    }
}