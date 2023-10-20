package com.example.titans_hockey_challenge.ui.twoplayertable

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.media.MediaPlayer
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.INVALID_POINTER_ID
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.titans_hockey_challenge.R
import com.example.titans_hockey_challenge.models.Paddle
import com.example.titans_hockey_challenge.models.Puck
import com.example.titans_hockey_challenge.utils.PUCK_SPEED
import com.example.titans_hockey_challenge.utils.STATE_LOSE
import com.example.titans_hockey_challenge.utils.STATE_RUNNING
import com.example.titans_hockey_challenge.utils.STATE_WIN
import com.example.titans_hockey_challenge.utils.TwoPlayerGameThread
import kotlin.math.abs

class TwoPlayerHockeyTable : SurfaceView, SurfaceHolder.Callback {

    var twoPlayerGame: TwoPlayerGameThread? = null
        private set
    private var mStatus: TextView? = null
    private var mScorePlayer1 : TextView? = null
    private var mScorePlayer2 : TextView? = null
    var player1 : Paddle? = null
        private set
    var player2 : Paddle? = null
        private set
    private var puck: Puck? = null
        private set
    private var mNetPaint: Paint? = null
    private var mGoalPostBoundsPaint: Paint? = null
    private var mTableBoundsPaint: Paint? = null
    private var mTableWidth = 0
    private var mTableHeight = 0
    private var mContext: Context? = null
    var mHolder: SurfaceHolder? = null
//    private var mAiMoveProbability = 0f

    private var activePointerIdPlayer1 = INVALID_POINTER_ID
    private var activePointerIdPlayer2 = INVALID_POINTER_ID

    private var mLastPLayer1TouchX = 0f
    private var mLastPlayer1TouchY = 0f
    private var mLastPLayer2TouchX = 0f
    private var mLastPlayer2TouchY = 0f

    private var mediaPlayer: MediaPlayer? = null
    private var puckHitSound: MediaPlayer? = null
    private var winningSound: MediaPlayer? = null
    private var losingSound: MediaPlayer? = null
    private var wallHitSound: MediaPlayer? = null
    private var goalPostHitSound: MediaPlayer? = null


    private fun initTwoPlayerHockeyTable(ctx: Context, attr: AttributeSet?) {
        mContext = ctx
        mHolder = holder
        mHolder!!.addCallback(this)
        twoPlayerGame = TwoPlayerGameThread(this.context, mHolder!!, this, object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                mStatus!!.visibility = msg.data.getInt("visibility")
                mStatus!!.text = msg.data.getString("text")
            }
        }, object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                mScorePlayer1!!.text = msg.data.getString("player1")
                mScorePlayer2!!.text = msg.data.getString("player2")
            }
        })

        val a = ctx.obtainStyledAttributes(attr, R.styleable.HockeyTable)
        val strikerHeight = a.getInteger(R.styleable.HockeyTable_racketHeight, 150)
        val strikerWidth = a.getInteger(R.styleable.HockeyTable_racketWidth, 150)
        val puckRadius = a.getInteger(R.styleable.HockeyTable_puckRadius, 40)

        // Set Player
        val player1Paint = Paint()
        player1Paint.isAntiAlias = true
        player1Paint.color = ContextCompat.getColor(mContext!!, R.color.player_color)

        val player1MiddlePaint = Paint()
        player1MiddlePaint.isAntiAlias = true
        player1MiddlePaint.style = Paint.Style.FILL
        player1MiddlePaint.color = ContextCompat.getColor(mContext!!, R.color.player_middle_color)

        val player1OuterPaint = Paint()
        player1OuterPaint.style = Paint.Style.FILL
        player1OuterPaint.color = ContextCompat.getColor(mContext!!, R.color.player_outer_color)
        player1 = Paddle(strikerWidth, strikerHeight, paint = player1Paint, middlePaint = player1MiddlePaint, outerPaint = player1OuterPaint)

        // Set Opponent
        val player2Paint = Paint()
        player2Paint.isAntiAlias = true
        player2Paint.color = ContextCompat.getColor(mContext!!, R.color.opponent_color)

        val player2MiddlePaint = Paint()
        player2MiddlePaint.isAntiAlias = true
        player2MiddlePaint.style = Paint.Style.FILL
        player2MiddlePaint.color = ContextCompat.getColor(mContext!!, R.color.opponent_middle_color)

        val player2OuterPaint = Paint()
        player2OuterPaint.style = Paint.Style.FILL
        player2OuterPaint.color = ContextCompat.getColor(mContext!!, R.color.opponent_outer_color)
        player2 = Paddle(strikerWidth, strikerHeight, paint = player2Paint, middlePaint = player2MiddlePaint, outerPaint = player2OuterPaint)

        // Set Puck
        val puckPaint = Paint()
        puckPaint.color = ContextCompat.getColor(mContext!!, R.color.puck_color)

        val puckInnerPaint = Paint()
        puckInnerPaint.color = ContextCompat.getColor(mContext!!, R.color.dark_gray)
        puck = Puck(puckRadius.toFloat(), puckPaint, puckInnerPaint)

        // Draw circular and middle line
        mNetPaint = Paint()
        mNetPaint!!.isAntiAlias = true
        mNetPaint!!.color = Color.BLACK
        mNetPaint!!.alpha = 100
        mNetPaint!!.style = Paint.Style.STROKE
        mNetPaint!!.strokeWidth = 8f

        // Draw Bounds
        mTableBoundsPaint = Paint()
        mTableBoundsPaint!!.isAntiAlias = true
        mTableBoundsPaint!!.style = Paint.Style.STROKE
        mTableBoundsPaint!!.strokeWidth = 35f
//        mAiMoveProbability = 0.8f

        // Draw Goal post
        mGoalPostBoundsPaint = Paint()
        mGoalPostBoundsPaint!!.isAntiAlias = true
        mGoalPostBoundsPaint!!.color = Color.BLACK
        mGoalPostBoundsPaint!!.style = Paint.Style.STROKE
        mGoalPostBoundsPaint!!.strokeWidth = 35f
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if (!twoPlayerGame!!.isPaused) {
            canvas.drawColor(ContextCompat.getColor(mContext!!, R.color.table_color))

            // Draw Hockey board with rounded corners
            val cornerRadius = 50f
            val rectF = RectF(0f, 0f, mTableWidth.toFloat(), mTableHeight.toFloat())
            val radii = floatArrayOf(
                cornerRadius, cornerRadius,
                cornerRadius, cornerRadius,
                cornerRadius, cornerRadius,
                cornerRadius, cornerRadius
            )

            val path = Path()
            path.addRoundRect(rectF, radii, Path.Direction.CW)
            canvas.drawPath(path, mTableBoundsPaint!!)

            // Draw middle line
            val middle = mTableWidth / 2
            canvas.drawLine(middle.toFloat(), 1f, middle.toFloat(), (mTableHeight - 1).toFloat(), mNetPaint!!)

            // Draw circular line
            val centerY = mTableHeight.toFloat() / 2
            val radius = minOf(middle, mTableHeight / 4) - 6f
            canvas.drawCircle(middle.toFloat(), centerY, radius, mNetPaint!!)

            // Draw goal post line
            // left goal post
            val leftGoalPostX = 1f
            val goalPostY1 = centerY - radius
            val goalPostY2 = centerY + radius
            canvas.drawLine(leftGoalPostX, goalPostY1, leftGoalPostX, goalPostY2, mGoalPostBoundsPaint!!)
            canvas.drawCircle(leftGoalPostX, centerY, radius, mNetPaint!!)

            // right goal post
            val rightGoalPostX = mTableWidth.toFloat() - 1
            canvas.drawLine(rightGoalPostX, goalPostY1, rightGoalPostX, goalPostY2, mGoalPostBoundsPaint!!)
            canvas.drawCircle(rightGoalPostX, centerY, radius, mNetPaint!!)

            twoPlayerGame!!.setScoreText(
                player1!!.score.toString(), player2!!.score.toString()
            )
            player1!!.drawCircle(canvas)
            player2!!.drawCircle(canvas)
            puck!!.draw(canvas)
        }
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initTwoPlayerHockeyTable(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initTwoPlayerHockeyTable(context, attrs)
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        twoPlayerGame!!.setRunning(true)
        twoPlayerGame!!.start()
    }

    override fun surfaceChanged(surfaceHolder: SurfaceHolder, format: Int, width: Int, height: Int) {
        mTableWidth = width
        mTableHeight = height
        twoPlayerGame!!.setUpNewRound()
    }

    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
        var retry = true
        twoPlayerGame!!.setRunning(false)

        pauseBackgroundSound()
        releaseSounds()

        while (retry) {
            try {
                twoPlayerGame!!.join()
                retry = false
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    fun update(canvas: Canvas?) {
        if (!twoPlayerGame!!.isPaused) {
            if (checkCollisionPaddle(player1, puck)) {
                handleCollision(player1, puck)
            } else if (checkCollisionPaddle(player2, puck)) {
                handleCollision(player2, puck)
            } else if (checkCollisionWithTopOrBottomWall()) {
                // resets the puck's Y velocity
                puck!!.velocityY = -puck!!.velocityY
                playWallHitSound()
            } else if (checkCollisionWithLeftOrRightWall()) {
                // resets the puck's X velocity
                puck!!.velocityX = -puck!!.velocityX
                playWallHitSound()
            } else if (checkCollisionWithLeftGoalPost()) {
                twoPlayerGame!!.setState(STATE_LOSE)
                return
            } else if (checkCollisionWithRightGoalPost()) {
                twoPlayerGame!!.setState(STATE_WIN)
                return
            }
            puck!!.movePuck(canvas!!)
        }
    }

    private fun checkCollisionPaddle(paddle: Paddle?, puck: Puck?): Boolean {
        return paddle!!.bounds.intersects(puck!!.centerX - puck.radius, puck.centerY - puck.radius, puck.centerX + puck.radius, puck.centerY + puck.radius)
    }

    private fun checkCollisionWithTopOrBottomWall(): Boolean {
        return puck!!.centerY <= puck!!.radius || puck!!.centerY + puck!!.radius >= mTableHeight - 1
    }

    private fun checkCollisionWithLeftOrRightWall() : Boolean {
        return puck!!.centerX<= puck!!.radius || puck!!.centerX + puck!!.radius >= mTableWidth - 1
    }

    private fun checkCollisionWithLeftGoalPost() : Boolean {
        val goalPostX = 10f
        return puck!!.centerX - puck!!.radius <= goalPostX && puck!!.centerX + puck!!.radius >= goalPostX
    }

    private fun checkCollisionWithRightGoalPost() : Boolean {
        val goalPostX = mTableWidth - 10f
        return puck!!.centerX - puck!!.radius <= goalPostX && puck!!.centerX + puck!!.radius >= goalPostX
    }

    private fun handleCollision(paddle: Paddle?, puck: Puck?) {
        // Reverses the X velocity
        puck!!.velocityX = -puck.velocityX

        // Adjust the Y velocity to maintain a constant speed
        val currentSpeed = Math.sqrt((puck.velocityX * puck.velocityX + puck.velocityY * puck.velocityY).toDouble())
        val targetSpeed = PUCK_SPEED // Adjust this to your desired speed
        val factor = targetSpeed / currentSpeed
        puck.velocityX *= factor.toFloat()
        puck.velocityY *= factor.toFloat()

        // Move the puck out of the paddle to prevent sticking
        if (paddle === this.player1) {
            puck.centerX = player1!!.bounds.right + puck.radius
        } else if (paddle === player2) {
            puck.centerX = player2!!.bounds.left - puck.radius
        }

        playPuckHitSound()
    }

    fun playStartGameSound() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(mContext, R.raw.soundtrack2)
        }
        mediaPlayer?.start()
    }

    fun pauseBackgroundSound() {
        if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
            mediaPlayer?.pause()
        }
    }

    private fun playWinningSound() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        if (winningSound == null) {
            playGoalPostHitSound()
            winningSound = MediaPlayer.create(mContext, R.raw.yay)
        }
        winningSound?.start()
    }

    private fun playLosingSound() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        if (losingSound == null) {
            playGoalPostHitSound()
            losingSound = MediaPlayer.create(mContext, R.raw.aww)
        }
        losingSound?.start()
    }

    private fun playGoalPostHitSound() {
        if (goalPostHitSound == null) {
            goalPostHitSound = MediaPlayer.create(mContext, R.raw.scoring)
        }
        goalPostHitSound?.start()
    }

    private fun playPuckHitSound() {
        if (puckHitSound == null) {
            puckHitSound = MediaPlayer.create(mContext, R.raw.hockey_puck_hit_sound_effect)
        }
        puckHitSound?.start()
    }

    private fun playWallHitSound() {
        if (wallHitSound == null) {
            wallHitSound = MediaPlayer.create(mContext, R.raw.hitting_wall)
        }
        wallHitSound?.start()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!twoPlayerGame!!.sensorsOn()) {
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                    if (twoPlayerGame!!.isBetweenRounds) {
                        twoPlayerGame!!.setState(STATE_RUNNING)
                        playStartGameSound()
                    }

                    val pointerIndex = event.actionIndex
                    val x = event.getX(pointerIndex)
                    val y = event.getY(pointerIndex)

                    if (x < mTableWidth / 2) {
                        // getting the first player's(pointer) first touch when they touch the screen
                        activePointerIdPlayer1 = event.getPointerId(pointerIndex)
                        mLastPLayer1TouchX = x
                        mLastPlayer1TouchY = y
                    }

                    if (x > mTableWidth / 2) {
                        // getting the second player's(pointer) first touch when they touch the screen
                        activePointerIdPlayer2 = event.getPointerId(pointerIndex)
                        mLastPLayer2TouchX = x
                        mLastPlayer2TouchY = y
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    val pointerCount = event.pointerCount
                    for (i in 0 until pointerCount) {
                        val pointerId = event.getPointerId(i)
                        val x = event.getX(i)
                        val y = event.getY(i)

                        if (pointerId == activePointerIdPlayer1) {
                            // Handles movement for player 1
                            val dx = x - mLastPLayer1TouchX
                            val dy = y - mLastPlayer1TouchY
                            mLastPLayer1TouchX = x
                            mLastPlayer1TouchY = y
                            movePaddleStriker1(dx, dy, player1)
                        }

                        if (pointerId == activePointerIdPlayer2) {
                            // Handles movement for player 2
                            val dx = x - mLastPLayer2TouchX
                            val dy = y - mLastPlayer2TouchY
                            mLastPLayer2TouchX = x
                            mLastPlayer2TouchY = y
                            movePaddleStriker2(dx, dy, player2)
                        }
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                    val pointerIndex = event.actionIndex
                    val pointerId = event.getPointerId(pointerIndex)

                    if (pointerId == activePointerIdPlayer1) {
                        activePointerIdPlayer1 = INVALID_POINTER_ID
                    } else if (pointerId == activePointerIdPlayer2) {
                        activePointerIdPlayer2 = INVALID_POINTER_ID
                    }
                }
            }
        } else {
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (twoPlayerGame!!.isBetweenRounds) {
                    twoPlayerGame!!.setState(STATE_RUNNING)
                    playStartGameSound()
                }
            }
        }
        invalidate()
        return true
    }

    private fun movePaddleStriker1(dx: Float, dy: Float, paddle: Paddle?) {
        synchronized(mHolder!!) {

            if (paddle === this.player1) {
                val newLeft = paddle!!.bounds.left + dx
                val newTop = paddle.bounds.top + dy

                // defines the boundary that basically disallows the paddle from crossing
                // the center circle which is 130 units(from the left) from half of the table
                val boundary = mTableWidth / 2 - 150f

                // this will then only move the paddle if the position doesn't(lesser than or equals too) cross the
                // boundary(130 units from the center)
                if (newLeft + paddle.requestWidth <= boundary) {
                    movePaddle(paddle, newLeft, newTop)
                }
            }
        }
    }

    private fun movePaddleStriker2(dx: Float, dy: Float, paddle: Paddle?) {
        synchronized(mHolder!!) {

            if (paddle === this.player2) {
                val newLeft = paddle!!.bounds.left + dx
                val newTop = paddle.bounds.top + dy

                // defines the boundary that basically disallows the paddle from crossing
                // the center circle which is -130 units(from the right) from half of the table
                val boundary = mTableWidth / 2 + 150f  // Adjust the value as needed

                // this will then only move the paddle if the position doesn't(greater then or equals too) cross the
                // boundary(-130 units from the center) so its coming from the right
                if (newLeft >= boundary) {
                    movePaddle(paddle, newLeft, newTop)
                }
            }
        }
    }

    @Synchronized
    fun movePaddle(paddle: Paddle?, left: Float, top: Float) {
        var left = left
        var top = top
        if (left < 2) {
            left = 2f
        } else if (left + paddle!!.requestWidth >= mTableWidth - 2) {
            left = (mTableWidth - paddle.requestWidth - 2).toFloat()
        }
        if (top < 0) {
            top = 0f
        } else if (top + paddle!!.requestHeight >= mTableHeight) {
            top = (mTableHeight - paddle.requestHeight - 1).toFloat()
        }
        paddle!!.bounds.offsetTo(left, top)
    }

    fun setupTable() {
        placePuck()
        placePaddles()
    }

    // resets the paddles(player1/player2) to their starting position
    private fun placePaddles() {
        player1!!.bounds.offsetTo(2f, ((mTableHeight - player1!!.requestHeight) / 2).toFloat())
        player2!!.bounds.offsetTo((mTableWidth - player2!!.requestWidth).toFloat() - 2, ((mTableHeight - player2!!.requestHeight) / 2).toFloat())
    }

    // resets the puck to their initial position
    private fun placePuck() {
        puck!!.centerX = (mTableWidth / 2).toFloat()
        puck!!.centerY = (mTableHeight / 2).toFloat()
        puck!!.velocityY = puck!!.velocityY / abs(puck!!.velocityY) * PUCK_SPEED
        puck!!.velocityX = puck!!.velocityX / abs(puck!!.velocityX) * PUCK_SPEED
    }

    fun resumeGame() {
        if (twoPlayerGame!!.isBetweenRounds) {
            twoPlayerGame!!.setRunning(true)
            twoPlayerGame!!.setState(STATE_RUNNING)
        }
    }

    fun setScorePlayer1(view: TextView?) {
        mScorePlayer1 = view
    }

    fun setScorePlayer2(view: TextView?) {
        mScorePlayer2 = view
    }

    fun setStatus(view: TextView?) {
        mStatus = view
    }

    fun setTableBoundsColor(color: Int) {
        mTableBoundsPaint!!.color = color
    }

    private fun releaseSounds() {
        puckHitSound?.release()
        winningSound?.release()
        losingSound?.release()
    }
}