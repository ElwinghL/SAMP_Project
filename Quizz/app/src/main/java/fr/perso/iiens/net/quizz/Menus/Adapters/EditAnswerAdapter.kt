package fr.perso.iiens.net.quizz.Menus.Adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.perso.iiens.net.quizz.Menus.EditMenu
import fr.perso.iiens.net.quizz.Menus.EditQuizz
import fr.perso.iiens.net.quizz.R
import fr.perso.iiens.net.quizz.saveQuizz
import fr.perso.iiens.net.quizzStruct.Question
import fr.perso.iiens.net.quizzStruct.Quizzs

class EditAnswerAdapter(var editQuizz : EditQuizz, var question: Question, var quizzs: Quizzs) : RecyclerView.Adapter<EditAnswerAdapter.ViewHolder>() {

    class ViewHolder (v: View) : RecyclerView.ViewHolder(v) {
        var text : TextView = v.findViewById(R.id.edit_AnswerText) as TextView
        var rad_Truth : RadioButton = v.findViewById(R.id.rad_correctAnswer) as RadioButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(editQuizz)
        val v = inflater.inflate(R.layout.item_edit_answer,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = question.answers[position].trim()
        holder.rad_Truth.isChecked = question.truth == position
        holder.text.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                question.answers[position] = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                saveQuizz(quizzs,editQuizz.applicationContext)
                editQuizz.quizzs = quizzs
            }
        })

        holder.rad_Truth.setOnClickListener {
            question.truth = position
            saveQuizz(quizzs,editQuizz.applicationContext)
            editQuizz.quizzs = quizzs
            this.notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return question.answers.size
    }
}