package liu.game

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.graphics.minus

class MySurfaceView(context: Context?, attrs: AttributeSet?)
    : SurfaceView(context, attrs), SurfaceHolder.Callback {
    lateinit var surfaceHolder: SurfaceHolder
    lateinit var BG: Bitmap
    var BGmoveX:Int = 0

    init {
        surfaceHolder = getHolder()
        BG = BitmapFactory.decodeResource(getResources(), R.drawable.background)
        surfaceHolder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        var canvas: Canvas = surfaceHolder.lockCanvas()
        drawSomething(canvas)
        surfaceHolder.unlockCanvasAndPost(canvas)
    }
    fun drawSomething(canvas:Canvas) {
        BGmoveX =BGmoveX-5
        val res = context.resources
        var w:Int = res.displayMetrics.widthPixels  //讀取螢幕寬度
        var h:Int = res.displayMetrics.heightPixels
        var SrcRect:Rect = Rect(0, 0, BG.width, BG.height) //裁切
        var DestRect:Rect = Rect(0, 0, w, h)//顯示的區域
        //canvas.drawBitmap(BG, SrcRect, DestRect, null)
        var BGnewX:Int = w + BGmoveX

        if (BGnewX <= 0) {
            BGmoveX = 0
            // only need one draw
            canvas.drawBitmap(BG, SrcRect, DestRect, null)
        } else {
            // need to draw original and wrap
            DestRect= Rect(BGmoveX,0,BGmoveX+w,h)
            canvas.drawBitmap(BG, SrcRect, DestRect, null)
            DestRect=Rect(BGnewX,0,BGnewX+w,h)
            canvas.drawBitmap(BG, SrcRect, DestRect, null)
        }


    }


    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

    }
}