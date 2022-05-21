package liu.game

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

class MySurfaceView(context: Context?, attrs: AttributeSet?)
    : SurfaceView(context, attrs), SurfaceHolder.Callback {
    lateinit var surfaceHolder: SurfaceHolder
    lateinit var BG: Bitmap
    //lateinit var SuperMan:Bitmap

    init {
        surfaceHolder = getHolder()
        BG = BitmapFactory.decodeResource(getResources(), R.drawable.background)
        //SuperMan = BitmapFactory.decodeResource(getResources(), R.drawable.superman)
        surfaceHolder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {

        var canvas: Canvas = surfaceHolder.lockCanvas()
        drawSomething(canvas)
        surfaceHolder.unlockCanvasAndPost(canvas)
    }
    fun drawSomething(canvas:Canvas) {
        val res = context.resources
        var BirdX:Int = res.displayMetrics.widthPixels  //讀取螢幕寬度
        var BirdY:Int = res.displayMetrics.heightPixels
        var SrcRect:Rect = Rect(0, 0, BG.width, BG.height) //裁切
        var w:Int = BirdX
        var h:Int = BirdY
        var DestRect:Rect = Rect(0, 0, w, h)
        canvas.drawBitmap(BG, SrcRect, DestRect, null)
    }


    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

    }
}