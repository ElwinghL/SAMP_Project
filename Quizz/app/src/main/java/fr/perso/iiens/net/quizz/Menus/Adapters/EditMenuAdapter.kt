package fr.perso.iiens.net.quizz.Menus.Adapters

import android.content.Intent
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import fr.perso.iiens.net.quizz.Menus.EditMenu
import fr.perso.iiens.net.quizz.Menus.EditQuizz
import fr.perso.iiens.net.quizz.R
import fr.perso.iiens.net.quizz.saveQuizz
import fr.perso.iiens.net.quizzStruct.Quizzs

class EditMenuAdapter(var editMenu: EditMenu, var quizzs: Quizzs) :
    RecyclerView.Adapter<EditMenuAdapter.ViewHolder>() {
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var text: TextView = v.findViewById(R.id.edit_QuizzTitle) as TextView
        var btn_EditQuizz: Button = v.findViewById(R.id.btn_editQuizzDetails) as Button
        var btn_DelQuizz: Button = v.findViewById(R.id.btn_editQuizzDelete) as Button
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(editMenu)
        val v = inflater.inflate(R.layout.item_edit_quizz, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#C0C0C0"))
        }
        holder.text.text = quizzs.quizz[position].name.trim()
        holder.text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                quizzs.quizz[position].name = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                editMenu.quizzs = quizzs
            }
        })

        holder.btn_EditQuizz.setOnClickListener {
            val intent = Intent(editMenu, EditQuizz::class.java)
            intent.putExtra("KEY_QUIZZ", position)
            startActivity(editMenu, intent, null)
        }

        holder.btn_DelQuizz.setOnClickListener {
            removeQuizz(position)
        }
    }

    override fun getItemCount(): Int {
        return quizzs.quizz.size
    }

    private fun removeQuizz(index: Int) {
        quizzs.quizz.removeAt(index)
        editMenu.quizzs = quizzs
        this.notifyDataSetChanged()
        saveQuizz(quizzs, editMenu)
    }
}
