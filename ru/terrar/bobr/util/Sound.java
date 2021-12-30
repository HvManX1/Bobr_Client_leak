/*
 * Decompiled with CFR 0.150.
 */
package ru.terrar.bobr.util;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound
implements AutoCloseable {
    private boolean released = false;
    private AudioInputStream stream = null;
    private Clip clip = null;
    private FloatControl volumeControl = null;
    private boolean playing = false;

    public Sound(File f) {
        try {
            this.stream = AudioSystem.getAudioInputStream(f);
            this.clip = AudioSystem.getClip();
            this.clip.open(this.stream);
            this.clip.addLineListener(new Listener());
            this.volumeControl = (FloatControl)this.clip.getControl(FloatControl.Type.MASTER_GAIN);
            this.released = true;
        }
        catch (IOException | LineUnavailableException | UnsupportedAudioFileException exc) {
            exc.printStackTrace();
            this.released = false;
            this.close();
        }
    }

    public boolean isReleased() {
        return this.released;
    }

    public boolean isPlaying() {
        return this.playing;
    }

    public void play(boolean breakOld) {
        if (this.released) {
            if (breakOld) {
                this.clip.stop();
                this.clip.setFramePosition(0);
                this.clip.start();
                this.playing = true;
            } else if (!this.isPlaying()) {
                this.clip.setFramePosition(0);
                this.clip.start();
                this.playing = true;
            }
        }
    }

    public void play() {
        this.play(true);
    }

    public void stop() {
        if (this.playing) {
            this.clip.stop();
        }
    }

    @Override
    public void close() {
        if (this.clip != null) {
            this.clip.close();
        }
        if (this.stream != null) {
            try {
                this.stream.close();
            }
            catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }

    public void setVolume(float x) {
        if (x < 0.0f) {
            x = 0.0f;
        }
        if (x > 1.0f) {
            x = 1.0f;
        }
        float min = this.volumeControl.getMinimum();
        float max = this.volumeControl.getMaximum();
        this.volumeControl.setValue((max - min) * x + min);
    }

    public float getVolume() {
        float v = this.volumeControl.getValue();
        float min = this.volumeControl.getMinimum();
        float max = this.volumeControl.getMaximum();
        return (v - min) / (max - min);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void join() {
        if (!this.released) {
            return;
        }
        Clip clip = this.clip;
        synchronized (clip) {
            try {
                while (this.playing) {
                    this.clip.wait();
                }
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
    }

    public static Sound playSound(String path) {
        File f = new File(path);
        Sound snd = new Sound(f);
        snd.play();
        return snd;
    }

    private class Listener
    implements LineListener {
        private Listener() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void update(LineEvent ev) {
            if (ev.getType() == LineEvent.Type.STOP) {
                Sound.this.playing = false;
                Clip clip = Sound.this.clip;
                synchronized (clip) {
                    Sound.this.clip.notify();
                }
            }
        }
    }
}

