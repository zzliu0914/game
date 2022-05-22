package liu.game

import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageView
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import kotlinx.coroutines.*
import liu.game.databinding.ActivityMainBinding
@GlideModule
public final class MyAppGlideModule : AppGlideModule()

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var job: Job
    lateinit var mysv : MySurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val img: ImageView = findViewById(R.id.img)
        GlideApp.with(this)
            .load(R.drawable.me)
            .circleCrop()
            .override(800, 600)
            .into(img)

        binding.start.isEnabled = true
        binding.stop.isEnabled = false

        binding.start.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                job=GlobalScope.launch(Dispatchers.Main) {
                    while(job.isActive) {
                        binding.start.isEnabled = false
                        binding.start.visibility = INVISIBLE
                        binding.stop.isEnabled = true
                        binding.stop.visibility = VISIBLE
                        delay(25)
                        binding.mysv.fly.update()
                        val canvas: Canvas = binding.mysv.holder.lockCanvas()
                        binding.mysv.drawSomething(canvas)
                        binding.mysv.holder.unlockCanvasAndPost(canvas)
                    }
                }
            }
        })

        binding.stop.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                job.cancel()
                binding.start.isEnabled = true
                binding.start.visibility=VISIBLE
                binding.stop.isEnabled = false
                binding.stop.visibility=INVISIBLE
            }
        })

        binding.resume.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                job = GlobalScope.launch(Dispatchers.Main) {
                    while(job.isActive) {
                        binding.resume.visibility=INVISIBLE
                        binding.stop.isEnabled = true
                        delay(25)
                        binding.mysv.fly.update()
                        val canvas: Canvas = binding.mysv.holder.lockCanvas()
                        binding.mysv.drawSomething(canvas)
                        binding.mysv.holder.unlockCanvasAndPost(canvas)
                    }
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        job.cancel()
    }

    override fun onResume() {
        super.onResume()
        if (binding.start.isEnabled == false){
            binding.resume.visibility=VISIBLE
            binding.stop.isEnabled = false
        }
    }


}