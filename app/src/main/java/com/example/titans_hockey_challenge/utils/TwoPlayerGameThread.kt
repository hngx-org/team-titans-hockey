package com.example.titans_hockey_challenge.utils

import android.content.Context
import android.graphics.Canvas
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.SurfaceHolder
import android.view.View
import androidx.core.content.ContextCompat
import com.example.titans_hockey_challenge.R
import com.example.titans_hockey_challenge.ui.twoplayertable.TwoPlayerHockeyTable

class TwoPlayerGameThread(
    private val mCtx: Context,
    private val mSurfaceHolder: SurfaceHolder,
    private val mTwoPlayerHockeyTable: TwoPlayerHockeyTable,
    private val mGameStatusHandler: Handler,
    private val mScoreHandler: Handler,

    ) : Thread() {

    private val mSensorsOn = false
    var mRun = false
    private var mGameState = STATE_READY
    private val mRunLock : Any = Any()
    var isPaused = false

    override fun run() {
        var mNextGameTick = SystemClock.uptimeMillis()
        val skipTicks = 1000 / PHYS_FPS

        while (mRun) {
            if (!isPaused) {
                var c: Canvas? = null
                try {
                    c = mSurfaceHolder.lockCanvas(null)
                    if (c != null) {
                        synchronized(mSurfaceHolder) {
                            if (mGameState == STATE_RUNNING) {
                                mTwoPlayerHockeyTable.update(c)
                            }
                            synchronized(mRunLock) {
                                if (mRun) {
                                    mTwoPlayerHockeyTable.draw(c)
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c)
                    }
                }
            }

            mNextGameTick += skipTicks.toLong()
            val sleepTime = mNextGameTick - SystemClock.uptimeMillis()
            if (sleepTime > 0) {
                try {
                    sleep(sleepTime)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun setState(state: Int) {
        synchronized(mSurfaceHolder) {
            mGameState = state
            val res = mCtx.resources
            when (mGameState) {
                STATE_READY -> setUpNewRound()
                STATE_RUNNING -> hideStatusText()
                STATE_WIN -> {
                    setStatusText("Player 1 wins!")
                    mTwoPlayerHockeyTable.player1!!.score++
                    mTwoPlayerHockeyTable.setTableBoundsColor(ContextCompat.getColor(mCtx, R.color.player_color))
                    setUpNewRound()
                }
                STATE_LOSE -> {
                    setStatusText("Player 2 wins!")
                    mTwoPlayerHockeyTable.player2!!.score++
                    mTwoPlayerHockeyTable.setTableBoundsColor(ContextCompat.getColor(mCtx, R.color.opponent_color))
                    setUpNewRound()
                }
            }
        }
    }

    fun setUpNewRound() {
        synchronized(mSurfaceHolder) { mTwoPlayerHockeyTable.setupTable() }
    }

    fun setRunning(running: Boolean) {
        synchronized(mRunLock) { mRun = running }
    }

    fun sensorsOn(): Boolean {
        return mSensorsOn
    }

    fun _pause() {
        isPaused = true
    }

    fun _resume() {
        isPaused = false
    }

    val isBetweenRounds: Boolean
        get() = mGameState != STATE_RUNNING

    private fun setStatusText(text: String) {
        val msg = mGameStatusHandler.obtainMessage()
        val b = Bundle()
        b.putString("text", text)
        b.putInt("visibility", View.VISIBLE)
        msg.data = b
        mGameStatusHandler.sendMessage(msg)
    }

    private fun hideStatusText() {
        val msg = mGameStatusHandler.obtainMessage()
        val b = Bundle()
        b.putInt("visibility", View.INVISIBLE)
        msg.data = b
        mGameStatusHandler.sendMessage(msg)
    }

    fun setScoreText(player1Score: String?, player2Score: String?) {
        val msg = mScoreHandler.obtainMessage()
        val b = Bundle()
        b.putString("player1", player1Score)
        b.putString("player2", player2Score)
        msg.data = b
        mScoreHandler.sendMessage(msg)
    }

}