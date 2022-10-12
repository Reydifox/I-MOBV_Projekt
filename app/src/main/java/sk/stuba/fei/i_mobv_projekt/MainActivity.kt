package sk.stuba.fei.i_mobv_projekt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sk.stuba.fei.i_mobv_projekt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}