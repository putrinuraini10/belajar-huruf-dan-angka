package com.example.belajarhurufdanangka

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.belajarhurufdanangka.databinding.ActivityAngkaBinding

class AngkaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAngkaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAngkaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dummyAngka = listOf(
            Angka("1", R.drawable.one),
            Angka("2", R.drawable.two),
            Angka("3", R.drawable.three),
            Angka("4", R.drawable.four)
        )

        binding.recyclerAngka.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerAngka.adapter = AngkaAdapter(dummyAngka)
    }

}