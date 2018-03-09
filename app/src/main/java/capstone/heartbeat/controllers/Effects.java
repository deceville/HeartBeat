package capstone.heartbeat.controllers;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.widget.Toast;

import capstone.heartbeat.R;

public class Effects {

    private static final String TAG = Effects.class.toString();

    private static final Effects INSTANCE = new Effects();

    // Sound ID can't be 0 (zero)
    public static final int coin_SOUND = 1;
    public static final int badge_SOUND = 2;
    public static final int heart_SOUND = 3;

    private Effects() {

    }

    public static Effects getInstance() {
        return INSTANCE;
    }

    private SoundPool soundPool;
    private HashMap<Integer, Integer> soundPoolMap;
    int priority = 1;
    int no_loop = 0;
    private int volume;
    float normal_playback_rate = 1f;

    private Context context;

    public void init(Context context) {
        this.context = context;
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
        soundPoolMap = new HashMap<Integer, Integer>();
        soundPoolMap.put(coin_SOUND, soundPool.load(context, R.raw.coins, 1));
        soundPoolMap.put(badge_SOUND, soundPool.load(context, R.raw.badges, 1));
        soundPoolMap.put(heart_SOUND, soundPool.load(context, R.raw.heart, 1));
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        volume = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
    }

    public void playSound(int soundId) {
        Log.i(TAG, "!!!!!!!!!!!!!! playSound_1 !!!!!!!!!!");
        soundPool.play(soundId, volume, volume, priority, no_loop, normal_playback_rate);
    }
}