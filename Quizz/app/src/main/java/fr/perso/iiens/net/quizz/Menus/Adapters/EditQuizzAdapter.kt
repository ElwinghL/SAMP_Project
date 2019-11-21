package fr.perso.iiens.net.quizz.Menus.Adapters

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.perso.iiens.net.quizz.Menus.EditQuizz
import fr.perso.iiens.net.quizz.R
import fr.perso.iiens.net.quizz.saveQuizz
import fr.perso.iiens.net.quizzStruct.Quizz
import fr.perso.iiens.net.quizzStruct.Quizzs
import kotlinx.android.synthetic.main.activity_edit_menu.*
import java.util.*

class EditQuizzAdapter(var editQuizz: EditQuizz, var quizzs: Quizzs, var quizz: Quizz) :
    RecyclerView.Adapter<EditQuizzAdapter.ViewHolder>() {
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var text: TextView = v.findViewById(R.id.edit_Question) as TextView
        var btn_AddAnswer: Button = v.findViewById(R.id.btn_addAnswer) as Button
        var btn_DelQuestion: Button = v.findViewById(R.id.btn_removeQuestion) as Button
        var viewAnswerList: RecyclerView = v.findViewById(R.id.question_answerList) as RecyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(editQuizz)
        val v = inflater.inflate(R.layout.item_edit_question, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = quizz.questions[position].question.trim()
        holder.text.addTextChangedListener(object : TextWatcher {
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

        holder.viewAnswerList.adapter =
            EditAnswerAdapter(editQuizz, quizz.questions[position], quizzs)

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return viewHolder.itemViewType == target.itemViewType
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (quizz.questions[position].answers.size > 2 && viewHolder.adapterPosition != quizz.questions[position].truth) {
                    quizz.questions[position].answers.remove(quizz.questions[position].answers[viewHolder.adapterPosition])
                    if (viewHolder.adapterPosition < quizz.questions[position].truth) {
                        quizz.questions[position].truth -= 1
                    }
                    editQuizz.quizzs = quizzs
                    (holder.viewAnswerList.adapter as EditAnswerAdapter).notifyDataSetChanged()
                } else {
                    Toast.makeText(editQuizz, "Non.", Toast.LENGTH_LONG).show()
                    (holder.viewAnswerList.adapter as EditAnswerAdapter).notifyDataSetChanged()
                }
            }
        }
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(holder.viewAnswerList)

        holder.btn_AddAnswer.setOnClickListener {
            if (quizz.questions[position].answers.size < 4) {
                quizz.questions[position].answers.add("New Answer")
                holder.viewAnswerList.adapter?.notifyDataSetChanged()
            } else {
                Toast.makeText(
                    editQuizz, editQuizz.getString(R.string.error_addAnswersQty),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        holder.btn_DelQuestion.setOnClickListener {
            Log.d("TestPosition",position.toString())
            quizz.questions.remove(quizz.questions[position])
            this.notifyItemRemoved(position)
            editQuizz.quizzs = quizzs
        }
    }

    override fun getItemCount(): Int {
        return quizz.questions.size
    }
}