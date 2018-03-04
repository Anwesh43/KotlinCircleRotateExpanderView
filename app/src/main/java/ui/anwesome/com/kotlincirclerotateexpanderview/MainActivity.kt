package ui.anwesome.com.kotlincirclerotateexpanderview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ui.anwesome.com.circlerotateexpanderview.CircleRotateExpanderView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = CircleRotateExpanderView.create(this)
        view.addOnRotateListener {
            Toast.makeText(this, "rotated", Toast.LENGTH_SHORT).show()
        }
    }
}
