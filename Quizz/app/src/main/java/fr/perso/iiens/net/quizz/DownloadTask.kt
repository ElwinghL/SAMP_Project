package fr.perso.iiens.net.quizz

import android.os.AsyncTask
import android.util.Log
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.BufferedReader
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

class DownloadTask(var mainMenu: MainMenu) {
    var downloadTaskBG = DownloadTaskBG()
    var quizzs = ArrayList<Quizz>()

    fun execute() {
        downloadTaskBG.execute()
    }

    inner class DownloadTaskBG : AsyncTask<Void, Void, Quizzs>() {


        fun getPage(addressURL: String) {
            val bufferedReader: BufferedReader? = null
            var urlConnection: HttpURLConnection? = null

            try {
                val url: URL = URL(addressURL)
                urlConnection = url.openConnection() as HttpURLConnection
                val responseCode: Int = urlConnection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream: InputStream = urlConnection.inputStream

                    val dbf: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
                    val db: DocumentBuilder = dbf.newDocumentBuilder()
                    val doc: Document = db.parse(inputStream)

                    doc.documentElement.normalize()

                    val xmlQuizzs: Element = doc.getElementsByTagName("Quizzs").item(0) as Element
                    val xmlQuizz = xmlQuizzs.getElementsByTagName("Quizz")

                    for (iQuizz in 0..xmlQuizz.length) {
                        val xmlQuizzElt = xmlQuizz.item(iQuizz) as Element
                        val quizzName = xmlQuizzElt.getAttribute("type")
                        val xmlQuestions = xmlQuizzElt.getElementsByTagName("Question")

                        val questions = ArrayList<Question>()
                        for (iQuestion in 0..xmlQuestions.length) {
                            val xmlQuestionElt = xmlQuestions.item(iQuestion) as Element
                            val question = xmlQuestionElt.firstChild.nodeValue
                            val truth =
                                (xmlQuestionElt.getElementsByTagName("Reponse").item(0) as Element).getAttribute(
                                    "valeur"
                                ).toInt()
                            val xmlAnswerElt =
                                xmlQuestionElt.getElementsByTagName("Propositions").item(0) as Element
                            val answers = ArrayList<String>()

                            val xmlAnswers = xmlAnswerElt.getElementsByTagName("Proposition")
                            for (iAnswer in 0..xmlAnswers.length) {
                                answers.add(xmlAnswers.item(iAnswer).firstChild.nodeValue)
                            }
                            val jQuest = Question(question, answers, truth)
                            questions.add(jQuest)
                        }
                        val jQuizz = Quizz(quizzName, questions)
                        quizzs.add(jQuizz)
                    }
                }

            } catch (e: Exception) {
                //e.printStackTrace()
            } finally {
                if (bufferedReader != null) {
                    bufferedReader.close()
                } else {
                    // Nothing
                }

                urlConnection?.disconnect()
            }
            return
        }

        override fun doInBackground(vararg params: Void): Quizzs {
            getPage("https://dept-info.univ-fcomte.fr/joomla/images/CR0700/Quizzs.xml")
            Log.d("Test2",quizzs.size.toString())
            return Quizzs(quizzs)
        }

        override fun onPostExecute(quizzs: Quizzs) {
            Log.d("Test2",quizzs.getQuizzs.size.toString())
            mainMenu.printQuizzs(quizzs)
        }
    }
}