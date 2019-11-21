package fr.perso.iiens.net.quizz.Menus

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.Klaxon
import fr.perso.iiens.net.quizz.Menus.Adapters.EditMenuAdapter
import fr.perso.iiens.net.quizz.R
import fr.perso.iiens.net.quizz.saveQuizz
import fr.perso.iiens.net.quizzStruct.Quizzs
import kotlinx.android.synthetic.main.activity_edit_menu.*
import java.io.File
import java.util.*


class EditMenu : AppCompatActivity() {

    lateinit var quizzs: Quizzs
    lateinit var context: Context

    private fun updateList() {
        edit_ViewQuizzs.adapter?.notifyItemInserted(edit_ViewQuizzs.adapter!!.itemCount)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_menu)
        context = this.applicationContext
        quizzs = Klaxon().parse<Quizzs>(File(this.applicationContext.filesDir, "state.json"))!!

        edit_ViewQuizzs.layoutManager = LinearLayoutManager(this)
        edit_ViewQuizzs.adapter = EditMenuAdapter(this, quizzs)

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
                        quizzs.quizz,
                        viewHolder.adapterPosition,
                        target.adapterPosition
                    )
                    if (edit_ViewQuizzs.adapter!!.hasObservers()) edit_ViewQuizzs.adapter!!.notifyItemMoved(
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

        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(edit_ViewQuizzs)
        updateList()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        quizzs = Klaxon().parse<Quizzs>(File(this.applicationContext.filesDir, "state.json"))!!
        updateList()
    }
}