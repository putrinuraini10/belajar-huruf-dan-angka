package com.example.temanbelajar

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController

class Mengeja1Fragment : Fragment() {

    private var mediaPlayer: MediaPlayer? = null
    private var currentAnswerIndex = 0
    private val clickedSequence = mutableListOf<Int>()

    private lateinit var allWordTextViews: List<TextView>
    private lateinit var displayTextViews: List<TextView>

    private val expectedSequenceIds = listOf(
        listOf(R.id.tv_word_1, R.id.tv_word_3),
        listOf(R.id.tv_word_5),
        listOf(R.id.tv_word_1, R.id.tv_word_3),
        listOf(R.id.tv_word_4, R.id.tv_word_6),
        listOf(R.id.tv_word_4, R.id.tv_word_6),
        listOf(R.id.tv_word_2)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mengeja1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ivSpeaker: ImageView = view.findViewById(R.id.iv_speaker)
        val btnKoreksi: Button = view.findViewById(R.id.btn_koreksi)

        allWordTextViews = listOf(
            view.findViewById(R.id.tv_word_1),
            view.findViewById(R.id.tv_word_2),
            view.findViewById(R.id.tv_word_3),
            view.findViewById(R.id.tv_word_4),
            view.findViewById(R.id.tv_word_5),
            view.findViewById(R.id.tv_word_6)
        )

        displayTextViews = listOf(
            view.findViewById(R.id.tv_display_1),
            view.findViewById(R.id.tv_display_2),
            view.findViewById(R.id.tv_display_3),
            view.findViewById(R.id.tv_display_4),
            view.findViewById(R.id.tv_display_5),
            view.findViewById(R.id.tv_display_6)
        )

        setupClickListeners()
        resetQuiz()

        ivSpeaker.setOnClickListener {
            playAudio(R.raw.iniibubudi)
        }

        btnKoreksi.setOnClickListener {
            if (isAnswerSequenceCompleteAndCorrect()) {
                playAudio(R.raw.quiz_finish_sound)
                findNavController().navigate(R.id.action_mengeja1Fragment_to_mengeja2Fragment)
            } else {
                playAudio(R.raw.wrong_answer_sound)
                Toast.makeText(context, "Urutan jawaban salah atau belum lengkap!", Toast.LENGTH_SHORT).show()
                resetQuiz()
            }
        }
    }

    private fun setupClickListeners() {
        allWordTextViews.forEach { textView ->
            textView.setOnClickListener {
                handleWordClick(textView)
            }
        }
    }

    private fun playAudio(audioResId: Int) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, audioResId)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            it.release()
            mediaPlayer = null
        }
    }

    private fun handleWordClick(clickedTextView: TextView) {
        if (currentAnswerIndex >= expectedSequenceIds.size) {
            Toast.makeText(context, "Semua bagian kata sudah diklik. Silakan klik 'Koreksi'!", Toast.LENGTH_SHORT).show()
            return
        }

        val currentExpectedOptions = expectedSequenceIds[currentAnswerIndex]

        if (clickedTextView.id in currentExpectedOptions) {
            clickedTextView.visibility = View.INVISIBLE
            clickedTextView.isClickable = false

            val displayTv = displayTextViews[currentAnswerIndex]
            displayTv.text = clickedTextView.text.toString()
            displayTv.visibility = View.VISIBLE

            clickedSequence.add(clickedTextView.id)
            currentAnswerIndex++

            val audioResId = getAudioResIdForTextView(clickedTextView.id)
            if (audioResId != 0) {
                playAudio(audioResId)
            }

        } else {
            playAudio(R.raw.wrong_answer_sound)
            Toast.makeText(context, "Jawaban salah! Urutan diatur ulang.", Toast.LENGTH_SHORT).show()
            resetQuiz()
        }
    }

    private fun getAudioResIdForTextView(textViewId: Int): Int {
        return when (textViewId) {
            R.id.tv_word_1, R.id.tv_word_3 -> R.raw.hurufi
            R.id.tv_word_5 -> R.raw.hurufni
            R.id.tv_word_4, R.id.tv_word_6 -> R.raw.hurufbu
            R.id.tv_word_2 -> R.raw.hurufdi
            else -> 0
        }
    }

    private fun isAnswerSequenceCompleteAndCorrect(): Boolean {
        if (clickedSequence.size != expectedSequenceIds.size) {
            return false
        }

        for (i in clickedSequence.indices) {
            val clickedId = clickedSequence[i]
            val expectedOptionsForStep = expectedSequenceIds[i]

            if (clickedId !in expectedOptionsForStep) {
                return false
            }
        }
        return true
    }

    private fun resetQuiz() {
        currentAnswerIndex = 0
        clickedSequence.clear()

        allWordTextViews.forEach {
            it.visibility = View.VISIBLE
            it.isClickable = true
        }

        displayTextViews.forEach {
            it.text = ""
            it.visibility = View.INVISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}