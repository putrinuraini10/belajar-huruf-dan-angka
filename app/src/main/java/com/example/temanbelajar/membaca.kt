package com.example.temanbelajar

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.appcompat.app.AlertDialog
import android.util.Log

class membaca : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var fragmentContainer: FragmentContainerView
    private lateinit var mainContentLayout: LinearLayout

    private lateinit var hiasann: ImageView
    private lateinit var desain: ImageView
    private lateinit var desainn: ImageView
    private lateinit var bawah: ImageView
    private lateinit var bawahl: ImageView
    private lateinit var bubble: ImageView
    private lateinit var infoIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.klikmembaca)

        fragmentContainer = findViewById(R.id.fragment_container_membaca)
        mainContentLayout = findViewById(R.id.main_content_layout_membaca)

        hiasann = findViewById(R.id.hiasann)
        desain = findViewById(R.id.desain)
        desainn = findViewById(R.id.desainn)
        bawah = findViewById(R.id.bawah)
        bawahl = findViewById(R.id.bawahl)
        bubble = findViewById(R.id.bubble)
        infoIcon = findViewById(R.id.info_icon)

        val btnHurufAbjad: Button = findViewById(R.id.btn_hurufabjad)
        val btnMengeja: Button = findViewById(R.id.btn_mengeja)

        btnHurufAbjad.setOnClickListener {
            playAudioAndNavigate(R.raw.hurufabjad, AbjadFragment())
        }

        btnMengeja.setOnClickListener {
            playAudioAndNavigate(R.raw.mengeja, Mengeja1Fragment())
        }

        infoIcon.setOnClickListener {
            showAboutUsPopup()
        }

        showMainContent()
    }

    private fun playAudio(audioResId: Int) {
        mediaPlayer?.release()
        mediaPlayer = null
        mediaPlayer = MediaPlayer.create(this, audioResId)
        mediaPlayer?.setOnCompletionListener { mp ->
            mp.release()
            mediaPlayer = null
        }
        mediaPlayer?.start()
    }

    private fun playAudioAndNavigate(audioResId: Int, fragment: Fragment) {
        mediaPlayer?.release()
        mediaPlayer = null
        mediaPlayer = MediaPlayer.create(this, audioResId)
        mediaPlayer?.setOnCompletionListener { mp ->
            mp.release()
            mediaPlayer = null
            navigateToFragment(fragment)
        }
        mediaPlayer?.start()
    }

    private fun navigateToFragment(fragment: Fragment) {
        hideMainContent()

        fragmentContainer.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_membaca, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun showMainContent() {
        mainContentLayout.visibility = View.VISIBLE
        hiasann.visibility = View.VISIBLE
        desain.visibility = View.VISIBLE
        desainn.visibility = View.VISIBLE
        bawah.visibility = View.VISIBLE
        bawahl.visibility = View.VISIBLE
        bubble.visibility = View.VISIBLE
        infoIcon.visibility = View.VISIBLE
        fragmentContainer.visibility = View.GONE
    }

    private fun hideMainContent() {
        mainContentLayout.visibility = View.GONE
        hiasann.visibility = View.GONE
        desain.visibility = View.GONE
        desainn.visibility = View.GONE
        bawah.visibility = View.GONE
        bawahl.visibility = View.GONE
        bubble.visibility = View.GONE
        infoIcon.visibility = View.GONE
    }

    private fun showAboutUsPopup() {
        val title = "Tentang kami"
        val message = """
            Aplikasi ini merupakan aplikasi edukasi untuk anak-anak yang bertujuan membantu belajar mengeja, mengenal huruf abjad, angka, warna, dan bernyanyi sambil belajar.

            Dibuat oleh:
            Putri Nuraini
            Hikmatun Nazilah
            Clara Putri Andini
        """.trimIndent()
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()

            supportFragmentManager.addOnBackStackChangedListener(object : androidx.fragment.app.FragmentManager.OnBackStackChangedListener {
                override fun onBackStackChanged() {
                    if (supportFragmentManager.backStackEntryCount == 0) {
                        showMainContent()
                    }
                    supportFragmentManager.removeOnBackStackChangedListener(this)
                }
            })
        } else {
            super.onBackPressed()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}