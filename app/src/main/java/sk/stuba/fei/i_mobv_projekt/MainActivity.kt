package sk.stuba.fei.i_mobv_projekt

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import sk.stuba.fei.i_mobv_projekt.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton: Button = findViewById(R.id.button)
        rollButton.setOnClickListener { serveDrink() }
    }

    /**
     * Roll the dice and update the screen with the result.
     */
    private fun serveDrink() {
        val animView: View = findViewById(R.id.animation_view)
        animView.visibility = View.VISIBLE
    }
}