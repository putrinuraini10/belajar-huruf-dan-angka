package com.example.belajarhurufdanangka

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HurufActivity : AppCompatActivity() {

    private lateinit var textHuruf: TextView
    private lateinit var imageHuruf: ImageView
    private lateinit var textPenjelasan: TextView
    private lateinit var btnSebelumnya: Button
    private lateinit var btnBerikutnya: Button
    private lateinit var btnSuara: Button
    private var currentIndex = 0
    private var mediaPlayer: MediaPlayer? = null

    private val hurufList = listOf(
        Huruf("A", "A untuk Apel", R.drawable.apple, R.raw.suara_a),
        Huruf("B", "B untuk Bola", R.drawable.bola, R.raw.suara_b),
        Huruf("C", "C untuk Ceri", R.drawable.ceri, R.raw.suara_c),
        // Tambahkan huruf lainnya di sini
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_huruf)

        textHuruf = findViewById(R.id.textHuruf)
        imageHuruf = findViewById(R.id.imageHuruf)
        textPenjelasan = findViewById(R.id.textPenjelasan)
        btnSebelumnya = findViewById(R.id.btnSebelumnya)
        btnBerikutnya = findViewById(R.id.btnBerikutnya)
        btnSuara = findViewById(R.id.btnSuara)

        tampilkanHuruf()

        btnSebelumnya.setOnClickListener {
            currentIndex = if (currentIndex > 0) currentIndex - 1 else hurufList.size - 1
            tampilkanHuruf()
        }

        btnBerikutnya.setOnClickListener {
            currentIndex = if (currentIndex < hurufList.size - 1) currentIndex + 1 else 0
            tampilkanHuruf()
        }

        btnSuara.setOnClickListener {
            putarSuara()
        }
    }

    private fun tampilkanHuruf() {
        val hurufSekarang = hurufList[currentIndex]
        textHuruf.text = hurufSekarang.huruf
        imageHuruf.setImageResource(hurufSekarang.gambarResId)
        textPenjelasan.text = hurufSekarang.penjelasan
        hentikanSuara()
    }

    private fun putarSuara() {
        hentikanSuara()
        mediaPlayer = MediaPlayer.create(this, hurufList[currentIndex].suaraResId)
        mediaPlayer?.start()
    }

    private fun hentikanSuara() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onDestroy() {
        super.onDestroy()
        hentikanSuara()
    }
}