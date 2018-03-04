package ui.anwesome.com.circlerotateexpanderview

/**
 * Created by anweshmishra on 04/03/18.
 */
import android.content.*
import android.graphics.*
import android.view.*
class CircleRotateExpanderView(ctx : Context) : View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val renderer = Renderer(this)
    override fun onDraw(canvas : Canvas) {
        renderer.render(canvas, paint)
    }
    override fun onTouchEvent(event : MotionEvent)  : Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }
    data class State(var dir : Float = 0f, var prevScale : Float = 0f, var jDir : Int = 0, var j : Int = 0) {
        val scales : Array<Float> = arrayOf(0f, 0f, 0f)
        fun update(stopcb : (Float) -> Unit) {
            scales[j] += 0.1f * dir
            if(Math.abs(scales[j] - prevScale) > 1) {
                scales[j] = prevScale + dir
                j += jDir
                if(j == scales.size || j == -1) {
                    jDir *= -1
                    j += jDir
                    prevScale = scales[j]
                    dir = 0f
                    stopcb(prevScale)
                }
            }
        }
        fun startUpdating(startcb : () -> Unit) {
            if(dir == 0f) {
                dir = 1  - 2 * prevScale
                startcb()
            }
        }
    }
    data class Animator(var view : View, var animated : Boolean = false) {
        fun animate(updatecb : () -> Unit) {
            if(animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch(ex : Exception) {

                }
            }
        }
        fun start() {
            if(!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop() {
            if(animated) {
                animated = false
            }
        }
    }
    data class CircleRotateExpander(var x : Float, var y : Float, var size : Float, var deg : Float = 0f) {
        val state = State()
        fun draw(canvas : Canvas, paint : Paint) {
            canvas.save()
            canvas.translate(x, y)
            canvas.rotate(deg + 90f * state.scales[2])
            for(i in 0..3) {
                canvas.save()
                canvas.translate(size * state.scales[1], 0f)
                val r = size/10
                canvas.drawArc(RectF(-r, -r, r, r), 0f, 360f * state.scales[0], false, paint)
                canvas.restore()
            }
            canvas.restore()
        }
        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }
    data class Renderer(var view : View, var time : Int  = 0) {
        var circleRotateExpander : CircleRotateExpander ?= null
        val animator = Animator(view)
        fun render(canvas : Canvas, paint : Paint) {
            if(time == 0) {
                val w = canvas.width.toFloat()
                val h = canvas.height.toFloat()
                circleRotateExpander = CircleRotateExpander(w/2, h/2, Math.min(w,h)/3)
            }
            circleRotateExpander?.draw(canvas, paint)
            time++
            animator.animate {
                circleRotateExpander?.update {
                    animator.stop()
                }
            }
        }
        fun handleTap() {
            circleRotateExpander?.startUpdating {
                animator.start()
            }
        }
    }
}