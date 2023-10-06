package kz.just_code.timer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import kz.just_code.timer.databinding.ActivityMainBinding
import java.util.Timer

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpExplicitButton()
        setUpImplicitButton()

    }

    private fun setUpImplicitButton() {

        binding.btnSendImp.setOnClickListener {
            if(isValid()){
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_TEXT, binding.secondsView.text.toString())
                intent.type ="text/plain"
                val choseIntent = Intent.createChooser(intent, "Title")
                startActivity(choseIntent)
            }
            else{
                Toast.makeText(this, "Something is wrong with your input", Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun isValid() =!binding.secondsView.text.isNullOrBlank()


    private fun setUpExplicitButton() {
        binding.btnSendEx.setOnClickListener {
            if(isValid()){
                val intent = Intent(this, TimerActivity::class.java)
                intent.putExtra(ArgumentKey.SECONDS.name, binding.secondsView.text.toString())
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Something is wrong with your input", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
enum class ArgumentKey{
    SECONDS
}