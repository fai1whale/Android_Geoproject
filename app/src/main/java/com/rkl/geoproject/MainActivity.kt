package com.rkl.geoproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX ="index"

class MainActivity : AppCompatActivity() {

    //contract that you will declare what view later, but before using it.
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var questionTextView: TextView

    private val quizViewModel : QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex


        //declaring view.
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_test_view)

        trueButton.setOnClickListener { view: View ->
            //send message
            checkAnswer(true)
        }

        questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        falseButton.setOnClickListener { view: View ->
            //send message
            checkAnswer(false)
        }

        questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        prevButton.setOnClickListener{
            quizViewModel.moveBack()
            updateQuestion()
        }

        updateQuestion()
    }

    override fun onStart(){
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResumed() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }


    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
            savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }



    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer){
            R.string.correct_toast} else {R.string.incorrect_toast}
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show()
    }
}