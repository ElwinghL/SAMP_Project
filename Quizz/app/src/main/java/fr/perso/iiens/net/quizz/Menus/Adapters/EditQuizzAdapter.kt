package fr.perso.iiens.net.quizz.Menus.Adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.perso.iiens.net.quizz.Menus.EditQuizz
import fr.perso.iiens.net.quizz.R
import fr.perso.iiens.net.quizz.saveQuizz
import fr.perso.iiens.net.quizzStruct.Quizz
import fr.perso.iiens.net.quizzStruct.Quizzs

class EditQuizzAdapter(var editQuizz: EditQuizz, var quizzs : Quizzs,var quizz: Quizz) : RecyclerView.Adapter<EditQuizzAdapter.ViewHolder>() {
    class ViewHolder (v: View) : RecyclerView.ViewHolder(v) {
        var text : TextView = v.findViewById(R.id.edit_Question) as TextView
        var btn_AddAnswer : Button = v.findViewById(R.id.btn_addAnswer) as Button
        var btn_DelQuestion : Button = v.findViewById(R.id.btn_removeQuestion) as Button
        var viewAnswerList : RecyclerView = v.findViewById(R.id.question_answerList) as RecyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(editQuizz)
        val v = inflater.inflate(R.layout.item_edit_question,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = quizz.questions[position].question
        holder.text.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                quizz.questions[position].question = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                saveQuizz(quizzs,editQuizz.applicationContext)
                editQuizz.quizzs = quizzs
            }
        })

        holder.viewAnswerList.layoutManager = LinearLayoutManager(editQuizz)

        holder.viewAnswerList.adapter = EditAnswerAdapter(editQuizz,quizz.questions[position],quizzs)

        holder.btn_AddAnswer.setOnClickListener {
            if (quizz.questions[position].answers.size < 4) {
                quizz.questions[position].answers.add("New Answer")
                holder.viewAnswerList.adapter?.notifyDataSetChanged()
            } else {
                Toast.makeText(editQuizz, editQuizz.getString(R.string.error_addAnswersQty),
                    Toast.LENGTH_LONG).show()
            }
        }

        holder.btn_DelQuestion.setOnClickListener {
            quizz.questions.remove(quizz.questions[position])
            saveQuizz(quizzs,editQuizz.applicationContext)
            editQuizz.quizzs = quizzs
            this.notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int {
        return quizz.questions.size
    }
}