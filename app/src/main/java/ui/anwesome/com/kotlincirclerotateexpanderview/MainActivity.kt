package ui.anwesome.com.kotlincirclerotateexpanderview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.circlerotateexpanderview.CircleRotateExpanderView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CircleRotateExpanderView.create(this)
    }
}
