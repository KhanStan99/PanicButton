package `in`.trentweet.panicbutton

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    var seconds = 0
    var running = false
    var user: List<Users>? = null

    private val repo: UserRepository by lazy {
        UserRepository(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        infoBtn.setOnClickListener {
            showAlertDialogButtonClicked()
        }

        kotlin.run {
            repo.insertUser(Users(time = "a"))
        }

        button.setOnClickListener {
            user = repo.getAllUsers()
            Log.e("AAAAAAAAA -> ", user!![0].time)
            if (animationView.isAnimating) {
                running = false
                seconds = 0
                animationView.cancelAnimation()
                button.text = getString(R.string.start_meditation)
            } else {
                animationView.playAnimation()
                button.text = getString(R.string.stop_meditation)
                startTimer()
            }
        }
    }

    private fun startTimer() {
        val handler = Handler()
        running = true
        handler.post(object : Runnable {
            override fun run() {
                val hours: Int = seconds / 3600
                val minutes: Int = seconds % 3600 / 60
                val secs: Int = seconds % 60

                val time: String = java.lang.String
                    .format(
                        Locale.getDefault(),
                        "%d:%02d:%02d", hours,
                        minutes, secs
                    )

                timer.text = time

                if (running) {
                    seconds++
                }

                handler.postDelayed(this, 1000)
            }
        })
    }

    private fun showAlertDialogButtonClicked() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        // set the custom layout
        val customLayout: View = layoutInflater
            .inflate(
                R.layout.dialog_layout,
                null
            )
        builder.setView(customLayout)

        builder
            .setPositiveButton(
                "OK"
            ) { dialog, which ->
                dialog.cancel()
            }


        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


}