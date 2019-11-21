package fr.perso.iiens.net.quizz

import android.os.AsyncTask
import android.util.Log
import fr.perso.iiens.net.quizz.Menus.MainMenu
import fr.perso.iiens.net.quizzStruct.Question
import fr.perso.iiens.net.quizzStruct.Quizz
import fr.perso.iiens.net.quizzStruct.Quizzs
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.BufferedReader
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

class DownloadTask(var mainMenu: MainMenu, var link: String) {
    private var downloadTaskBG = DownloadTaskBG()
    var quizzs = ArrayList<Quizz>()
    var rQuizzs: Quizzs? = null

    fun execute() {
        downloadTaskBG.execute()
    }

    inner class DownloadTaskBG : AsyncTask<Void, Void, Quizzs?>() {


        private fun getPage(addressURL: String) {
            val bufferedReader: BufferedReader? = null
            var urlConnection: HttpURLConnection? = null

            try {
                val url = URL(addressURL)
                urlConnection = url.openConnection() as HttpURLConnection
                val responseCode: Int = urlConnection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream: InputStream = urlConnection.inputStream

                    val dbf: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
                    val db: DocumentBuilder = dbf.newDocumentBuilder()
                    val doc: Document = db.parse(inputStream)

                    doc.documentElement.normalize()

                    val nodeQuizzs = doc.getElementsByTagName("Quizzs")
                    val eltQuizzs = nodeQuizzs.item(0) as Element

                    val nodeQuizz = eltQuizzs.getElementsByTagName("Quizz")
                    for (iQuizz in 0 until nodeQuizz.length) {
                        val eltQuizz = nodeQuizz.item(iQuizz) as Element
                        val quizzName = eltQuizz.getAttribute("type")
                        Log.d("TestQuizz", quizzName)
                        val questions = ArrayList<Question>()
                        val nodeQuestions = eltQuizz.getElementsByTagName("Question")

                        for (iQuestion in 0 until nodeQuestions.length) {
                            val eltQuestion = nodeQuestions.item(iQuestion) as Element
                            val questionName = eltQuestion.firstChild.nodeValue.trim()
                            Log.d("TestQuestions", questionName)
                            val truth =
                                (eltQuestion.getElementsByTagName("Reponse").item(0) as Element).getAttribute(
                                    "valeur"
                                ).toInt() - 1
                            Log.d("TestTruth",truth.toString())
                            val eltPropositions = eltQuestion.getElementsByTagName("Propositions").item(0) as Element
                            val propositions = ArrayList<String>()
                            val nodePropostition = eltPropositions.getElementsByTagName("Proposition")

                            for (iProposition in 0 until nodePropostition.length) {
                                val eltProposition = nodePropostition.item(iProposition) as Element
                                val propositionName = eltProposition.firstChild.nodeValue.trim()
                                Log.d("TestProposition",propositionName)

                                propositions.add(propositionName)
                            }
                            questions.add(Question(questionName,propositions,truth))
                        }
                        quizzs.add(Quizz(quizzName,questions))
                    }
                    rQuizzs = Quizzs(quizzs)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                bufferedReader?.close()
                urlConnection?.disconnect()
            }
            return
        }

        override fun doInBackground(vararg params: Void): Quizzs? {
            getPage(link)
            Log.d("Test2", quizzs.size.toString())
            return rQuizzs
        }

        override fun onPostExecute(quizzs: Quizzs?) {
            Log.d("Test2", quizzs?.quizz?.size.toString())
            if (quizzs != null) {
                mainMenu.printQuizzs(quizzs)
            }

            if (quizzs != null) {
                if (mainMenu.currentQuizzs.isEmpty()) {
                    mainMenu.currentQuizzs = quizzs
                }else {
                    mainMenu.currentQuizzs.concatQuizzs(quizzs)
                }
            }

            saveQuizz(mainMenu.currentQuizzs,mainMenu.applicationContext)
        }
    }
}