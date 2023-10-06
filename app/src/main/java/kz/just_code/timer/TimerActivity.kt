package kz.just_code.timer

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kz.just_code.timer.databinding.ActivityTimerBinding

class TimerActivity :AppCompatActivity() {
    private lateinit var binding:ActivityTimerBinding
    private var seconds:Int? = null
    private var isRunning:Boolean = false
    private lateinit var timer:CountDownTimer
    private var timeRemaining:Long = 0
    private var timeAfterPause:Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTimeView()
        with(binding) {
            btnPause.setOnClickListener {
                if(isRunning){
                    pauseClicked()
                }
            }
            btnStart.setOnClickListener {
               if(!isRunning) startClicked()
            }
            btnReset.setOnClickListener{
               if(!isRunning) resetClicked()
            }
        }
    }
    private fun pauseClicked(){
        isRunning = false
        timeAfterPause = timeRemaining
        timer.cancel()
        Toast.makeText(this, "The times is on pause", Toast.LENGTH_SHORT).show()
    }
    private fun startClicked(){
        isRunning = true
        timer = object : CountDownTimer((seconds?.times(1000))?.toLong()!!, 1000){
            override fun onTick(p0: Long) {
                timeRemaining = p0
                updateTimerText()
            }

            override fun onFinish() {
                isRunning = false
                binding.timeView.text = getString(R.string.time_is_up )
            }
        }.start()
    }

    private fun resetClicked(){
        isRunning = true
        timer = object : CountDownTimer(this.timeAfterPause,1000){
            override fun onTick(p0: Long) {
                timeRemaining = p0
                updateTimerText()
            }

            override fun onFinish() {
                isRunning = false
                binding.timeView.text = getString(R.string.time_is_up )
            }
        }.start()
    }
    private fun updateTimerText(){
        val timeFormatted = formatText((timeRemaining/1000).toInt())
        binding.timeView.text = timeFormatted
    }

    private fun setTimeView(){
        intent.extras?.let{
            val res1 = it.getString(ArgumentKey.SECONDS.name)
            val res2 = it.getString(Intent.EXTRA_TEXT)
            if(res2 != null){
                seconds = res2?.toInt()!!
            }
            else{
                seconds = res1?.toInt()!!
            }
            val formattedTime = formatText(seconds!!)
            binding.timeView.text = formattedTime
        }
    }
    private fun formatText(seconds:Int):String{
        var minutes = (seconds/60)
        var sec = (seconds%60)
        return String.format("%02d:%02d", minutes, sec)
    }
}