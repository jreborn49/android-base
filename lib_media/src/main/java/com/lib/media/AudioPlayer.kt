package com.lib.media

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build

class AudioPlayer : MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener,
    MediaPlayer.OnErrorListener, MediaPlayer.OnSeekCompleteListener {

    private val mAudioManager: AudioManager
    private var mMediaPlayer: MediaPlayer? = null
    private var mOnActionListener: OnActionListener? = null

    companion object {
        private val sInstance: AudioPlayer by lazy { AudioPlayer() }

        @SuppressLint("StaticFieldLeak")
        private lateinit var mContext: Context
        fun init(context: Context) {
            this.mContext = context
        }

        fun getInstance() = sInstance
    }

    private constructor() {
        mAudioManager = mContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    //设置数据源
    fun setDataSource(path: String): AudioPlayer {
        synchronized(AudioPlayer::class.java) {
            stop()
            mMediaPlayer = MediaPlayer()
            mMediaPlayer?.setDataSource(path)
        }
        return this
    }

    //开始播放
    @JvmOverloads
    fun start() {
        synchronized(AudioPlayer::class.java) {
            mMediaPlayer?.setOnCompletionListener(this)
            mMediaPlayer?.setOnPreparedListener(this)
            mMediaPlayer?.setOnErrorListener(this)
            mMediaPlayer?.setOnSeekCompleteListener(this)
            mMediaPlayer?.prepareAsync()
        }
    }

    fun restart() {
        synchronized(AudioPlayer::class.java) {
            mMediaPlayer?.start()
        }
    }

    //停止播放
    fun stop() {
        synchronized(AudioPlayer::class.java) {
            if (mMediaPlayer?.isPlaying == true) {
                mMediaPlayer?.pause()
            }
            mMediaPlayer?.stop()
            mMediaPlayer?.reset()
            mMediaPlayer?.release()
            mMediaPlayer = null
        }
    }

    //暂停播放
    fun pause() {
        synchronized(AudioPlayer::class.java) {
            mMediaPlayer?.pause()
        }
    }

    fun seekTo(seekTo: Int) {
        synchronized(AudioPlayer::class.java) {
            mMediaPlayer?.pause()
            mMediaPlayer?.seekTo(seekTo)
        }
    }

    //播放完成
    override fun onCompletion(mp: MediaPlayer) {
        mOnActionListener?.onCompletion()
    }

    //准备好了
    override fun onPrepared(mp: MediaPlayer?) {
        mp?.start()
        mOnActionListener?.onPrepared(mp?.duration ?: -1)
    }

    fun isPlaying(): Boolean {
        return mMediaPlayer?.isPlaying ?: false
    }

    //播放出错了
    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        stop()
        mOnActionListener?.onError(what, extra)
        return true
    }

    fun setOnActionListener(listener: OnActionListener?): AudioPlayer {
        synchronized(AudioPlayer::class.java) {
            mOnActionListener = listener
        }
        return this
    }

    fun getCurrentPosition(): Int {
        synchronized(AudioPlayer::class.java) {
            return mMediaPlayer?.currentPosition ?: 0
        }
    }

    //设置是外音播放还是听筒播放
    fun setAudioStreamType(isEarPhoneModeEnable: Boolean): AudioPlayer {
        synchronized(AudioPlayer::class.java) {
            //Android 26 被毁弃， setAudioAttributes(android.media.AudioAttributes)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val attributes: AudioAttributes = if (isEarPhoneModeEnable) {
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                } else {
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                        .build()
                }
                mMediaPlayer?.setAudioAttributes(attributes)
            } else {
                val streamType: Int = if (isEarPhoneModeEnable) {
                    AudioManager.STREAM_MUSIC
                } else {
                    AudioManager.STREAM_VOICE_CALL
                }
                mMediaPlayer?.setAudioStreamType(streamType)
            }
        }
        return this
    }

    interface OnActionListener {
        //播放完成了
        fun onCompletion()

        //播放失败了
        fun onError(what: Int, extra: Int)

        fun onPrepared(duration: Int)
    }

    override fun onSeekComplete(mp: MediaPlayer?) {
        mMediaPlayer?.start()
    }

}