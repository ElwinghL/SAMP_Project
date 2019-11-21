package fr.perso.iiens.net.quizz.Menus

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.Klaxon
import fr.perso.iiens.net.quizz.Menus.Adapters.EditQuizzAdapter
import fr.perso.iiens.net.quizz.R
import fr.perso.iiens.net.quizz.saveQuizz
import fr.perso.iiens.net.quizzStruct.Question
import fr.perso.iiens.net.quizzStruct.Quizz
import fr.perso.iiens.net.quizzStruct.Quizzs
import kotlinx.android.synthetic.main.activity_edit_quizz.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class EditQuizz : AppCompatActivity() {

    lateinit var quizzs: Quizzs
    lateinit var context: Context
    lateinit var quizz : Quizz
    private fun updateList() {
        edit_ViewQuestionList.adapter?.notifyItemInserted(edit_ViewQuestionList.adapter!!.itemCount)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_quizz)
        context = this.applicationContext
        quizzs = Klaxon().parse<Quizzs>(File(this.applicationContext.filesDir, "state.json"))!!
        quizz = quizzs.quizz[intent.getSerializableExtra("KEY_QUIZZ") as Int]
        edit_ViewQuestionList.layoutManager = LinearLayoutManager(this)
        edit_ViewQuestionList.adapter = EditQuizzAdapter(this,quizzs,quizz)
        //Ajout d'une question
        btn_addQuestion.setOnClickListener {
            val defaultAnswers = ArrayList<String>()
            defaultAnswers.add("Good Answer")
            defaultAnswers.add("Wrong Answer")
            val defaultQuestion = Question("Your new Question",defaultAnswers,0)

            quizz.questions.add(defaultQuestion)
            updateList()
            Toast.makeText(this,R.string.question_added,Toast.LENGTH_LONG).show()
        }
        //Gestion de l'affichage des questions -> Déplacement
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return if (viewHolder.itemViewType != target.itemViewType) {
                    false
                } else {
                    Collections.swap(
                        quizz.questions,
                        viewHolder.adapterPosition,
                        target.adapterPosition
                    )
                    if (edit_ViewQuestionList.adapter!!.hasObservers()) edit_ViewQuestionList.adapter!!.notifyItemMoved(
                        viewHolder.adapterPosition,
                        target.adapterPosition
                    )
                    saveQuizz(quizzs, context)
                    true
                }
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }
        }
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(edit_ViewQuestionList)
        updateList()

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        quizzs = Klaxon().parse<Quizzs>(File(this.applicationContext.filesDir, "state.json"))!!
        updateList()
    }
}
