package liu.game

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class MySurfaceView(context: Context?, attrs: AttributeSet?)
    : SurfaceView(context, attrs), SurfaceHolder.Callback ,GestureDetector.OnGestureListener{
    lateinit var surfaceHolder: SurfaceHolder
    lateinit var BG: Bitmap
    var BGmoveX:Int = 0
    var fly:Fly
    var gDetector: GestureDetector

    init {
        surfaceHolder = getHolder()
        BG = BitmapFactory.decodeResource(getResources(), R.drawable.background)
        surfaceHolder.addCallback(this)
        fly= Fly(context !!)
        gDetector = GestureDetector(context, this)
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
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.BLACK
        paint.textSize = 50f
        canvas.drawText("射擊遊戲(作者：劉姿瑩)",50f,50f, paint)

        fly.draw(canvas)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return true
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        fly.y = e2!!.y.toInt() - fly.h/2
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return true
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gDetector.onTouchEvent(event)
        return true
    }
}